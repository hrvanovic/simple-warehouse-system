package com.blixmark.controller;

import com.blixmark.UIWindow;
import com.blixmark.model.ClientModel;
import com.blixmark.model.TableClientModel;
import com.blixmark.utilites.*;
import com.blixmark.view.ClientListLayout;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;


public class ClientsController {
    private final UIWindow uiWindow;
    public final ClientListLayout view;

    public ClientsController() {
        uiWindow = UIWindow.mainWindow;
        view = new ClientListLayout();

        onStart();
        uiWindow.setNewWindow(
                Localization.get("clients", "list-title"),
                view.getPanel(),
                false,
                true,
                null
        );
    }

    private void onStart() {
        updateItemsTable();
        view.getTable().getColumnModel().getColumn(1).setCellEditor(new TableEditor());
        view.getTable().getColumnModel().getColumn(2).setCellEditor(new TableEditor());
        view.getTable().addMouseListener(new TableMouseListener(this));
        view.getSearch().addKeyListener(new TableSearchListener<>(view.getTableModel(), view.getTable()));
        view.getRefreshButton().addActionListener(e -> updateItemsTable());
        view.getCreateButton().addActionListener(e -> uiWindow.startNewController("CreateClientDialog", this));
        view.getTable().getColumnModel().getColumn(3).setCellRenderer(new DeleteColumnTableRenderer());
    }

    private void updateItemsTable() {
        onProcess("loading-clients");
        new Thread(() -> {
            try {
                view.getTableModel().setClientList(ClientUtility.getClientList());
               onFinish();
            } catch (Exception e) {
                onError(e.getMessage());
            }
        }).start();
    }

    public void deleteClient(int rowIndex) {
        String optionText = Localization.get("items", "delete-prefix") + " \"" + view.getTableModel().getClient(rowIndex).getName() + "\" ?";
        if(UserDialog.showOption(optionText) == 0) {
            try {
                ClientUtility.delete(view.getTableModel().getClient(rowIndex));
                view.getTableModel().removeRow(rowIndex);
            } catch (Exception exception) {
                UserDialog.showError(exception.getMessage());
            }
        }
    }

    public void onError(String errMsg) {
        view.getTableScrollPane().setVisible(false);
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText(errMsg);
    }

    public void onFinish() {
        view.getTextLoader().setVisible(false);
        view.getTableScrollPane().setVisible(true);
    }

    public void onProcess(String processText) {
        view.getTableScrollPane().setVisible(false);
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText(processText);
    }

    static class TableMouseListener extends MouseAdapter {
        final private ClientsController parent;

        public TableMouseListener(ClientsController parent) {
            this.parent = parent;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            JTable tableMouseEv = (JTable) e.getSource();
            Point tablePoint = e.getPoint();
            int pointedCol = tableMouseEv.columnAtPoint(tablePoint);
            int pointedRow = tableMouseEv.rowAtPoint(tablePoint);

            if(pointedCol == 3 && tableMouseEv.getSelectedRow() != -1)
                parent.deleteClient(pointedRow);
        }
    }

    static class TableEditor extends AbstractCellEditor implements TableCellEditor {
        private final JTextField componentField = new JTextField();
        private final JTextArea componentArea = new JTextArea();
        private int colIndex, rowIndex;
        private TableClientModel tableModel;
        private JTable table;

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) {
            this.colIndex = colIndex;
            this.rowIndex = rowIndex;
            this.tableModel = (TableClientModel) table.getModel();
            this.table = table;

            if(colIndex == 2) {
                componentArea.setText((String) value);
                table.setRowHeight(rowIndex, 90);
                return componentArea;
            } else {
                componentField.setText((String) value);
                return componentField;
            }
        }

        @Override
        protected void fireEditingStopped() {
            table.setRowHeight(38);
            super.fireEditingStopped();
        }

        @Override
        protected void fireEditingCanceled() {
            super.fireEditingCanceled();
            table.setRowHeight(38);
        }

        @Override
        public void removeCellEditorListener(CellEditorListener l) {
            super.removeCellEditorListener(l);
            table.setRowHeight(38);
        }

        @Override
        public Object getCellEditorValue() {
            ClientModel client = new ClientModel(tableModel.getClient(rowIndex));

            switch (colIndex) {
                case 1:
                    client.setName(componentField.getText());
                    break;
                case 2:
                    client.setDesc(componentArea.getText());
                    break;
            }

            table.setRowHeight(38);

            try {
                ClientUtility.edit(client);
                tableModel.setClient(client, rowIndex);

                if(colIndex == 2)
                    return componentArea.getText();
                else
                    return componentField.getText();

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
}
