package org.database5700.teamproject.model;

public class Job {

  protected int jobID;
  protected String name;

  public Job(int jobID, String name) {
    this.jobID = jobID;
    this.name = name;
  }

  public Job(String name) {
    this.name = name;
  }

  public int getJobID() {
    return jobID;
  }

  public void setJobID(int jobID) {
    this.jobID = jobID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Job{" + "jobID=" + jobID + ", name='" + name + '\'' + '}';
  }
}
