package com.blixmark.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class UserPassEditDialog implements Layout {
    private JPanel panel;
    private JButton positiveButton;
    private JPasswordField fieldPasswordFirst;
    private JPasswordField fieldPasswordSecond;
    private JLabel loaderText;

    public UserPassEditDialog() {
        this.customUI();
    }

    private void customUI() {
        this.setLoaderText(false);

        // Focusable
        this.positiveButton.setFocusable(false);

        // Caret
        this.fieldPasswordFirst.setCaretColor(new Color(51,51,51,250));
        this.fieldPasswordSecond.setCaretColor(new Color(51,51,51,250));

        // Border
        this.positiveButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5,15,5,15)));
        this.fieldPasswordFirst.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldPasswordSecond.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));;
    }

    public void setLoaderText(boolean visible) {
        this.loaderText.setVisible(visible);
    }

    public JLabel getTextLoader() {
        return loaderText;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JPasswordField getFieldPasswordFirst() {
        return this.fieldPasswordFirst;
    }

    public JPasswordField getFieldPasswordSecond() {
        return this.fieldPasswordSecond;
    }

    public JButton getPosButton() {
        return this.positiveButton;
    }
}
