package com.blixmark.view;

import com.blixmark.Config;
import com.blixmark.model.TableClientModel;
import com.blixmark.utilites.SystemLog;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ClientListLayout implements Layout {
    private JTable table;
    private JTextArea searchInput;
    private JButton createButton;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel preloaderText;
    private JButton refreshButton;
    final private TableClientModel tableModel;

    /**
     * Konstruktor ItemsLayout-a.
     */
    public ClientListLayout() {
        this.tableModel = new TableClientModel();
        this.table.setModel(tableModel);
        this.customUI();
    }

    /**
     * Metod za uredivanje korisnickog grafickog interfejsa za Login Layout.
     */
    private void customUI() {
        // Icons
        try {
            this.createButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "add-black-18dp.png"))
            ));
            this.refreshButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "refresh-black-18dp.png"))
            ));
        } catch (IOException ioe) { new SystemLog().newLog(ioe.getMessage()); }

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(new Color(51, 51, 51));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));

        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(100);

        table.setRowHeight(38);
        table.getTableHeader().setMinimumSize(new Dimension(table.getTableHeader().getMinimumSize().width, 25));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getMinimumSize().width, 25));

        // Spacing
        table.setIntercellSpacing(new Dimension(10,5));

        table.getTableHeader().setReorderingAllowed(false);

        // Focusable
        this.createButton.setFocusable(false);
        this.refreshButton.setFocusable(false);

        // Caret
        this.searchInput.setCaretColor(Color.WHITE);

        // Border
        this.preloaderText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        createButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        refreshButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        searchInput.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
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
    public TableClientModel getTableModel() {
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
        this.table = new JTable();
    }
}
