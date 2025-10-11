package com.example.wsbp;

import java.util.Objects;

/**
 * ユーザー名の表現を一元化する小さな値オブジェクト。
 * null が入ってしまう異常系も型で表せるようにする。
 */
public record UserName(String value) {

  // 異常（未設定）状態を表す定数。明示的に扱えるようにする。
  public static final UserName 異常 = new UserName(null);

  /**
   * ユーザー名文字列から値オブジェクトを生成するファクトリ。
   * null の場合は異常状態として返す。
   */
  public static UserName of(String rawUserName) {
    return rawUserName == null ? 異常 : new UserName(rawUserName);
  }

  /**
   * 正常にユーザー名が設定されているかを判定する。
   */
  public boolean is正常() {
    return value != null;
  }

  /**
   * ユーザー名文字列を必須で取り出す。未設定の場合は例外にする。
   */
  public String asNonNull() {
    return Objects.requireNonNull(value, "ユーザー名が未設定のまま利用されました。");
  }

  /**
   * 表示用にユーザー名を文字列で返す。未設定ならエラーメッセージを返す。
   */
  public String toDisplayString() {
    // ページ側で is正常()/asNonNull() を組み合わせなくても済むようにする
    return is正常() ? value : "ユーザー名が未設定のまま利用されました。";
  }
}
