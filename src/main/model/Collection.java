package model;

import java.util.ArrayList;

// Represents a collection of board games
public class Collection {

    // EFFECTS: creates a new, empty collection of board games
    private ArrayList<BoardGame> boardGames = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: adds a board game to the collection
    public void addBoardGame(BoardGame game) {
        this.boardGames.add(game);
    }

    // MODIFIES: this
    // EFFECTS: removes a board game to the collection
    public void removeBoardGame(BoardGame game) {
        this.boardGames.remove(game);
    }

    // EFFECTS: produces list of board games with given category tag
    public ArrayList<BoardGame> getBoardGamesWithCategory(String category) {
        ArrayList<BoardGame> gamesWithCategory = new ArrayList<>();

        for (BoardGame game : this.boardGames) {
            if (game.getCategories().contains(category)) {
                gamesWithCategory.add(game);
            }
        }
        return gamesWithCategory;
    }

    // REQUIRES: number is integer is >= 1
    // EFFECTS: produces list of board games with given number of players
    public ArrayList<BoardGame> getBoardGamesWithNumPlayers(int number) {
        ArrayList<BoardGame> gamesWithNumPlayers = new ArrayList<>();

        for (BoardGame game : this.boardGames) {
            if (game.getPlayers()[0] <= number && number <= game.getPlayers()[1]) {
                gamesWithNumPlayers.add(game);
            }
        }
        return gamesWithNumPlayers;
    }

    // REQUIRES: number is integer >= 1
    // EFFECTS: produces list of board games with given length (minutes)
    public ArrayList<BoardGame> getBoardGamesWithLength(int number) {
        ArrayList<BoardGame> gamesWithLength = new ArrayList<>();

        for (BoardGame game : this.boardGames) {
            if (game.getLength()[0] <= number && number <= game.getLength()[1]) {
                gamesWithLength.add(game);
            }
        }
        return gamesWithLength;
    }

    // EFFECTS: gets all board games in the collection
    public ArrayList<BoardGame> getBoardGames() {
        return this.boardGames;
    }

    // REQUIRES: name is a non-empty string
    // EFFECTS: gets board game by name
    public BoardGame getBoardGameByName(String name) {
        BoardGame game = null;
        for (BoardGame b : boardGames) {
            if (b.getName().equals(name)) {
                game = b;
            }
        }
        return game;
    }
}

