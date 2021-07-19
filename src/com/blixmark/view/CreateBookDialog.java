package com.blixmark.view;

import com.blixmark.model.ClientModel;
import com.blixmark.model.UserModel;
import com.blixmark.utilites.AutoCombobox;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.LinkedList;

public class CreateBookDialog implements Layout {
    private JPanel panel;
    private JButton positiveButton;
    private AutoCombobox<ClientModel> fieldClient;
    private JTextField fieldCode;
    private JTextField fieldCodeClient;
    private JTextField fieldDate;
    private JTextField fieldPlace;
    private AutoCombobox<UserModel> fieldUser;
    private JPanel panelCodeClient;
    private JPanel formPanel;
    private JLabel labelClient;
    private JLabel loaderText;

    /**
     * Konstruktor CreateItemDialog-a.
     */
    public CreateBookDialog() {
        this.customUI();
    }

    /**
     * Metod za uredivanje korisnickog grafickog interfejsa za Login Layout.
     */
    private void customUI() {
        loaderText.setVisible(false);

        // Focusable
        this.positiveButton.setFocusable(false);

        // Caret
        this.fieldCode.setCaretColor(new Color(51, 51, 51, 250));
        this.fieldCodeClient.setCaretColor(new Color(51, 51, 51, 250));
        this.fieldDate.setCaretColor(new Color(51, 51, 51, 250));
        this.fieldPlace.setCaretColor(new Color(51, 51, 51, 250));

        // Border

        this.fieldCode.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldCodeClient.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldDate.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldPlace.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.positiveButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        this.fieldUser.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldClient.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    public void setLoaderText(boolean visible) {
        this.formPanel.setVisible(visible);
    }

    /**
     * Metod za vracanje komponente glavnog panela CreateItemDialog-a.
     *
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Metod za vracanje komponente polja za unos Šife.
     *
     * @return JTextField
     */

    public JPanel getPanelCodeClient() {
        return this.panelCodeClient;
    }

    public JTextField getFieldCode() {
        return this.fieldCode;
    }

    public JTextField getFieldCodeClient() {
        return this.fieldCodeClient;
    }

    public AutoCombobox<ClientModel> getFieldClient() {
        return fieldClient;
    }

    public AutoCombobox<UserModel> getFieldUser() {
        return this.fieldUser;
    }

    public JTextField getFieldDate() {
        return this.fieldDate;
    }

    public JTextField getFieldPlace() {
        return this.fieldPlace;
    }

    public JLabel getLabelClient() {
        return this.labelClient;
    }

    public JButton getPosButton() {
        return this.positiveButton;
    }

    public JLabel getTextLoader() {
        return loaderText;
    }

    private void createUIComponents() {
        fieldUser = new AutoCombobox<UserModel>(new LinkedList<>());
        fieldClient = new AutoCombobox<ClientModel>(new LinkedList<>());
    }
}
