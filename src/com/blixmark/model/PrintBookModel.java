package com.blixmark.model;

public class PrintBookModel {
    private int rbr;
    private String code;
    private String name;
    private String jm;
    private float amount;

    public PrintBookModel(int rbr, String code, String name, String jm, float amount) {
        this.rbr = rbr;
        this.code = code;
        this.name = name;
        this.jm = jm;
        this.amount = amount;
    }

    public int getRbr() {
        return rbr;
    }

    public void setRbr(int rbr) {
        this.rbr = rbr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
