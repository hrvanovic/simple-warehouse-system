package com.blixmark.utilites;

import javax.swing.*;

public class UserDialog {
    public static int showOption(String text) {
        return JOptionPane.showOptionDialog(null, text, "Upozorenje!", 0,
                JOptionPane.INFORMATION_MESSAGE, null, new String[] {"DA", "NE"}, null);
    }

    public static JOptionPane showWarning(String text) {
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.showMessageDialog(null, text, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
        return jOptionPane;
    }

    public static JOptionPane showError(String text) {
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.showOptionDialog(null, text, "Greška!", 0, JOptionPane.ERROR_MESSAGE, null, new String[] {"Razumijem"}, null);
        return jOptionPane;
    }
}
