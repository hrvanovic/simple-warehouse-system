package com.blixmark.view;
import javax.swing.*;

public class LoginLayout {
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField passField;
    private JButton button;
    private JLabel onWrongCredText;
    public int numWrongCreditials = 0;

    /**
     * Konstruktor Login Layout-a.
     */
    public LoginLayout() {
        this.customUI();
    }

    /**
     * Metod za uredivanje korisnickog grafickog interfejsa za Login Layout.
     */
    private void customUI() {

        // Visibility
        this.onWrongCredText.setVisible(false);

        // Focusable
        this.button.setFocusable(false);

        // Border
        this.emailField.setBorder(BorderFactory.createCompoundBorder(this.emailField.getBorder(), BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        this.passField.setBorder(BorderFactory.createCompoundBorder(this.emailField.getBorder(), BorderFactory.createEmptyBorder(0, 3, 0, 10)));
    }

    /**
     * Metod za vracanje komponente teksta koji se prikazuje u slucaju progresne kreditacije pri loginu.
     * @return JLabel
     */
    public JLabel getWrongCreditials() {
        return this.onWrongCredText;
    }

    /**
     * Metod za vracanje komponente Email polja.
     * @return JTextField
     */
    public JTextField getEmailField() {
        return this.emailField;
    }

    /**
     * Metod za vracanje komponente polja Lozinke.
     * @return JPasswordField
     */
    public JPasswordField getPassField() {
        return this.passField;
    }

    /**
     * Metod za vracanje komponente dugmeta za prijavu.
     * @return JButton
     */
    public JButton getButton() {
        return this.button;
    }

    /**
     * Metod za vracanje glavnog panela Login Layouta.
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.panel;
    }
}
