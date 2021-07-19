package com.blixmark.model;

public class ClientModel implements AutoComboImpl {
    private int id;
    private String name;
    private String desc;

    public ClientModel() {
        this.id = 0;
        this.name = null;
        this.desc = null;
    }

    public ClientModel(ClientModel client) {
        this.id = client.getId();
        this.name = client.getName();
        this.desc = client.getDesc();
    }

    public ClientModel(int clientID, String clientName, String clientDesc) {
        this.id = clientID;
        this.name = clientName;
        this.desc = clientDesc;
    }

    public ClientModel(String clientName, String clientDesc) {
        this.name = clientName;
        this.desc = clientDesc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        if(name == null) return "";
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void cloneClient(ClientModel client) {
        setName(client.getName());
        setDesc(client.getDesc());
    }
}
