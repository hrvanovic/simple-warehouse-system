package com.blixmark.model;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TableUserModel extends AbstractTableModel {
    final private String[] columns = {"ID", "Ime", "Email", "Pozicija", "", ""};
    private List<UserModel> list = new LinkedList<>();
    private List<UserModel> copyList = new LinkedList<>();
    private String tableSearchWord;

    public void setUserList(LinkedList<UserModel> userList) {
        list.clear();
        list.addAll(userList);

        if(tableSearchWord != null) {
            copyList.clear();
            search(tableSearchWord);
            return;
        }

        fireTableDataChanged();
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
                || content.getEmail().toLowerCase().contains(tableSearchWord.toLowerCase())
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public void removeRow(int rowIndex) {
        list.remove(rowIndex);
        fireTableDataChanged();
    }

    public UserModel getUser(int rowIndex) {
        return list.get(rowIndex);
    }

    public void setUser(UserModel user, int rowIndex) {
        list.set(rowIndex, user);
    }

    public void addValue(UserModel user) {
        ((LinkedList) list).addFirst(user);
        fireTableDataChanged();
    }

    public int getUserID(int rowIndex) {
        return list.get(rowIndex).getId();
    }

    public String getUserName(int rowIndex) {
        return list.get(rowIndex).getName();
    }

    public String getUserEmail(int rowIndex) {
        return list.get(rowIndex).getEmail();
    }

    public String getUserPosition(int rowIndex) {
        return list.get(rowIndex).getPosition();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return (colIndex < 4 && colIndex != 0);
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
                return list.get(rowIndex).getEmail();
            case 3:
                return list.get(rowIndex).getPosition();
            default:
                return null;
        }
    }
}
