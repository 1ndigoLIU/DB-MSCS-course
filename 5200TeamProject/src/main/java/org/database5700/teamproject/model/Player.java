package org.database5700.teamproject.model;

public class Player {

  protected int accountId;
  protected String name;
  protected String email;
  protected String status;

  public Player(int accountId, String name, String email, String status) {
    this.accountId = accountId;
    this.name = name;
    this.email = email;
    this.status = status;
  }

  public Player(String name, String email, String status) {
    this.name = name;
    this.email = email;
    this.status = status;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Player{" + "accountId=" + accountId + ", name='" + name + '\'' + ", email='" + email
        + '\'' + ", status=" + status + '}';
  }
}
