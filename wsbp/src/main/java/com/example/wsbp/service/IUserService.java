package com.example.wsbp.service;

public interface IUserService {

  /**
   * ユーザー名とパスワードをデータベースに記録する
   *
   * @param userName ユーザー名
   * @param userPass パスワード
   */
  public void registerUser(String userName, String userPass);

  /**
   * ユーザ名とパスワードをデータベースに照合する
   *
   * @param userName ユーザー名
   * @param userPass パスワード
   * @return 照合成功であれば<code>true</code>, 照合失敗は<code>false</code>
   */
  public boolean existsUser(String userName, String userPass);

}
