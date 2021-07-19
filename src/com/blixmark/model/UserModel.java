package com.blixmark.model;

public class UserModel implements AutoComboImpl {
    private int id;
    private String name;
    private String email;
    private String position;


    public UserModel() {
        this.id = 0;
        this.name = null;
        this.email = null;
        this.position = null;
    }

    public UserModel(UserModel userModel) {
        this.id = userModel.getId();
        this.name = userModel.getName();
        this.email = userModel.getEmail();
        this.position = userModel.getPosition();
    }

    public UserModel(int userID, String userName, String userEmail, String userPosition) {
        this.id = userID;
        this.name = userName;
        this.email = userEmail;
        this.position = userPosition;
    }

    public UserModel(String userName, String userEmail, String userPosition) {
        this.name = userName;
        this.email = userEmail;
        this.position = userPosition;
    }

    public UserModel copy(UserModel user) {
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPosition(this.position);

        return user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
