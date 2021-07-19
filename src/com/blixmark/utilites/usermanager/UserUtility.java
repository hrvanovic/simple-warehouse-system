package com.blixmark.utilites.usermanager;

import com.blixmark.model.UserModel;
import com.blixmark.utilites.Validator;
import com.blixmark.utilites.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class UserUtility {
    public static void create(UserModel user, String userPassword) throws Exception {
        Database database = new Database();

        String selectQuery = "SELECT kor_id FROM korisnici WHERE kor_email = ? OR kor_ime = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setString(1, user.getEmail());
        selectStatement.setString(2, user.getName());
        ResultSet selectResult = selectStatement.executeQuery();

        if(selectResult.next())
            throw new Exception("Ime ili Email je vec u bazi!");

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if(!pat.matcher(user.getEmail()).matches())
            throw new Exception("E-Mail nije validan!");


        String insertQuery = "INSERT INTO korisnici (kor_ime, kor_email, kor_pozicija, kor_pass) VALUES (?,?,?,?)";
        PreparedStatement insertStatement = database.getPreparedStatement(insertQuery);
        insertStatement.setString(1, user.getName());
        insertStatement.setString(2, user.getEmail());
        insertStatement.setString(3, user.getPosition());
        insertStatement.setString(4, userPassword);
        insertStatement.execute();

        String selectIDQuery = "SELECT kor_id FROM korisnici ORDER BY kor_id DESC";
        PreparedStatement selectIDStatement = database.getPreparedStatement(selectIDQuery);
        selectIDStatement.execute();

        ResultSet resultSet = selectIDStatement.getResultSet();

        if (resultSet.next())
            user.setId(resultSet.getInt(1));
    }

    public static void edit(UserModel user) throws Exception {
        Database database = new Database();

        String selectQuery = "SELECT kor_id FROM korisnici WHERE (kor_email = ? OR kor_ime = ?) AND NOT kor_id = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setString(1, user.getEmail());
        selectStatement.setString(2, user.getName());
        selectStatement.setInt(3, user.getId());
        ResultSet selectResult = selectStatement.executeQuery();

        if(selectResult.next())
            throw new Exception("Ime ili Email je vec u bazi!");


        if (!Validator.validateEmail(user.getEmail()))
            throw new Exception("EMAIL NIJE VALIDAN!");

        String updateQuery = "UPDATE korisnici SET kor_ime = ?, kor_email = ?, kor_pozicija = ? WHERE kor_id = ?";
        PreparedStatement updateStatement = database.getPreparedStatement(updateQuery);
        updateStatement.setString(1, user.getName());
        updateStatement.setString(2, user.getEmail());
        updateStatement.setString(3, user.getPosition());
        updateStatement.setInt(4, user.getId());
        updateStatement.executeQuery();
    }

    public static void editPassword(UserModel user, String newPassword) throws Exception {
        Database database = new Database();

        String updateQuery = "UPDATE korisnici SET kor_pass = ? WHERE kor_id = ?";
        PreparedStatement updaeStatement = database.getPreparedStatement(updateQuery);
        updaeStatement.setString(1, newPassword);
        updaeStatement.setInt(2, user.getId());
        updaeStatement.executeUpdate();
    }

    public static void delete(int userID) throws Exception {
        Database database = new Database();

        String deleteQuery = "DELETE FROM korisnici WHERE kor_id = ?";
        PreparedStatement deleteStatement = database.getPreparedStatement(deleteQuery);
        deleteStatement.setInt(1, userID);

        if(deleteStatement.executeUpdate() == 0)
            throw new Exception("Doslo je do greske!");
    }

    public static LinkedList<UserModel> getUserList() throws Exception {
        Database database = new Database();
        LinkedList<UserModel> userList = new LinkedList<>();
        String selectQuery = "SELECT kor_id, kor_ime, kor_email, kor_pozicija FROM korisnici";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.executeQuery();
        ResultSet selectResult = selectStatement.getResultSet();

        while (selectResult.next()) {
            userList.add(new UserModel(
                    selectResult.getInt(1),
                    selectResult.getString(2),
                    selectResult.getString(3),
                    selectResult.getString(4)
            ));
        }

        return userList;
    }

    public static UserModel getUser(int userID) throws Exception {
        return getUser(userID, new Database());
    }

    public static UserModel getUser(int userID, Database database) throws Exception {
        String selectQuery = "SELECT kor_ime, kor_email, kor_pozicija FROM korisnici WHERE kor_id = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, userID);
        selectStatement.executeQuery();
        ResultSet selectResult = selectStatement.getResultSet();

        if (selectResult.next()) {
            return new UserModel(
                    userID,
                    selectResult.getString(1),
                    selectResult.getString(2),
                    selectResult.getString(3)
            );
        }
        return new UserModel();
    }
}
