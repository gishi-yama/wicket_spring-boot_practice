package com.example.wsbp.service;

public interface IUserService {

  /**
   * ユーザー名とパスワードをデータベースに記録する
   *
   * @param userName ユーザー名
   * @param userPass パスワード
   */
  public void registerUser(String userName, String userPass);

}
