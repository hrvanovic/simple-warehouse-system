package com.blixmark.utilites;

import com.blixmark.model.AutoComboImpl;
import com.blixmark.model.ComboAutoModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class AutoCombobox<T extends AutoComboImpl> extends JComboBox<String> {
    public int caretPos = 0;
    public JTextField textField = null;

    public AutoCombobox(LinkedList<T> list) {
        setEditor(new BasicComboBoxEditor());
        setEditable(true);
        setModel(new ComboAutoModel<>(list, this));
    }

    public boolean isSelected() {
        return ((ComboAutoModel<T, String>) getModel()).isSelected();
    }

    public void setList(LinkedList<T> list) {
        ((ComboAutoModel<T, String>) getModel()).setList(list);
    }

    public T getSelected() {
        return ((ComboAutoModel<T, String>) getModel()).getSelectedClient();
    }

    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        textField.setText(getItemAt(index));
        textField.setSelectionEnd(caretPos + textField.getText().length());
        if(textField.getText().length() > caretPos)
            textField.moveCaretPosition(caretPos);
    }

    public void setEditor(ComboBoxEditor editor) {
        super.setEditor(editor);
        if(editor.getEditorComponent() instanceof JTextField) {
            textField = (JTextField) editor.getEditorComponent();
            textField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent ke) {

                    if(textField.getText().equals("")) {
                        setSelectedItem("");
                        return;
                    }

                    char key = ke.getKeyChar();
                    if (!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;
                    caretPos = textField.getCaretPosition();
                    String text;

                    try {
                        text = textField.getText(0, caretPos);
                    } catch (javax.swing.text.BadLocationException e) {
                        return;
                    }

                    for (int i= 0; i < getItemCount(); i++) {
                        String element = getItemAt(i);
                        if(element == null)
                            element = "";
                        if (element.toLowerCase().startsWith(text.toLowerCase())) {
                            setSelectedIndex(i);
                            return;
                        } else
                            setSelectedItem("");
                    }
                }
            });
        }
    }
}