package com.blixmark.controller;

import com.blixmark.UIWindow;
import com.blixmark.utilites.AuthSession;
import com.blixmark.view.HomeLayout;

import java.awt.*;

public class HomeController {
    private final UIWindow uiWindow;
    private final HomeLayout view;

    public HomeController() {
        this.uiWindow = UIWindow.mainWindow;
        view = new HomeLayout();

        onStart();
        uiWindow.setNewWindow(
                "aaaa",
                view.getPanel(),
                false,
                true,
                new Dimension(1400,900)
        );
        uiWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    private void onStart() {
        view.getHelloText().setText(
                "<html>" +
                        "Dobro dosao/la " + AuthSession.getUser().getName() +
                        "<br /><br />" +
                        "E-Mail: support@blixmark.com <br />" +
                        "<a style='text-decoration: none' href='www.blixmark.com'>www.blixmark.com</a>" +
                        "<br /><br />" +
                        "2021 &copy Blixmark" +
                        "</html>"
        );
    }
}