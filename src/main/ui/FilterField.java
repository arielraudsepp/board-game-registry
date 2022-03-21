package ui;

import javax.swing.*;

public class FilterField extends JTextArea {
    private JRadioButton button;
    private JTextField field;

    public FilterField(JComponent parent, String name) {
        button = new JRadioButton(name);
        field = new JTextField();
        addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given filter field to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
        parent.add(field);
    }

    public JRadioButton getButton() {
        return button;
    }

    public JTextField getField() {
        return field;
    }


}