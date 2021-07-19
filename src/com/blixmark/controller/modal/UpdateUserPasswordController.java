package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.enumeration.BookType;
import com.blixmark.model.UserModel;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.usermanager.UserUtility;
import com.blixmark.view.UserPassEditDialog;
import org.json.simple.JSONObject;

import java.awt.*;

public class UpdateUserPasswordController {
    final private UserModel user;
    final private DialogUIWindow dialogUIWindow;
    final private UserPassEditDialog view;
    final private JSONObject translate;

    public UpdateUserPasswordController(UserModel user) {
        this.user = user;

        dialogUIWindow = new DialogUIWindow();
        view = new UserPassEditDialog();
        translate = (JSONObject) Localization.get("users");

        onStart();
        dialogUIWindow.setNewWindow(
                Localization.get("users", "edit-pass-title") + " " + user.getName(),
                view.getPanel(),
                true,
                false,
                new Dimension(400, 195)
        );
    }

    private void onStart() {
        view.getPosButton().addActionListener(e -> {
            onProcess();
            new Thread(() -> {
                try {
                    String userPassword = new String(view.getFieldPasswordFirst().getPassword());
                    String userPasswordVerify = new String(view.getFieldPasswordSecond().getPassword());

                    if(!userPassword.equals(userPasswordVerify))
                        throw new Exception(translate.get("pass-not-same").toString());

                    UserUtility.editPassword(user, userPassword);
                    onFinish();
                } catch (Exception exception) {
                    onException(exception.getMessage());
                }
            }).start();
        });
    }

    private void onException(String errText) {
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText(errText);
    }

    private void onFinish() {
        view.getTextLoader().setVisible(false);
        dialogUIWindow.close();
    }

    private void onProcess() {
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText(translate.get("please-wait").toString());
    }
}
