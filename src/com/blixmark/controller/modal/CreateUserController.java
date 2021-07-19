package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.enumeration.BookType;
import com.blixmark.model.UserModel;
import com.blixmark.model.TableUserModel;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.usermanager.UserUtility;
import com.blixmark.view.CreateUserDialog;

import java.awt.*;

public class CreateUserController {
    final private DialogUIWindow dialogUIWindow;
    final private CreateUserDialog view;
    final private TableUserModel tableModel;

    public CreateUserController(TableUserModel tableModel) {
        this.tableModel = tableModel;
        dialogUIWindow = new DialogUIWindow();
        view = new CreateUserDialog();

        onStart();
        dialogUIWindow.setNewWindow(
                Localization.get("users", "title-create"),
                view.getPanel(),
                true,
                false,
                new Dimension(400, 280)
        );
    }

    private void onStart() {
        view.getPosButton().addActionListener(e -> {
            onProcess();
            new Thread(() -> {
                try {
                    UserModel newUser = new UserModel(
                            view.getNameField().getText(),
                            view.getEmailField().getText(),
                            view.getPositionField().getText()
                    );
                    String userPassword = view.getPasswordField().getPassword().toString();
                    UserUtility.create(
                            newUser,
                            userPassword
                    );
                    tableModel.addValue(newUser);
                    onFinish();
                } catch (Exception exception) {
                    onError(exception.getMessage());
                }
            }).start();
        });
    }

    private void onError(String errorMsg) {
        view.getPanel().setEnabled(true);
        view.getLoaderText().setVisible(true);
        view.getLoaderText().setText(errorMsg);
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
