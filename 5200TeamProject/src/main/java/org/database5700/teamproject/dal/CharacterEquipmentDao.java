package org.database5700.teamproject.dal;

import org.database5700.teamproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.Character;

public class CharacterEquipmentDao {

  protected ConnectionManager connectionManager;

  private static CharacterEquipmentDao instance = null;

  protected CharacterEquipmentDao() {
    connectionManager = new ConnectionManager();
  }

  public static CharacterEquipmentDao getInstance() {
    if (instance == null) {
      instance = new CharacterEquipmentDao();
    }
    return instance;
  }

  public CharacterEquipment create(CharacterEquipment characterEquipment) throws SQLException {
    String insertCharacterEquipment = "INSERT INTO CharacterEquipment(characterID,slotType,itemID) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCharacterEquipment);
      insertStmt.setInt(1, characterEquipment.getCharacter().getCharacterID());
      insertStmt.setString(2, characterEquipment.getSlotType());
      insertStmt.setInt(3, characterEquipment.getItem().getItemID());
      insertStmt.executeUpdate();
      return characterEquipment;
    } catch (SQLException e) {
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

  public CharacterEquipment getCharacterEquipmentByCharacterAndSlotType(Character character,
      String slotType) throws SQLException {
    String selectCharacterEquipment = "SELECT characterID,slotType,itemID FROM CharacterEquipment WHERE characterID=? AND slotType=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterEquipment);
      selectStmt.setInt(1, character.getCharacterID());
      selectStmt.setString(2, slotType);
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      ItemDao itemDao = ItemDao.getInstance();
      if (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        String resultSlotType = results.getString("SlotType");
        int itemID = results.getInt("itemID");
        Item item = itemDao.getItemByID(itemID);
        return new CharacterEquipment(resultCharacter, resultSlotType, item);
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
    return null;
  }

  public List<CharacterEquipment> getCharacterEquipmentByCharacter(Character character)
      throws SQLException {
    List<CharacterEquipment> characterEquipments = new ArrayList<>();
    String selectCharacterEquipments = "SELECT characterID,slotType,itemID FROM CharacterEquipment WHERE characterID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterEquipments);
      selectStmt.setInt(1, character.getCharacterID());
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      ItemDao itemDao = ItemDao.getInstance();
      while (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        String slotType = results.getString("SlotType");
        int itemID = results.getInt("itemID");
        Item item = itemDao.getItemByID(itemID);
        CharacterEquipment characterEquipment = new CharacterEquipment(resultCharacter, slotType,
            item);
        characterEquipments.add(characterEquipment);
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
    return characterEquipments;
  }
}
