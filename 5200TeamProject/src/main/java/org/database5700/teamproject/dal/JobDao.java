package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.database5700.teamproject.model.*;

public class JobDao {

  protected ConnectionManager connectionManager;

  private static JobDao instance = null;

  protected JobDao() {
    connectionManager = new ConnectionManager();
  }

  public static JobDao getInstance() {
    if (instance == null) {
      instance = new JobDao();
    }
    return instance;
  }

  public Job create(Job job) throws SQLException {
    String insertJob = "INSERT INTO Job(name) VALUES(?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;

    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertJob, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, job.getName());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int jobID = -1;
      if (resultKey.next()) {
        jobID = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      job.setJobID(jobID);
      return job;
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

  public Job getJobByID(int jobID) throws SQLException {
    String selectJob = "SELECT jobID, name FROM Job WHERE jobID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectJob);
      selectStmt.setInt(1, jobID);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int resultJobID = results.getInt("jobID");
        String name = results.getString("name");
        Job job = new Job(resultJobID, name);
        return job;
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

  public Job updateJobName(Job job, String newName) throws SQLException {
    String updateJob = "UPDATE Job SET name=? WHERE jobID=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;

    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateJob);
      updateStmt.setString(1, newName);
      updateStmt.setInt(2, job.getJobID());
      updateStmt.executeUpdate();
      job.setName(newName);
      return job;
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
