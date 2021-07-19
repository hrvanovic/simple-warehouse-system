package com.blixmark.controller;
import com.blixmark.Config;
import com.blixmark.MainUIWindow;
import com.blixmark.utilites.SystemLog;
import com.blixmark.utilites.usermanager.LoginProcess;
import com.blixmark.view.LoginLayout;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginController {
    MainUIWindow uiWindow;
    LoginLayout view;

    public LoginController(MainUIWindow uiWindow) {
        this.uiWindow = uiWindow;
        this.view = new LoginLayout();

        onStart();
        uiWindow.setNewWindow(
                "Login",
                view.getPanel(),
                true,
                false,
                new Dimension(400,420)
        );
        uiWindow.setExtendedState(Frame.NORMAL);
        uiWindow.setMenuVisible(false);
    }

    public void onStart() {
        updateAutoFields();
        this.view.getPassField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                view.getButton().setEnabled(true);
            }
        });

        this.view.getPanel().setFocusable(true);
        this.view.getPanel().requestFocusInWindow();
        this.view.getPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    onLoginButtonClick();
            }
        });
        this.view.getEmailField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    onLoginButtonClick();
            }
        });

        this.view.getPassField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    onLoginButtonClick();
            }
        });

        this.view.getButton().addActionListener(e -> this.onLoginButtonClick());
    }

    public void onLoginButtonClick() {
        this.view.getButton().setEnabled(false);
        this.view.getWrongCreditials().setVisible(true);
        this.view.getWrongCreditials().setText("Molimo sačekajte...");

        new Thread(() -> {
            LoginProcess loginProcess = new LoginProcess();
            char[] passwordFieldText = this.view.getPassField().getPassword();
            boolean LoginStatus = loginProcess.checkLoginCreditials(
                    String.valueOf(this.view.getEmailField().getText()),
                    String.valueOf(passwordFieldText)
            );

            if (LoginStatus)
                try {
                    new HomeController();
                } catch (Exception e) { new SystemLog().newLog(e); }
            else
                this.addErrorMessage();

            this.view.getButton().setEnabled(true);
        }).start();

    }

    private void addErrorMessage() {
        if (this.view.numWrongCreditials <= 0)
            this.view.getWrongCreditials().setText("Pogresni podaci!");
        else
            this.view.getWrongCreditials().setText("Pogresni podaci! (" + this.view.numWrongCreditials + ")");

        this.view.getWrongCreditials().setVisible(true);
        this.view.numWrongCreditials++;
    }

    private void updateAutoFields() {
        this.view.getEmailField().setText(Config.get("email"));
    }
}
