package com.blixmark.controller;

import com.blixmark.Config;
import com.blixmark.UIWindow;
import com.blixmark.model.UserModel;
import com.blixmark.model.TableUserModel;
import com.blixmark.utilites.*;
import com.blixmark.utilites.usermanager.UserTableMouseListener;
import com.blixmark.utilites.usermanager.UserUtility;
import com.blixmark.view.UsersListLayout;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.EventObject;

public class UsersController {
    private final UIWindow uiWindow;
    public final UsersListLayout view;
    private final JSONObject translate;

    public UsersController() {
        this.uiWindow = UIWindow.mainWindow;
        view = new UsersListLayout();
        translate = (JSONObject) Localization.get("users");

        onStart();
        uiWindow.setNewWindow(
                Localization.get("users", "list-title"),
                view.getPanel(),
                false,
                true,
                null
        );
    }

    public void onStart() {
        updateData();
        view.getTable().getColumnModel().getColumn(1).setCellEditor(new TableEditor());
        view.getTable().getColumnModel().getColumn(2).setCellEditor(new TableEditor());
        view.getTable().getColumnModel().getColumn(3).setCellEditor(new TableEditor());
        view.getSearch().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getRefreshButton().addActionListener(e -> this.updateData());
        view.getCreateButton().addActionListener(e -> uiWindow.startNewController("CreateUserDialog", view.getTableModel()));
        view.getTable().addMouseListener(new UserTableMouseListener(this));
        view.getTable().getColumnModel().getColumn(4).setCellRenderer(new TableRenderer());
        view.getTable().getColumnModel().getColumn(5).setCellRenderer(new TableRenderer());
    }

    public void openPasswordEditDialog(int rowIndex) {
        uiWindow.startNewController("UpdateUserPasswordDialog", view.getTableModel().getUser(rowIndex));
    }

    public void removeUser(int rowIndex) {
        if (UserDialog.showOption("Da li zelite ukloniti radnika " + view.getTableModel().getUserName(rowIndex)) == 1)
            return;

        onProcess();
        new Thread(() -> {
            try {
                UserUtility.delete(view.getTableModel().getUserID(rowIndex));
                view.getTableModel().removeRow(rowIndex);
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
            }
        }).start();
    }

    private void updateData() {
        onProcess();
        new Thread(() -> {
            try {
                view.getTableModel().setUserList(UserUtility.getUserList());
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
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
        view.getTextLoader().setText(translate.get("loading-users").toString());
    }

    static class TableEditor extends AbstractCellEditor implements TableCellEditor {
        private final JTextField component = new JTextField();
        private int colIndex, rowIndex;
        private TableUserModel tableModel;

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) {
            this.colIndex = colIndex;
            this.rowIndex = rowIndex;
            this.tableModel = (TableUserModel) table.getModel();

            component.setText((String) value);
            return component;
        }

        public Object getCellEditorValue() {
            UserModel user = new UserModel(tableModel.getUser(rowIndex));

            switch (colIndex) {
                case 1:
                    user.setName(component.getText());
                    break;
                case 2:
                    user.setEmail(component.getText());
                    break;
                case 3:
                    user.setPosition(component.getText());
                    break;
            }

            try {
                UserUtility.edit(user);
                tableModel.setUser(user, rowIndex);
                return component.getText();
            } catch (Exception exception) {
                UserDialog.showError(exception.getMessage());
                return null;
            }
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            if (super.isCellEditable(e)) {
                if (e instanceof MouseEvent)
                    return (((MouseEvent) e).getClickCount() == 2);

                if (e instanceof KeyEvent)
                    return ((KeyEvent) e).getKeyCode() == KeyEvent.VK_INSERT;
            }
            return false;
        }
    }

    static class TableRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int colIndex) {
            JButton button = new JButton();

            try {
                if (colIndex == 4) {
                    button.setIcon(new ImageIcon(
                            ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "lock-black-18dp.png"))
                    ));
                } else if (colIndex == 5) {
                    button.setIcon(new ImageIcon(
                            ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "delete-black-18dp.png"))
                    ));
                }
            } catch (Exception exception) {
                SystemLog.newLog(exception);
            }

            button.setBackground(new Color(224, 224, 224));
            button.setMaximumSize(new Dimension(30, 10));
            return button;
        }
    }
}
