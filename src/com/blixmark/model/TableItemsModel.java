package com.blixmark.model;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TableItemsModel extends AbstractTableModel {
    final private String[] columns = {"ID", "Naziv robe", "Sifra", "JM", "Na stanju", "", ""};
    private List<ItemModel> list = new LinkedList<>();
    private List<ItemModel> copyList = new LinkedList<>();
    private String tableSearchWord;

    public void setUserList(List<ItemModel> userList) {
        list = new LinkedList<>();
        list.addAll(userList);

        if(tableSearchWord != null) {
            copyList.clear();
            search(tableSearchWord);
            return;
        }

        this.fireTableDataChanged();
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
                        || content.getCode().toLowerCase().contains(tableSearchWord.toLowerCase())
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public int getItemID(int column) {
        return list.get(column).getId();
    }

    public void setItemAt(ItemModel item, int rowIndex) {
        list.set(rowIndex, item);
    }

    public void removeRow(int rowIndex) {
        list.remove(rowIndex);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public ItemModel getItemAt(int rowIndex) {
        return list.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getName();
            case 2:
                return list.get(rowIndex).getCode();
            case 3:
                return list.get(rowIndex).getJm();
            case 4:
                return list.get(rowIndex).getAmount();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0 && columnIndex < 5);
    }


    public void addValue(ItemModel itemModel) {
        if(list instanceof LinkedList)
            ((LinkedList) list).addFirst(itemModel);
        else
            list.add(itemModel);
        fireTableDataChanged();
    }
}
