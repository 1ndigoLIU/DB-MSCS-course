package org.database5700.teamproject.model;

import java.math.BigDecimal;

public class Consumable extends Item {

  protected int itemLevel;
  protected String description;

  public Consumable(int itemID, String name, int maxStackSize, Boolean soldable,
      BigDecimal vendorPrice, int itemLevel, String description) {
    super(itemID, name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.description = description;
  }

  public Consumable(String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice,
      int itemLevel, String description) {
    super(name, maxStackSize, soldable, vendorPrice);
    this.itemLevel = itemLevel;
    this.description = description;
  }

  public int getItemLevel() {
    return itemLevel;
  }

  public void setItemLevel(int itemLevel) {
    this.itemLevel = itemLevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Consumable{" + "itemLevel=" + itemLevel + ", description='" + description + '\''
        + ", itemID=" + itemID + ", name='" + name + '\'' + ", maxStackSize=" + maxStackSize
        + ", soldable=" + soldable + ", vendorPrice=" + vendorPrice + '}';
  }
}
