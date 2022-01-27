package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGameTest {
    private BoardGame testBoardGame;

    @BeforeEach
    void runBefore() {
        testBoardGame = new BoardGame("Cosmic Encounter",3, 5, 60, 120);
    }

    @Test
    void testConstructor() {
        assertEquals("Cosmic Encounter", testBoardGame.getName());
        assertEquals(3, testBoardGame.getPlayers()[0]);
        assertEquals(5, testBoardGame.getPlayers()[1]);
        assertEquals(60, testBoardGame.getLength()[0]);
        assertEquals(120, testBoardGame.getLength()[1]);
        assertEquals(0, testBoardGame.getCategories().size());
    }

    @Test
    void testAddCategories() {
        testBoardGame.addCategory("bluffing");
        assertTrue(testBoardGame.getCategories().contains("bluffing"));
        assertEquals(1, testBoardGame.getCategories().size());
        testBoardGame.addCategory("negotiation");
        assertTrue(testBoardGame.getCategories().contains("negotiation"));
        assertEquals(2, testBoardGame.getCategories().size());
    }
}
