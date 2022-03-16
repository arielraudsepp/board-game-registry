package ui;

import javax.swing.*;
import java.awt.*;

public class FormField extends JTextArea {
    private JLabel label;
    private JTextField field;

    public FormField(JComponent parent, String name) {
        label = new JLabel(name);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        field = new JTextField(5);
        field.setPreferredSize(new Dimension(50, 20));
        addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(label);
        parent.add(field);
    }

    public JTextField getField() {
        return field;
    }
}