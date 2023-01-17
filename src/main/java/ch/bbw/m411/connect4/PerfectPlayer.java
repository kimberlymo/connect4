package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class PerfectPlayer extends Connect4ArenaMain.DefaultPlayer {
    private int bestMove = NOMOVE;
    private static final long TIME_LIMIT_MILLIS = 1250; // time limit for iterative deepening
    private static final int WIN_VALUE = 1000; // something less than INFINITY but more than max of evaluate()
    private static final int INFINITY = 100000; // a very high value
    private int startDepth; // the start number of depth before alpha-beta starts for example 1
    private long startTime;

    @Override
    int play() {
        startTime = System.currentTimeMillis();
        int movesAvailable = countMoves();
        // once the limit is exceeded one more run will be done
        for (int distance = 1; distance < movesAvailable && System.currentTimeMillis() - startTime < TIME_LIMIT_MILLIS; distance++) {
            startDepth = distance;
            alphaBeta(myColor, movesAvailable, distance, -INFINITY, INFINITY);
        }
        return bestMove;
    }

    private int alphaBeta(Connect4ArenaMain.Stone player, int freeCount, int depth, int alpha, int beta) {
        if (isWinning(board, player.opponent())) {
            return -WIN_VALUE; // we lost
        }

        if (isWinning(board, player)) {
            return WIN_VALUE; // we won
        }

        if (depth == 0 || freeCount == 0) {
            return evaluate(player); // it is a draw
        }

        int bestScore = alpha;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            // is the current position free and can the stone be placed here
            if (board[i] == null && (i < WIDTH || board[i - WIDTH] != null)) {
                board[i] = player;
                int score = -alphaBeta(player.opponent(), freeCount - 1, depth - 1, -beta, -bestScore);
                board[i] = null;

                // will only go in if-clause, when the algorithm has found the best way
                if (bestScore < score) {
                    bestScore = score;
                    if (depth == startDepth) {
                        System.out.println("position " + i + " has new best value " + score);
                        // will set the best move
                        bestMove = i;
                    }
                    // to break out of loop
                    if (bestScore >= beta) {
                        break;
                    }
                } else if (depth == startDepth) {
                    System.out.println("position " + i + " isn't better with value " + score);
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
        // board with scores so the perfectPlayer knows which positions are prioritised
        int[] scores = new int[]{
                3, 4, 6, 7, 6, 4, 3,
                2, 4, 6, 7, 6, 4, 2,
                2, 4, 6, 7, 6, 4, 2,
                3, 4, 6, 7, 6, 4, 3
        };

        int playerScore = 0;

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (board[i] == curPlayer) {
                playerScore += scores[i];
            } else if (board[i] == curPlayer.opponent()) {
                // will subtract score, because opponent has a stone there
                playerScore -= scores[i];
            }
        }
        return playerScore;
    }
}
