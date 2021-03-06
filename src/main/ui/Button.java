package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button implements ActionListener {

    private JButton button;
    private BGApp app;

    public Button(BGApp app, JComponent parent, String name) {
        this.app = app;
        button = new JButton(name);
        addToParent(parent);
        button.setActionCommand("click");
        button.addActionListener(this);
    }

    // EFFECTS: calls app to handle button action if button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click")) {
            app.handleAction(Button.this);
        }
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    public String getText() {
        return button.getText();
    }

}

