package ui;

import model.BoardGame;
import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BGApp extends JFrame {
    private static final String JSON_FILE = "./data/collection.json";
    private Collection collection;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private ArrayList<Button> buttons;
    private JSplitPane mainPanel;
    private JSplitPane leftPanel;
    private JSplitPane rightPanel;
    private AddForm formArea;
    private JPanel buttonArea;
    private GameList listPanel;
    private JTextArea displayArea;

    public BGApp() {
        collection = new Collection();
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        buttons = new ArrayList<>();

        mainPanel = new JSplitPane();
        leftPanel = new JSplitPane();
        rightPanel = new JSplitPane();

        this.setTitle("Board Game Collection App");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 550));
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(mainPanel);
        initPanels();
        pack();

    }

    // MODIFIES: this
    // EFFECTS: configures main, left and right panels and adds components
    private void initPanels() {
        mainPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        mainPanel.setLeftComponent(leftPanel);
        mainPanel.setRightComponent(rightPanel);
        mainPanel.setDividerLocation(300);

        leftPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        leftPanel.setDividerLocation(400);
        leftPanel.setTopComponent(createFormArea());
        leftPanel.setBottomComponent(createButtons());

        rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        rightPanel.setDividerLocation(300);
        rightPanel.setTopComponent(createListArea());
        rightPanel.setBottomComponent(createTextArea());
    }

    // MODIFIES: this
    // EFFECTS:  creates a text panel to display game details
    private JPanel createTextArea() {
        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Selected Game Details: "));
        displayArea = new JTextArea(7, 20);
        displayArea.setEditable(false);
        textPanel.add(displayArea);
        return textPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a new games list panel
    private JPanel createListArea() {
        listPanel = new GameList(this);
        return listPanel;
    }

    // MODIFIES: this
    // EFFECTS:  creates form panel with add game form and 'add' button
    private JPanel createFormArea() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formArea = new AddForm(formPanel);

        new Button(this, formPanel, "Add");
        return formPanel;
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which creates panel of 'load' and 'save' buttons
    private JPanel createButtons() {
        buttonArea = new JPanel();
        buttonArea.setSize(new Dimension(0, 0));

        Button loadButton = new Button(this, buttonArea, "Load");
        buttons.add(loadButton);

        Button saveButton = new Button(this, buttonArea, "Save");
        buttons.add(saveButton);

        return buttonArea;
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
            dialog.showMessageDialog(buttonArea, message);
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
        dialog.showMessageDialog(buttonArea, message);
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

    // MODIFIES: this
    // EFFECTS: displays game details for a game at specified index in collection
    public void showGameDetails(int index) {
        BoardGame game = collection.getBoardGames().get(index);
        String message = displayGameDetails(game);
        displayArea.setText(message);
    }

    // EFFECTS: returns string of information about a specified game
    private String displayGameDetails(BoardGame game) {
        String gameDetails = "\n";
        gameDetails += "Name: " + game.getName() + "\n";
        gameDetails += "Number of Players: " + game.getPlayers()[0] + "-" + game.getPlayers()[1] + "\n";
        gameDetails += "Length (minutes): " + game.getLength()[0] + "-" + game.getLength()[1] + "\n";
        if (game.getCategories().isEmpty()) {
            gameDetails += "Category Tags: None" + "\n";
        } else {
            gameDetails += "Category Tags: " + game.getCategories() + "\n";
        }
        return gameDetails;
    }

    // EFFECTS: produces list of all game names
    public ArrayList<String> getGameNames() {
        ArrayList<String> gameNames = new ArrayList<>();
        for (BoardGame game: collection.getBoardGames()) {
            gameNames.add(game.getName());
        }
        return gameNames;
    }

    // MODIFIES: this
    // EFFECTS: adds new game created from form field to collection and
    // notifies user if empty string or non-integers have been entered in players and length fields
    public void addNewGame() {
        try {
            BoardGame game = formArea.createNewGame();
            collection.addBoardGame(game);
        } catch (NumberFormatException e) {
            String message = "Please only enter numbers in 'players' and 'length' fields";
            JOptionPane dialog = new JOptionPane();
            dialog.showMessageDialog(buttonArea, message);
        }
    }

    // MODIFIES: this
    // EFFECTS: does specified action based on the function of the button
    public void handleAction(Button button) {
        String type = button.getText();
        switch (type) {
            case "Add":
                addNewGame();
                listPanel.updateList();
                break;
            case "Load":
                loadCollection();
                listPanel.updateList();
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
