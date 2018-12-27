package com.example.wsbp.data;

import java.io.Serializable;

// AUTH_USER テーブルのデータを入れるクラス
// Wicketの Model に使うかもしれないクラスは、 implements Serializable をつける
public class AuthUser implements Serializable {

  private String userName;  // auth_userテーブルのuser_name列のデータ
  private String userPass;  // auth_userテーブルのuser_pass列のデータ

  public AuthUser() {
    userName = "";
    userPass = "";
  }

  public String getUserName() {
    return userName;
  }

  public String getUserPass() {
    return userPass;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }
}
