package ui;

import model.BoardGame;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel  extends JPanel {
    private BGApp app;
    private JTextArea textArea;

    public DisplayPanel(BGApp app) {
        super();
        this.setLayout(new BorderLayout());
        this.app = app;
        this.add(new JLabel("Selected Game Details: "), BorderLayout.PAGE_START);
        textArea = new JTextArea(7, 20);
        textArea.setEditable(false);
        this.add(textArea, BorderLayout.PAGE_END);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(String text) {
        textArea.setText(text);
    }

    // MODIFIES: this
    // EFFECTS: displays game details for a game by name
    public void showGameDetails(String name) {
        BoardGame game = app.getGame(name);
        String message = displayGameDetails(game);
        setTextArea(message);
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
}
