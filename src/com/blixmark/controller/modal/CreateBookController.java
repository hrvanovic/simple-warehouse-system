package com.blixmark.controller.modal;

import com.blixmark.DialogUIWindow;
import com.blixmark.controller.BooksController;
import com.blixmark.model.BookModel;
import com.blixmark.model.ClientModel;
import com.blixmark.model.UserModel;
import com.blixmark.utilites.AuthSession;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.UserDialog;
import com.blixmark.enumeration.BookType;
import com.blixmark.utilites.bookmanager.BookUtility;
import com.blixmark.utilites.ClientUtility;
import com.blixmark.utilites.usermanager.UserUtility;
import com.blixmark.view.CreateBookDialog;

import java.awt.*;

public class CreateBookController {
    final private BooksController parentController;
    final private BookType bookType;
    final private CreateBookDialog view;

    private DialogUIWindow dialogUIWindow;

    public CreateBookController(BookType bookType, Object parentController) {
        this.bookType = bookType;
        this.parentController = (BooksController) parentController;
        dialogUIWindow = new DialogUIWindow();
        view = new CreateBookDialog();

        onStart();
        dialogUIWindow.setNewWindow(
                Localization.get(bookType, "title-create"),
                view.getPanel(),
                true,
                false,
                new Dimension(400, (bookType == BookType.RECEIPT ? 420 : 380))
        );
    }

    private void updateData() {
        if (bookType == BookType.DELIVERY)
            view.getPanelCodeClient().setVisible(false);
        try {
            if (AuthSession.getUser() != null) {
                view.getFieldUser().setSelectedItem(AuthSession.getUser().getName());
                view.getFieldUser().getEditor().setItem(AuthSession.getUser().getName());
            }
            view.getFieldCode().setText(BookUtility.getUniqueCode(bookType));
            view.getFieldClient().setList(ClientUtility.getClientList());
            view.getFieldUser().setList(UserUtility.getUserList());
            view.getFieldDate().setText(BookUtility.getCurrentDate());
            view.getFieldPlace().setText(BookUtility.getLastPlace(bookType));
        } catch (Exception exception) {
            UserDialog.showError("Doslo je do greske!");
        }
    }

    private void onStart() {
        updateData();
        view.getPosButton().addActionListener(e -> {
            onProcess();
            try {
                UserModel bookUser = null;
                ClientModel bookClient = null;

                if (view.getFieldUser().getSelected() != null)
                    bookUser = UserUtility.getUser(view.getFieldUser().getSelected().getId());

                if (view.getFieldClient().getSelected() != null)
                    bookClient = ClientUtility.get(view.getFieldClient().getSelected().getId());

                BookModel book = new BookModel(
                        view.getFieldCode().getText(),
                        bookUser,
                        bookClient,
                        view.getFieldDate().getText(),
                        view.getFieldPlace().getText(),
                        view.getFieldCodeClient().getText(),
                        bookType
                );

                BookUtility.create(book);
                parentController.getTableModel().addRow(book);
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
                exception.printStackTrace();
            }
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
        view.getTextLoader().setText("Molimo sačekajte...");
    }

    public void customComponents() {
        this.view.getLabelClient().setText("Klijent");
    }
}
