package ui;

import model.BoardGame;

import javax.swing.*;

public class AddForm extends JPanel {
    private JPanel panel;

    private FormField nameField;
    private FormField minPlayersField;
    private FormField maxPlayersField;
    private FormField minLengthField;
    private FormField maxLengthField;


    // MODIFIES: this, parent
    // EFFECTS: creates add game form and adds in to parent
    public AddForm(JComponent parent) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        nameField = new FormField(panel,"Board Game Name: ");
        minPlayersField = new FormField(panel,"Minimum # of Players: ");
        maxPlayersField = new FormField(panel, "Maximum # of Players: ");
        minLengthField = new FormField(panel,"Minimum Length (minutes): ");
        maxLengthField = new FormField(panel,"Maximum Length (minutes): ");

        addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(panel);
    }

    // EFFECTS: creates new game from form fields
    public BoardGame createNewGame() throws NumberFormatException {
        String name = nameField.getField().getText();
        Integer minPlayers = Integer.valueOf(minPlayersField.getField().getText());
        Integer maxPlayers = Integer.valueOf(maxPlayersField.getField().getText());
        Integer minLength = Integer.valueOf(minLengthField.getField().getText());
        Integer maxLength = Integer.valueOf(maxLengthField.getField().getText());
        return new BoardGame(name, minPlayers, maxPlayers, minLength, maxLength);

    }
}