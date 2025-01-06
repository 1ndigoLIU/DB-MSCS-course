package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

public class CharacterCurrencyDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CharacterCurrencyDao instance = null;

  protected CharacterCurrencyDao() {
    connectionManager = new ConnectionManager();
  }

  public static CharacterCurrencyDao getInstance() {
    if (instance == null) {
      instance = new CharacterCurrencyDao();
    }
    return instance;
  }

  public CharacterCurrency create(CharacterCurrency characterCurrency) throws SQLException {
    String insertCharacterCurrency =
        "INSERT INTO CharacterCurrency(characterID,currencyID,amount,earnedWithinWeek) "
            + "VALUES(?,?,?,?);";
    if (characterCurrency.getAmount() == 0 && characterCurrency.getEarnedWithinWeek() == 0) {
      insertCharacterCurrency =
          "INSERT INTO CharacterCurrency(characterID,currencyID) " + "VALUES(?,?);";
    }
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCharacterCurrency);
      insertStmt.setInt(1, characterCurrency.getCharacter().getCharacterID());
      insertStmt.setInt(2, characterCurrency.getCurrency().getCurrentcyID());
      if (characterCurrency.getAmount() != 0 || characterCurrency.getEarnedWithinWeek() != 0) {
        insertStmt.setInt(3, characterCurrency.getAmount());
        insertStmt.setInt(4, characterCurrency.getEarnedWithinWeek());
      }
      insertStmt.executeUpdate();
      return characterCurrency;
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

  public CharacterCurrency getCharacterCurrencyByCharacterAndCurrency(Character character,
      Currency currency) throws SQLException {
    String selectCharacterCurrency =
        "SELECT characterID,currencyID,amount,earnedWithinWeek " + "FROM CharacterCurrency "
            + "WHERE characterID=? AND currencyID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterCurrency);
      selectStmt.setInt(1, character.getCharacterID());
      selectStmt.setInt(2, currency.getCurrentcyID());
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      CurrencyDao currencyDao = CurrencyDao.getInstance();
      if (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        int currencyID = results.getInt("currencyID");
        Currency resultCurrency = currencyDao.getCurrencyByID(currencyID);
        int amount = results.getInt("amount");
        int earnedWithinWeek = results.getInt("earnedWithinWeek");
        CharacterCurrency characterCurrency = new CharacterCurrency(resultCharacter, resultCurrency,
            amount, earnedWithinWeek);
        return characterCurrency;
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

  public CharacterCurrency updateAmount(CharacterCurrency characterCurrency, int newAmount)
      throws SQLException {
    String updateCharacterCurrency =
        "UPDATE CharacterCurrency SET amount=? " + "WHERE characterID=? AND currencyID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCharacterCurrency);
      updateStmt.setInt(1, newAmount);
      updateStmt.setInt(2, characterCurrency.getCharacter().getCharacterID());
      updateStmt.setInt(3, characterCurrency.getCurrency().getCurrentcyID());
      updateStmt.executeUpdate();
      characterCurrency.setAmount(newAmount);
      return characterCurrency;
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

  public CharacterCurrency updateEarnedWithinWeek(CharacterCurrency characterCurrency,
      int newEarnedWithinWeek) throws SQLException {
    String updateCharacterCurrency = "UPDATE CharacterCurrency SET earnedWithinWeek=? "
        + "WHERE characterID=? AND currencyID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCharacterCurrency);
      updateStmt.setInt(1, newEarnedWithinWeek);
      updateStmt.setInt(2, characterCurrency.getCharacter().getCharacterID());
      updateStmt.setInt(3, characterCurrency.getCurrency().getCurrentcyID());
      updateStmt.executeUpdate();
      characterCurrency.setEarnedWithinWeek(newEarnedWithinWeek);
      return characterCurrency;
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
