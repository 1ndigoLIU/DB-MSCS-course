package org.database5700.teamproject.model;

public class Character {

  protected int characterID;
  protected Player player;
  protected String firstName;
  protected String lastName;

  public Character(int characterID, Player player, String firstName, String lastName) {
    this.characterID = characterID;
    this.player = player;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Character(Player player, String firstName, String lastName) {
    this.player = player;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public int getCharacterID() {
    return characterID;
  }

  public void setCharacterID(int characterID) {
    this.characterID = characterID;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Character{" + "characterID=" + characterID + ", player=" + player + ", firstName='"
        + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
  }
}
