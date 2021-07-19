package com.blixmark.utilites;

import com.blixmark.Config;
import com.blixmark.utilites.SystemLog;
import java.sql.*;

public class Database {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Konstruktor Database.
     */
    public Database() {
        this.createConnection();
    }

    /**
     * Konstruktor za kreiranje SQL konekcije.
     */
    private void createConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            this.connection = DriverManager.getConnection(Config.get("sqlhost"), Config.get("sqluser"), Config.get("sqlpass"));
        } catch (Exception exception) {

        }
    }

    public PreparedStatement getPreparedStatement(String query) {
        try {
            this.preparedStatement = this.connection.prepareStatement(query);
            return this.preparedStatement;
        } catch (SQLException sqlException) {
            return null;
        }
    }

    public void execute() {
        try {
            this.preparedStatement.execute();
        } catch (Exception e) {

        } finally {
            try {
                this.connection.close();
                this.preparedStatement.close();
            } catch (Exception e) {}
        }
    }

    public ResultSet executeQuery() {
        try {
            ResultSet resultSet = this.preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                this.connection.close();
                this.preparedStatement.close();
            } catch (Exception e) { }
        }
    }

    public void closeConn() {
        try {
            if(this.preparedStatement != null)
                this.preparedStatement.close();

            if(this.connection != null)
                this.connection.close();
        } catch (Exception exception) { }
    }
}
