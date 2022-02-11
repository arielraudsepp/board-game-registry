package model;

import java.util.ArrayList;

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

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public int[] getPlayers() {
        return players;
    }

    public int[] getLength() {
        return length;
    }
}
