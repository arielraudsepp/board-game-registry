package ui;

import model.BoardGame;
import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BGApp extends JFrame {
    private static final String JSON_FILE = "./data/collection.json";
    private Collection collection;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private ArrayList<Button> buttons;
    private JPanel textArea;
    private JPanel buttonArea;
    private JTextArea displayArea;

    public BGApp() {
        collection = new Collection();
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        buttons = new ArrayList<>();

        setPreferredSize(new Dimension(400, 90));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        this.setTitle("Board Game Collection App");
        this.setBounds(200, 200, 600, 800);
        setLayout(new BorderLayout());
        this.setVisible(true);
        createButtons();
        createField();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all text
    private void createField() {
        textArea = new JPanel();
        textArea.setSize(new Dimension(100, 100));
        add(textArea, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(displayArea);
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all buttons
    private void createButtons() {
        buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0, 2));
        buttonArea.setSize(new Dimension(0, 0));
        add(buttonArea, BorderLayout.SOUTH);

        Button loadButton = new Button(this, buttonArea, "Load");
        buttons.add(loadButton);

        Button displayAllButton = new Button(this, buttonArea, "Display All");
        buttons.add(displayAllButton);
    }



    // Code modified from CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads collection from file
    public void loadCollection() {
        String message = "";
        try {
            collection = jsonReader.read();
            message = "Collection loaded from file";
        } catch (IOException e) {
            message = "Unable to read from file: " + JSON_FILE;
        }
        JOptionPane.showMessageDialog(buttonArea, message);
    }

    // EFFECTS: display list of game details for entire collection
    public void showDetailedGamesList() {
        String message = "";
        if (collection.isEmpty()) {
            message += "Collection is empty!";
        } else {
            for (BoardGame game : collection.getBoardGames()) {
                message += displayGameDetails(game);
            }
        }
        displayArea.setText(message);
    }


    // EFFECTS: displays detailed information about a specified game
    private String displayGameDetails(BoardGame game) {
        String gameDetails = "\n";
        gameDetails += "Name: " + game.getName() + "\n";
        gameDetails += "Number of Players: " + game.getPlayers()[0] + "-" + game.getPlayers()[1] + "\n";
        gameDetails += "Length (minutes): " + game.getLength()[0] + "-" + game.getLength()[1] + "\n";
        gameDetails += "Category Tags: " + game.getCategories() + "\n";
        return gameDetails;
    }

    public void handleAction(Button button) {
        String type = button.getText();
        switch (type) {
            case "Load":
                loadCollection();
                break;
            case "Display All":
                showDetailedGamesList();
                break;
        }
    }



    public static void main(String[] args) {
        new BGApp();
    }
}
