package org.example;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    static char[] board;
    static char currentPlayer = 'X';
    static int xWins = 0;
    static int oWins = 0;
    static int ties = 0;
    static char lastLoser = 'O'; // so X goes first in the first game

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");

        boolean playAgain = true;
        while (playAgain) {
            resetBoard();
            currentPlayer = lastLoser; // loser starts next round
            boolean gameOn = true;

            while (gameOn) {
                printBoard();
                System.out.print("Player " + currentPlayer + ", enter your move (1–9): ");
                String input = sc.nextLine();

                if (!input.matches("[1-9]")) {
                    System.out.println("Invalid input. Try a number between 1 and 9.");
                    continue;
                }

                int move = Integer.parseInt(input) - 1;
                if (board[move] == 'X' || board[move] == 'O') {
                    System.out.println("Spot already taken. Try again.");
                    continue;
                }

                board[move] = currentPlayer;

                if (checkWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    updateScore(currentPlayer);
                    lastLoser = (currentPlayer == 'X') ? 'O' : 'X';
                    gameOn = false;
                } else if (isBoardFull()) {
                    printBoard();
                    System.out.println("It's a tie!");
                    ties++;
                    gameOn = false;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }

            printScore();
            System.out.print("\nWould you like to play again (yes/no)? ");
            String again = sc.nextLine().trim().toLowerCase();
            playAgain = again.startsWith("y");
            if (playAgain) {
                System.out.println("\nGreat! This time " + lastLoser + " will go first!\n");
            }
        }

        writeLogToDisk();
        System.out.println("Writing the game log to disk. Please see game.txt for the final statistics!");
        sc.close();
    }

    static void resetBoard() {
        board = new char[]{'1','2','3','4','5','6','7','8','9'};
    }

    static void printBoard() {
        System.out.println();
        System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    static boolean checkWin() {
        int[][] winPatterns = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // cols
            {0,4,8}, {2,4,6}           // diagonals
        };

        for (int[] pattern : winPatterns) {
            if (board[pattern[0]] == currentPlayer &&
                board[pattern[1]] == currentPlayer &&
                board[pattern[2]] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    static boolean isBoardFull() {
        for (char c : board) {
            if (c != 'X' && c != 'O') {
                return false;
            }
        }
        return true;
    }

    static void updateScore(char winner) {
        if (winner == 'X') {
            xWins++;
        } else {
            oWins++;
        }
    }

    static void printScore() {
        System.out.println("\nThe current log is:");
        System.out.println("Player X Wins   " + xWins);
        System.out.println("Player O Wins   " + oWins);
        System.out.println("Ties            " + ties);
    }

    static void writeLogToDisk() {
        try {
            FileWriter writer = new FileWriter("game.txt");
            writer.write("Tic-Tac-Toe Game Log\n\n");
            writer.write("Player X Wins: " + xWins + "\n");
            writer.write("Player O Wins: " + oWins + "\n");
            writer.write("Ties: " + ties + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }
}
