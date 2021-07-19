package com.blixmark.utilites.bookmanager;

import com.blixmark.controller.BookEditController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookItemsTableMouseListener extends MouseAdapter {
    BookEditController parent;

    public BookItemsTableMouseListener(BookEditController parent) {
        this.parent = parent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JTable tableMouseEv = (JTable) e.getSource();
        Point tablePoint = e.getPoint();
        int pointedCol = tableMouseEv.columnAtPoint(tablePoint);
        int pointedRow = tableMouseEv.rowAtPoint(tablePoint);
        int columnCount = parent.getTableModel().getColumnCount() - 1;

        if(e.getClickCount() == 1 && pointedCol == columnCount)
            parent.deleteItem(pointedRow);
    }
}
