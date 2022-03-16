package ui;

import javax.swing.*;

public class AddForm extends JPanel {

    private JPanel panel;
    private BGApp app;

    private FormField nameField;
    private FormField minPlayersField;
    private FormField maxPlayersField;
    private FormField minLengthField;
    private FormField maxLengthField;



    public AddForm(BGApp app, JComponent parent) {
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

    public JTextField getNameField() {
        return nameField.getField();
    }

    public JTextField getMinPlayerField() {
        return minPlayersField.getField();
    }

    public JTextField getMaxPlayerField() {
        return maxPlayersField.getField();
    }

    public JTextField getMinLengthField() {
        return minLengthField.getField();
    }

    public JTextField getMaxLengthField() {
        return maxLengthField.getField();
    }
}