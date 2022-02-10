package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    private Collection testCollection;

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
        BoardGame caverns = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        this.testCollection.removeBoardGame(caverns);
        assertFalse(this.testCollection.getBoardGames().contains(caverns));
    }

    @Test
    void testGetBoardGamesWithCategory() {
        BoardGame cosmic = this.testCollection.getBoardGameByName("Cosmic Encounter");
        cosmic.addCategory("bluffing");
        assertTrue(this.testCollection.getBoardGamesWithCategory("bluffing").contains(cosmic));
        BoardGame caverns = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithCategory("bluffing").contains(caverns));
    }

    @Test
    void testGetBoardGamesWithNumPlayers() {
        BoardGame cosmic = this.testCollection.getBoardGameByName("Cosmic Encounter");
        assertTrue(this.testCollection.getBoardGamesWithNumPlayers(4).contains(cosmic));
        BoardGame caverns = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithNumPlayers(2).contains(caverns));
        assertFalse(this.testCollection.getBoardGamesWithNumPlayers(10).contains(caverns));
    }

    @Test
    void testGetBoardGamesWithLength() {
        BoardGame cosmic = this.testCollection.getBoardGameByName("Cosmic Encounter");
        assertTrue(this.testCollection.getBoardGamesWithLength(90).contains(cosmic));
        BoardGame caverns = this.testCollection.getBoardGameByName("Cutthroat Caverns");
        assertFalse(this.testCollection.getBoardGamesWithLength(30).contains(caverns));
        assertFalse(this.testCollection.getBoardGamesWithLength(240).contains(caverns));
    }
}

