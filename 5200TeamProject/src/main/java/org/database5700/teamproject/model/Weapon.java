package org.database5700.teamproject.model;

import java.math.BigDecimal;

public class Weapon extends Item {

  protected int itemLevel;
  protected String slotType;
  protected int requiredLevel;
  protected int damage;
  protected BigDecimal autoAttack;
  protected BigDecimal attackDelay;

  public Weapon(int itemID, String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice,
      int itemLevel, String slotType, int requiredLevel, int damage, BigDecimal autoAttack,
      BigDecimal attackDelay) {
    super(itemID, name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.slotType = slotType;
    this.requiredLevel = requiredLevel;
    this.damage = damage;
    this.autoAttack = autoAttack;
    this.attackDelay = attackDelay;
  }

  public Weapon(String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice,
      int itemLevel, String slotType, int requiredLevel, int damage, BigDecimal autoAttack,
      BigDecimal attackDelay) {
    super(name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.slotType = slotType;
    this.requiredLevel = requiredLevel;
    this.damage = damage;
    this.autoAttack = autoAttack;
    this.attackDelay = attackDelay;
  }

  public int getItemLevel() {
    return itemLevel;
  }

  public String getSlotType() {
    return slotType;
  }

  public int getRequiredLevel() {
    return requiredLevel;
  }

  public int getDamage() {
    return damage;
  }

  public BigDecimal getAutoAttack() {
    return autoAttack;
  }

  public BigDecimal getAttackDelay() {
    return attackDelay;
  }

  public void setItemLevel(int itemLevel) {
    this.itemLevel = itemLevel;
  }

  public void setSlotType(String slotType) {
    this.slotType = slotType;
  }

  public void setRequiredLevel(int requiredLevel) {
    this.requiredLevel = requiredLevel;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void setAutoAttack(BigDecimal autoAttack) {
    this.autoAttack = autoAttack;
  }

  public void setAttackDelay(BigDecimal attackDelay) {
    this.attackDelay = attackDelay;
  }

  @Override
  public String toString() {
    return "Weapon{" + "itemLevel=" + itemLevel + ", slotType=" + slotType + ", requiredLevel="
        + requiredLevel + ", damage=" + damage + ", autoAttack=" + autoAttack + ", attackDelay="
        + attackDelay + ", itemID=" + itemID + ", name='" + name + '\'' + ", maxStackSize="
        + maxStackSize + ", soldable=" + soldable + ", vendorPrice=" + vendorPrice + '}';
  }
}
