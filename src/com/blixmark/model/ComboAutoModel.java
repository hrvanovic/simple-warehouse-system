package com.blixmark.model;

import javax.swing.*;
import java.util.LinkedList;

public class ComboAutoModel<T extends AutoComboImpl, R> extends AbstractListModel<String> implements ComboBoxModel<String> {
    LinkedList<T> list = new LinkedList<>();
    JComboBox<R> combo;
    String currentElement = null;

    public ComboAutoModel(LinkedList<T> list, JComboBox<R> combo) {
        list.add(0, null);
        this.list.addAll(list);
        this.combo = combo;
    }

    public void setList(LinkedList<T> list) {
        this.list.addAll(list);
    }

    public T getSelectedClient() {
        if(combo.getSelectedIndex() < 0)
            return null;
        return list.get(combo.getSelectedIndex());
    }

    public boolean isSelected() {
        return (combo.getSelectedIndex() > 0);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getSelectedItem() {
        return currentElement;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        currentElement = (String) anItem;
    }


    @Override
    public String getElementAt(int index) {
        if(index <= 0)
            return null;
        return list.get(index).getName();
    }
}
