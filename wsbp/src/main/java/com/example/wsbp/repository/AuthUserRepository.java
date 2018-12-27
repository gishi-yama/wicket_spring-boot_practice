package com.example.wsbp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthUserRepository implements IAuthUserRepository {

  // SpringJDBCのデータベース制御用インスタンス
  private final JdbcTemplate jdbc;

  // jdbc の di/ioc 設定（Wicketとやり方が異なるので注意）
  @Autowired
  public AuthUserRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public int insert(String userName, String userPass) {
    String sql = "insert into auth_user values (?, ?)";
    int n = jdbc.update(sql, userName, userPass);
    return n;
  }

  @Override
  public boolean exists(String userName, String userPass) {
    // ユーザ名とパスワードが一致する情報が auth_user テーブルにあれば、true を返す
    // テーブルになければ、何も返さない
    String sql = "select true from auth_user "
      + "where user_name = ? and user_pass = ?";

    // 検索用のSQLを実行する方法。検索結果をList（可変長配列）で返す。
    // データの追加時と若干異なるので注意。
    List<Boolean> booleans = jdbc.query(sql,
      new SingleColumnRowMapper(Boolean.class),
      new Object[]{userName, userPass});

    // Listにデータがある(＝trueの要素ものがある)：照合成功
    // Listにデータがない(要素が何もない)：照合失敗
    return !booleans.isEmpty();
  }


}
