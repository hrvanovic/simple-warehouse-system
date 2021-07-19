package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.controller.BookEditController;
import com.blixmark.model.TableBookInItemsModel;
import com.blixmark.model.BookItemModel;
import com.blixmark.model.ItemModel;
import com.blixmark.utilites.TableSearchListener;
import com.blixmark.utilites.bookmanager.BookUtility;
import com.blixmark.enumeration.ItemType;
import com.blixmark.utilites.ItemUtility;
import com.blixmark.view.AddItemToBookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddItemToBookController {
    final private DialogUIWindow dialogUIWindow;
    final private AddItemToBookView view;
    final private BookEditController parent;

    public AddItemToBookController(Object parent) {
        this.parent = (BookEditController) parent;
        dialogUIWindow = new DialogUIWindow();
        view = new AddItemToBookView();

        onStart();
        dialogUIWindow.setNewWindow(
                "Dodaj robu",
                view.getPanel(),
                true,
                false,
                new Dimension(800, 480)
        );
    }

    private void onStart() {
        updateTableData();
        view.getTableModel().switchList(ItemType.PRO);
        view.getComboBook().addActionListener(e -> {
            view.getTableModel().switchList(
                    view.getSelectedType()
            );
        });
        view.getRefreshButton().addActionListener(e -> {
            updateTableData();
        });
        view.getSearchInput().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getTable().addMouseListener(new TableMouseListener(this));
    }

    private void updateTableData() {
        onProcess();
        new Thread(() -> {
            try {
                view.getTableModel().setList(ItemUtility.getItemList(ItemType.PRO));
                view.getTableModel().addList(ItemUtility.getItemList(ItemType.RAW));
                view.getTableModel().addList(ItemUtility.getItemList(ItemType.COM));
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
            }
        }).start();
    }

    private void addItemToBook(ItemModel item) {
        try {
            BookItemModel bookItemModel = new BookItemModel(item, parent.getBook());
            BookUtility.addItem(bookItemModel);
            parent.getTableModel().addItem(bookItemModel);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void onException(String errText) {
        view.getTableScrollPane().setVisible(false);
        view.getTextLoader().setText(errText);
    }

    private void onFinish() {
        view.getTextLoader().setVisible(false);
        view.getTableScrollPane().setVisible(true);
    }

    private void onProcess() {
        view.getTableScrollPane().setVisible(false);
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText("Ucitavanje...");
    }

    class TableMouseListener extends MouseAdapter {
        private AddItemToBookController controller;
        public TableMouseListener(AddItemToBookController controller) {
            this.controller = controller;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2) {
                System.out.printf("AJSDASDASN DSAD MAS");
                JTable table = (JTable) e.getSource();
                int row = table.rowAtPoint(e.getPoint());
                ((TableBookInItemsModel) table.getModel()).getItemAt(row);
                controller.addItemToBook(
                        ((TableBookInItemsModel) table.getModel()).getItemAt(row)
                );
            }
        }
    }
}
