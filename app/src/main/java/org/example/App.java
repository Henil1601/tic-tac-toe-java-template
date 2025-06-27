package org.example;

import java.util.Scanner;

public class App {
    public static String[] board;
    public static String currentPlayer;
    public static int xWins = 0;
    public static int oWins = 0;
    public static int ties = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean playAgain = true;

        System.out.println("Welcome to Tic-Tac-Toe!");

        while (playAgain) {
            initializeBoard();
            boolean gameOn = true;
            printBoard();

            while (gameOn) {
                System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
                String input = sc.nextLine().trim();

                if (!input.matches("[1-9]")) {
                    System.out.println("That is not a valid move! Try again.");
                    continue;
                }

                int move = Integer.parseInt(input) - 1;

                if (!board[move].equals(String.valueOf(move + 1))) {
                    System.out.println("That is not a valid move! Try again.");
                    continue;
                }

                board[move] = currentPlayer;
                printBoard();

                if (checkWin()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    updateScore(currentPlayer);
                    gameOn = false;
                } else if (isBoardFull()) {
                    System.out.println("It's a tie!");
                    ties++;
                    gameOn = false;
                } else {
                    switchPlayer();
                }
            }

            printScore();

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Would you like to play again (yes/no)? ");
                String again = sc.nextLine().trim().toLowerCase();

                if (again.equals("yes")) {
                    validInput = true;
                    switchPlayer(); // Loser goes first
                } else if (again.equals("no")) {
                    System.out.println("Goodbye!");
                    saveGameLog();
                    playAgain = false;
                    validInput = true;
                } else {
                    System.out.println("That is not a valid entry!");
                }
            }
        }

        sc.close();
    }

    public static void initializeBoard() {
        board = new String[9];
        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    public static void printBoard() {
        System.out.println();
        System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    public static boolean checkWin() {
        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] pattern : winPatterns) {
            if (board[pattern[0]].equals(currentPlayer) &&
                board[pattern[1]].equals(currentPlayer) &&
                board[pattern[2]].equals(currentPlayer)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isBoardFull() {
        for (String cell : board) {
            if (!cell.equals("X") && !cell.equals("O")) {
                return false;
            }
        }
        return true;
    }

    public static void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }

    public static void updateScore(String winner) {
        if (winner.equals("X")) {
            xWins++;
        } else {
            oWins++;
        }
    }

    public static void printScore() {
        System.out.println("The current log is:");
        System.out.println("Player X Wins: " + xWins);
        System.out.println("Player O Wins: " + oWins);
        System.out.println("Ties: " + ties);
        System.out.println();
    }

    public static void saveGameLog() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter("game.txt");
            writer.println("Final Game Log:");
            writer.println("Player X Wins: " + xWins);
            writer.println("Player O Wins: " + oWins);
            writer.println("Ties: " + ties);
            writer.close();
            System.out.println("Game log saved to game.txt");
        } catch (Exception e) {
            System.out.println("Error saving game log.");
        }
    }

    static {
        currentPlayer = "X"; // Default starting player
    }
}
