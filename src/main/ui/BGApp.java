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
    private ArrayList<AddForm> formFields;
    private JSplitPane mainPanel;
    private JSplitPane leftPanel;
    private JPanel rightPanel;
    private AddForm formArea;
    private JPanel addArea;
    private JPanel buttonArea;
    private JTextArea displayArea;

    public BGApp() {
        collection = new Collection();
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        buttons = new ArrayList<>();
        formFields = new ArrayList<>();

        mainPanel = new JSplitPane();
        leftPanel = new JSplitPane();
        rightPanel = new JPanel();

        this.setTitle("Board Game Collection App");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(mainPanel);
        initPanels();
        pack();

    }

    private void initPanels() {
        mainPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        mainPanel.setLeftComponent(leftPanel);
        mainPanel.setRightComponent(rightPanel);
        mainPanel.setDividerLocation(300);

        leftPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        leftPanel.setDividerLocation(400);
        leftPanel.setTopComponent(createFormArea());
        leftPanel.setBottomComponent(createButtons());

        rightPanel.add(createTextArea());
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all text
    private JPanel createTextArea() {
        JPanel textPanel = new JPanel();
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 18));
        displayArea.setEditable(false);
        textPanel.add(displayArea);
        return textPanel;
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all text
    private JPanel createFormArea() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formArea = new AddForm(this, formPanel);
        formArea.setSize(new Dimension(100, 100));
        formArea.setFont(new Font("Arial", Font.PLAIN, 18));

        Button addButton = new Button(this, formPanel, "Add");
        buttons.add(addButton);
        return formPanel;
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all buttons
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
        String message = "";
        try {
            collection = jsonReader.read();
            message = "Collection loaded from file";
        } catch (IOException e) {
            message = "Unable to read from file: " + JSON_FILE;
        }
        JOptionPane dialog = new JOptionPane();
        dialog.showMessageDialog(buttonArea, message);
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
        if (game.getCategories().isEmpty()) {
            gameDetails += "Category Tags: None" + "\n";
        } else {
            gameDetails += "Category Tags: " + game.getCategories() + "\n";
        }
        return gameDetails;
    }

    public void addNewGame() {
        String name = formArea.getNameField().getText();
        Integer minPlayers = Integer.valueOf(formArea.getMinPlayerField().getText());
        Integer maxPlayers = Integer.valueOf(formArea.getMaxPlayerField().getText());
        Integer minLength = Integer.valueOf(formArea.getMinLengthField().getText());
        Integer maxLength = Integer.valueOf(formArea.getMaxLengthField().getText());
        BoardGame game = new BoardGame(name, minPlayers, maxPlayers, minLength, maxLength);
        collection.addBoardGame(game);
    }

    public void handleAction(Button button) {
        String type = button.getText();
        switch (type) {
            case "Add":
                addNewGame();
                showDetailedGamesList();
                break;
            case "Load":
                loadCollection();
                showDetailedGamesList();
                break;
            case "Save":
                saveCollection();
        }
    }

    public static void main(String[] args) {
        new BGApp();
    }
}
