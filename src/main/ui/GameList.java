package ui;

// Code modified from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class GameList extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private BGApp app;
    private DisplayPanel display;

    private static final String tagString = "Add Tag";
    private static final String fireString = "Delete";
    private JButton deleteButton;
    private JTextField categoryTag;


    public GameList(BGApp app) {
        super(new BorderLayout());
        this.app = app;
        display = new DisplayPanel(app);


        listModel = new DefaultListModel();
        updateList();

        createList();
        JScrollPane listScrollPane = new JScrollPane(list);

        JPanel buttonPane = createButtonPane();

        add(listScrollPane, BorderLayout.PAGE_START);
        add(buttonPane, BorderLayout.CENTER);
        add(display, BorderLayout.PAGE_END);
    }

    class DeleteListener implements ActionListener {
        // MODIFIES: this, app
        // EFFECTS: listener for delete button; disables delete button if list is empty,
        // deletes game at specified index and repopulates the games list
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            int size = listModel.getSize();

            if (size <= 0) {
                deleteButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
            app.removeGame(index);
            app.updateLists();
        }
    }

    // MODIFIES: this, app
    // EFFECTS: listener for 'add tag' button and tag field; enables 'add tag' button
    // if there is text in field,
    class TagListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public TagListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            if (index < 0) {
                String message = "Please select a game from the list";
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(categoryTag, message);
            } else {
                String name = listModel.elementAt(index).toString();

                String tag = categoryTag.getText();
                app.addCategoryTag(index, tag);
                display.showGameDetails(name);

                categoryTag.requestFocusInWindow();
                categoryTag.setText("");
            }
        }

        // MODIFIES: this
        // EFFECTS: if text is added to the tag field, enables the 'add tag' button
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: if text is removed from the tag field, checks if field is empty
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // MODIFIES: this
        // EFFECTS: if the tag field is not empty, enables the 'add tag' button
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: enables 'add tag' button, if it not already enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: if tag field is empty, disables the 'add tag' button and returns true
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates all buttons and creates button pane
    private JPanel createButtonPane() {
        JButton addTagButton = new JButton(tagString);
        TagListener tagListener = new TagListener(addTagButton);
        addTagButton.setActionCommand(tagString);
        addTagButton.addActionListener(tagListener);
        addTagButton.setEnabled(false);

        deleteButton = new JButton(fireString);
        deleteButton.setActionCommand(fireString);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);

        categoryTag = new JTextField(10);
        categoryTag.addActionListener(tagListener);
        categoryTag.getDocument().addDocumentListener(tagListener);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(categoryTag);
        buttonPane.add(addTagButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(deleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        return buttonPane;
    }

    // MODIFIES: this
    // EFFECTS: creates the list
    private void createList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
    }

    // MODIFIES: this
    // EFFECTS: removes all elements from list and repopulates the list with all
    // board game names from collection
    public void updateList() {
        listModel.clear();
        ArrayList<String> gameNames = app.getGameNames();
        for (String name : gameNames) {
            listModel.addElement(name);
        }
    }

    // EFFECTS: this
    // MODIFIES: if selection of element in list is complete, disables delete button if no element is selected,
    // or enables delete button and calls app to show game details in display area
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);

            } else {
                deleteButton.setEnabled(true);
                int index = list.getSelectedIndex();
                String name = listModel.elementAt(index).toString();
                display.showGameDetails(name);
            }
        }
    }
}
