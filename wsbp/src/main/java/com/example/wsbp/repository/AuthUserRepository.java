package com.example.wsbp.repository;

import com.example.wsbp.data.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
      new SingleColumnRowMapper<>(Boolean.class),
      new Object[]{userName, userPass});

    // Listにデータがある(＝trueの要素ものがある)：照合成功
    // Listにデータがない(要素が何もない)：照合失敗
    return !booleans.isEmpty();
  }

  @Override
  public List<AuthUser> find() {
    // auth_user テーブルの user_name, user_pass を検索する
    String sql = "select user_name, user_pass from auth_user";

    // 検索用のSQLを実行する方法。
    // 取り出したいデータの型によって、第2引数の RowMapper を切り替える。
    // ? を使うSQLであれば、第3引数の Object型配列 の要素に順番に設定する。
    List<AuthUser> users = jdbc.query(sql,
      new BeanPropertyRowMapper<>(AuthUser.class),
      new Object[]{});

    // 取り出したデータ（Listの要素）をそのまま返値とする。
    return users;
  }

}
