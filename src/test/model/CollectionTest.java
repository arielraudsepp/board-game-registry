package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    private Collection testCollection;
    private BoardGame game;

    public CollectionTest() {
    }

    @BeforeEach
    void buildCollection() {
        this.testCollection = new Collection();
        BoardGame cutthroat = new BoardGame("Cutthroat Caverns", 3, 6, 90, 90);
        this.testCollection.addBoardGame(cutthroat);
        BoardGame cosmic = new BoardGame("Cosmic Encounter", 3, 5, 60, 120);
        this.testCollection.addBoardGame(cosmic);
    }

    @Test
    void testCollection() {
        assertEquals("Cutthroat Caverns", this.testCollection.getBoardGames().get(0).getName());
    }

    @Test
    void testRemoveBoardGame() {
        this.game = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        this.testCollection.removeBoardGame(this.game);
        assertFalse(this.testCollection.getBoardGames().contains(this.game));
    }

    @Test
    void testGetBoardGamesWithCategory() {
        this.game = this.testCollection.getBoardGameByName("Cosmic Encounter");
        this.game.addCategory("bluffing");
        assertTrue(this.testCollection.getBoardGamesWithCategory("bluffing").contains(this.game));
        this.game = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithCategory("bluffing").contains(this.game));
    }

    @Test
    void testGetBoardGamesWithNumPlayers() {
        this.game = this.testCollection.getBoardGameByName("Cosmic Encounter");
        assertTrue(this.testCollection.getBoardGamesWithNumPlayers(4).contains(this.game));
        this.game = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithNumPlayers(2).contains(this.game));
    }

    @Test
    void testGetBoardGamesWithLength() {
        this.game = this.testCollection.getBoardGameByName("Cosmic Encounter");
        assertTrue(this.testCollection.getBoardGamesWithLength(90).contains(this.game));
        this.game = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithLength(30).contains(this.game));
    }
}

