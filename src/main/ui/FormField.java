package ui;

import javax.swing.*;

public class FormField extends JTextArea {
    private JLabel label;
    private JTextField field;

    public FormField(JComponent parent, String name) {
        label = new JLabel(name);
        field = new JTextField();
        addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given form field to the parent component
    public void addToParent(JComponent parent) {
        parent.add(label);
        parent.add(field);
    }

    public JTextField getField() {
        return field;
    }
}