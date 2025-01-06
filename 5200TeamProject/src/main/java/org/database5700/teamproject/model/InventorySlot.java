package org.database5700.teamproject.model;

public class InventorySlot {

  protected int slotID;
  protected Character character;
  protected Item item;
  protected int slotNumber;
  protected int itemCount;

  public InventorySlot(int slotID, Character character, Item item, int slotNumber, int itemCount) {
    this.slotID = slotID;
    this.character = character;
    this.item = item;
    this.slotNumber = slotNumber;
    this.itemCount = itemCount;
  }

  public InventorySlot(Character character, Item item, int slotNumber, int itemCount) {
    this.character = character;
    this.item = item;
    this.slotNumber = slotNumber;
    this.itemCount = itemCount;
  }

  public int getSlotID() {
    return slotID;
  }

  public void setSlotID(int slotID) {
    this.slotID = slotID;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(int slotNumber) {
    this.slotNumber = slotNumber;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  @Override
  public String toString() {
    return "InventorySlot{" + "slotID=" + slotID + ", character=" + character + ", item=" + item
        + ", slotNumber=" + slotNumber + ", itemCount=" + itemCount + '}';
  }
}
