package com.blixmark.utilites.bookmanager;

import com.blixmark.controller.BooksController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookTableMouseListener extends MouseAdapter {
    BooksController parent;

    public BookTableMouseListener(BooksController parent) {
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

        if(e.getClickCount() >= 2 && pointedCol != columnCount)
            parent.openBookEdit(pointedRow);

        if(e.getClickCount() == 1 && pointedCol == columnCount)
            parent.deleteBook(pointedRow);
    }
}
