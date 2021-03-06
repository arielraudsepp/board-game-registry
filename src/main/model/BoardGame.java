package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a board game with name, minimum and maximum number of players, minimum and maximum
// length (in minutes) and a list of category tags for the game
public class BoardGame implements Writable {
    private final String name;
    private final Integer[] players;
    private final Integer[] length;
    private final ArrayList<String> categories;

    // REQUIRES: name is non-zero length, minPlayers <= maxPlayers, minLength <= maxlength
    // EFFECTS: creates board game with name, min and max players, min and max length, and no categories
    public BoardGame(String name, Integer minPlayers, Integer maxPlayers, Integer minLength, Integer maxLength) {
        this.name = name;
        this.players = new Integer[]{minPlayers, maxPlayers};
        this.length = new Integer[]{minLength, maxLength};
        this.categories = new ArrayList<>();
    }

    // REQUIRES: category is not empty string
    // MODIFIES: this
    // EFFECTS: add category tag to list for board game
    public void addCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    // EFFECTS: returns a list of category tags for a board games
    public ArrayList<String> getCategories() {
        return categories;
    }

    // EFFECTS: returns the name for a board game
    public String getName() {
        return name;
    }

    // EFFECTS: returns the array (min, max) of players for a board game
    public Integer[] getPlayers() {
        return players;
    }

    // EFFECTS: returns the array of length (min, max) for a board game
    public Integer[] getLength() {
        return length;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("players", players);
        json.put("length", length);
        json.put("categories", categories);
        return json;
    }
}
