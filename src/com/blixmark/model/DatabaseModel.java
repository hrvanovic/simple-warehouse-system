package com.blixmark.model;

import com.blixmark.enumeration.BookType;
import com.blixmark.enumeration.ItemType;

public class DatabaseModel {
    public static String getItemTable(ItemType type) {
        switch (type) {
            case PRO:
                return "proizvodi";
            case RAW:
                return "sirovine";
            case COM:
                return "komercijala";
            default:
                return null;
        }
    }

    public static String getBookTable(BookType type) {
        switch (type) {
            case RECEIPT:
                return "primke";
            case DELIVERY:
                return "otpremnice";
            default:
                return null;
        }
    }

    public static String getBookItemsTable(BookType type) {
        switch (type) {
            case RECEIPT:
                return "primke_sadrzaj";
            case DELIVERY:
                return "otpremnice_sadrzaj";
            default:
                return null;
        }
    }
}
