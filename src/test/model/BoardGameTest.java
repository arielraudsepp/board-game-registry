package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardGameTest {
    private BoardGame testBoardGame;

    public BoardGameTest() {
    }

    @BeforeEach
    void runBefore() {
        this.testBoardGame = new BoardGame("Cosmic Encounter", 3, 5, 60, 120);
    }

    @Test
    void testConstructor() {
        Assertions.assertEquals("Cosmic Encounter", this.testBoardGame.getName());
        Assertions.assertEquals(3, this.testBoardGame.getPlayers()[0]);
        Assertions.assertEquals(5, this.testBoardGame.getPlayers()[1]);
        Assertions.assertEquals(60, this.testBoardGame.getLength()[0]);
        Assertions.assertEquals(120, this.testBoardGame.getLength()[1]);
        Assertions.assertEquals(0, this.testBoardGame.getCategories().size());
    }

    @Test
    void testAddCategories() {
        this.testBoardGame.addCategory("bluffing");
        Assertions.assertTrue(this.testBoardGame.getCategories().contains("bluffing"));
        Assertions.assertEquals(1, this.testBoardGame.getCategories().size());
        this.testBoardGame.addCategory("negotiation");
        Assertions.assertTrue(this.testBoardGame.getCategories().contains("negotiation"));
        Assertions.assertEquals(2, this.testBoardGame.getCategories().size());
    }
}
