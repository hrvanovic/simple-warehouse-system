package com.blixmark.controller;

import com.blixmark.Config;
import com.blixmark.UIWindow;
import com.blixmark.enumeration.BookType;
import com.blixmark.model.PrintBookModel;
import com.blixmark.model.TableBookItemsModel;
import com.blixmark.model.BookModel;
import com.blixmark.utilites.DeleteColumnTableRenderer;
import com.blixmark.utilites.Localization;
import com.blixmark.utilites.UserDialog;
import com.blixmark.utilites.bookmanager.*;
import com.blixmark.utilites.ClientUtility;
import com.blixmark.utilites.usermanager.UserUtility;
import com.blixmark.view.BookEditLayout;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookEditController {
    private final UIWindow uiWindow;
    private BookEditLayout view;
    private BookModel book;

    public BookEditController(BookModel book) {
        this.uiWindow = UIWindow.mainWindow;
        this.book = book;
        view = new BookEditLayout();

        onStart();
        uiWindow.setNewWindow(
                Localization.get(book.getBookType(), "book-edit-prefix") + " \"" + book.getBookCode() + "\"",
                view.getPanel(),
                false,
                true,
                null
        );
    }

    private void onStart() {
        updateData();
        view.getTable().getColumnModel().getColumn(5).setCellRenderer(new DeleteColumnTableRenderer());
        view.getTable().addMouseListener(new BookItemsTableMouseListener(this));
        view.getRefreshButton().addActionListener(e -> onUpdate());
        view.getSpremiButton().addActionListener(e -> saveFormData());
        view.getCreateButton().addActionListener(e -> uiWindow.startNewController("AIB", this));
        view.getButtonPrint().addActionListener(e -> {
            onProcess();
            new Thread(() -> {
                try {
                    List<PrintBookModel> list = BookUtility.getItemsPrintList(book);;
                    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);

                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("booksParam", itemsJRBean);
                    parameters.put("pageTitle", Localization.get("company", "name"));
                    parameters.put("clientTitle", Localization.get(book.getBookType(), "clientTitle") + ":");
                    parameters.put("clientDesc", ((book.getBookClient().getName() != null) ? book.getBookClient().getName() + " - " + book.getBookClient().getDesc() : ""));
                    parameters.put("bookPlace", ((book.getBookPlace().length() > 1) ? Localization.get("books", "print-place-prefix") + ": " + book.getBookPlace() : ""));
                    parameters.put("bookTitle", Localization.get(book.getBookType(), "print-title"));
                    parameters.put("bookCode", book.getBookCode() + " (" + book.getBookDate() + ")");
                    parameters.put("companyDesc", Localization.get("company", "desc"));
                    parameters.put("bookClientCode", ((book.getBookClientCode() != null) ? "Broj otpremnice dobavljaca: " + book.getBookClientCode() : ""));
                    parameters.put("bookUser", ((book.getBookClientCode() != null) ? book.getBookUser().getName() : ""));
                    parameters.put("bookDesc", book.getBookComment());

                    parameters.put("bookControllerSignLabel", Localization.get("books", "controllerSignLabel"));
                    parameters.put("bookUserSignLabel", Localization.get("books", "userSignLabel"));
                    parameters.put("bookDriverSignLabel", Localization.get("books", "driverSignLabel"));
                    parameters.put("bookClientSignLabel", Localization.get("books", "clientSignLabel"));

                    InputStream input = new FileInputStream(new File(Config.getDirectoryPath() + "print/book.jrxml"));
                    JasperDesign jasperDesign = JRXmlLoader.load(input);
                    jasperDesign.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    jasperReport.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
                    jasperPrint.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setTitle("Print \"" + book.getBookCode() + "\"");
                    jasperViewer.show();

                    onFinish();
                } catch (Exception exception) {
                    onException(exception.getMessage());
                }
            }).start();
        });
    }

    private void saveFormData() {
        onProcess();
        new Thread(() -> {
            try {
                book.setBookCode(view.getFieldCode().getText());
                if (book.getBookUser() != null)
                    book.setBookUser(view.getFieldUser().getSelected());
                if (book.getBookClient() != null)
                    book.setBookClient(view.getFieldClient().getSelected());
                book.setBookDate(view.getFieldDate().getText());
                book.setBookPlace(view.getFieldPlace().getText());
                book.setBookComment(view.getFieldComment().getText());
                book.setBookClientCode(view.getFieldClientCode().getText());
                BookUtility.edit(book);
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
                exception.printStackTrace();
            }
        }).start();
    }

    private void updateFormData() throws Exception {
        book = BookUtility.get(book.getBookID(), book.getBookType());

        view.getFieldClient().setList(ClientUtility.getClientList());
        view.getFieldUser().setList(UserUtility.getUserList());
        if (book.getBookType() == BookType.DELIVERY)
            view.getPanelCodeClient().setVisible(false);

        view.getFieldCode().setText(book.getBookCode());
        if (book.getBookClient() != null && book.getBookClient().getId() != 0) {
            view.getFieldClient().setSelectedItem(book.getBookClient().getName());
            view.getFieldClient().getEditor().setItem(book.getBookClient().getName());
        }
        view.getFieldDate().setText(book.getBookDate());
        view.getFieldPlace().setText(book.getBookPlace());
        view.getFieldComment().setText(book.getBookComment());
        if (book.getBookUser() != null && book.getBookUser().getId() != 0) {
            view.getFieldUser().setSelectedItem(book.getBookUser().getName());
            view.getFieldUser().getEditor().setItem(book.getBookUser().getName());
        }
        view.getFieldClientCode().setText(book.getBookClientCode());
    }

    private void updateTableData() throws Exception {
        view.getTableModel().setList(BookUtility.getItemsList(book));
    }

    private void updateData() {
        onProcess();
        new Thread(() -> {
            try {
                updateFormData();
                updateTableData();
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
                exception.printStackTrace();
            }
        }).start();
    }

    private void onUpdate() {
        onProcess();
        new Thread(() -> {
            try {
                updateTableData();
                onFinish();
            } catch (Exception exception) {
                onException(exception.getMessage());
                exception.printStackTrace();
            }
        }).start();
    }

    public void deleteItem(int rowIndex) {
        try {
            String optionText = "Da li ste sigurni da zelite izbrisati " +
                    (((view.getTableModel().getItemName(rowIndex) == null) ? "nepostojecu robu!" : view.getTableModel().getItemName(rowIndex)));
            if (UserDialog.showOption(optionText) == 0) {
                BookUtility.deleteItem(view.getTableModel().getItemAt(rowIndex), book.getBookType());
                view.getTableModel().removeRow(rowIndex);
            }
        } catch (Exception exception) {
            UserDialog.showError("Greska prilikom brisanja!");
            exception.printStackTrace();
        }
    }

    private void onException(String errText) {
        view.getTableScrollPane().setVisible(false);
        view.getTextLoader().setText(errText);
    }

    private void onFinish() {
        view.getTextLoader().setVisible(false);
        view.getTableScrollPane().setVisible(true);
        view.getPanelFields().setVisible(true);
    }

    private void onProcess() {
        view.getTableScrollPane().setVisible(false);
        view.getPanelFields().setVisible(false);
        view.getTextLoader().setVisible(true);
        view.getTextLoader().setText("Molimo sacekajte");
    }

    public TableBookItemsModel getTableModel() {
        return view.getTableModel();
    }

    public BookModel getBook() {
        return book;
    }
}
