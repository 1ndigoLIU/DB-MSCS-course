package org.database5700.teamproject.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.database5700.teamproject.model.*;

public class ConsumableDao extends ItemDao {

  protected ConnectionManager connectionManager;
  private static ConsumableDao instance = null;

  protected ConsumableDao() {
    connectionManager = new ConnectionManager();
  }

  public static ConsumableDao getInstance() {
    if (instance == null) {
      instance = new ConsumableDao();
    }
    return instance;
  }

  public Consumable create(Consumable consumable) throws SQLException {
    Item item = create(
        new Item(consumable.getName(), consumable.getMaxStackSize(), consumable.getSoldable(),
            consumable.getVendorPrice()));
    consumable.setItemID(item.getItemID());
    String insertConsumable = "INSERT INTO Consumable(itemID,itemLevel,description) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertConsumable);
      insertStmt.setInt(1, consumable.getItemID());
      insertStmt.setInt(2, consumable.getItemLevel());
      insertStmt.setString(3, consumable.getDescription());
      insertStmt.executeUpdate();
      return consumable;
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
    }
  }

  public Consumable getConsumableByID(int itemID) throws SQLException {
    String selectItem =
        "SELECT Consumable.itemID AS itemID,`name`,maxStackSize,soldable,vendorPrice,itemLevel,description "
            + "FROM Consumable INNER JOIN Item ON Consumable.itemID=Item.itemID "
            + "WHERE Consumable.itemID=?;";
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

        int itemLevel = results.getInt("itemLevel");
        String description = results.getString("description");
        Consumable consumable = new Consumable(resultItemID, name, maxStackSize, soldable,
            vendorPrice, itemLevel, description);
        return consumable;
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

  public Consumable updateItemLevel(Consumable consumable, int itemLevel) throws SQLException {
    String updateConsumable = "UPDATE Consumable set itemLevel = ? where itemID = ?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateConsumable);
      updateStmt.setInt(1, consumable.getItemID());
      updateStmt.setInt(2, itemLevel);
      updateStmt.executeUpdate();
      consumable.setItemLevel(itemLevel);
      return consumable;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }
}
