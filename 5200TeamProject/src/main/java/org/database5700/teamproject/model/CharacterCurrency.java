package org.database5700.teamproject.model;

public class CharacterCurrency {

  protected Character character;
  protected Currency currency;
  protected int amount = 0;
  protected int earnedWithinWeek = 0;

  public CharacterCurrency(Character character, Currency currency, int amount,
      int earnedWithinWeek) {
    this.character = character;
    this.currency = currency;
    this.amount = amount;
    this.earnedWithinWeek = earnedWithinWeek;
  }

  public CharacterCurrency(Character character, Currency currency) {
    this.character = character;
    this.currency = currency;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getEarnedWithinWeek() {
    return earnedWithinWeek;
  }

  public void setEarnedWithinWeek(int earnedWithinWeek) {
    this.earnedWithinWeek = earnedWithinWeek;
  }

  @Override
  public String toString() {
    return "CharacterCurrency{" + "character=" + character + ", currency=" + currency + ", amount="
        + amount + ", earnedWithinWeek=" + earnedWithinWeek + '}';
  }
}
