package com.blixmark.model;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TableClientModel extends AbstractTableModel {
    final private String[] columns = {"ID", "Ime", "Opis", ""};
    private List<ClientModel> list = new LinkedList<>();
    private List<ClientModel> copyList = new LinkedList<>();
    private String tableSearchWord;


    public void setClientList(LinkedList<ClientModel> clientList) {
        list = new LinkedList<>();
        list.addAll(clientList);

        if(tableSearchWord != null) {
            copyList.clear();
            search(tableSearchWord);
            return;
        }
    }

    public void search(String text) {
        tableSearchWord = text;
        if(copyList.size() == 0)
            copyList.addAll(list);

        if(tableSearchWord == null) {
            list.clear();
            list.addAll(copyList);
            copyList.clear();
            return;
        }

        list = copyList.stream().filter(content ->
                content.getName().toLowerCase().contains(tableSearchWord.toLowerCase())
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public ClientModel getClient(int rowIndex) {
        return list.get(rowIndex);
    }

    public void setClient(ClientModel client, int rowIndex) {
        list.set(rowIndex, client);
    }

    public void removeRow(int rowIndex) throws Exception {
        list.remove(rowIndex);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getName();
            case 2:
                return list.get(rowIndex).getDesc();
            default:
                return null;
        }
    }

    public void addValue(ClientModel clientModel) {
        if(list instanceof LinkedList)
            ((LinkedList) list).addFirst(clientModel);
        else
            list.add(clientModel);
        fireTableDataChanged();
    }
}
