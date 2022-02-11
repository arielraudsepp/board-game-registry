package model;

import java.util.ArrayList;

// Represents a board game with name, minimum and maximum number of players, minimum and maximum
// length (in minutes) and a list of category tags for the game
public class BoardGame {
    private final String name;
    private final int[] players;
    private final int[] length;
    private final ArrayList<String> categories;

    // REQUIRES: name is non-zero length, minPlayers <= maxPlayers, minLength <= maxlength
    // EFFECTS: creates board game with name, min and max players, min and max length, and no categories
    public BoardGame(String name, int minPlayers, int maxPlayers, int minLength, int maxLength) {
        this.name = name;
        this.players = new int[]{minPlayers, maxPlayers};
        this.length = new int[]{minLength, maxLength};
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
    public int[] getPlayers() {
        return players;
    }

    // EFFECTS: returns the array of length (min, max) for a board game
    public int[] getLength() {
        return length;
    }
}
