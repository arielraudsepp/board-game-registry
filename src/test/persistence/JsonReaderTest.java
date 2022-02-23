package persistence;

import model.Collection;
import model.BoardGame;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// All methods modified from JSONReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Collection col = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            Collection col = reader.read();
            assertTrue(col.getBoardGames().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            Collection col = reader.read();
            ArrayList<BoardGame> games = col.getBoardGames();
            assertEquals(2, games.size());
            assertEquals("Cosmic Encounter", games.get(0).getName());
            assertEquals("Cutthroat Caverns", games.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
