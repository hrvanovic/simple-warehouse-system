package com.blixmark.model;

import com.blixmark.enumeration.BookType;
import javax.swing.*;

public class ComboIOModel extends AbstractListModel implements ComboBoxModel {
    private final String[] elements = {"Otpremnice", "Primke"};
    private int currentElement = 0;

    public BookType getSelectedBook() {
        switch (currentElement) {
            case 0:
                return BookType.DELIVERY;
            case 1:
                return BookType.RECEIPT;
            default:
                return null;
        }
    }

    @Override
    public int getSize() {
        return elements.length;
    }

    @Override
    public Object getElementAt(int index) {
        return elements[index];
    }

    @Override
    public void setSelectedItem(Object item) {
        if(item.equals(elements[0]))
            currentElement = 0;
        else if(item.equals(elements[1]))
            currentElement = 1;
    }

    @Override
    public Object getSelectedItem() {
        return elements[currentElement];
    }
}
