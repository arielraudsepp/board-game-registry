// Code modified from CPSC210/JSONSerializationDemo

package persistence;

import model.BoardGame;
import model.Collection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        Collection col = new Collection();
        addGames(col, jsonObject);
        return col;
    }

    // MODIFIES: col
    // EFFECTS: parses games from JSON object and adds them to the collection
    private void addGames(Collection col, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(col, nextGame);
        }
    }

    // MODIFIES: col
    // EFFECTS: parses game from JSON object and adds it to the collection
    private void addGame(Collection col, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray players = jsonObject.getJSONArray("players");
        int minPlayers = players.getInt(0);
        int maxPlayers = players.getInt(1);
        JSONArray length = jsonObject.getJSONArray("length");
        int minLength = length.getInt(0);
        int maxLength = length.getInt(1);
        JSONArray categories = jsonObject.getJSONArray("categories");
        ArrayList<String> categoryList = new ArrayList<>();
        if (!categories.isEmpty()) {
            int len = categories.length();
            int index = 0;
            while (index < len) {
                categoryList.add(categories.get(index).toString());
                index++;
            }
        }
        BoardGame boardGame = new BoardGame(name, minPlayers, maxPlayers, minLength, maxLength);
        for (String category : categoryList) {
            boardGame.addCategory(category);
        }
        col.addBoardGame(boardGame);
    }
}
