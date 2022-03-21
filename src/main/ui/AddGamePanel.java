package ui;

import model.BoardGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGamePanel extends JPanel implements ActionListener {
    private BGApp app;

    private FormField nameField;
    private FormField minPlayersField;
    private FormField maxPlayersField;
    private FormField minLengthField;
    private FormField maxLengthField;
    private JButton addButton;

    // MODIFIES: this, parent
    // EFFECTS: creates add game form and adds in to parent
    public AddGamePanel(BGApp app, JComponent parent) {
        super();
        this.app = app;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nameField = new FormField(this,"Board Game Name: ");
        minPlayersField = new FormField(this,"Minimum # of Players: ");
        maxPlayersField = new FormField(this, "Maximum # of Players: ");
        minLengthField = new FormField(this,"Minimum Length (minutes): ");
        maxLengthField = new FormField(this,"Maximum Length (minutes): ");

        addButton = new JButton("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        this.add(addButton);

        addToParent(parent);
    }

    // MODIFIES: app
    // EFFECTS: creates game and updates game list
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addNewGame();
            app.updateLists();
        }
    }

    // MODIFIES: parent
    // EFFECTS:  adds panel to the parent component
    public void addToParent(JComponent parent) {
        parent.add(this);
    }

    // MODIFIES: this
    // EFFECTS: checks if game by same name already exists in colletion, if not
    // adds new game created from form field to collection and
    // notifies user if empty string or non-integers have been entered in players and length fields
    public void addNewGame() {
        String name = nameField.getField().getText();
        if (!app.gameAlreadyAdded(name)) {
            try {
                BoardGame game = createNewGame();
                app.addGame(game);
            } catch (NumberFormatException e) {
                String message = "Please only enter numbers in 'players' and 'length' fields";
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(this, message);
            }
        }
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