package org.database5700.teamproject.model;

import java.math.BigDecimal;

public class Item {
  protected int itemID;
  protected String name;
  protected int maxStackSize;
  protected Boolean soldable;
  protected BigDecimal vendorPrice;


  public Item(int itemID, String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice) {
    this.itemID = itemID;
    this.name = name;
    this.maxStackSize = maxStackSize;
    this.soldable = soldable;
    this.vendorPrice = vendorPrice;
  }

  public Item(String name, int maxStackSize, Boolean soldable, BigDecimal vendorPrice) {
    this.name = name;
    this.maxStackSize = maxStackSize;
    this.soldable = soldable;
    this.vendorPrice = vendorPrice;
  }

  public int getItemID() {
    return itemID;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public java.lang.String getName() {
    return name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }

  public int getMaxStackSize() {
    return maxStackSize;
  }

  public void setMaxStackSize(int maxStackSize) {
    this.maxStackSize = maxStackSize;
  }

  public Boolean getSoldable() {
    return soldable;
  }

  public void setSoldable(Boolean soldable) {
    this.soldable = soldable;
  }

  public BigDecimal getVendorPrice() {
    return vendorPrice;
  }

  public void setVendorPrice(BigDecimal vendorPrice) {
    this.vendorPrice = vendorPrice;
  }

  @Override
  public java.lang.String toString() {
    return "Item{" +
        "itemID=" + itemID +
        ", name='" + name + '\'' +
        ", maxStackSize=" + maxStackSize +
        ", soldable=" + soldable +
        ", vendorPrice=" + vendorPrice +
        '}';
  }
}
