package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.controller.ItemsController;
import com.blixmark.model.ItemModel;
import com.blixmark.enumeration.BookType;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.TableSearchListener;
import com.blixmark.utilites.bookmanager.BookUtility;
import com.blixmark.view.IODataDialog;

import java.awt.*;

public class IOItemController {
    final private ItemModel item;
    final private DialogUIWindow dialogUIWindow;
    final private IODataDialog view;
    final private ItemsController parent;

    public IOItemController(ItemModel item, ItemsController parent) {
        this.item = item;
        this.parent = parent;
        dialogUIWindow = new DialogUIWindow();
        view = new IODataDialog();

        onCreate();
        dialogUIWindow.setNewWindow(
                Localization.get("items", "io-title-prefix") + " \"" + item.getName() + "\"",
                view.getPanel(),
                true,
                false,
                new Dimension(800, 480)
        );
    }

    private void onCreate() {
        updateTableData();
        view.getRefreshButton().addActionListener(e -> updateTableData());
        view.getSearchInput().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getComboBook().addActionListener(e -> {
            view.getTableModel().switchList(
                    view.getComboBookModel().getSelectedBook()
            );
        });
        view.getTableModel().switchList(BookType.DELIVERY);
    }

    private void updateTableData() {
        onProcess();
        new Thread(() -> {
            try {
                view.getTableModel().setList(BookUtility.getIOList(item, BookType.RECEIPT));
                view.getTableModel().addList(BookUtility.getIOList(item, BookType.DELIVERY));
                view.getTableModel().switchList(view.getComboBookModel().getSelectedBook());
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
                exception.printStackTrace();
            }
        }).start();
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
        view.getTextLoader().setText(Localization.get(item.getType(), "loading-items"));
    }
}
