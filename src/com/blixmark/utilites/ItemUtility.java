package com.blixmark.utilites;

import com.blixmark.enumeration.BookType;
import com.blixmark.enumeration.ItemType;
import com.blixmark.model.BookModel;
import com.blixmark.model.ItemModel;
import org.json.simple.JSONObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class ItemUtility {
    private static final JSONObject translator = (JSONObject) Localization.get("items");

    public static void create(ItemModel item, ItemType itemType) throws Exception {
        Database database = new Database();

        String selectQuery = "SELECT pro_code FROM " + ItemModel.getDatabaseName(itemType) + " WHERE pro_code = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setString(1, item.getCode());
        ResultSet selectResult = selectStatement.executeQuery();

        if (selectResult.next())
            throw new Exception(translator.get("code-exist").toString());

        String insertQuery = "INSERT INTO " + ItemModel.getDatabaseName(itemType) + " (pro_code, pro_name, pro_jm, pro_amount) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStatement = database.getPreparedStatement(insertQuery);
        insertStatement.setString(1, item.getCode());
        insertStatement.setString(2, item.getName());
        insertStatement.setString(3, item.getJm());
        insertStatement.setFloat(4, item.getAmount());

        if (insertStatement.executeUpdate() == 0)
            throw new Exception(translator.get("error-create").toString() + " \"" + item.getName() + "\"!");

        PreparedStatement selectIDStatement = database.getPreparedStatement("SELECT pro_id FROM " + ItemModel.getDatabaseName(itemType) + " ORDER BY pro_id DESC");
        selectIDStatement.execute();

        ResultSet resultSet = selectIDStatement.getResultSet();
        if (resultSet.next())
            item.setId(resultSet.getInt(1));
    }

    public static boolean delete(ItemModel item) throws Exception {
        Database database = new Database();

        String searchQuery = "SELECT s_id FROM " + BookModel.getDatabaseItemsName(BookType.RECEIPT) + " WHERE s_item = ?" +
                " UNION ALL SELECT s_id FROM " + BookModel.getDatabaseItemsName(BookType.DELIVERY) + " WHERE s_item = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(searchQuery);
        selectStatement.setInt(1, item.getId());
        selectStatement.setInt(2, item.getId());
        ResultSet selectResult = selectStatement.executeQuery();

        if(selectResult.next()) {
            String optionText = "\"" + item.getName() + "\"" + translator.get("item-io-found").toString();
            if(UserDialog.showOption(optionText) == 1)
                return false;
        }

        String deleteQuery = "DELETE FROM " + ItemModel.getDatabaseName(item.getType()) + " WHERE pro_id = ?";
        PreparedStatement deleteStatement = database.getPreparedStatement(deleteQuery);
        deleteStatement.setInt(1, item.getId());
        if (deleteStatement.executeUpdate() == 0)
            throw new Exception(translator.get("error-delete").toString() + " \"" + item.getName() + "\"!");

        return true;
    }

    public static List<ItemModel> getItemList(ItemType itemType) throws Exception {
        Database database = new Database();
        List<ItemModel> itemList = new LinkedList<>();
        String selectQuery = "SELECT pro_id, pro_name, pro_code, pro_jm, pro_amount FROM " + ItemModel.getDatabaseName(itemType);
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.executeQuery();
        ResultSet selectResult = selectStatement.getResultSet();

        while (selectResult.next()) {
            itemList.add(new ItemModel(
                    selectResult.getInt(1),
                    selectResult.getString(2),
                    selectResult.getString(3),
                    selectResult.getString(4),
                    selectResult.getFloat(5),
                    itemType
            ));
        }

        return itemList;
    }

    public static void edit(ItemModel item) throws Exception {
        Database database = new Database();

        String updateQuery = "UPDATE " + ItemModel.getDatabaseName(item.getType()) + " SET pro_name = ?, pro_code = ?, pro_jm = ?, pro_amount = ? WHERE pro_id = ?";
        PreparedStatement updateStatement = database.getPreparedStatement(updateQuery);
        updateStatement.setString(1, item.getName());
        updateStatement.setString(2, item.getCode());
        updateStatement.setString(3, item.getJm());
        updateStatement.setFloat(4, item.getAmount());
        updateStatement.setInt(5, item.getId());

        if(updateStatement.executeUpdate() == 0)
            throw new Exception(translator.get("error-edit").toString() + " \"" + item.getName() + "\"!");
    }

    public static ItemModel get(int itemID, ItemType itemType) throws Exception {
        return get(itemID, itemType, new Database());
    }

    public static ItemModel get(int itemID, ItemType itemType, Database database) throws Exception {
        String selectQuery = "SELECT pro_name, pro_code, pro_jm, pro_amount FROM " + ItemModel.getDatabaseName(itemType) + " WHERE pro_id = ?";
        PreparedStatement selectStatement = database.getPreparedStatement(selectQuery);
        selectStatement.setInt(1, itemID);
        ResultSet selectResult = selectStatement.executeQuery();

        if(selectResult.next()) {
            return new ItemModel(
              itemID,
              selectResult.getString(1),
              selectResult.getString(2),
              selectResult.getString(3),
              selectResult.getFloat(4),
              itemType
            );
        }
        return new ItemModel();
    }
}
