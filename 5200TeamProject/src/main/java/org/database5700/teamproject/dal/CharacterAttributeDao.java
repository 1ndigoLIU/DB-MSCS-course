package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

public class CharacterAttributeDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CharacterAttributeDao instance = null;

  protected CharacterAttributeDao() {
    connectionManager = new ConnectionManager();
  }

  public static CharacterAttributeDao getInstance() {
    if (instance == null) {
      instance = new CharacterAttributeDao();
    }
    return instance;
  }

  public CharacterAttribute create(CharacterAttribute characterAttribute) throws SQLException {
    String insertCharacterAttribute =
        "INSERT INTO CharacterAttribute(characterID,attrName,value) " + "VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCharacterAttribute);
      insertStmt.setInt(1, characterAttribute.getCharacter().getCharacterID());
      insertStmt.setString(2, characterAttribute.getAttrName());
      insertStmt.setInt(3, characterAttribute.getValue());
      insertStmt.executeUpdate();
      return characterAttribute;
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

  public CharacterAttribute getCharacterAttributeByCharacterAndAttrName(Character character,
      String attrName) throws SQLException {
    String selectCharacterAttribute =
        "SELECT characterID,attrName,value " + "FROM CharacterAttribute "
            + "WHERE characterID=? AND attrName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterAttribute);
      selectStmt.setInt(1, character.getCharacterID());
      selectStmt.setString(2, attrName);
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      if (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        String resultAttrName = results.getString("attrName");
        int value = results.getInt("value");
        CharacterAttribute characterAttribute = new CharacterAttribute(resultCharacter,
            resultAttrName, value);
        return characterAttribute;
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

  public CharacterAttribute updateValue(CharacterAttribute characterAttribute, int newValue)
      throws SQLException {
    String updateCharacterAttribute =
        "UPDATE CharacterAttribute SET value=? " + "WHERE characterID=? AND attrName=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCharacterAttribute);
      updateStmt.setInt(1, newValue);
      updateStmt.setInt(2, characterAttribute.getCharacter().getCharacterID());
      updateStmt.setString(3, characterAttribute.getAttrName());
      updateStmt.executeUpdate();
      characterAttribute.setValue(newValue);
      return characterAttribute;
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

  public List<CharacterAttribute> getCharacterAttributesByCharacter(Character character)
      throws SQLException {
    List<CharacterAttribute> characterAttributes = new ArrayList<>();
    String selectCharacterAttributes = "SELECT characterID,attrName,`value` FROM CharacterAttribute WHERE characterID= ? ORDER BY `value` DESC;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterAttributes);
      selectStmt.setInt(1, character.getCharacterID());
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      while (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        String attrName = results.getString("attrName");
        int value = results.getInt("value");
        CharacterAttribute characterAttribute = new CharacterAttribute(resultCharacter, attrName,
            value);
        characterAttributes.add(characterAttribute);
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
    return characterAttributes;
  }
}
