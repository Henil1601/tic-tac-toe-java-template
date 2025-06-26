package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testCheckWinRow() {
        App.board = new char[] {'X', 'X', 'X', '4', '5', '6', '7', '8', '9'};
        App.currentPlayer = 'X';
        assertTrue(App.checkWin());
    }

    @Test
    void testBoardNotFull() {
        App.board = new char[] {'X', 'O', '3', '4', 'O', '6', 'O', '8', '9'};
        assertFalse(App.isBoardFull());
    }

    @Test
    void testBoardFull() {
        App.board = new char[] {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        assertTrue(App.isBoardFull());
    }
}
