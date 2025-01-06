package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.Item;
import org.database5700.teamproject.model.ItemAttributeBonus;

public class ItemAttributeBonusDao {

  protected ConnectionManager connectionManager;
  private static ItemAttributeBonusDao instance = null;

  protected ItemAttributeBonusDao() {
    connectionManager = new ConnectionManager();
  }

  public static ItemAttributeBonusDao getInstance() {
    if (instance == null) {
      instance = new ItemAttributeBonusDao();
    }
    return instance;
  }

  public ItemAttributeBonus create(ItemAttributeBonus itemAttributeBonus) throws SQLException {
    String insertItemAttributeBonus = "INSERT INTO ItemAttributeBonus(itemID,attrName,bonus,consumable,bonusCap) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertItemAttributeBonus);
      insertStmt.setInt(1, itemAttributeBonus.getItem().getItemID());
      insertStmt.setString(2, itemAttributeBonus.getAttrName());
      insertStmt.setInt(3, itemAttributeBonus.getBonus());
      insertStmt.setBoolean(4, itemAttributeBonus.getConsumable());
      Integer bonusCap = itemAttributeBonus.getBonusCap();
      if (bonusCap != null) {
        insertStmt.setInt(5, bonusCap);
      } else {
        insertStmt.setNull(5, Types.INTEGER);
      }
      insertStmt.executeUpdate();
      return itemAttributeBonus;
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

  public ItemAttributeBonus getItemAttributeBonusByItemIDAndAttrName(Item item, String attrName)
      throws SQLException {
    String selectItemAttributeBonus =
        "SELECT itemID,attrName,bonus,consumable,bonusCap " + "FROM ItemAttributeBonus "
            + "WHERE itemID=? AND attrName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectItemAttributeBonus);
      selectStmt.setInt(1, item.getItemID());
      selectStmt.setString(2, attrName);
      results = selectStmt.executeQuery();
      ItemDao itemDao = ItemDao.getInstance();
      if (results.next()) {
        int itemID = results.getInt("itemID");
        Item resultItem = itemDao.getItemByID(itemID);
        String resultAttrName = results.getString("attrName");
        int bonus = results.getInt("bonus");
        boolean consumable = results.getBoolean("consumable");
        Integer bonusCap = (Integer) results.getObject("bonusCap");
        ItemAttributeBonus itemAttributeBonus = new ItemAttributeBonus(resultItem, resultAttrName,
            bonus, consumable, bonusCap);
        return itemAttributeBonus;
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

  public List<ItemAttributeBonus> getItemAttributeBonusesByItem(Item item) throws SQLException {
    List<ItemAttributeBonus> itemAttributeBonuses = new ArrayList<ItemAttributeBonus>();
    String selectBonuses =
        "SELECT itemID,attrName,bonus,consumable,bonusCap " + "FROM ItemAttributeBonus "
            + "WHERE itemID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectBonuses);
      selectStmt.setInt(1, item.getItemID());
      results = selectStmt.executeQuery();
      ItemDao itemDao = ItemDao.getInstance();
      while (results.next()) {
        int itemID = results.getInt("itemID");
        Item resultItem = itemDao.getItemByID(itemID);
        String attrName = results.getString("attrName");
        int bonus = results.getInt("bonus");
        boolean consumable = results.getBoolean("consumable");
        Integer bonusCap = (Integer) results.getObject("bonusCap");
        ItemAttributeBonus itemAttributeBonus = new ItemAttributeBonus(resultItem, attrName, bonus,
            consumable, bonusCap);
        itemAttributeBonuses.add(itemAttributeBonus);
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
    return itemAttributeBonuses;
  }
}
