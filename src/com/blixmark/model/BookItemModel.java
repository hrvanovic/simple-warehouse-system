package com.blixmark.model;

import com.blixmark.enumeration.BookType;

public class  BookItemModel {
    private int id;
    private int bookID;
    private ItemModel item;
    private float amount;
    private BookType bookType;

    public BookItemModel(ItemModel item, BookModel book) {
        this.id = 0;
        this.bookID = book.getBookID();
        this.item = item;
        this.amount = 0;
        this.bookType = book.getBookType();
    }

    public BookItemModel(int id, int bookID, ItemModel item, float amount, BookType bookType) {
        this.id = id;
        this.bookID = bookID;
        this.item = item;
        this.amount = amount;
        this.bookType = bookType;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
