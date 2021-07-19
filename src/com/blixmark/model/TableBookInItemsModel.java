package com.blixmark.model;

import com.blixmark.enumeration.ItemType;
import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TableBookInItemsModel extends AbstractTableModel {
    final private String[] columns = {"Naziv", "Sifra", "JM", "Kolicina"};
    private List<ItemModel> allList = new LinkedList<>();
    private List<ItemModel> list = new LinkedList<>();
    private ItemType currentType = ItemType.PRO;
    private String tableSearchWord;

    public void search(String text) {
        tableSearchWord = text;

        if(tableSearchWord == null) {
            switchList(currentType);
            return;
        }

        list = allList.stream().filter(content ->
                (content.getName().toLowerCase().contains(tableSearchWord.toLowerCase())
                        || content.getCode().toLowerCase().contains(tableSearchWord.toLowerCase()))
                && content.getType().equals(currentType)
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public void addList(List<ItemModel> list) {
        allList.addAll(list);
        switchList(currentType);
        fireTableDataChanged();
    }

    public ItemModel getItemAt(int rowIndex) {
        return list.get(rowIndex);
    }

    public void switchList(ItemType itemType) {
        currentType = itemType;
        list = allList.stream()
                .filter(content -> itemType.equals(content.getType()))
                .collect(Collectors.toList());
        fireTableDataChanged();
    }

    public void setList(List<ItemModel> list) {
        this.allList = new LinkedList<>();
        this.allList.addAll(list);
        this.list = allList;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public String getColumnName(int colIndex) {
        return columns[colIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        switch (colIndex) {
            case 0:
                return list.get(rowIndex).getName();
            case 1:
                return list.get(rowIndex).getCode();
            case 2:
                return list.get(rowIndex).getJm();
            case 3:
                return list.get(rowIndex).getAmount();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }
}
