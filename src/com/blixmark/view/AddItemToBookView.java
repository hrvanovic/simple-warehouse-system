package com.blixmark.view;

import com.blixmark.Config;
import com.blixmark.model.TableBookInItemsModel;
import com.blixmark.utilites.SystemLog;
import com.blixmark.enumeration.ItemType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddItemToBookView implements Layout {
    private JPanel panel;
    private JTable table;
    private TableBookInItemsModel tableModel;
    private JScrollPane jScrollPane;
    private JLabel preloaderText;
    private JComboBox<String> comboBook;
    private JButton refreshButton;
    private JTextArea searchInput;
    private ComboModel comboBookModel;

    public AddItemToBookView() {
        this.tableModel = new TableBookInItemsModel();
        this.table.setModel(tableModel);
        table.setAutoCreateRowSorter(false);
        this.customUI();
    }

    private void customUI() {
        // Icons
        try {
            refreshButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "refresh-black-18dp.png"))
            ));
        } catch (IOException ioe) {
            SystemLog.newLog(ioe.getMessage());
        }

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(new Color(51, 51, 51));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));

        table.setRowHeight(32);
        table.getTableHeader().setMinimumSize(new Dimension(table.getTableHeader().getMinimumSize().width, 25));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getMinimumSize().width, 25));

        // Spacing
        table.setIntercellSpacing(new Dimension(10,5));

        table.getTableHeader().setEnabled(false);

        // Focusable
        refreshButton.setFocusable(false);
        refreshButton.setFocusable(false);

        // Caret
        searchInput.setCaretColor(new Color(55,55,55));

        // Border
        comboBook.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
        refreshButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        searchInput.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
    }

    public JTextArea getSearchInput() {
        return searchInput;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JLabel getPreloaderText() {
        return preloaderText;
    }

    public TableBookInItemsModel getTableModel() {
        return this.tableModel;
    }

    public JScrollPane getTableScrollPane() {
        return jScrollPane;
    }

    public JTable getTable() {
        return this.table;
    }

    public JLabel getTextLoader() {
        return preloaderText;
    }

    public JComboBox getComboBook() {
        return comboBook;
    }

    public ComboModel getComboBookModel() {
        return comboBookModel;
    }

    public ItemType getSelectedType() {
        return comboBookModel.getSelectedType();
    }

    private void createUIComponents() {
        this.table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        comboBook = new JComboBox<>();
        comboBookModel = new ComboModel();
        comboBook.setModel(comboBookModel);
    }

    class ComboModel extends AbstractListModel implements ComboBoxModel {
        private final String[] elements = {"Proizvodi", "Sirovine", "Komercijala"};
        private int currentElement = 0;

        public ItemType getSelectedType() {
            switch (currentElement) {
                case 0:
                    return ItemType.PRO;
                case 1:
                    return ItemType.RAW;
                case 2:
                    return ItemType.COM;
                default:
                    return null;
            }
        }

        @Override
        public int getSize() {
            return elements.length;
        }

        @Override
        public Object getElementAt(int index) {
            return elements[index];
        }

        @Override
        public void setSelectedItem(Object item) {
            if(item.equals(elements[0]))
                currentElement = 0;
            else if(item.equals(elements[1]))
                currentElement = 1;
            else if(item.equals(elements[2]))
                currentElement = 2;
        }

        @Override
        public Object getSelectedItem() {
            return elements[currentElement];
        }
    }
}
