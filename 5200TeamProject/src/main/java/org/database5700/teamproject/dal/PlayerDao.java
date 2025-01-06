package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.database5700.teamproject.model.Player;

public class  PlayerDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static PlayerDao instance = null;

  protected PlayerDao() {
    connectionManager = new ConnectionManager();
  }

  public static PlayerDao getInstance() {
    if (instance == null) {
      instance = new PlayerDao();
    }
    return instance;
  }

  public Player getPlayerByID(int playerID) throws SQLException {
    String selectPlayer = "SELECT accountID, name, email, status FROM Player WHERE accountID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPlayer);
      selectStmt.setInt(1, playerID);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int accoundID = results.getInt("accountID");
        String name = results.getString("name");
        String email = results.getString("email");
        String status = results.getString("status");
        Player player = new Player(accoundID, name, email, status);
        return player;
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

  public Player create(Player player) throws SQLException {
    String insertPlayer = "INSERT INTO Player(name, email, status) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertPlayer,
          Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, player.getName());
      insertStmt.setString(2, player.getEmail());
      insertStmt.setString(3, player.getStatus());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int accountID = -1;
      if (resultKey.next()) {
        accountID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      player.setAccountId(accountID);
      return player;
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

  public List<Player> getPlayerByStatus(String status) throws SQLException {
    List<Player> players = new ArrayList<>();
    String selectPlayer = "SELECT accountID, name, email, status FROM Player WHERE status=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPlayer);
      selectStmt.setString(1, status);
      results = selectStmt.executeQuery();
      while (results.next()) {
        int accoundID = results.getInt("accountID");
        String name = results.getString("name");
        String email = results.getString("email");
        String resultStatus = results.getString("status");
        Player player = new Player(accoundID, name, email, resultStatus);
        players.add(player);
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
    return players;
  }


  public List<Player> getPlayerByName(String name) throws SQLException {
    List<Player> players = new ArrayList<>();
    String selectPlayer = "SELECT accountID, name, email, status FROM Player WHERE name LIKE ? ORDER BY name;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPlayer);
      selectStmt.setString(1, "%" + name + "%");
      results = selectStmt.executeQuery();
      while (results.next()) {
        int accoundID = results.getInt("accountID");
        String resultName = results.getString("name");
        String email = results.getString("email");
        String status = results.getString("status");
        Player player = new Player(accoundID, resultName, email, status);
        players.add(player);
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
    return players;
  }


  public Player updateName(Player player, String newName) throws SQLException {
    String updateName = "UPDATE Player SET name=? WHERE accountID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateName);
      updateStmt.setString(1, newName);
      updateStmt.setInt(2, player.getAccountId());
      updateStmt.executeUpdate();
      player.setName(newName);
      return player;
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

  public void delete(Player player) throws SQLException {
    String deletePlayer = "DELETE FROM Player WHERE accountID=?";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deletePlayer);
      deleteStmt.setInt(1, player.getAccountId());
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
