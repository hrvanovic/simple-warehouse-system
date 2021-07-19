package com.blixmark.view;

import com.blixmark.Config;
import com.blixmark.model.ComboIOModel;
import com.blixmark.model.TableIOModel;
import com.blixmark.utilites.SystemLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IODataDialog implements Layout {
    private JPanel panel;
    private JTable table;
    private TableIOModel tableModel;
    private JScrollPane jScrollPane;
    private JLabel preloaderText;
    private JComboBox<String> comboBook;
    private JButton refreshButton;
    private JTextArea searchInput;
    private ComboIOModel comboBookModel;

    public IODataDialog() {
        this.tableModel = new TableIOModel();
        this.table.setModel(tableModel);
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

    public TableIOModel getTableModel() {
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

    public ComboIOModel getComboBookModel() {
        return comboBookModel;
    }

    private void createUIComponents() {
        this.table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        comboBook = new JComboBox<>();
        comboBookModel = new ComboIOModel();
        comboBook.setModel(comboBookModel);
    }
}
