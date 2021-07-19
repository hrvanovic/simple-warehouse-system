package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.controller.ClientsController;
import com.blixmark.model.ClientModel;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.ClientUtility;
import com.blixmark.view.AddClientDialog;

import java.awt.*;

public class CreateClientController {
    final private ClientsController parentController;
    final private DialogUIWindow dialogUIWindow;
    final private AddClientDialog view;

    public CreateClientController(Object parentController) {
        this.parentController = (ClientsController) parentController;
        dialogUIWindow = new DialogUIWindow();
        view = new AddClientDialog();

        onStart();
        dialogUIWindow.setNewWindow(
                Localization.get("clients", "title-create"),
                view.getPanel(),
                true,
                false,
                new Dimension(400,250)
        );
    }

    private void onStart() {
        view.getPosButton().addActionListener(event -> {
            onProcess();
            new Thread(() -> {
                try {
                    String clientName = view.getFieldName().getText();
                    String clientDesc = view.getFieldDesc().getText();
                    ClientModel newClient = new ClientModel(clientName, clientDesc);

                    ClientUtility.create(newClient);
                    parentController.view.getTableModel().addValue(newClient);
                    dialogUIWindow.close();

                    onFinish();
                    parentController.onFinish();
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

    private void onFinish() {
        dialogUIWindow.close();
    }

    private void onProcess() {
        view.getPanel().setEnabled(true);
        view.getLoaderText().setVisible(true);
        view.getLoaderText().setText("Molimo pricekajte...");
    }
}
