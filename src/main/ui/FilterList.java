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


public class FilterList extends JPanel
                      implements ActionListener {
    private JList list;
    private DefaultListModel listModel;
    private BGApp app;

    private JRadioButton lengthButton;
    private JRadioButton playersButton;
    private JRadioButton categoryButton;
    private JTextField lengthField;
    private JTextField playersField;
    private JTextField categoryField;
    private JButton filterButton;

    public FilterList(BGApp app) {
        super(new BorderLayout());
        this.app = app;
        listModel = new DefaultListModel();


        createList();
        JScrollPane listScrollPane = new JScrollPane(list);


        add(createFilterFields(), BorderLayout.NORTH);
        add(createFilterButton(), BorderLayout.CENTER);
        add(listScrollPane, BorderLayout.PAGE_END);

        groupButtons();

    }

    private JPanel createFilterButton() {
        filterButton = new JButton("Filter");
        filterButton.setActionCommand("filter");
        filterButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,
                BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(filterButton);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the list
    private void createList() {
        list = new JList(listModel);
        list.setVisibleRowCount(10);
    }

    // EFFECTS: creates radio button and fields for filter criteria
    private JPanel createFilterFields() {
        JPanel filterFields = new JPanel();
        filterFields.setLayout(new BoxLayout(filterFields, BoxLayout.Y_AXIS));

        lengthButton = new JRadioButton("Length (mins)");
        filterFields.add(lengthButton);
        lengthField = new JTextField();
        filterFields.add(lengthField);

        playersButton = new JRadioButton("Number of Players");
        filterFields.add(playersButton);
        playersField = new JTextField();
        filterFields.add(playersField);

        categoryButton = new JRadioButton("Category");
        filterFields.add(categoryButton);
        categoryField = new JTextField();
        filterFields.add(categoryField);

        return filterFields;
    }

    // EFFECTS: groups all radio buttons together
    private void groupButtons() {
        ButtonGroup group = new ButtonGroup();
        group.add(lengthButton);
        group.add(playersButton);
        group.add(categoryButton);
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
        return Integer.valueOf(lengthField.getText());
    }

    private Integer getPlayers() throws NumberFormatException {
        return Integer.valueOf(playersField.getText());
    }

    private String getCategory() {
        return categoryField.getText();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("filter")) {
            if (lengthButton.isSelected()) {
                Integer length = getLength();
                updateList(app.searchByLength(length));
            } else if (playersButton.isSelected()) {
                Integer players = getPlayers();
                updateList(app.searchByPlayers(players));
            } else if (categoryButton.isSelected()) {
                String category = getCategory();
                updateList(app.searchByCategory(category));
            }
        }
    }

}
