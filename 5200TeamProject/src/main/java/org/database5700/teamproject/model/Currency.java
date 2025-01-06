package org.database5700.teamproject.model;

public class Currency {

  protected int currentcyID;
  protected String name;
  protected int maxAmount;
  protected Integer weeklyCap;

  public Currency(int currentcyID, String name, int maxAmount, Integer weeklyCap) {
    this.currentcyID = currentcyID;
    this.name = name;
    this.maxAmount = maxAmount;
    this.weeklyCap = weeklyCap;
  }

  public Currency(String name, int maxAmount, Integer weeklyCap) {
    this.name = name;
    this.maxAmount = maxAmount;
    this.weeklyCap = weeklyCap;
  }

  public Currency(int currentcyID) {
    this.currentcyID = currentcyID;
  }

  public int getCurrentcyID() {
    return currentcyID;
  }

  public void setCurrentcyID(int currentcyID) {
    this.currentcyID = currentcyID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(int maxAmount) {
    this.maxAmount = maxAmount;
  }

  public Integer getWeeklyCap() {
    return weeklyCap;
  }

  public void setWeeklyCap(Integer weeklyCap) {
    this.weeklyCap = weeklyCap;
  }

  @Override
  public String toString() {
    return "Currency{" + "currentcyID=" + currentcyID + ", name='" + name + '\'' + ", maxAmount="
        + maxAmount + ", weeklyCap=" + weeklyCap + '}';
  }
}
