package ui;

import model.BoardGame;
import model.Collection;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CollectionApp {
    private static final String JSON_FILE = "./data/collection.json";
    private Collection collection;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: starts the Collection App
    public CollectionApp() {
        runCollection();
    }

    // REQUIRES: command must be an integer >= 1
    // MODIFIES: this
    // EFFECTS: handles user command for main menu
    private void runCollection() {
        boolean continueRun = true;
        int command;
        int loadCommand = input.nextInt();
        handleSaveLoadCommand(loadCommand, "load");
        while (continueRun) {
            displayMainOptions();
            command = input.nextInt();
            if  (command == 8) {
                displayQuitMenu();
                int quitCommand = input.nextInt();
                handleSaveLoadCommand(quitCommand, "save");
                continueRun = false;
            } else {
                handleMainMenuCommand(command);
            }
        }
        System.out.println("\nBye!");
    }

    // MODIFIES: this
    // EFFECTS: display quit menu options to user
    private void displayQuitMenu() {
        System.out.println("\nWould you like to save your collection to file?");
        System.out.println("1 -> Yes");
        System.out.println("2 -> No");
    }

    // MODIFIES: this
    // EFFECTS: handles user command for quit menu
    private void handleSaveLoadCommand(int command, String action) {
        if (command == 1 && action.equals("save")) {
            saveCollection();
        } else if (command == 1 && action.equals("load")) {
            loadCollection();
        } else if (command == 2) {
            return;
        } else {
            System.out.println("Not valid command: " + command);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles user command for main menu
    private void handleMainMenuCommand(int command) {
        if (command == 1) {
            viewAllGames();
        } else if (command == 2) {
            addGame();
        } else if (command == 3) {
            removeGame();
        } else if (command == 4) {
            addCategory();
        } else if (command == 5) {
            searchGames();
        } else if (command == 6) {
            loadCollection();
        } else if (command == 7) {
            saveCollection();
        } else {
            System.out.println("Not valid command: " + command);
        }
    }

    // EFFECTS: displays main menu options to user
    private void displayMainOptions() {
        System.out.println("\nPlease select from the following options");
        System.out.println("1 -> view games in collection");
        System.out.println("2 -> add game to collection");
        System.out.println("3 -> remove game from collection");
        System.out.println("4 -> add category tags to a game");
        System.out.println("5 -> search for a game");
        System.out.println("6 -> load collection from file");
        System.out.println("7 -> save collection to file");
        System.out.println("8 -> quit");
    }

    // EFFECTS: displays numbered list of games in collection to user, or message if collection is empty
    private void viewAllGames() {
        if (!isEmptyCollection()) {
            printDetailedGamesList();
        }
    }

    // EFFECTS: displays numbered list of games in collection to user, or message if collection is empty
    private void viewNumberedGames() {
        if (!isEmptyCollection()) {
            printNumberedGameNames();
        }
    }

    // REQUIRES: non-empty, unique string for name, min players >= 1,
    // min players <= max players, min length >= 1, min length <= max length
    // MODIFIES: this
    // EFFECTS: creates a new board game with name, min and max number of players,
    // min and max length (in minutes) and provides confirmation
    private void addGame() {
        System.out.println("\nName:");
        String name = input.next();
        System.out.println("Minimum # of Players:");
        int minPlayers = input.nextInt();
        System.out.println("Maximum # of Players:");
        int maxPlayers = input.nextInt();
        System.out.println("Minimum Length (minutes):");
        int minLength = input.nextInt();
        System.out.println("Maximum Length (minutes):");
        int maxLength = input.nextInt();
        BoardGame game = new BoardGame(name, minPlayers, maxPlayers, minLength, maxLength);
        collection.addBoardGame(game);
        System.out.println("\nThe following game has been added to your collection: ");
        printGameDetails(game);
    }

    // REQUIRES: game already exists in collection,
    // inputted command is an integer >= 1
    // MODIFIES: this
    // EFFECTS: removes the specified game from the collection
    private void removeGame() {
        viewNumberedGames();
        if (!collection.getBoardGames().isEmpty()) {
            System.out.println("\nWhich game would you like to remove?");
            int gameNumber = input.nextInt();
            int gameIndex = gameNumber - 1;
            BoardGame game = collection.getBoardGames().get(gameIndex);
            collection.removeBoardGame(game);
            System.out.println("\nThe following game has been removed from your collection: ");
            printGameDetails(game);
        }
    }

    // REQUIRES: game already exists in collection,
    // inputted command is an integer >= 1
    // MODIFIES: this
    // EFFECTS: adds a category tag to the specified game
    private void addCategory() {
        if (!isEmptyCollection()) {
            viewNumberedGames();
            System.out.println("\nWhich game would you like to add a tag for?");
            int gameNumber = input.nextInt();
            int gameIndex = gameNumber - 1;
            BoardGame game = collection.getBoardGames().get(gameIndex);
            printGameDetails(game);
            System.out.println("\nPlease enter the tag you want to add: ");
            String category = input.next();
            game.addCategory(category);
            System.out.println("\nThe category tags for the following game been updated: ");
            printGameDetails(game);
        }
    }

    // REQUIRES: inputted command is an integer >= 1
    // EFFECTS: handles user command for search menu
    private void searchGames() {
        if (!isEmptyCollection()) {
            searchGamesOptions();
            int searchCommand = input.nextInt();
            if (searchCommand == 1) {
                searchByPlayers();
            } else if (searchCommand == 2) {
                searchByLength();
            } else if (searchCommand == 3) {
                searchByCategory();
            } else {
                System.out.println("Not valid command: " + searchCommand);
            }
        }
    }

    // EFFECTS: displays the search  menu options to user
    private void searchGamesOptions() {
        System.out.println("\nPlease select from the following search options: ");
        System.out.println("1 -> search by number of players");
        System.out.println("2 -> search by game length");
        System.out.println("3 -> search by category");
    }

    // REQUIRES: number of players is integer >= 1
    // EFFECTS: displays all games matching user entered number of players
    private void searchByPlayers() {
        System.out.println("\nPlease enter the number of players: ");
        int numPlayers = input.nextInt();
        ArrayList<BoardGame> games = collection.getBoardGamesWithNumPlayers(numPlayers);
        printFilteredGamesList(games);
    }

    // REQUIRES: length is integer >= 1
    // EFFECTS: displays all games matching user entered length of game
    private void searchByLength() {
        System.out.println("\nPlease enter a length (minutes): ");
        int length = input.nextInt();
        ArrayList<BoardGame> games = collection.getBoardGamesWithLength(length);
        printFilteredGamesList(games);
    }

    // EFFECTS: displays all games matching user entered category tag
    private void searchByCategory() {
        System.out.println("\nPlease enter a category tag: ");
        String category = input.next();
        ArrayList<BoardGame> games = collection.getBoardGamesWithCategory(category);
        printFilteredGamesList(games);
    }

    // EFFECTS: displays a numbered list of all game titles in the collection
    private void printNumberedGameNames() {
        System.out.println("\nThese are the games currently in your collection: ");
        int index = 1;
        for (BoardGame game: collection.getBoardGames()) {
            System.out.println(index + " -> " + game.getName());
            index++;
        }
    }

    // EFFECTS: display list of game details for entire collection
    private void printDetailedGamesList() {
        System.out.println("\nThese are the games currently in your collection: ");
        for (BoardGame game : collection.getBoardGames()) {
            printGameDetails(game);
        }
    }

    // EFFECTS: display list of game details, or message if list is empty
    private void printFilteredGamesList(ArrayList<BoardGame> games) {
        if (games.isEmpty()) {
            System.out.println("\nNo games in your collection match search criteria");
        } else {
            System.out.println("\nThe following games match your search criteria: ");
            for (BoardGame game : games) {
                printGameDetails(game);
            }
        }
    }

    // EFFECTS: displays detailed information about a specified game
    private void printGameDetails(BoardGame game) {
        System.out.println("\nName: " + game.getName());
        System.out.println("Number of Players: " + game.getPlayers()[0] + "-" + game.getPlayers()[1]);
        System.out.println("Length (minutes): " + game.getLength()[0] + "-" + game.getLength()[1]);
        System.out.println("Category Tags: " + game.getCategories());
    }

    // EFFECTS: checks if collection is empty
    private boolean isEmptyCollection() {
        return collection.getBoardGames().isEmpty();
    }

    // Code modified from CPSC210/JsonSerializationDemo
    // EFFECTS: saves the collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved collection");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE);
        }
    }

    // Code modified from CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads collection from file
    private void loadCollection() {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded collection");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE);
        }
    }
}
