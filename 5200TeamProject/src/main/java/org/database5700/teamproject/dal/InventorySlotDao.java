package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

public class InventorySlotDao {

  protected ConnectionManager connectionManager;
  private static InventorySlotDao instance = null;

  protected InventorySlotDao() {
    connectionManager = new ConnectionManager();
  }

  public static InventorySlotDao getInstance() {
    if (instance == null) {
      instance = new InventorySlotDao();
    }
    return instance;
  }

  public InventorySlot create(InventorySlot inventorySlot) throws SQLException {
    String insertConsumable = "INSERT INTO InventorySlot(characterID,itemID,slotNumber,itemCount) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertConsumable, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, inventorySlot.getCharacter().getCharacterID());
      insertStmt.setInt(2, inventorySlot.getItem().getItemID());
      insertStmt.setInt(3, inventorySlot.getSlotNumber());
      insertStmt.setInt(4, inventorySlot.getItemCount());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int slotID = -1;
      if (resultKey.next()) {
        slotID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      inventorySlot.setSlotID(slotID);
      return inventorySlot;
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

  public InventorySlot getInventorySlotByID(int slotID) throws SQLException {
    String selectInventorySlot = "SELECT slotID,characterID,itemID,slotNumber,itemCount FROM InventorySlot WHERE slotID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectInventorySlot);
      selectStmt.setInt(1, slotID);
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      ItemDao itemDao = ItemDao.getInstance();
      if (results.next()) {
        int resultSlotID = results.getInt("slotID");
        int characterID = results.getInt("characterID");
        Character character = characterDao.getCharacterByID(characterID);
        int itemID = results.getInt("itemID");
        Item item = itemDao.getItemByID(itemID);
        int slotNumber = results.getInt("slotNumber");
        int itemCount = results.getInt("itemCount");
        InventorySlot inventorySlot = new InventorySlot(resultSlotID, character, item, slotNumber,
            itemCount);
        return inventorySlot;
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

  public InventorySlot updateItemCount(InventorySlot inventorySlot, int newItemCount)
      throws SQLException {
    String updateItemCount = "UPDATE InventorySlot SET itemCount=? " + "WHERE slotID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateItemCount);
      updateStmt.setInt(1, newItemCount);
      updateStmt.setInt(2, inventorySlot.getSlotID());
      updateStmt.executeUpdate();
      inventorySlot.setItemCount(newItemCount);
      return inventorySlot;
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

  public List<InventorySlot> getInventorySlotsByCharacter(Character character)
      throws SQLException {
    List<InventorySlot> inventorySlots = new ArrayList<>();
    String selectInventorySlots = "SELECT slotID,characterID,itemID,slotNumber,itemCount FROM InventorySlot WHERE characterID=? ORDER BY slotNumber;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectInventorySlots);
      selectStmt.setInt(1, character.getCharacterID());
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      ItemDao itemDao = ItemDao.getInstance();
      while (results.next()) {
        int slotID = results.getInt("slotID");
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        int itemID = results.getInt("itemID");
        Item item = itemDao.getItemByID(itemID);
        int slotNumber = results.getInt("slotNumber");
        int itemCount = results.getInt("itemCount");
        InventorySlot inventorySlot = new InventorySlot(slotID, resultCharacter, item, slotNumber,
            itemCount);
        inventorySlots.add(inventorySlot);
      }
    } catch (SQLException e) {
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
    return inventorySlots;
  }

  public void delete(InventorySlot inventorySlot) throws SQLException {
    String deleteItemEquipableJob = "DELETE FROM InventorySlot WHERE slotID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteItemEquipableJob);
      deleteStmt.setInt(1, inventorySlot.getSlotID());
      deleteStmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (deleteStmt != null) {
        deleteStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
