package com.blixmark.utilites.bookmanager;

import com.blixmark.enumeration.BookType;
import com.blixmark.model.*;
import com.blixmark.utilites.SystemLog;
import com.blixmark.utilites.ClientUtility;
import com.blixmark.utilites.Database;
import com.blixmark.utilites.ItemUtility;
import com.blixmark.utilites.usermanager.UserUtility;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class BookUtility {
    public static void create(BookModel book) throws Exception {
        Database database = new Database();

        if(!book.getBookCode().equals("")) {
            String selectDuplicate = "SELECT b_sifra FROM " + BookModel.getDatabaseName(book.getBookType()) + " WHERE b_sifra = ?";
            PreparedStatement selectDuplicateStatement = database.getPreparedStatement(selectDuplicate);
            selectDuplicateStatement.setString(1, book.getBookCode());
            ResultSet selectDuplicateResult = selectDuplicateStatement.executeQuery();

            if(selectDuplicateResult.next())
                throw new Exception("Broj je vec u bazi!");
        }

        if(!book.getBookClientCode().equals("")) {
            String selectDuplicate = "SELECT b_codeclient FROM " + BookModel.getDatabaseName(book.getBookType()) + " WHERE b_codeclient = ?";
            PreparedStatement selectDuplicateStatement = database.getPreparedStatement(selectDuplicate);
            selectDuplicateStatement.setString(1, book.getBookClientCode());
            ResultSet selectDuplicateResult = selectDuplicateStatement.executeQuery();

            if(selectDuplicateResult.next())
                throw new Exception("Broj otpremnice dobavljaca je vec u bazi!");
        }

        int bookClientId = (book.getBookClient() == null) ? 0 : book.getBookClient().getId();
        int bookUserId = (book.getBookUser() == null) ? 0 : book.getBookUser().getId();

        String insertQuery;

        if (book.getBookType() == BookType.RECEIPT)
            insertQuery = "INSERT INTO " + BookModel.getDatabaseName(book.getBookType()) + "(b_sifra,b_klijent,b_korisnik,b_vrijeme,b_mjesto,b_codeclient) VALUES (?,?,?,?,?,?)";
        else
            insertQuery = "INSERT INTO " + BookModel.getDatabaseName(book.getBookType()) + "(b_sifra,b_klijent,b_korisnik,b_vrijeme,b_mjesto) VALUES (?,?,?,?,?)";
        PreparedStatement insertStatement = database.getPreparedStatement(insertQuery);
        insertStatement.setString(1, book.getBookCode());
        insertStatement.setInt(2, bookClientId);
        insertStatement.setInt(3, bookUserId);
        insertStatement.setString(4, book.getBookDate());
        insertStatement.setString(5, book.getBookPlace());
        if (book.getBookType() == BookType.RECEIPT)
            insertStatement.setString(6, book.getBookClientCode());
        insertStatement.execute();

        String selectQuery = "SELECT b_id FROM " + BookModel.getDatabaseName(book.getBookType()) + " ORDER BY b_id DESC LIMIT 0,1";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next())
            book.setBookID(resultSet.getInt(1));
        else
            throw new Exception("Doslo je do greske!");
    }

    public static void delete(int bookID, BookType bookType) throws Exception {
        Database database = new Database();

        String updateQuery = "SELECT s_item, s_itemtype, s_amount FROM " + BookModel.getDatabaseItemsName(bookType) + " WHERE s_book = ?";
        PreparedStatement selectPrepared = database.getPreparedStatement(updateQuery);
        selectPrepared.setInt(1, bookID);
        ResultSet selectResult = selectPrepared.executeQuery();

        float bookAmount;
        ItemModel item;
        while(selectResult.next()) {
            item = ItemUtility.get(selectResult.getInt(1), ItemModel.toType(selectResult.getInt(2)), database);
            if(item.getId() == 0)
                continue;

            bookAmount = selectResult.getFloat(3);
            switch (bookType) {
                case RECEIPT:
                    item.setAmount(item.getAmount() - bookAmount);
                    break;
                case DELIVERY:
                    item.setAmount(item.getAmount() + bookAmount);
                    break;
            }
            ItemUtility.edit(item);
        }

        String deleteQuery = "DELETE FROM " + BookModel.getDatabaseName(bookType) + " WHERE b_id = ?";
        PreparedStatement deleteStatement = database.getPreparedStatement(deleteQuery);
        deleteStatement.setInt(1, bookID);
        deleteStatement.executeUpdate();

        String deleteItemsQuery = "DELETE FROM " + BookModel.getDatabaseItemsName(bookType) + " WHERE s_book = ?";
        PreparedStatement deleteItemsStatement = database.getPreparedStatement(deleteItemsQuery);
        deleteItemsStatement.setInt(1, bookID);
        deleteItemsStatement.executeUpdate();
    }

    public static void edit(BookModel book) throws Exception {
        Database database = new Database();

        String updateQuery;
        if (book.getBookType() == BookType.RECEIPT)
            updateQuery = "UPDATE " + BookModel.getDatabaseName(book.getBookType()) + " SET b_sifra = ?, b_korisnik = ?, b_klijent = ?, b_vrijeme = ?" +
                    ", b_mjesto = ?, b_komentar = ?, b_codeclient = ? WHERE b_id = ?";
        else
            updateQuery = "UPDATE " + BookModel.getDatabaseName(book.getBookType()) + " SET b_sifra = ?, b_korisnik = ?, b_klijent = ?, b_vrijeme = ?" +
                    ", b_mjesto = ?, b_komentar = ? WHERE b_id = ?";

        PreparedStatement updateStatement = database.getPreparedStatement(updateQuery);

        updateStatement.setString(1, book.getBookCode());
        updateStatement.setInt(2, ((book.getBookUser()) == null) ? 0 : book.getBookUser().getId());
        updateStatement.setInt(3, ((book.getBookClient()) == null) ? 0 : book.getBookClient().getId());
        updateStatement.setString(4, book.getBookDate());
        updateStatement.setString(5, book.getBookPlace());
        updateStatement.setString(6, book.getBookComment());
        if (book.getBookType() == BookType.RECEIPT) {
            updateStatement.setString(7, book.getBookClientCode());
            updateStatement.setInt(8, book.getBookID());
        } else
            updateStatement.setInt(7, book.getBookID());

        updateStatement.execute();
    }

    public static LinkedList<BookModel> getList(BookType bookType) throws Exception {
        LinkedList<BookModel> bookList = new LinkedList<>();
        Database database = new Database();

        String selectQuery = "SELECT b_id, b_sifra, b_korisnik, b_klijent, b_vrijeme, b_mjesto, b_komentar"
                + ((bookType == BookType.RECEIPT) ? ", b_codeclient" : "") + " FROM " + BookModel.getDatabaseName(bookType);
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        ResultSet selectResult = selectStatement.executeQuery();

        while (selectResult.next()) {
            BookModel book = new BookModel(
                    selectResult.getInt(1),
                    selectResult.getString(2),
                    UserUtility.getUser(selectResult.getInt(3), database),
                    ClientUtility.get(selectResult.getInt(4), database),
                    selectResult.getString(5),
                    selectResult.getString(6),
                    selectResult.getString(7),
                    ((bookType == BookType.RECEIPT) ? selectResult.getString(8) : ""),
                    bookType
            );
            if (bookType == BookType.RECEIPT)
                book.setBookClientCode(selectResult.getString(8));

            bookList.add(book);
        }

        return bookList;
    }

    public static BookModel get(int bookID, BookType bookType) throws Exception {
        return get(bookID, bookType, new Database());
    }

    public static BookModel get(int bookID, BookType bookType, Database database) throws Exception {
        String selectQuery = "SELECT b_sifra, b_korisnik, b_klijent, b_vrijeme, b_mjesto, b_komentar"
                + ((bookType == BookType.RECEIPT) ? ", b_codeclient" : "") + " FROM " + BookModel.getDatabaseName(bookType) + " WHERE b_id = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, bookID);
        ResultSet selectResult = selectStatement.executeQuery();

        if (selectResult.next()) {
            return new BookModel(
                    bookID,
                    selectResult.getString(1),
                    UserUtility.getUser(selectResult.getInt(2), database),
                    ClientUtility.get(selectResult.getInt(3), database),
                    selectResult.getString(4),
                    selectResult.getString(5),
                    selectResult.getString(6),
                    ((bookType == BookType.RECEIPT) ? selectResult.getString(7) : null),
                    bookType
            );
        }
        return null;
    }

    public static LinkedList<BookItemModel> getItemsList(BookModel book) throws Exception {
        Database database = new Database();

        LinkedList<BookItemModel> list = new LinkedList<>();
        String selectQuery = "SELECT s_id, s_item, s_itemtype, s_amount FROM " + BookModel.getDatabaseItemsName(book.getBookType()) + " WHERE s_book = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, book.getBookID());
        ResultSet selectResult = selectStatement.executeQuery();

        while (selectResult.next()) {
            list.add(
                    new BookItemModel(
                            selectResult.getInt(1),
                            book.getBookID(),
                            ItemUtility.get(selectResult.getInt(2), ItemModel.toType(selectResult.getInt(3)), database),
                            selectResult.getFloat(4),
                            book.getBookType()
                    )
            );
        }

        return list;
    }

    public static ArrayList<PrintBookModel> getItemsPrintList(BookModel book) throws Exception {
        Database database = new Database();

        ArrayList<PrintBookModel> list = new ArrayList<>();
        String selectQuery = "SELECT s_id, s_item, s_itemtype, s_amount FROM " + BookModel.getDatabaseItemsName(book.getBookType()) + " WHERE s_book = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, book.getBookID());
        ResultSet selectResult = selectStatement.executeQuery();

        int count = 0;
        while (selectResult.next()) {
            ItemModel item = ItemUtility.get(selectResult.getInt(2), ItemModel.toType(selectResult.getInt(3)), database);
            list.add(
                    new PrintBookModel(
                            ++count,
                            item.getCode(),
                            item.getName(),
                            item.getJm(),
                            selectResult.getFloat(4)
                    )
            );
        }

        return list;
    }

    public static String getLastPlace(BookType bookType) {
        Database database = new Database();
        String lastPlace = null;
        try {
            String selectQuery = "SELECT b_mjesto FROM " + BookModel.getDatabaseName(bookType);
            PreparedStatement preparedStatement = database.getPreparedStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("b_mjesto") != null)
                    lastPlace = resultSet.getString("b_mjesto");
            }
        } catch (SQLException e) {
            SystemLog.newLog(e);
        }
        return lastPlace;
    }

    public static String getUniqueCode(BookType bookType) {
        Database database = new Database();
        String prevCode = null;

        try {
            String selectQuery = "SELECT b_sifra FROM " + BookModel.getDatabaseName(bookType) + " ORDER BY b_sifra DESC LIMIT 10";
            PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
            selectStatement.execute();
            ResultSet resultSet = selectStatement.getResultSet();

            while (resultSet.next())
                prevCode = resultSet.getString("b_sifra");

            DateFormat df = new SimpleDateFormat("yy");
            String currentYear = df.format(Calendar.getInstance().getTime());

            if (prevCode == null)
                prevCode = "0/" + currentYear;

            String[] prevCodeArr = prevCode.split("/", 2);

            if(prevCodeArr.length == 0 && prevCodeArr[1] == null)
                return "1/" + currentYear;

            if (prevCodeArr[1].equals(currentYear))
                return (Integer.parseInt(prevCodeArr[0]) + 1) + "/" + currentYear;
            else
                return "1/" + currentYear;

        } catch (Exception exception) {
            SystemLog.newLog(exception);
        }

        return null;
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void addItem(BookItemModel bookItem) throws Exception {
        Database database = new Database();

        String insertQuery = "INSERT INTO " + BookModel.getDatabaseItemsName(bookItem.getBookType()) + " (s_book, s_item, s_itemtype, s_amount) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = database.getPreparedStatement(insertQuery);
        preparedStatement.setInt(1, bookItem.getBookID());
        preparedStatement.setInt(2, bookItem.getItem().getId());
        preparedStatement.setInt(3, ItemModel.toType(bookItem.getItem().getType()));
        preparedStatement.setFloat(4, bookItem.getAmount());
        preparedStatement.execute();

        PreparedStatement pr1 = database.getPreparedStatement("SELECT s_id FROM " + BookModel.getDatabaseItemsName(bookItem.getBookType()) + " ORDER BY s_id DESC");
        pr1.execute();
        ResultSet resultSet = pr1.getResultSet();

        if (resultSet.next())
            bookItem.setId(resultSet.getInt(1));
    }

    public static void deleteItem(BookItemModel bookItem, BookType bookType) throws Exception {
        Database database = new Database();

        String deleteQuery = "DELETE FROM " + BookModel.getDatabaseItemsName(bookType) + " WHERE s_id = ?";
        PreparedStatement preparedStatement = database.getPreparedStatement(deleteQuery);
        preparedStatement.setInt(1, bookItem.getId());
        preparedStatement.executeUpdate();

        if(bookItem.getItem().getId() == 0)
            return;

        ItemModel item = ItemUtility.get(bookItem.getItem().getId(), bookItem.getItem().getType());

        switch (bookItem.getBookType()) {
            case RECEIPT:
                item.setAmount(item.getAmount() - bookItem.getAmount());
                break;

            case DELIVERY:
                item.setAmount(item.getAmount() + bookItem.getAmount());
                break;
        }

        ItemUtility.edit(item);
    }

    public static void editItem(BookItemModel bookItem, float newAmount) throws Exception {
        Database database = new Database();
        String query = "UPDATE " + BookModel.getDatabaseItemsName(bookItem.getBookType()) + "," + ItemModel.getDatabaseName(bookItem.getItem().getType()) +
                " SET s_amount = ?, pro_amount = ? WHERE s_id = ? AND pro_id = ?";

        float updatedAmount = ItemUtility.get(bookItem.getItem().getId(), bookItem.getItem().getType()).getAmount();
        switch (bookItem.getBookType()) {
            case RECEIPT:
                if(newAmount > bookItem.getAmount())
                    updatedAmount += (newAmount - bookItem.getAmount());
                else
                    updatedAmount -= (bookItem.getAmount() - newAmount);
                break;

            case DELIVERY:
                if(newAmount > bookItem.getAmount())
                    updatedAmount -= (newAmount - bookItem.getAmount());
                else
                    updatedAmount += (bookItem.getAmount() - newAmount);
                break;
        }

        PreparedStatement preparedStatement = database.getPreparedStatement(query);
        preparedStatement.setFloat(1, newAmount);
        preparedStatement.setFloat(2, updatedAmount);
        preparedStatement.setInt(3, bookItem.getId());
        preparedStatement.setInt(4, bookItem.getItem().getId());
        preparedStatement.executeUpdate();
    }

    public static LinkedList<IODataModel> getIOList(ItemModel item, BookType bookType) throws Exception {
        Database database = new Database();
        LinkedList<IODataModel> list = new LinkedList<>();
        String selectQuery = "SELECT s_id, s_book, s_item, s_amount, s_itemtype, s_type FROM " + BookModel.getDatabaseItemsName(bookType) + " WHERE s_item = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, item.getId());
        ResultSet selectResult = selectStatement.executeQuery();

        while (selectResult.next()) {
            list.add(new IODataModel(
                    selectResult.getInt(1),
                    BookUtility.get(selectResult.getInt(2), BookModel.toType(selectResult.getString(6).charAt(0)), database),
                    selectResult.getFloat(4)
            ));
        }

        return list;
    }
}
