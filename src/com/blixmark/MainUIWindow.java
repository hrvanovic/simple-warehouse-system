package com.blixmark;

import javax.swing.*;

public class MainUIWindow extends UIWindow {
    public MainUIWindow() {
        super();
        if(mainWindow == null)
            mainWindow = this;
    }
}