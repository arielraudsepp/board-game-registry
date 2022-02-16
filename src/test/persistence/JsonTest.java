package persistence;

import model.BoardGame;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBoardGame(String name, int[] players, int[] length, ArrayList<String> categories, BoardGame game) {
        assertEquals(name, game.getName());
        assertEquals(players, game.getPlayers());
        assertEquals(length, game.getLength());
        assertEquals(categories, game.getCategories());
    }
}
