package com.blixmark.model;

import com.blixmark.enumeration.BookType;

public class BookModel {
    private int bookID;
    private String bookCode;
    private UserModel bookUser;
    private ClientModel bookClient;
    private String bookPlace;
    private String bookComment;
    private String bookDate;
    private BookType bookType;
    private String bookClientCode;

    public BookModel(int id, String code, UserModel user, ClientModel client, String bookDate, String bookPlace, String bookComment, String bookClientCode, BookType type) {
        this.bookID = id;
        this.bookCode = code;
        this.bookUser = user;
        this.bookClient = client;
        this.bookType = type;
        this.bookClientCode = bookClientCode;
        this.bookPlace = bookPlace;
        this.bookDate = bookDate;
        this.bookComment = bookComment;
    }

    public BookModel(String code, UserModel user, ClientModel client, String date, String place, String bookClientCode, BookType type) {
        this.bookCode = code;
        this.bookUser = user;
        this.bookClient = client;
        this.bookDate = date;
        this.bookPlace = place;
        this.bookType = type;
        this.bookClientCode = bookClientCode;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public UserModel getBookUser() {
        return bookUser;
    }

    public void setBookUser(UserModel bookUser) {
        this.bookUser = bookUser;
    }

    public ClientModel getBookClient() {
        return bookClient;
    }

    public void setBookClient(ClientModel bookClient) {
        this.bookClient = bookClient;
    }

    public String getBookPlace() {
        return bookPlace;
    }

    public void setBookPlace(String bookPlace) {
        this.bookPlace = bookPlace;
    }

    public String getBookComment() {
        return bookComment;
    }

    public void setBookComment(String bookComment) {
        this.bookComment = bookComment;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getBookClientCode() {
        return bookClientCode;
    }

    public void setBookClientCode(String bookClientCode) {
        this.bookClientCode = bookClientCode;
    }

    public static String getDatabaseName(BookType bookType) {
        switch (bookType) {
            case RECEIPT:
                return "primke";
            case DELIVERY:
                return "otpremnice";
            default:
                return null;
        }
    }

    public static String getDatabaseItemsName(BookType bookType) {
        switch (bookType) {
            case RECEIPT:
                return "primke_sadrzaj";
            case DELIVERY:
                return "otpremnice_sadrzaj";
            default:
                return null;
        }
    }


    public static BookType toType(char charType) {
        switch (charType) {
            case 'O':
                return BookType.DELIVERY;
            case 'I':
                return BookType.RECEIPT;
            case 'o':
                return BookType.DELIVERY;
            case 'i':
                return BookType.RECEIPT;
            default:
                return null;
        }
    }

    public static BookType toType(String charType) {
        switch (charType) {
            case "out":
                return BookType.DELIVERY;
            case "in":
                return BookType.RECEIPT;
            default:
                return null;
        }
    }
}
