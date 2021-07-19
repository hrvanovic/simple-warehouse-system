package com.blixmark.utilites;

import com.blixmark.model.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TableSearchListener<T> implements KeyListener {
    final private JTable table;
    final private T itemTableModel;

    public TableSearchListener(T tableModel, JTable table) {
        this.table = table;
        this.itemTableModel = tableModel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        String searchText = ((JTextArea) e.getSource()).getText();
        if(itemTableModel instanceof TableUserModel) {
            ((TableUserModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableItemsModel) {
            ((TableItemsModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableIOModel) {
            ((TableIOModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableBookModel) {
            ((TableBookModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableBookItemsModel) {
            ((TableBookItemsModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableBookInItemsModel) {
            ((TableBookInItemsModel) itemTableModel).search(searchText);
        } else if(itemTableModel instanceof TableClientModel) {
            ((TableClientModel) itemTableModel).search(searchText);
        }
    }
}
