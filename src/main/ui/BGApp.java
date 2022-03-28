package ui;

import model.BoardGame;
import model.Collection;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class BGApp extends JFrame {
    private static final String JSON_FILE = "./data/collection.json";
    private Collection collection;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JTabbedPane mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JPanel listPanel;
    private GameList listArea;
    private JPanel filterPanel;
    private JPanel textPanel;


    public BGApp() {
        collection = new Collection();
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        mainPanel = new JTabbedPane();

        initFrame();
        createDisplayPanel();
        createAddGamePanel();
        createFilterPanel();
        createListPanel();
        createButtonPanel();


        setMinimumSize(new Dimension(500, 550));
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(mainPanel);
        initPanels();
        pack();

    }

    // MODIFIES: this
    // EFFECTS: initializes frame
    public void initFrame() {
        this.setTitle("Board Game Collection App");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                    Event e = it.next();
                    System.out.println(e);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: configures main panel and adds components
    private void initPanels() {
        mainPanel.addTab("Load/Save", buttonPanel);
        mainPanel.addTab("Add Game", formPanel);
        mainPanel.addTab("Filter", filterPanel);
        mainPanel.addTab("All Games", listPanel);
    }

    // MODIFIES: this
    // EFFECTS:  creates a text panel to display game details
    private JPanel createDisplayPanel() {
        textPanel = new JPanel();
        DisplayPanel displayArea = new DisplayPanel(this);
        textPanel.add(displayArea);
        return textPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a new filter panel
    private JPanel createFilterPanel() {
        filterPanel = new JPanel();
        FilterPanel filterArea = new FilterPanel(this);
        filterPanel.add(filterArea);
        return filterPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a new games list panel
    private JPanel createListPanel() {
        listPanel = new JPanel(new GridLayout(2, 1));
        listArea = new GameList(this);
        listPanel.add(listArea);
        return listPanel;
    }

    // MODIFIES: this
    // EFFECTS:  creates add game panel
    private JPanel createAddGamePanel() {
        formPanel = new JPanel();
        AddGamePanel form = new AddGamePanel(this, formPanel);
        formPanel.add(form);
        return formPanel;
    }


    // MODIFIES: this
    // EFFECTS: creates panel of 'load' and 'save' buttons
    private JPanel createButtonPanel() {
        buttonPanel = new JPanel(new BorderLayout());
        JPanel image = new ImagePanel();
        image.setSize(new Dimension(200, 200));
        buttonPanel.add(image, BorderLayout.CENTER);

        JPanel buttonArea = new JPanel();
        new Button(this, buttonArea, "Load");
        new Button(this, buttonArea, "Save");
        buttonPanel.add(buttonArea, BorderLayout.PAGE_END);
        return buttonPanel;
    }

    // Code modified from CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads collection from file
    public void loadCollection() {
        try {
            collection = jsonReader.read();
        } catch (IOException e) {
            String message = "Unable to read from file: " + JSON_FILE;
            JOptionPane dialog = new JOptionPane();
            dialog.showMessageDialog(buttonPanel, message);
        }
    }

    // Code modified from CPSC210/JsonSerializationDemo
    // EFFECTS: saves the collection to file
    private void saveCollection() {
        String message = "";
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            message = "Saved collection to file";
        } catch (FileNotFoundException e) {
            message = "Unable to write to file: " + JSON_FILE;
        }
        JOptionPane dialog = new JOptionPane();
        dialog.showMessageDialog(buttonPanel, message);
    }

    // MODIFIES: this
    // EFFECTS: removes game at specified index in collection
    public void removeGame(int index) {
        BoardGame game = collection.getBoardGames().get(index);
        collection.removeBoardGame(game);
    }

    // MODIFIES: this
    // EFFECTS: adds tag to game at specified index in collection
    public void addCategoryTag(int index, String tag) {
        BoardGame game = collection.getBoardGames().get(index);
        game.addCategory(tag);
    }

    // EFFECTS: returns board game at specified index in collection
    public BoardGame getGame(String name) {
        return collection.getBoardGameByName(name);
    }

    // EFFECTS: produces list of all game names
    public ArrayList<String> getGameNames() {
        ArrayList<String> gameNames = new ArrayList<>();
        for (BoardGame game: collection.getBoardGames()) {
            gameNames.add(game.getName());
        }
        return gameNames;
    }

    // REQUIRES: number of players is integer >= 1
    // EFFECTS: displays all games matching user entered number of players
    public ArrayList<String> searchByPlayers(Integer num) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<BoardGame> games = collection.getBoardGamesWithNumPlayers(num);
        for (BoardGame game : games) {
            names.add(game.getName());
        }
        return names;
    }

    // EFFECTS: returns names of games matching user entered length of game
    public ArrayList<String> searchByLength(Integer num) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<BoardGame> games = collection.getBoardGamesWithLength(num);
        for (BoardGame game: games) {
            names.add(game.getName());
        }
        return names;
    }


    // EFFECTS: returns names of games matching user entered category tag
    public ArrayList<String> searchByCategory(String category) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<BoardGame> games = collection.getBoardGamesWithCategory(category);
        for (BoardGame game : games) {
            names.add(game.getName());
        }
        return names;
    }

    // MODIFIES: this
    // EFFECTS: adds specified board game to collection
    public void addGame(BoardGame game) {
        collection.addBoardGame(game);
    }

    // MODIFIES: this
    // EFFECTS: updates game list and filter list
    public void updateLists() {
        listArea.updateList();
    }

    // EFFECTS: returns true if game with name exists in collection
    public boolean gameAlreadyAdded(String name) {
        return collection.getBoardGameByName(name) != null;
    }

    // MODIFIES: this
    // EFFECTS: does specified action based on the function of the button
    public void handleAction(Button button) {
        String type = button.getText();
        switch (type) {
            case "Load":
                loadCollection();
                updateLists();
                break;
            case "Save":
                saveCollection();
        }
    }

    // EFFECTS: creates and runs new BGApp
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BGApp();
            }
        });
    }


}
