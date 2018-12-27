package com.example.wsbp.repository;

import com.example.wsbp.data.AuthUser;

import java.util.List;

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


  /**
   * AuthUserテーブルのすべての情報を検索する
   *
   * @return レコードの内容を {@link AuthUser} の {@link List} で返す
   */
  public List<AuthUser> find();

}
