package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static String[] board = new String[9];
    public static String currentPlayer = "X";
    public static int xWins = 0;
    public static int oWins = 0;
    public static int ties = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        String loser = "";

        while (playAgain) {
            resetBoard();
            currentPlayer = loser.isEmpty() ? "X" : loser;

            boolean gameEnded = false;
            while (!gameEnded) {
                printBoard();
                int move = getPlayerMove(scanner);
                board[move - 1] = currentPlayer;

                if (checkWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    updateScore(currentPlayer);
                    loser = currentPlayer.equals("X") ? "O" : "X";
                    gameEnded = true;
                } else if (isBoardFull()) {
                    printBoard();
                    System.out.println("It's a tie!");
                    ties++;
                    loser = "";
                    gameEnded = true;
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }

            printScore();

            System.out.print("Would you like to play again (yes/no)? ");
            String answer = scanner.nextLine().trim().toLowerCase();
            playAgain = answer.equals("yes");

            if (!playAgain) {
                writeGameLogToFile();
                System.out.println("Writing the game log to disk. Please see game.txt for the final statistics!");
            } else if (!loser.isEmpty()) {
                System.out.println("Great! This time " + loser + " will go first!");
            }
        }

        scanner.close();
    }

    public static void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    public static void printBoard() {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8] + "\n");
    }

    public static int getPlayerMove(Scanner scanner) {
        int move;
        while (true) {
            System.out.print("What is your move? ");
            String input = scanner.nextLine();
            try {
                move = Integer.parseInt(input);
                if (move < 1 || move > 9 || !board[move - 1].equals(String.valueOf(move))) {
                    System.out.println("Invalid move. Try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number between 1 and 9.");
            }
        }
        return move;
    }

    public static boolean checkWin() {
        String[][] winConditions = {
            {board[0], board[1], board[2]},
            {board[3], board[4], board[5]},
            {board[6], board[7], board[8]},
            {board[0], board[3], board[6]},
            {board[1], board[4], board[7]},
            {board[2], board[5], board[8]},
            {board[0], board[4], board[8]},
            {board[2], board[4], board[6]}
        };

        for (String[] line : winConditions) {
            if (line[0].equals(line[1]) && line[1].equals(line[2])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBoardFull() {
        for (String s : board) {
            if (s.matches("[1-9]")) return false;
        }
        return true;
    }

    public static void updateScore(String winner) {
        if (winner.equals("X")) {
            xWins++;
        } else {
            oWins++;
        }
    }

    public static void printScore() {
        System.out.println("\nThe current log is:");
        System.out.println("Player X Wins   " + xWins);
        System.out.println("Player O Wins   " + oWins);
        System.out.println("Ties            " + ties);
    }

    public static void writeGameLogToFile() {
        try {
            FileWriter writer = new FileWriter("game.txt");
            writer.write("Player X Wins   " + xWins + "\n");
            writer.write("Player O Wins   " + oWins + "\n");
            writer.write("Ties            " + ties + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing the log.");
        }
    }
}
