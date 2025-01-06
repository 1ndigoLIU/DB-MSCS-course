package org.database5700.teamproject.model;

import java.math.BigDecimal;

public class Gear extends Item {
  protected int itemLevel;
  protected String slotType;
  protected int requiredLevel;
  protected int defenseRating;
  protected int magicDefenseRating;

  public Gear(int itemID, String name, int maxStackSize, Boolean soldable,
      BigDecimal vendorPrice, int itemLevel, String slotType, int requiredLevel,
      int defenseRating, int magicDefenseRating) {
    super(itemID, name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.slotType = slotType;
    this.requiredLevel = requiredLevel;
    this.defenseRating = defenseRating;
    this.magicDefenseRating = magicDefenseRating;
  }

  public Gear(String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice,
      int itemLevel,
      String slotType, int requiredLevel, int defenseRating, int magicDefenseRating) {
    super(name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.slotType = slotType;
    this.requiredLevel = requiredLevel;
    this.defenseRating = defenseRating;
    this.magicDefenseRating = magicDefenseRating;
  }

  public int getItemLevel() {
    return itemLevel;
  }

  public void setItemLevel(int itemLevel) {
    this.itemLevel = itemLevel;
  }

  public String getSlotType() {
    return slotType;
  }

  public void setSlotType(String slotType) {
    this.slotType = slotType;
  }

  public int getRequiredLevel() {
    return requiredLevel;
  }

  public void setRequiredLevel(int requiredLevel) {
    this.requiredLevel = requiredLevel;
  }

  public int getDefenseRating() {
    return defenseRating;
  }

  public void setDefenseRating(int defenseRating) {
    this.defenseRating = defenseRating;
  }

  public int getMagicDefenseRating() {
    return magicDefenseRating;
  }

  public void setMagicDefenseRating(int magicDefenseRating) {
    this.magicDefenseRating = magicDefenseRating;
  }

  @Override
  public String toString() {
    return "Gear{" +
        "itemLevel=" + itemLevel +
        ", slotType=" + slotType +
        ", requiredLevel=" + requiredLevel +
        ", defenseRating=" + defenseRating +
        ", magicDefenseRating=" + magicDefenseRating +
        ", itemID=" + itemID +
        ", name='" + name + '\'' +
        ", maxStackSize=" + maxStackSize +
        ", soldable=" + soldable +
        ", vendorPrice=" + vendorPrice +
        '}';
  }
}
