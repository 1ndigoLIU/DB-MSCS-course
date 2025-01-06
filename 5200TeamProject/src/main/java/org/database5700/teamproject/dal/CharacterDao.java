package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.Character;
import org.database5700.teamproject.model.Player;

public class CharacterDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CharacterDao instance = null;

  protected CharacterDao() {
    connectionManager = new ConnectionManager();
  }

  public static CharacterDao getInstance() {
    if (instance == null) {
      instance = new CharacterDao();
    }
    return instance;
  }

  public Character getCharacterByID(int characterID) throws SQLException {
    String selectCharacter = "SELECT characterID, accountID, firstName, lastName FROM `Character` WHERE characterID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacter);
      selectStmt.setInt(1, characterID);
      results = selectStmt.executeQuery();
      PlayerDao playerDao = PlayerDao.getInstance();
      if (results.next()) {
        int resultCharacterID = results.getInt("characterID");
        int accountID = results.getInt("accountID");
        String firstName = results.getString("firstName");
        String lastName = results.getString("lastName");
        Player player = playerDao.getPlayerByID(accountID);
        Character character = new Character(resultCharacterID, player, firstName, lastName);
        return character;
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

  public Character create(Character character) throws SQLException {
    String insertCharacter = "INSERT INTO `Character`(accountID, firstName, lastName) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCharacter, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, character.getPlayer().getAccountId());
      insertStmt.setString(2, character.getFirstName());
      insertStmt.setString(3, character.getLastName());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int characterID = -1;
      if (resultKey.next()) {
        characterID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      character.setCharacterID(characterID);
      return character;
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

  public List<Character> getCharacterByPlayer(Player player) throws SQLException {
    List<Character> characters = new ArrayList<>();
    String selectCharacter = "SELECT characterID, accountID, firstName, lastName FROM `Character` WHERE accountID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacter);
      selectStmt.setInt(1, player.getAccountId());
      results = selectStmt.executeQuery();
      PlayerDao playerDao = PlayerDao.getInstance();
      while (results.next()) {
        int characterID = results.getInt("characterID");
        int accountID = results.getInt("accountID");
        String firstName = results.getString("firstName");
        String lastName = results.getString("lastName");
        Player resultPlayer = playerDao.getPlayerByID(accountID);
        Character character = new Character(characterID, resultPlayer, firstName, lastName);
        characters.add(character);
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
    return characters;
  }

  public void delete(Character character) throws SQLException {
    String deleteCharacter = "DELETE FROM `Character` WHERE characterID=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCharacter);
      deleteStmt.setInt(1, character.getCharacterID());
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