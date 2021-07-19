package com.blixmark.utilites;

import com.blixmark.Config;
import com.blixmark.utilites.SystemLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;

public class DeleteColumnTableRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = new JButton();

        try {
            button.setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "delete-black-22px.png"))
            ));
        } catch (Exception exception) {
            SystemLog.newLog(exception.getMessage());
        }

        button.setBackground(new Color(224, 224, 224));
        button.setMaximumSize(new Dimension(30, 10));
        return button;
    }
}
