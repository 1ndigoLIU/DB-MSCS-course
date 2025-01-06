package org.database5700.teamproject.model;

public class CharacterJob {

  protected Character character;
  protected Job job;
  protected int level;
  protected int experiencePoint;

  public CharacterJob(Character character, Job job, int level, int experiencePoint) {
    this.character = character;
    this.job = job;
    this.level = level;
    this.experiencePoint = experiencePoint;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public Job getJob() {
    return job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExperiencePoint() {
    return experiencePoint;
  }

  public void setExperiencePoint(int experiencePoint) {
    this.experiencePoint = experiencePoint;
  }

  @Override
  public String toString() {
    return "CharacterJob{" + "character=" + character + ", job=" + job + ", level=" + level + '}';
  }
}
