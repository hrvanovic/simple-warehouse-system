package com.blixmark.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class AddClientDialog implements Layout {
    private JPanel panel;
    private JButton positiveButton;
    private JTextField fieldName;
    private JTextArea fieldDesc;
    private JScrollPane scrollDesc;
    private JLabel loaderText;

    /**
     * Konstruktor CreateItemDialog-a.
     */
    public AddClientDialog() {
        this.customUI();
    }

    /**
     * Metod za uredivanje korisnickog grafickog interfejsa za Login Layout.
     */
    private void customUI() {

        this.setLoaderText(false);

        // Focusable
        this.positiveButton.setFocusable(false);

        // Caret
        this.fieldName.setCaretColor(new Color(51, 51, 51, 250));
        this.fieldDesc.setCaretColor(new Color(51, 51, 51, 250));

        // Border
        this.positiveButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5,15,5,15)));
        this.fieldName.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5,5,5,5)));
        this.scrollDesc.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5,5,5,5)));
    }

    public void setLoaderText(boolean visible) {
        this.loaderText.setVisible(visible);
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JTextField getFieldName() {
        return this.fieldName;
    }

    public JTextArea getFieldDesc() {
        return this.fieldDesc;
    }

    public JButton getPosButton() {
        return this.positiveButton;
    }

    public JLabel getLoaderText() {
        return loaderText;
    }
}
