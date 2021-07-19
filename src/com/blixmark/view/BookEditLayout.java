package com.blixmark.view;

import com.blixmark.Config;
import com.blixmark.model.TableBookItemsModel;
import com.blixmark.model.ClientModel;
import com.blixmark.model.UserModel;
import com.blixmark.utilites.AutoCombobox;
import com.blixmark.utilites.SystemLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class BookEditLayout implements Layout {
    private JTable table;
    private JTextArea searchInput;
    private JButton createButton;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel preloaderText;
    private JButton refreshButton;
    private JTextField fieldCode;
    private AutoCombobox<ClientModel> fieldClient;
    private JTextField fieldDate;
    private JTextPane fieldComment;
    private JButton spremiButton;
    private AutoCombobox<UserModel> fieldUser;
    private JTextField fieldPlace;
    private JTextField fieldClientCode;
    private JPanel panelCodeClient;
    private JPanel panelFields;
    private JLabel labelClient;
    private JButton buttonPrint;
    private TableBookItemsModel tableModel;

    public BookEditLayout() {
        tableModel = new TableBookItemsModel();
        table.setModel(tableModel);
        customUI();
    }

    private void customUI() {
        preloaderText.setVisible(false);

        // Icons
        try {
            this.buttonPrint.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "print-black-18dp.png"))
            ));
            this.createButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "add-black-18dp.png"))
            ));
            this.refreshButton.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "refresh-black-18dp.png"))
            ));
        } catch (IOException ioe) { new SystemLog().newLog(ioe.getMessage()); }

        // Spacing
        this.table.setIntercellSpacing(new Dimension(10, 10));

        // Dimensions
        table.setRowHeight(38);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(500);
        table.getColumnModel().getColumn(4).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(100);

        table.getTableHeader().setEnabled(false);

        // Ordering
        this.table.getTableHeader().setReorderingAllowed(false);

        // Focusable
        this.createButton.setFocusable(false);
        this.refreshButton.setFocusable(false);
        this.spremiButton.setFocusPainted(false);
        this.spremiButton.setFocusable(false);
        this.buttonPrint.setFocusable(false);

        // Caret
        this.searchInput.setCaretColor(new Color(51, 51, 51,255));
        this.fieldClientCode.setCaretColor(new Color(51, 51, 51,255));
        this.fieldCode.setCaretColor(new Color(51, 51, 51,255));
        this.fieldComment.setCaretColor(new Color(51, 51, 51,255));
        this.fieldDate.setCaretColor(new Color(51, 51, 51,255));
        this.fieldPlace.setCaretColor(new Color(51, 51, 51,255));


        // Width
        this.fieldComment.setPreferredSize(new Dimension(80, 80));
        this.fieldComment.setMaximumSize(new Dimension(80, 80));

        // Border
        this.preloaderText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.fieldClientCode.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldCode.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldClient.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldComment.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldDate.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldPlace.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));
        this.fieldUser.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185,255)), BorderFactory.createEmptyBorder(3,6,3,6)));

        buttonPrint.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        createButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        refreshButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(5,15,5,15)));
        searchInput.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(212, 212, 212)), BorderFactory.createEmptyBorder(3,3,3,3)));
    }

    public JPanel getPanelFields() {
        return this.panelFields;
    }
    public JTextField getFieldClientCode() {
        return this.fieldClientCode;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JPanel getPanelCodeClient() {
        return this.panelCodeClient;
    }
    public JLabel getTextLoader() {
        return this.preloaderText;
    }

    public JScrollPane getTableScrollPane() {
        return this.scrollPane;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }


    public JButton getCreateButton() {
        return createButton;
    }

    public JTextArea getSearch() {
        return this.searchInput;
    }

    public TableBookItemsModel getTableModel() {
        return this.tableModel;
    }

    public JTextField getFieldCode() {
        return this.fieldCode;
    }

    public AutoCombobox<UserModel> getFieldUser() {
        return this.fieldUser;
    }

    public AutoCombobox<ClientModel> getFieldClient() {
        return this.fieldClient;
    }

    public JTextField getFieldDate() {
        return this.fieldDate;
    }

    public JTextPane getFieldComment() {
        return this.fieldComment;
    }

    public JTextField getFieldPlace() {
        return this.fieldPlace;
    }

    public JButton getSpremiButton() {
        return this.spremiButton;
    }

    public JButton getButtonPrint() {
        return this.buttonPrint;
    }

    public JLabel getLabelClient() {
        return this.labelClient;
    }

    public JTable getTable() {
        return this.table;
    }

    private void createUIComponents() {
        fieldClient = new AutoCombobox<>(new LinkedList<>());
        fieldUser = new AutoCombobox<>(new LinkedList<>());
    }
}
