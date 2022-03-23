package ui;

// Code modified from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FilterPanel extends JPanel
                      implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private BGApp app;
    private DisplayPanel display;

    private FilterField lengthField;
    private FilterField playersField;
    private FilterField categoryField;
    private JButton filterButton;

    public FilterPanel(BGApp app) {
        super(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.app = app;
        listModel = new DefaultListModel();
        display = new DisplayPanel(app);

        createFilterButton();
        createList();
        JScrollPane listScrollPane = new JScrollPane(list);

        add(createFilterFields(), BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(display, BorderLayout.PAGE_END);

        groupButtons();

    }

    // MODIFIES: this
    // EFFECTS: creates the filter button
    private void createFilterButton() {
        filterButton = new JButton("Filter");
        FilterListener filterListener = new FilterListener(filterButton);
        filterButton.setActionCommand("filter");
        filterButton.addActionListener(filterListener);
    }

    // MODIFIES: this
    // EFFECTS: creates the filter list
    private void createList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
    }

    // MODIFIES: this
    // EFFECTS: creates radio button and fields for filter criteria
    private JPanel createFilterFields() {
        JPanel filterFields = new JPanel();
        filterFields.setLayout(new BoxLayout(filterFields, BoxLayout.Y_AXIS));

        lengthField = new FilterField(filterFields, "Length (mins)");
        playersField = new FilterField(filterFields, "Number of Players");
        categoryField = new FilterField(filterFields, "Category");

        filterFields.add(filterButton);

        return filterFields;
    }

    // EFFECTS: groups all radio buttons together
    private void groupButtons() {
        ButtonGroup group = new ButtonGroup();

        group.add(lengthField.getButton());
        group.add(playersField.getButton());
        group.add(categoryField.getButton());
    }

    // MODIFIES: this
    // EFFECTS: removes all elements from list and repopulates the list
    // with specified board game names
    public void updateList(ArrayList<String> gameNames) {
        listModel.clear();
        for (String name : gameNames) {
            listModel.addElement(name);
        }
    }

    private Integer getLength() throws NumberFormatException {
        return Integer.valueOf(lengthField.getField().getText());
    }

    private Integer getPlayers() throws NumberFormatException {
        return Integer.valueOf(playersField.getField().getText());
    }

    private String getCategory() {
        return categoryField.getField().getText();
    }


    // MODIFIES: this, app
    // EFFECTS: listener for 'filter' button, calls action based on which radio button is selected
    class FilterListener implements ActionListener {
        private JButton button;

        public FilterListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getActionCommand().equals("filter")) {
                    if (lengthField.getButton().isSelected()) {
                        Integer length = getLength();
                        updateList(app.searchByLength(length));
                    } else if (playersField.getButton().isSelected()) {
                        Integer players = getPlayers();
                        updateList(app.searchByPlayers(players));
                    } else if (categoryField.getButton().isSelected()) {
                        String category = getCategory();
                        updateList(app.searchByCategory(category));
                    }
                }
            } catch (NumberFormatException e) {
                String message = "Please only enter numbers in 'players' and 'length' fields";
                JOptionPane dialog = new JOptionPane();
                dialog.showMessageDialog(button, message);
            }
        }
    }

    // EFFECTS: this
    // MODIFIES: if selection of element in list is complete,
    // calls app to show game details in display area
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() != -1) {
                int index = list.getSelectedIndex();
                String name = listModel.elementAt(index).toString();
                display.showGameDetails(name);
            }
        }
    }
}