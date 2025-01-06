package org.database5700.teamproject.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.database5700.teamproject.model.*;

public class WeaponDao extends ItemDao {

  protected ConnectionManager connectionManager;
  private static WeaponDao instance = null;

  protected WeaponDao() {
    connectionManager = new ConnectionManager();
  }

  public static WeaponDao getInstance() {
    if (instance == null) {
      instance = new WeaponDao();
    }
    return instance;
  }

  public Weapon create(Weapon weapon) throws SQLException {
    Item item = create(new Item(weapon.getName(), weapon.getMaxStackSize(), weapon.getSoldable(),
        weapon.getVendorPrice()));
    weapon.setItemID(item.getItemID());
    String insertWeapon =
        "INSERT INTO Weapon(itemID,itemLevel,slotType,requiredLevel,damage,autoAttack,attackDelay"
            + ") VALUES(?,?,?,?,?,?,?)";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertWeapon);
      insertStmt.setInt(1, weapon.getItemID());
      insertStmt.setInt(2, weapon.getItemLevel());
      insertStmt.setString(3, weapon.getSlotType());
      insertStmt.setInt(4, weapon.getRequiredLevel());
      insertStmt.setInt(5, weapon.getDamage());
      insertStmt.setBigDecimal(6, weapon.getAutoAttack());
      insertStmt.setBigDecimal(7, weapon.getAttackDelay());
      insertStmt.executeUpdate();
      return weapon;
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

  public Weapon getWeaponByID(int itemID) throws SQLException {
    String selectWeapon =
        "SELECT Weapon.itemID AS itemID,`name`,maxStackSize,soldable,vendorPrice,itemLevel,slotType,requiredLevel,damage,autoAttack,attackDelay "
            + "FROM Weapon INNER JOIN Item ON Weapon.itemID=Item.itemID "
            + "WHERE Weapon.itemID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectWeapon);
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
        int damage = results.getInt("damage");
        BigDecimal autoAttack = results.getBigDecimal("autoAttack");
        BigDecimal attackDelay = results.getBigDecimal("attackDelay");

        return new Weapon(resultItemID, name, maxStackSize, soldable, vendorPrice, itemLevel,
            slotType, requiredLevel, damage, autoAttack, attackDelay);
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

  public void delete(Weapon weapon) throws SQLException {
    String deleteApplicant = "Delete FROM Weapon WHERE itemID = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteApplicant);
      deleteStmt.setInt(1, weapon.getItemID());
      deleteStmt.executeUpdate();
      super.delete(weapon);
    } catch (SQLException e) {
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
