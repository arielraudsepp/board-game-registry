package ui;

import model.BoardGame;
import model.Collection;

import java.util.ArrayList;
import java.util.Scanner;

public class CollectionApp {
    private Collection collection;
    private Scanner input;

    public CollectionApp() {
        runCollection();
    }

    private void runCollection() {
        boolean continueRun = true;
        int command;

        init();

        while (continueRun) {
            displayOptions();
            command = input.nextInt();
            if  (command == 6) {
                continueRun = false;
            } else {
                handleCommand(command);
            }
        }
        System.out.println("\nBye!");
    }

    // MODIFIES: this
    // EFFECTS: handles user command
    private void handleCommand(int command) {
        if (command == 1) {
            viewGames();
        } else if (command == 2) {
            addGame();
        } else if (command == 3) {
            removeGame();
        } else if (command == 4) {
            addCategory();
        } else if (command == 5) {
            searchGames();
        } else {
            System.out.println("Not valid command: " + command);
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes collection
    private void init() {
        collection = new Collection();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("\nWelcome to your Board Game Registry!");
    }

    // EFFECTS: displays options to user
    private void displayOptions() {
        System.out.println("\nPlease select from the following options");
        System.out.println("1 -> view games in collection");
        System.out.println("2 -> add game to collection");
        System.out.println("3 -> remove game from collection");
        System.out.println("4 -> add category tags to a game");
        System.out.println("5 -> search for a game");
        System.out.println("6 -> quit");
    }

    private void viewGames() {
        if (collection.getBoardGames().isEmpty()) {
            System.out.println("\nYou have no games in your collection!");
        } else {
            printGameNames();
        }
    }

    private void addGame() {
        System.out.println("Name:");
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

    private void removeGame() {
        viewGames();
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

    private void addCategory() {
        printGameNames();
        System.out.println("\nWhich game would you like to add a tag for?");
        int gameNumber = input.nextInt();
        int gameIndex = gameNumber - 1;
        BoardGame game = collection.getBoardGames().get(gameIndex);
        printGameDetails(game);
        System.out.println("\nPlease enter the tag you want to add: ");
        String category = input.next();
        game.addCategory(category);
        printGameDetails(game);
    }

    private void searchGamesOptions() {
        System.out.println("\nPlease select from the following search options");
        System.out.println("1 -> search by number of players");
        System.out.println("2 -> search by game length");
        System.out.println("3 -> search by category");
    }

    private void searchGames() {
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

    private void printGameNames() {
        System.out.println("\nThese are the games currently in your collection: ");
        int index = 1;
        for (BoardGame game: collection.getBoardGames()) {
            System.out.println(index + " -> " + game.getName());
            index++;
        }
    }

    private void printGameDetails(BoardGame game) {
        System.out.println("\nName: " + game.getName());
        System.out.println("Number of Players: " + game.getPlayers()[0] + "-" + game.getPlayers()[1]);
        System.out.println("Length (minutes): " + game.getLength()[0] + "-" + game.getLength()[1]);
        System.out.println("Category Tags: " + game.getCategories());
    }

    private void searchByPlayers() {
        System.out.println("How many players?");
        int numPlayers = input.nextInt();
        ArrayList<BoardGame> games = collection.getBoardGamesWithNumPlayers(numPlayers);
        for (BoardGame game : games) {
            printGameDetails(game);
        }
    }

    private void searchByLength() {
        System.out.println("How long?");
        int length = input.nextInt();
        ArrayList<BoardGame> games = collection.getBoardGamesWithLength(length);
        for (BoardGame game : games) {
            printGameDetails(game);
        }
    }

    private void searchByCategory() {
        System.out.println("Which category would you like to search for?");
        String category = input.next();
        ArrayList<BoardGame> games = collection.getBoardGamesWithCategory(category);
        for (BoardGame game : games) {
            printGameDetails(game);
        }
    }
}
