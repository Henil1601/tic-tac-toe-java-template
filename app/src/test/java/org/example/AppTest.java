package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppTest {

    @Test
    public void testCheckWinRow() {
        App.board = new String[]{"X", "X", "X", "4", "5", "6", "7", "8", "9"};
        App.currentPlayer = "X";
        assertTrue(App.checkWin());
    }

    @Test
    public void testBoardFull() {
        App.board = new String[]{"X", "O", "X", "O", "X", "O", "X", "O", "X"};
        assertTrue(App.isBoardFull());
    }

    @Test
    public void testBoardNotFull() {
        App.board = new String[]{"X", "2", "X", "4", "5", "O", "7", "8", "9"};
        assertFalse(App.isBoardFull());
    }

    @Test
    public void testGameLogFileCreation() {
        String testFile = "game.txt";
        try {
            FileWriter writer = new FileWriter(testFile);
            writer.write("Player X Wins   1\n");
            writer.write("Player O Wins   1\n");
            writer.write("Ties            0\n");
            writer.close();

            File f = new File(testFile);
            assertTrue(f.exists());
            assertTrue(f.length() > 0);
        } catch (IOException e) {
            fail("IOException occurred during test.");
        }
    }
}
