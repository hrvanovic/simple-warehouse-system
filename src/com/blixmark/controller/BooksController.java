package com.blixmark.controller;

import com.blixmark.Config;
import com.blixmark.UIWindow;
import com.blixmark.model.TableBookModel;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.SystemLog;
import com.blixmark.utilites.TableSearchListener;
import com.blixmark.utilites.UserDialog;
import com.blixmark.utilites.bookmanager.BookTableMouseListener;
import com.blixmark.utilites.bookmanager.BookUtility;
import com.blixmark.enumeration.BookType;
import com.blixmark.view.BookView;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;

public class BooksController {
    private final UIWindow uiWindow;
    public final BookView view;
    private final BookType bookType;

    public BooksController(BookType bookType) {
        this.bookType = bookType;
        this.uiWindow = UIWindow.mainWindow;
        view = new BookView();

        onStart();
        uiWindow.setNewWindow(
                Localization.get(bookType, "list-title"),
                view.getPanel(),
                false,
                true,
                null
        );
    }

    private void onStart() {
        updateData();
        view.getSearch().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getTable().addMouseListener(new BookTableMouseListener(this));
        view.getTable().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getRefreshButton().addActionListener(e -> updateData());
        view.getCreateButton().addActionListener(e -> uiWindow.startNewController("CREATEBOOK_" + bookType, this));
    }

    private void updateData() {
        onProcess();
        new Thread(() -> {
            try {
                view.getTableModel().setList(BookUtility.getList(bookType), bookType);
                view.getTable().getColumnModel().getColumn((getTableModel().getColumnCount() - 1)).setCellRenderer(new BookTableCellRenderer());
                view.updateUI();
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
            }

            onFinish();
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
        view.getTextLoader().setText("Molimo sačekajte");
    }

    public void deleteBook(int rowIndex) {
        try {
            int deleteConfirmPane = UserDialog.showOption(Localization.get(bookType, "delete-prefix")
                    + " " + getTableModel().getBookCode(rowIndex) + " ?");
            if(deleteConfirmPane == 0) {
                BookUtility.delete(view.getTableModel().getBookID(rowIndex), bookType);
                view.getTableModel().removeRow(rowIndex);
            }
        } catch (Exception exception) {
            UserDialog.showError("Grečka prilikom brisanja!");
            exception.printStackTrace();
        }
    }

    public TableBookModel getTableModel() {
        return view.getTableModel();
    }

    public void openBookEdit(int rowIndex) {
        uiWindow.startNewController("BOOKEDIT", view.getTableModel().getBookAt(rowIndex));
    }

    class BookTableCellRenderer implements TableCellRenderer {
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
}
