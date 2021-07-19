package com.blixmark.utilites;

import com.blixmark.model.ClientModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class ClientUtility {
    public static void create(ClientModel client) throws Exception {
        Database database = new Database();
        String clientName = client.getName();
        String clientDesc = client.getDesc();

        String selectQuery = "SELECT k_id FROM klijenti WHERE k_ime = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setString(1, clientName);
        ResultSet selectResult = selectStatement.executeQuery();

        if(selectResult.next())
            throw new Exception("Klijent je vec u bazi!");

        String insertQuery = "INSERT INTO klijenti (k_ime,k_opis) VALUES (?,?)";
        PreparedStatement insertStatement = database.getPreparedStatement(insertQuery);
        insertStatement.setString(1, clientName);
        insertStatement.setString(2, clientDesc);
        insertStatement.execute();

        String selectIdQuery = "SELECT k_id FROM klijenti ORDER BY k_id DESC";
        PreparedStatement selectIdStatement = database.getPreparedStatement(selectIdQuery);
        selectIdStatement.execute();

        ResultSet resultSet = selectIdStatement.getResultSet();

        if (resultSet.next())
            client.setId(resultSet.getInt(1));
    }

    public static void delete(ClientModel client) throws Exception {
        Database database = new Database();
        PreparedStatement deleteStatement = database.getPreparedStatement("DELETE FROM klijenti WHERE k_id = ?");
        deleteStatement.setInt(1, client.getId());
        deleteStatement.execute();
    }

    public static void edit(ClientModel client) throws Exception {
        Database database = new Database();
        int clientID = client.getId();
        String clientName = client.getName();
        String clientDesc = client.getDesc();

        String updateQuery = "UPDATE klijenti SET k_ime = ?, k_opis = ? WHERE k_id = ?";
        PreparedStatement updateStatement = database.getPreparedStatement(updateQuery);
        updateStatement.setString(1, clientName);
        updateStatement.setString(2, clientDesc);
        updateStatement.setInt(3, clientID);
        updateStatement.executeUpdate();
    }

    public static LinkedList<ClientModel> getClientList() throws Exception {
        Database database = new Database();
        LinkedList<ClientModel> clientList = new LinkedList<>();
        String selectQuery = "SELECT k_id, k_ime, k_opis FROM klijenti ORDER BY k_ime DESC";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.executeQuery();
        ResultSet selectResult = selectStatement.getResultSet();

        while (selectResult.next()) {

            clientList.add(new ClientModel(
                    selectResult.getInt(1),
                    selectResult.getString(2),
                    selectResult.getString(3)
            ));
        }

        return clientList;
    }

    public static ClientModel get(int clientID) throws Exception {
        return get(clientID, new Database());
    }

    public static ClientModel get(int clientID, Database database) throws Exception {
        String selectQuery = "SELECT k_ime, k_opis FROM klijenti WHERE k_id = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, clientID);
        selectStatement.executeQuery();
        ResultSet selectResult = selectStatement.getResultSet();

        if (selectResult.next()) {
            return new ClientModel(
                    clientID,
                    selectResult.getString(1),
                    selectResult.getString(2)
            );
        }
        return new ClientModel();
    }
}
