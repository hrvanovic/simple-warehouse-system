package com.blixmark.utilites;

import com.blixmark.model.UserModel;

public class AuthSession {
    private static UserModel user;

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        AuthSession.user = user;
    }
}
