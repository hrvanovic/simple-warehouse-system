package com.blixmark.controller;

import com.blixmark.Config;
import com.blixmark.UIWindow;
import com.blixmark.enumeration.ItemType;
import com.blixmark.model.ItemModel;
import com.blixmark.model.TableItemsModel;
import com.blixmark.utilites.*;
import com.blixmark.view.ItemsLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.EventObject;

public class ItemsController {
    final private UIWindow uiWindow;
    final private ItemType itemType;
    final private ItemsLayout view;


    public ItemsController(ItemType itemType) {
        this.uiWindow = UIWindow.mainWindow;
        this.itemType = itemType;
        view = new ItemsLayout();

        onStart();
        uiWindow.setNewWindow(
                Localization.get(itemType, "list-title"),
                view.getPanel(),
                false,
                true,
                null
        );
    }

    public void onStart() {
        updateData();
        try {
            view.getRefreshButton().setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "refresh-black-18dp.png"))
            ));
            view.getCreateButton().setIcon(new ImageIcon(
                    ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "add-black-18dp.png"))
            ));

            view.getTable().getColumnModel().getColumn(1).setCellEditor(new TableEditor());
            view.getTable().getColumnModel().getColumn(2).setCellEditor(new TableEditor());
            view.getTable().getColumnModel().getColumn(3).setCellEditor(new TableEditor());
            view.getTable().getColumnModel().getColumn(4).setCellEditor(new TableEditor());

            view.getTable().getColumnModel().getColumn(5).setCellRenderer(new TableRenderer());
            view.getTable().getColumnModel().getColumn(6).setCellRenderer(new TableRenderer());
        } catch (Exception exception) {
            UserDialog.showError(exception.getMessage());
        }

        // Listeners
        view.getRefreshButton().addActionListener(e -> updateData());
        view.getCreateButton().addActionListener(e -> uiWindow.startNewController(itemType + "_CREATE", view.getTableModel()));
        view.getTable().addMouseListener(new TableMouseListener(this));
        view.getSearch().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
    }

    private void updateData() {
        onProcess();
        new Thread(() -> {
            try {
                view.getTableModel().setUserList(ItemUtility.getItemList(itemType));
                onFinish();
            } catch (Exception e) {
                onException(e.getMessage());
            }
        }).start();
    }

    public void deleteItem(int rowIndex) {
        String optionText = Localization.get("items", "delete-prefix") + " \"" + view.getTableModel().getItemAt(rowIndex).getName() + "\" ?";
        if(UserDialog.showOption(optionText) == 0) {
            try {
                if(!ItemUtility.delete(view.getTableModel().getItemAt(rowIndex)))
                    return;
                view.getTableModel().removeRow(rowIndex);
            } catch (Exception exception) {
                UserDialog.showError(exception.getMessage());
            }
        }
    }

    public void openIODialog(int rowIndex) {
        uiWindow.startNewController("IODIALOG", view.getTableModel().getItemAt(rowIndex), this);
    }

    static class TableEditor extends AbstractCellEditor implements TableCellEditor {
        private TableItemsModel tableModel;
        private int rowIndex, colIndex;
        final private JTextField component = new JTextField();

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) {
            this.tableModel = (TableItemsModel) table.getModel();
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            component.setText(value.toString());

            return component;
        }

        @Override
        public Object getCellEditorValue() {
            ItemModel item = new ItemModel(tableModel.getItemAt(rowIndex));

            switch (colIndex) {
                case 1:
                    item.setName(component.getText());
                    break;
                case 2:
                    item.setCode(component.getText());
                    break;
                case 3:
                    item.setJm(component.getText());
                    break;
                case 4:
                    if(!Validator.validateAmount(component.getText())) {
                        UserDialog.showError(Localization.get("items", "error-amount-prefix") + " \"" + component.getText() + "\"!");
                        return null;
                    }
                    item.setAmount(Float.parseFloat(component.getText()));
                    break;
            }

            try {
                ItemUtility.edit(item);
                tableModel.setItemAt(item, rowIndex);
                return component.getText();
            } catch (Exception exception) {
                UserDialog.showError(exception.getMessage());
                return null;
            }
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            if(super.isCellEditable(e)) {
                if(e instanceof MouseEvent) {
                    return (((MouseEvent) e).getClickCount() == 2);
                } else if(e instanceof KeyEvent) {
                    return ((KeyEvent) e).getKeyCode() == KeyEvent.VK_INSERT;
                }
            }
            return false;
        }
    }

    static class TableMouseListener extends MouseAdapter {
        final private ItemsController parent;

        public TableMouseListener(ItemsController parent) {
            this.parent = parent;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            JTable tableMouseEv = (JTable) e.getSource();
            Point tablePoint = e.getPoint();
            int pointedCol = tableMouseEv.columnAtPoint(tablePoint);
            int pointedRow = tableMouseEv.rowAtPoint(tablePoint);

            if(pointedCol == 5 && tableMouseEv.getSelectedRow() != -1)
                parent.openIODialog(pointedRow);

            if(pointedCol == 6 && tableMouseEv.getSelectedRow() != -1)
                parent.deleteItem(pointedRow);
        }
    }

    static class TableRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = new JButton();
            try {
                if(column == 5)
                    button.setIcon(new ImageIcon(
                            ImageIO.read(new File(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "oi-black-22px.png"))
                    ));
                else if(column == 6)
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
        view.getTextLoader().setText(Localization.get(itemType, "loading-items"));
    }
}