package com.blixmark.model;

import com.blixmark.enumeration.BookType;
import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TableIOModel extends AbstractTableModel {
    private String[] columns = {};
    private LinkedList<IODataModel> allList = new LinkedList<>();
    private List<IODataModel> list = allList;
    private BookType currentType;

    public void setList(LinkedList<IODataModel> contentList) {
        allList = new LinkedList<>();
        allList.addAll(contentList);
        fireTableDataChanged();
    }

    public void search(String text) {
        if(currentType == BookType.RECEIPT) {
            list = allList.stream().filter(content ->
                    (content.getBook().getBookCode().toLowerCase().startsWith(text.toLowerCase())
                            || content.getBook().getBookDate().toLowerCase().startsWith(text.toLowerCase()))
                    && content.getBook().getBookType().equals(currentType)
            )
                    .collect(Collectors.toList());
        } else {
            list = allList.stream().filter(content ->
                    (content.getBook().getBookCode().toLowerCase().startsWith(text.toLowerCase())
                            || content.getBook().getBookClient().getName().toLowerCase().startsWith(text.toLowerCase())
                            || content.getBook().getBookDate().toLowerCase().startsWith(text.toLowerCase()))
                            && content.getBook().getBookType().equals(currentType)
            )
                    .collect(Collectors.toList());
        }
        fireTableDataChanged();
    }

    public void addList(LinkedList<IODataModel> contentList) {
        allList.addAll(contentList);
        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public void switchList(BookType bookType) {
        currentType = bookType;
        switch (bookType) {
            case RECEIPT:
                list = allList.stream().filter(content ->
                        BookType.RECEIPT.equals(content.getBook().getBookType()))
                        .collect(Collectors.toList());
                columns =  new String[] {"Sifra primke", "Dobavljač", "Datum", "Količina"};
                break;
            case DELIVERY:
                list = allList.stream().filter(content ->
                        BookType.DELIVERY.equals(content.getBook().getBookType()))
                        .collect(Collectors.toList());
                columns = new String[] {"Sifra otpremnice", "Primaoc", "Datum", "Količina"};
                break;
        }

        fireTableStructureChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
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
                return list.get(rowIndex).getBook().getBookCode();
            case 1:
                return list.get(rowIndex).getBook().getBookClient().getName();
            case 2:
                return list.get(rowIndex).getBook().getBookDate();
            case 3:
                return list.get(rowIndex).getAmount();
            default:
                return null;
        }
    }
}
