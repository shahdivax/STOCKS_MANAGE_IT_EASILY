package com.divax.stocks;

public class user {
  String username, mail, password, userId;

  public user(String username, String mail, String password, String userId) {
    this.username = username;
    this.mail = mail;
    this.password = password;
    this.userId = userId;
  }

  public user() {}

  public user(String username, String mail) {
    this.username = username;
    this.mail = mail;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
