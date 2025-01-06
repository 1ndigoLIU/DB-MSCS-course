package org.database5700.teamproject.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.database5700.teamproject.model.Item;

public class ItemDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static ItemDao instance = null;

  protected ItemDao() {
    connectionManager = new ConnectionManager();
  }

  public static ItemDao getInstance() {
    if (instance == null) {
      instance = new ItemDao();
    }
    return instance;
  }

  public Item getItemByID(int itemID) throws SQLException {
    String selectItem = "SELECT itemID,name,maxStackSize,soldable,vendorPrice FROM Item WHERE itemID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectItem);
      selectStmt.setInt(1, itemID);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int resultItemID = results.getInt("itemID");
        String name = results.getString("name");
        int maxStackSize = results.getInt("maxStackSize");
        boolean soldable = results.getBoolean("soldable");
        BigDecimal vendorPrice = results.getBigDecimal("vendorPrice");
        Item item = new Item(resultItemID, name, maxStackSize, soldable, vendorPrice);
        return item;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public Item create(Item item) throws SQLException {
    String insertItem = "INSERT INTO Item(name, maxStackSize, soldable, vendorPrice) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, item.getName());
      insertStmt.setInt(2, item.getMaxStackSize());
      insertStmt.setBoolean(3, item.getSoldable());
      insertStmt.setBigDecimal(4, item.getVendorPrice());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int itemID = -1;
      if (resultKey.next()) {
        itemID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      item.setItemID(itemID);
      return item;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }
      if (resultKey != null) {
        resultKey.close();
      }
    }
  }

  public void delete(Item item) throws SQLException {
    String deleteItem = "DELETE FROM Item WHERE itemID=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteItem);
      deleteStmt.setInt(1, item.getItemID());
      deleteStmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }
}
