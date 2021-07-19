package com.blixmark.view;

import com.blixmark.Config;
import com.blixmark.model.TableBookModel;
import com.blixmark.utilites.SystemLog;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BookView implements Layout {
    private JTable table;
    private JTextArea searchInput;
    private JButton createButton;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel preloaderText;
    private JButton refreshButton;
    final private TableBookModel tableModel;

    /**
     * Konstruktor ItemsLayout-a.
     */
    public BookView() {
        this.tableModel = new TableBookModel();
        this.table.setModel(tableModel);
        this.customUI();
    }

    /**
     * Metod za uredivanje korisnickog grafickog interfejsa za Login Layout.
     */
    private void customUI() {
        // Font
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(new Color(51, 51, 51));

        // Icons
        try {
            createButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "add-black-18dp.png"))
            ));
            refreshButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "refresh-black-18dp.png"))
            ));
        } catch (IOException ioe) { new SystemLog().newLog(ioe.getMessage()); }


        table.getTableHeader().setMinimumSize(new Dimension(table.getTableHeader().getMinimumSize().width, 30));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getMinimumSize().width, 30));

        // Spacing
        table.setIntercellSpacing(new Dimension(5, 5));

        // Dimensions
        table.setRowHeight(38);

        // Ordering
        table.getTableHeader().setReorderingAllowed(false);

        // Focusable
        createButton.setFocusable(false);
        refreshButton.setFocusable(false);

        table.getTableHeader().setEnabled(false);

        // Caret
        searchInput.setCaretColor(new Color(55,55,55));

        // Border
        preloaderText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        createButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        refreshButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        searchInput.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
    }

    public void updateUI() {
        if(tableModel.getColumnCount() > 1) {
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(150);
            table.getColumnModel().getColumn((tableModel.getColumnCount() - 1)).setMaxWidth(100);
        }
    }
    /**
     * Metod za vracanje komponente glavnog panela Items Layouta.
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Metod za vracanje komponente teksta za prikaz prije ucitavanja prodataka.
     * @return JLabel
     */
    public JLabel getTextLoader() {
        return this.preloaderText;
    }

    /**
     * Metod za vracanje komponente Scroll Pane za tabelu.
     * @return JScrollPane
     */
    public JScrollPane getTableScrollPane() {
        return this.scrollPane;
    }

    /**
     * Metod za vracanje komponente dugmeta za refresovanje tabele.
     * @return JButton
     */
    public JButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * Metod za vracanje komponente dugmeta za kreiranje novog Itema.
     * @return JButton
     */
    public JButton getCreateButton() {
        return createButton;
    }

    /**
     * Metod za vracanje komponente polja za pretrazivanje Itema.
     * @return JTextArea
     */
    public JTextArea getSearch() {
        return this.searchInput;
    }

    /**
     * Metod za vracanje komponente modela tabele.
     * @return DefaultTableModel
     */
    public TableBookModel getTableModel() {
        return this.tableModel;
    }

    /**
     * Metod za vracanje komponente tabele.
     * @return JTable
     */
    public JTable getTable() {
        return this.table;
    }

    private void createUIComponents() {
        this.table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public boolean isVisible() {
                return true;
            }
        };
    }
}
