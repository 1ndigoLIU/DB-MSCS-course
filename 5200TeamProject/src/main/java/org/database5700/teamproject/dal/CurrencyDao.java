package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import org.database5700.teamproject.model.*;

public class CurrencyDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CurrencyDao instance = null;

  protected CurrencyDao() {
    connectionManager = new ConnectionManager();
  }

  public static CurrencyDao getInstance() {
    if (instance == null) {
      instance = new CurrencyDao();
    }
    return instance;
  }

  public Currency create(Currency currency) throws SQLException {
    String insertCurrency = "INSERT INTO Currency(name,maxAmount,weeklyCap) " + "VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCurrency, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, currency.getName());
      insertStmt.setInt(2, currency.getMaxAmount());
      Integer weeklyCap = currency.getWeeklyCap();
      if (weeklyCap != null) {
        insertStmt.setInt(3, weeklyCap);
      } else {
        insertStmt.setNull(3, Types.INTEGER);
      }
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int currentcyID = -1;
      if (resultKey.next()) {
        currentcyID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      currency.setCurrentcyID(currentcyID);
      return currency;
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

  public Currency getCurrencyByID(int currentcyID) throws SQLException {
    String selectCurrency = "SELECT currencyID,name,maxAmount,weeklyCap FROM Currency WHERE currencyID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCurrency);
      selectStmt.setInt(1, currentcyID);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int resultCurrentcyID = results.getInt("currencyID");
        String name = results.getString("name");
        int maxAmount = results.getInt("maxAmount");
        Integer weeklyCap = (Integer) results.getObject("weeklyCap");
        Currency currency = new Currency(resultCurrentcyID, name, maxAmount, weeklyCap);
        return currency;
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

  public Currency updateMaxAmount(Currency currency, int newMaxAmount) throws SQLException {
    String updateCurrency = "UPDATE Currency SET maxAmount=? WHERE currencyID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCurrency);
      updateStmt.setInt(1, newMaxAmount);
      updateStmt.setInt(2, currency.getCurrentcyID());
      updateStmt.executeUpdate();
      currency.setMaxAmount(newMaxAmount);
      return currency;
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

  public Currency updateWeeklyCap(Currency currency, Integer newWeeklyCap) throws SQLException {
    String updateCurrency = "UPDATE Currency SET weeklyCap=? WHERE currentcyID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCurrency);
      if (newWeeklyCap != null) {
        updateStmt.setInt(1, newWeeklyCap);
      } else {
        updateStmt.setNull(1, Types.INTEGER);
      }
      updateStmt.setInt(2, currency.getCurrentcyID());
      updateStmt.executeUpdate();
      currency.setWeeklyCap(newWeeklyCap);
      return currency;
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
