package com.example.wsbp.repository;

public interface IAuthUserRepository {

  /**
   * ユーザー名とパスワードをAuthUserテーブルに記録する
   *
   * @param userName ユーザー名
   * @param userPass パスワード
   * @return データベースの更新行数
   */
  public int insert(String userName, String userPass);

  /**
   * ユーザ名とパスワードが一致するレコードがAuthUserテーブルにあるか検索する
   *
   * @param userName ユーザー名
   * @param userPass パスワード
   * @return レコードの有無, 存在すれば<code>true</code>, それ以外は <code>false</code>
   */
  public boolean exists(String userName, String userPass);

}
