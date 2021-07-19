package com.blixmark.utilites.usermanager;

import com.blixmark.controller.UsersController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTableMouseListener extends MouseAdapter {
    final private UsersController parent;

    public UserTableMouseListener(UsersController parent) {
        this.parent = parent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JTable tableMouseEv = (JTable) e.getSource();
        Point tablePoint = e.getPoint();
        int pointedCol = tableMouseEv.columnAtPoint(tablePoint);
        int pointedRow = tableMouseEv.rowAtPoint(tablePoint);

        if (e.getClickCount() == 1 && tableMouseEv.getSelectedRow() != -1 && pointedCol == 4)
            parent.openPasswordEditDialog(pointedRow);

        if (e.getClickCount() == 1 && tableMouseEv.getSelectedRow() != -1 && pointedCol == 5)
            parent.removeUser(pointedRow);
    }
}
