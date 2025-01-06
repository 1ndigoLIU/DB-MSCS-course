package org.database5700.teamproject.model;

public class CharacterAttribute {

  protected Character character;
  protected String attrName;
  protected int value;

  public CharacterAttribute(Character character, String attrName, int value) {
    this.character = character;
    this.attrName = attrName;
    this.value = value;
  }

  public CharacterAttribute(Character character, String attrName) {
    this.character = character;
    this.attrName = attrName;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CharacterAttribute{" + "character=" + character + ", attrName='" + attrName + '\''
        + ", value=" + value + '}';
  }
}
