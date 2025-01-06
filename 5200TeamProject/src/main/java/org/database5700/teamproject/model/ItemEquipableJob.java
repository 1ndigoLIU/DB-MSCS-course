package org.database5700.teamproject.model;

public class ItemEquipableJob {

  protected Item item;
  protected Job job;

  public ItemEquipableJob(Item item, Job job) {
    this.item = item;
    this.job = job;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Job getJob() {
    return job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  @Override
  public String toString() {
    return "ItemEquipableJob{" + "item=" + item + ", job=" + job + '}';
  }
}
