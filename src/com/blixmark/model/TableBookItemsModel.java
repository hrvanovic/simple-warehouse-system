package com.blixmark.model;

import com.blixmark.utilites.UserDialog;
import com.blixmark.utilites.bookmanager.BookUtility;
import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TableBookItemsModel extends AbstractTableModel {
    final private String[] columns = {"ID", "Sifra", "Naziv", "JM", "Kolicina", ""};
    List<BookItemModel> list = new LinkedList<>();
    private List<BookItemModel> copyList = new LinkedList<>();
    private String tableSearchWord;

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
                content.getItem().getName().toLowerCase().contains(tableSearchWord.toLowerCase())
                        || content.getItem().getCode().toLowerCase().contains(tableSearchWord.toLowerCase())
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public void setList(LinkedList<BookItemModel> list) {
        this.list = new LinkedList<>();
        this.list.addAll(list);

        if(tableSearchWord != null) {
            copyList.clear();
            search(tableSearchWord);
            return;
        }

        fireTableDataChanged();
    }

    public void addItem(BookItemModel item) {
        if(list instanceof LinkedList)
            ((LinkedList) list).addFirst(item);
        else
            list.add(item);
        fireTableDataChanged();
    }

    public int getItemID(int rowIndex) {
        return list.get(rowIndex).getId();
    }

    public String getItemName(int rowIndex) {
        return list.get(rowIndex).getItem().getName();
    }

    public void setItemAt(BookItemModel item, int rowIndex) {
        list.set(rowIndex, item);
    }

    public BookItemModel getItemAt(int rowIndex) {
        return list.get(rowIndex);
    }

    public void removeRow(int rowIndex) {
        list.remove(rowIndex);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public String getColumnName(int index) {
        return columns[index];
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
                return list.get(rowIndex).getItem().getCode();
            case 2:
                return list.get(rowIndex).getItem().getName();
            case 3:
                return list.get(rowIndex).getItem().getJm();
            case 4:
                return list.get(rowIndex).getAmount();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            BookUtility.editItem(getItemAt(rowIndex), Float.parseFloat(value.toString()));
            if(columnIndex == 4)
                list.get(rowIndex).setAmount(Float.parseFloat(value.toString()));
        } catch (Exception exception) {
            exception.printStackTrace();
            UserDialog.showError("Zao nam je, nije moguce azurirat kolicinu.");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 4);
    }
}
