package org.database5700.teamproject.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

public class ItemEquipableJobDao {

  protected ConnectionManager connectionManager;

  private static ItemEquipableJobDao instance = null;

  protected ItemEquipableJobDao() {
    connectionManager = new ConnectionManager();
  }

  public static ItemEquipableJobDao getInstance() {
    if (instance == null) {
      instance = new ItemEquipableJobDao();
    }
    return instance;
  }

  public ItemEquipableJob create(ItemEquipableJob itemEquipableJob) throws SQLException {
    String insertItemEquipableJob = "INSERT INTO ItemEquipableJob(itemID, jobID) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertItemEquipableJob);
      insertStmt.setInt(1, itemEquipableJob.getItem().getItemID());
      insertStmt.setInt(2, itemEquipableJob.getJob().getJobID());
      insertStmt.executeUpdate();
      return itemEquipableJob;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (insertStmt != null) {
        insertStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  public ItemEquipableJob getItemEquipableJobByItemAndJob(Item item, Job job) throws SQLException {
    String selectItemEquipableJob =
        "SELECT itemID,jobID " + "FROM ItemEquipableJob " + "WHERE itemID=? AND jobID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectItemEquipableJob);
      selectStmt.setInt(1, item.getItemID());
      selectStmt.setInt(2, job.getJobID());
      results = selectStmt.executeQuery();
      ItemDao itemDao = ItemDao.getInstance();
      JobDao jobDao = JobDao.getInstance();
      if (results.next()) {
        int itemID = results.getInt("itemID");
        Item resultItem = itemDao.getItemByID(itemID);
        int jobID = results.getInt("jobID");
        Job resultJob = jobDao.getJobByID(jobID);
        return new ItemEquipableJob(resultItem, resultJob);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (results != null) {
        results.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return null;
  }

  public void delete(ItemEquipableJob itemEquipableJob) throws SQLException {
    String deleteItemEquipableJob = "DELETE FROM ItemEquipableJob WHERE itemID = ? AND jobID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteItemEquipableJob);
      deleteStmt.setInt(1, itemEquipableJob.getItem().getItemID());
      deleteStmt.setInt(2, itemEquipableJob.getJob().getJobID());
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
