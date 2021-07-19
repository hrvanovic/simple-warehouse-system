package com.blixmark.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CreateItemDialog implements Layout {
    private JPanel panel;
    private JButton positiveButton;
    private JTextField codeField;
    private JTextField fieldName;
    private JTextField fieldAmount;
    private JTextField fieldJM;
    private JLabel loaderText;

    public CreateItemDialog() {
        customUI();
    }

    private void customUI() {
        loaderText.setVisible(false);

        // Focusable
        positiveButton.setFocusable(false);

        // Caret
        fieldName.setCaretColor(new Color(51,51,51,250));
        codeField.setCaretColor(new Color(51,51,51,250));
        fieldJM.setCaretColor(new Color(51,51,51,250));
        fieldAmount.setCaretColor(new Color(51,51,51,250));

        // Border
        positiveButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        fieldName.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        codeField.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        fieldAmount.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        fieldJM.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getNameField() {
        return fieldName;
    }

    public JTextField getCodeField() {
        return codeField;
    }

    public JTextField getJMField() {
        return fieldJM;
    }

    public JTextField getAmountField() {
        return fieldAmount;
    }

    public JButton getPosButton() {
        return positiveButton;
    }

    public JLabel getLoaderText() {
        return loaderText;
    }
}
