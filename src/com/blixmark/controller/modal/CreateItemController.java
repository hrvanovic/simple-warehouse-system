package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.enumeration.BookType;
import com.blixmark.model.ItemModel;
import com.blixmark.model.TableItemsModel;
import com.blixmark.enumeration.ItemType;
import com.blixmark.utilites.ItemUtility;
import com.blixmark.utilites.Validator;
import com.blixmark.view.CreateItemDialog;

import java.awt.*;

public class CreateItemController {
    final private ItemType itemType;
    final private TableItemsModel itemTableModel;
    final private DialogUIWindow dialogUIWindow;
    final private CreateItemDialog view;

    public CreateItemController(ItemType itemType, TableItemsModel itemTableModel) {
        this.itemType = itemType;
        this.itemTableModel = itemTableModel;
        dialogUIWindow = new DialogUIWindow();
        view = new CreateItemDialog();

        onStart();
        dialogUIWindow.setNewWindow(
                "Nova roba",
                view.getPanel(),
                true,
                false,
                new Dimension(400, 300)
        );
    }

    private void onStart() {
        view.getPosButton().addActionListener(event -> {
            onLoading();
            new Thread(() -> {
                try {
                    String itemName = view.getNameField().getText();
                    String itemCode = view.getCodeField().getText();
                    String itemJM = view.getJMField().getText();

                    if(!Validator.validateAmount(view.getAmountField().getText()))
                        throw new Exception("Podaci nisu validni!");

                    float itemAmount = Float.parseFloat(view.getAmountField().getText());
                    ItemModel itemModel = new ItemModel(itemName, itemCode, itemJM, itemAmount, itemType);

                    ItemUtility.create(itemModel, itemType);
                    itemTableModel.addValue(itemModel);

                    onLoaded();
                } catch (Exception e) {
                    onError(e.getMessage());
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private void onError(String errMsg) {
        view.getPanel().setEnabled(true);
        view.getLoaderText().setVisible(true);
        view.getLoaderText().setText(errMsg);
    }

    private void onLoaded() {
        dialogUIWindow.close();
    }

    private void onLoading() {
        view.getPanel().setEnabled(true);
        view.getLoaderText().setVisible(true);
        view.getLoaderText().setText("Molimo pricekajte...");
    }
}
