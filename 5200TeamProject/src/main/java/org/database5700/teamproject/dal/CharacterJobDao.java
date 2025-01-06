package org.database5700.teamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

public class CharacterJobDao {

  protected ConnectionManager connectionManager;

  private static CharacterJobDao instance = null;

  protected CharacterJobDao() {
    connectionManager = new ConnectionManager();
  }

  public static CharacterJobDao getInstance() {
    if (instance == null) {
      instance = new CharacterJobDao();
    }
    return instance;
  }

  public CharacterJob create(CharacterJob characterJob) throws SQLException {
    String insertCharacterJob = "INSERT INTO CharacterJob(characterID, jobID, `level`, experiencePoint) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCharacterJob);
      insertStmt.setInt(1, characterJob.getCharacter().getCharacterID());
      insertStmt.setInt(2, characterJob.getJob().getJobID());
      insertStmt.setInt(3, characterJob.getLevel());
      insertStmt.setInt(4, characterJob.getExperiencePoint());
      insertStmt.executeUpdate();
      return characterJob;
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

  public CharacterJob getCharacterJobByCharacterAndJob(Character character, Job job)
      throws SQLException {
    String selectCharacterJob =
        "SELECT characterID,jobID,`level`,experiencePoint " + "FROM CharacterJob "
            + "WHERE characterID=? AND jobID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterJob);
      selectStmt.setInt(1, character.getCharacterID());
      selectStmt.setInt(2, job.getJobID());
      results = selectStmt.executeQuery();
      CharacterDao characterDao = CharacterDao.getInstance();
      JobDao jobDao = JobDao.getInstance();
      if (results.next()) {
        int characterID = results.getInt("characterID");
        Character resultCharacter = characterDao.getCharacterByID(characterID);
        int jobID = results.getInt("jobID");
        Job resultJob = jobDao.getJobByID(jobID);
        int level = results.getInt("level");
        int experiencePoint = results.getInt("experiencePoint");
        CharacterJob characterJob = new CharacterJob(resultCharacter, resultJob, level,
            experiencePoint);
        return characterJob;
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

  public List<CharacterJob> getCharacterJobByCharacter(Character character) throws SQLException {
    List<CharacterJob> characterJobs = new ArrayList<>();
    String selectCharacterJobs = "SELECT jobID, `level`, experiencePoint FROM CharacterJob WHERE characterID = ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCharacterJobs);
      selectStmt.setInt(1, character.getCharacterID());
      results = selectStmt.executeQuery();
      JobDao jobDao = JobDao.getInstance();
      while (results.next()) {
        int jobID = results.getInt("jobID");
        Job job = jobDao.getJobByID(jobID);
        int level = results.getInt("level");
        int experiencePoint = results.getInt("experiencePoint");
        CharacterJob characterJob = new CharacterJob(character, job, level, experiencePoint);
        characterJobs.add(characterJob);
      }
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
    return characterJobs;
  }


  public CharacterJob updateLevel(CharacterJob characterJob, int newLevel) throws SQLException {
    String updateLevel = "UPDATE CharacterJob SET `level` = ? WHERE characterID = ? AND jobID = ?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;

    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateLevel);
      updateStmt.setInt(1, newLevel);
      updateStmt.setInt(2, characterJob.getCharacter().getCharacterID());
      updateStmt.setInt(3, characterJob.getJob().getJobID());
      updateStmt.executeUpdate();
      characterJob.setLevel(newLevel);
      return characterJob;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (updateStmt != null) {
        updateStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
