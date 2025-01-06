package org.database5700.teamproject.model;

public class ItemAttributeBonus {

  protected Item item;
  protected String attrName;
  protected int bonus;
  protected Boolean consumable;
  protected Integer bonusCap;

  public ItemAttributeBonus(Item item, String attrName, int bonus, Boolean consumable,
      Integer bonusCap) {
    this.item = item;
    this.attrName = attrName;
    this.bonus = bonus;
    this.consumable = consumable;
    this.bonusCap = bonusCap;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public int getBonus() {
    return bonus;
  }

  public void setBonus(int bonus) {
    this.bonus = bonus;
  }

  public Boolean getConsumable() {
    return consumable;
  }

  public void setConsumable(Boolean consumable) {
    this.consumable = consumable;
  }

  public Integer getBonusCap() {
    return bonusCap;
  }

  public void setBonusCap(Integer bonusCap) {
    this.bonusCap = bonusCap;
  }

  @Override
  public String toString() {
    return "ItemAttributeBonus{" + "item=" + item + ", attrName='" + attrName + '\'' + ", bonus="
        + bonus + ", consumable=" + consumable + ", bonusCap=" + bonusCap + '}';
  }
}
