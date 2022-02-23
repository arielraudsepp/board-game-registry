package persistence;

import model.BoardGame;
import model.Collection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// All methods modified from JSONWriterTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Collection col = new Collection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            Collection col = new Collection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(col);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            col = reader.read();
            assertTrue(col.getBoardGames().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCollection() {
        try {
            Collection col = new Collection();
            col.addBoardGame(new BoardGame("Cosmic Encounter", 3, 5, 60, 120));
            col.addBoardGame(new BoardGame("Cutthroat Caverns", 3, 6, 90, 90));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(col);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            col = reader.read();
            ArrayList<BoardGame> games = col.getBoardGames();
            assertEquals(2, games.size());
            assertEquals("Cosmic Encounter", games.get(0).getName());
            assertEquals("Cutthroat Caverns", games.get(1).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
