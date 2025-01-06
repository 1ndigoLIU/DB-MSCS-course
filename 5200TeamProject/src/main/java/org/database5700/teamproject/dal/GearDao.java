package org.database5700.teamproject.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.database5700.teamproject.model.*;

public class GearDao extends ItemDao {

  protected ConnectionManager connectionManager;
  private static GearDao instance = null;

  protected GearDao() {
    connectionManager = new ConnectionManager();
  }

  public static GearDao getInstance() {
    if (instance == null) {
      instance = new GearDao();
    }
    return instance;
  }

  public Gear create(Gear gear) throws SQLException {
    Item item = create(new Item(gear.getName(), gear.getMaxStackSize(), gear.getSoldable(),
        gear.getVendorPrice()));
    gear.setItemID(item.getItemID());
    String insertGear =
        "INSERT INTO Gear(itemID, itemLevel, slotType, requiredLevel, defenseRating, magicDefenseRating"
            + ") VALUES(?,?,?,?,?,?)";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertGear);
      insertStmt.setInt(1, gear.getItemID());
      insertStmt.setInt(2, gear.getItemLevel());
      insertStmt.setString(3, gear.getSlotType());
      insertStmt.setInt(4, gear.getRequiredLevel());
      insertStmt.setInt(5, gear.getDefenseRating());
      insertStmt.setInt(6, gear.getMagicDefenseRating());
      insertStmt.executeUpdate();
      return gear;
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

  public Gear getGearByID(int itemID) throws SQLException {
    Item item = getItemByID(itemID);
    String selectGear =
        "SELECT Gear.itemID AS itemID,`name`,maxStackSize,soldable,vendorPrice,itemLevel,slotType,requiredLevel,defenseRating,magicDefenseRating "
            + "FROM Gear INNER JOIN Item ON Gear.itemID=Item.itemID " + "WHERE Gear.itemID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectGear);
      selectStmt.setInt(1, itemID);
      results = selectStmt.executeQuery();
      if (results.next()) {
        int resultItemID = results.getInt("itemID");
        String name = results.getString("name");
        int maxStackSize = results.getInt("maxStackSize");
        boolean soldable = results.getBoolean("soldable");
        BigDecimal vendorPrice = results.getBigDecimal("vendorPrice");

        int itemLevel = results.getInt("itemLevel");
        String slotType = results.getString("slotType");
        int requiredLevel = results.getInt("requiredLevel");
        int defenseRating = results.getInt("defenseRating");
        int magicDefenseRating = results.getInt("magicDefenseRating");
        return new Gear(resultItemID, name, maxStackSize, soldable, vendorPrice, itemLevel,
            slotType, requiredLevel, defenseRating, magicDefenseRating);
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

  public Gear updateItemLevel(Gear gear, int itemLevel) throws SQLException {
    String updateGear = "UPDATE Gear SET itemLevel = ? WHERE itemID = ?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;

    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateGear);
      updateStmt.setInt(1, itemLevel);
      updateStmt.setInt(2, gear.getItemID());
      int affectedRows = updateStmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("No record found with the provided itemID to update.");
      }
      gear.setItemLevel(itemLevel);
      // Fetch the updated Gear object
      return gear;
    } catch (SQLException e) {
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
