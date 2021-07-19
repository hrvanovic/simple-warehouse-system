package com.blixmark;

import com.blixmark.controller.*;
import com.blixmark.controller.modal.*;
import com.blixmark.model.*;
import com.blixmark.enumeration.BookType;
import com.blixmark.enumeration.ItemType;
import com.blixmark.utilites.Localization;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIWindow {
    private JFrame jFrame;
    protected final MainMenu mainMenu;
    public static UIWindow mainWindow;

    public UIWindow() {
        jFrame = new JFrame();
        mainMenu = new MainMenu();
        jFrame.setJMenuBar(mainMenu);
        jFrame.setIconImage(new ImageIcon(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "icons" + Config.separator + "icon.png").getImage());

        if(this instanceof MainUIWindow)
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UIManager.put("OptionPane.background", new ColorUIResource(255,255,255));
        UIManager.put("Panel.background", new ColorUIResource(255,255,255));
    }

    public void setNewWindow(String title, JPanel panel, boolean locateToCenter, boolean menu, Dimension prefferedSize) {
        jFrame.setTitle(title);
        jFrame.setContentPane(panel);

        mainMenu.setVisible(menu);

        if(prefferedSize != null)
            jFrame.setSize(prefferedSize);

        if(locateToCenter && this instanceof MainUIWindow)
            jFrame.setLocationRelativeTo(null);

        if(locateToCenter && this instanceof DialogUIWindow) {
            Frame mainFrame = Frame.getFrames()[0];
            jFrame.setLocationRelativeTo(mainFrame);
        }

        jFrame.setVisible(true);
    }

    public void setExtendedState(int state) {
        jFrame.setExtendedState(state);
    }

    public void setMenuVisible(boolean visible) {
        mainMenu.setVisible(visible);
    }

    public void close() {
        this.jFrame.dispose();
    }

    public void startNewController(String controllerID, Object parent) {
        switch (controllerID) {
            case "CREATEBOOK_RECEIPT":
                new CreateBookController(BookType.RECEIPT, parent);
                break;
            case "CREATEBOOK_DELIVERY":
                new CreateBookController(BookType.DELIVERY, parent);
                break;
            case "AIB":
                new AddItemToBookController(parent);
                break;
            case "CreateClientDialog":
                new CreateClientController(parent);
                break;
        }
    }

    public void startNewController(String controllerID, UserModel userModel) {
        switch (controllerID) {
            case "UpdateUserPasswordDialog":
                new UpdateUserPasswordController(userModel);
                break;
        }
    }

    public void startNewController(String controllerID, TableUserModel tableUserModel) {
        switch (controllerID) {
            case "CreateUserDialog":
                new CreateUserController(tableUserModel);
                break;
        }
    }

    public void startNewController(String controllerID, ItemModel item, ItemsController parent) {
        switch(controllerID) {
            case "IODIALOG":
                new IOItemController(item, parent);
                break;
        }
    }

    public void startNewController(String controllerID, BookModel book) {
        switch(controllerID) {
            case "BOOKEDIT":
                new BookEditController(book);
                break;
        }
    }

    public void startNewController(String controllerID, TableItemsModel itemTableModel) {
        switch (controllerID) {
            case "PRO_CREATE":
                new CreateItemController(ItemType.PRO, itemTableModel);
                break;
            case "RAW_CREATE":
                new CreateItemController(ItemType.RAW, itemTableModel);
                break;
            case "COM_CREATE":
                new CreateItemController(ItemType.COM, itemTableModel);
                break;
        }
    }

    public static void startNewController(String controllerID) {
        switch (controllerID) {
            case "PRO":
                new ItemsController(ItemType.PRO);
                break;
            case "RAW":
                new ItemsController(ItemType.RAW);
                break;
            case "COM":
                new ItemsController(ItemType.COM);
                break;
            case "DELIVERY":
                new BooksController(BookType.DELIVERY);
                break;
            case "RECEIPT":
                new BooksController(BookType.RECEIPT);
                break;
            case "CLIENTS":
                new ClientsController();
                break;
            case "USERS":
                new UsersController();
                break;
        }
    }

    class MainMenu extends JMenuBar {
        public MenuItem active;
        public MainMenu() {
            JSONArray categories = (JSONArray) ((JSONObject) Localization.get("menu")).get("list");
            for(int c = 0; c < categories.size(); c++) {
                JSONArray menus = (JSONArray) ((JSONObject) categories.get(c)).get("list");
                for(int m = 0; m < menus.size(); m++) {
                    JSONObject menu = (JSONObject) menus.get(m);
                    JMenu jmenu = new MenuItem(
                            new MenuModel(
                                    menu.get("title").toString(),
                                    menu.get("action").toString()
                            )
                    );
                    jmenu.addMouseListener(new MenuAction());
                    add(jmenu);
                }
                if(c < menus.size()) {
                    JMenu separator = new JMenu("|");
                    separator.setEnabled(false);
                    add(separator);
                }
            }

            setOpaque(true);
            setMinimumSize(new Dimension(getMaximumSize().width, 40));
            setPreferredSize(new Dimension(getMaximumSize().width, 40));
            setBackground(new Color(224, 224, 224, 255));
        }

        class MenuItem extends JMenu {
            private MenuModel model;

            public MenuItem(MenuModel model) {
                super(model.getTitle());
                this.model = model;
                this.setForeground(new Color(51, 51, 51));
            }

            public MenuModel getMenuModel() {
                return model;
            }

            public void setActive() {
                if(active != null) {
                    active.setBackground(new Color(224, 224, 224, 255));
                    active.setForeground(new Color(51, 51, 51));
                }
                setBackground(new Color(244,244,244));
                setForeground(Color.darkGray);
                setOpaque(true);
                active = this;
            }
        }
    }
    class MenuAction extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            MainMenu.MenuItem jMenu = (MainMenu.MenuItem) event.getSource();
            jMenu.setActive();
            UIWindow.startNewController(jMenu.getMenuModel().getAction());
        }
    }
}