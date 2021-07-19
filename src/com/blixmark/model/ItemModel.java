package com.blixmark.model;

import com.blixmark.enumeration.ItemType;

public class ItemModel {
    private int id;
    private String name;
    private String code;
    private String jm;
    private float amount;
    private final ItemType type;

    public ItemModel() {
        this.id = 0;
        this.name = null;
        this.code = null;
        this.jm = null;
        this.amount = 0;
        this.type = null;
    }

    public ItemModel(ItemModel item) {
        this.id = item.getId();
        this.name = item.getName();
        this.code = item.getCode();
        this.jm = item.getJm();
        this.amount = item.getAmount();
        this.type = item.getType();
    }

    public ItemModel(int itemID, String itemName, String itemCode, String itemJM, float itemAmount, ItemType itemType) {
        this.id = itemID;
        this.name = itemName;
        this.code = itemCode;
        this.jm = itemJM;
        this.amount = itemAmount;
        this.type = itemType;
    }

    public ItemModel(String itemName, String itemCode, String itemJM, float itemAmount, ItemType itemType) {
        this.id = 0;
        this.name = itemName;
        this.code = itemCode;
        this.jm = itemJM;
        this.amount = itemAmount;
        this.type = itemType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getJm() {
        return jm;
    }

    public float getAmount() {
        return amount;
    }

    public ItemType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public static ItemType toType(int intType) {
        switch (intType) {
            case 0:
                return ItemType.PRO;
            case 1:
                return ItemType.RAW;
            case 2:
                return ItemType.COM;
            default:
                return null;
        }
    }

    public static int toType(ItemType type) {
        switch (type) {
            case RAW:
                return 1;
            case COM:
                return 2;
            default:
                return 0;
        }
    }

    public static String getDatabaseName(ItemType itemType) {
        switch (itemType) {
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

}
