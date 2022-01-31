package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Collection {
    private final ArrayList<BoardGame> boardGames = new ArrayList();

    public void addBoardGame(BoardGame game) {
        this.boardGames.add(game);
    }

    public void removeBoardGame(BoardGame game) {
        this.boardGames.remove(game);
    }

    public ArrayList<BoardGame> getBoardGamesWithCategory(String category) {
        ArrayList gamesWithCategory = new ArrayList<>();
        Iterator iter = this.boardGames.iterator();

        while (iter.hasNext()) {
            BoardGame game = (BoardGame) iter.next();
            if (game.getCategories().contains(category)) {
                gamesWithCategory.add(game);
            }
        }
        return gamesWithCategory;
    }

    public ArrayList<BoardGame> getBoardGamesWithNumPlayers(int number) {
        ArrayList gamesWithNumPlayers = new ArrayList<>();
        Iterator iter = this.boardGames.iterator();

        while (iter.hasNext()) {
            BoardGame game = (BoardGame) iter.next();
            if (game.getPlayers()[0] <= number && number <= game.getPlayers()[1]) {
                gamesWithNumPlayers.add(game);
            }
        }
        return gamesWithNumPlayers;
    }

    public ArrayList<BoardGame> getBoardGamesWithLength(int number) {
        ArrayList gamesWithLength = new ArrayList<>();
        Iterator iter = this.boardGames.iterator();

        while (iter.hasNext()) {
            BoardGame game = (BoardGame) iter.next();
            if (game.getLength()[0] <= number && number <= game.getLength()[1]) {
                gamesWithLength.add(game);
            }
        }
        return gamesWithLength;
    }

    public ArrayList<BoardGame> getBoardGames() {
        return this.boardGames;
    }

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

