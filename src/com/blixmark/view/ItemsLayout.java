package com.blixmark.view;

import com.blixmark.model.TableItemsModel;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class ItemsLayout implements Layout {
    private JTable table;
    private JTextArea searchInput;
    private JButton createButton;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel preloaderText;
    private JButton refreshButton;
    final private TableItemsModel tableModel;

    public ItemsLayout() {
        tableModel = new TableItemsModel();
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(false);

        // Font
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(new Color(51, 51, 51));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));

        // Spacing
        table.setIntercellSpacing(new Dimension(10,5));

        // Dimensions
        table.getTableHeader().setMinimumSize(new Dimension(table.getTableHeader().getMinimumSize().width, 30));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getMinimumSize().width, 30));
        table.setRowHeight(38);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(200);
        table.getColumnModel().getColumn(4).setMaxWidth(500);
        table.getColumnModel().getColumn(4).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        table.getColumnModel().getColumn(6).setMaxWidth(100);

        // Order
        table.getTableHeader().setReorderingAllowed(false);

        table.getTableHeader().setEnabled(false);

        // Focusable
        createButton.setFocusable(false);
        refreshButton.setFocusable(false);

        // Caret
        searchInput.setCaretColor(new Color(55,55,55));

        // Border
        preloaderText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        createButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        refreshButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        searchInput.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
    }


    public JPanel getPanel() {
        return panel;
    }

    public JLabel getTextLoader() {
        return preloaderText;
    }

    public JScrollPane getTableScrollPane() {
        return scrollPane;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JTextArea getSearch() {
        return searchInput;
    }

    public TableItemsModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    private void createUIComponents() {
        // DO NOTHING.
    }
}
