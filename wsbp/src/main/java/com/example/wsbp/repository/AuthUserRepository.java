package com.example.wsbp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

}
