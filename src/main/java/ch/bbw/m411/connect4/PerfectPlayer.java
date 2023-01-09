package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class PerfectPlayer extends Connect4ArenaMain.DefaultPlayer {
    private int bestMove = NOMOVE;
    private static final int WIN_VALUE = 1000; // something less than INFINITY but more than max of evaluate()
    private static final int INFINITY = 100000; // not MIN_VALUE as Integer.MIN_VALUE != -Integer.MAX_VALUE
    private final int maxDepth;
    private int minDepth;

    public PerfectPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    int play() {
        int movesAvailable = countMoves();
        minDepth = Math.min(movesAvailable, maxDepth);
        alphaBeta(myColor, movesAvailable, minDepth, -INFINITY, INFINITY);
        return bestMove;
    }

    private int alphaBeta(Connect4ArenaMain.Stone player, int freeCount, int depth, int alpha, int beta) {
        if (isWinning(board, player.opponent())) {
            return -WIN_VALUE; // we lost
        }

        if (isWinning(board, player)) {
            // This check isn't really needed, since the last player to play was the opponent
            // however we should check for a draw here
            return WIN_VALUE;
        }

        if (depth == 0) {
            return evaluate(player);
        }

        int bestScore = alpha;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (board[i] == null && (i < WIDTH || board[i - WIDTH] != null)) {
                board[i] = player;
                int wert = -alphaBeta(player.opponent(), freeCount - 1, depth - 1, -beta, -bestScore);
                board[i] = null;

                if (bestScore < wert) {
                    bestScore = wert;
                    if (depth == minDepth) {
                        System.out.println("position " + i + " has new best value " + wert);
                        bestMove = i;
                    }
                    if (bestScore >= beta) {
                        break;
                    }
                }
                else if (depth == minDepth) {
                    System.out.println("position " + i + " isn't better with value " + wert);
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
