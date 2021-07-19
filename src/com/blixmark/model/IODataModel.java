package com.blixmark.model;

public class IODataModel {
    private int id;
    private BookModel book;
    private ItemModel item;
    private float amount;

    public IODataModel(int id, BookModel book, float amount) {
        this.id = id;
        this.book = book;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
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
