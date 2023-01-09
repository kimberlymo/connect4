package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class PerfectPlayer extends Connect4ArenaMain.DefaultPlayer {
    private int bestMove = NOMOVE;
    private final int maxDepth;
    private int minDepth;

    public PerfectPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    int play() {
        int movesAvailable = countMoves();
        minDepth = Math.min(movesAvailable, maxDepth);
        alphaBeta(myColor, movesAvailable, minDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return bestMove;
    }

    private int alphaBeta(Connect4ArenaMain.Stone player, int freeCount, int depth, int alpha, int beta) {
        if (isWinning(board, player.opponent())) {
            return Integer.MIN_VALUE; // we lost
        }

        if (isWinning(board, player)) {
            return Integer.MAX_VALUE; // we won
        }

        if (depth == 0) {
            return evaluate(player);
        }

        int bestScore = alpha;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (board[i] == null) {
                board[i] = player;
                int wert = -alphaBeta(player.opponent(), freeCount - 1, depth - 1, -beta, -bestScore);
                board[i] = null;

                if (bestScore < wert) {
                    bestScore = wert;
                    if (depth == minDepth) {
                        bestMove = i;
                    }
                    if (bestScore >= beta) {
                        break;
                    }
                }
            }
        }
        return bestScore;
    }

    private int countMoves() {
        int moves = 0;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (board[i] == null) {
                moves++;
            }
        }
        return moves;
    }

    private int evaluate(Connect4ArenaMain.Stone curPlayer) {
        var scores = new int[]{
                3, 4, 6, 7, 6, 4, 3,
                2, 4, 6, 7, 6, 4, 2,
                2, 4, 6, 7, 6, 4, 2,
                3, 4, 6, 7, 6, 4, 3
        };

        var playerScore = 0;

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (board[i] == curPlayer) {
                playerScore += scores[i];
            }
        }
        return playerScore;
    }
}
