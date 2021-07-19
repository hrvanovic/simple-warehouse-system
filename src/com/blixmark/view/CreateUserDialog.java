package com.blixmark.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CreateUserDialog implements Layout {
    private JPanel panel;
    private JButton positiveButton;
    private JTextField fieldEmail;
    private JTextField fieldName;
    private JTextField fieldPozicija;
    private JPasswordField fieldPassword;
    private JLabel loaderText;

    /**
     * Konstruktor CreateItemDialog-a.
     */
    public CreateUserDialog() {
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
        this.fieldName.setCaretColor(new Color(51,51,51,250));
        this.fieldEmail.setCaretColor(new Color(51,51,51,250));
        this.fieldPassword.setCaretColor(new Color(51,51,51,250));
        this.fieldPozicija.setCaretColor(new Color(51,51,51,250));

        // Border
        this.positiveButton.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        this.fieldName.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldEmail.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldPozicija.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.fieldPassword.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(185, 185, 185)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    }

    public void setLoaderText(boolean visible) {
        this.loaderText.setVisible(visible);
    }

    /**
     * Metod za vracanje komponente glavnog panela CreateItemDialog-a.
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Metod za vracanje komponente polja za unos Naziva.
     * @return JTextField
     */
    public JTextField getNameField() {
        return this.fieldName;
    }

    /**
     * Metod za vracanje komponente polja za unos Šife.
     * @return JTextField
     */
    public JTextField getEmailField() {
        return this.fieldEmail;
    }

    /**
     * Metod za vracanje komponente polja za unos Količine.
     * @return JTextField
     */
    public JTextField getPositionField() {
        return this.fieldPozicija;
    }


    /**
     * Metod za vracanje komponente polja za unos Passworda.
     * @return JPasswordField
     */
    public JPasswordField getPasswordField() {
        return this.fieldPassword;
    }


    /**
     * Metod za vracanje komponente pozitivnog dugmeta.
     * @return JButton
     */
    public JButton getPosButton() {
        return this.positiveButton;
    }

    public JLabel getLoaderText() {
        return loaderText;
    }
}
