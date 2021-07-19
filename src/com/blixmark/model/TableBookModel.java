package com.blixmark.model;

import com.blixmark.enumeration.BookType;
import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TableBookModel extends AbstractTableModel {
    private String[] columns = {};
    private List<BookModel> list = new LinkedList<>();
    private List<BookModel> copyList = new LinkedList<>();
    private String tableSearchWord;

    public void setList(LinkedList<BookModel> bookList, BookType bookType) {
        list = new LinkedList<>();
        list = bookList;

        if(tableSearchWord != null) {
            copyList.clear();
            search(tableSearchWord);
            return;
        }

        switch(bookType) {
            case DELIVERY:
                columns = new String[] {"ID", "Broj Otpremnice", "Primaoc", "Datum", ""};
                break;
            case RECEIPT:
                columns = new String[] {"ID", "Broj Primke", "Dobavljac", "Datum", "Broj Otpremnice dobavljaca", ""};
                break;
        }

        fireTableStructureChanged();
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
                content.getBookCode().toLowerCase().startsWith(tableSearchWord.toLowerCase())
                        || content.getBookDate().toLowerCase().startsWith(tableSearchWord.toLowerCase())
                        || content.getBookDate().toLowerCase().endsWith(tableSearchWord.toLowerCase())
                        || content.getBookClientCode().toLowerCase().startsWith(tableSearchWord.toLowerCase())
                || content.getBookClient().getName().toLowerCase().contains(tableSearchWord.toLowerCase())
        )
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public BookModel getBookAt(int rowIndex) {
        return list.get(rowIndex);
    }

    public void addRow(BookModel book) {
        if(list instanceof LinkedList)
            ((LinkedList) list).addFirst(book);
        else
            list.add(book);
        fireTableDataChanged();
    }

    public void removeRow(int rowIndex) {
        list.remove(rowIndex);
        fireTableDataChanged();
    }

    public int getBookID(int rowIndex) {
        return list.get(rowIndex).getBookID();
    }

    public String getBookCode(int rowIndex) { return list.get(rowIndex).getBookCode(); }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getBookID();
            case 1:
                return list.get(rowIndex).getBookCode();
            case 2:
                return ((list.get(rowIndex).getBookClient() == null) ? null : list.get(rowIndex).getBookClient().getName());
            case 3:
                return list.get(rowIndex).getBookDate();
            case 4:
                return list.get(rowIndex).getBookClientCode();
            default:
                return null;
        }
    }
}
