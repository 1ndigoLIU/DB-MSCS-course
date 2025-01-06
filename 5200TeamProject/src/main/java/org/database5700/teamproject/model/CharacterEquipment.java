package org.database5700.teamproject.model;

public class CharacterEquipment {

  protected Character character;
  protected String slotType;
  protected Item item;

  public CharacterEquipment(Character character, String slotType, Item item) {
    this.character = character;
    this.slotType = slotType;
    this.item = item;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public String getSlotType() {
    return slotType;
  }

  public void setSlotType(String slotType) {
    this.slotType = slotType;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "CharacterEquipment{" + "character=" + character + ", item=" + item + ", slotType='"
        + slotType + '\'' + '}';
  }
}
