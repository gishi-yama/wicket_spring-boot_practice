package com.example.wsbp;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

// ブラウザ事に異なる情報を一時保存できるセッション
public class MySession extends AbstractAuthenticatedWebSession {

  // 認証の際に照合したユーザー名
  private UserName userName;

  // 認証状態が未確定かどうかを判定する Specification（Predicate の合成で表現）
  private static final Predicate<Authentication> 認証情報なし = Objects::isNull;
  private static final Predicate<Authentication> 認証未完了 =
      認証情報なし.negate().and(authentication -> !authentication.isAuthenticated());
  private static final Predicate<Authentication> 匿名認証 = AnonymousAuthenticationToken.class::isInstance;

  public MySession(Request request) {
    // コンストラクタ。初期状態は認証NG（userName = null）
    super(request);
    this.userName = UserName.異常;
  }

  public void sign(UserName userName) {
    // 認証したユーザを変更する。
    // セキュリティ上の配慮から、セッションを切り替える（古いセッションを使い回さない）
    replaceSession();
    if (!userName.is正常()) {
      // 異常なユーザー名を持つセッションは作成しない
      throw new IllegalArgumentException("異常なユーザー名ではサインインできません。");
    }
    this.userName = userName;
    var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    var authentication =
      new UsernamePasswordAuthenticationToken(userName.asNonNull(), null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    // Spring Security と Wicket のセッションを同期させる（DB ログインでも Azure ログインと同じ扱いにする）
    // Optional を使って HttpSession を安全に取り出し、存在する場合のみ同期させる
    Optional.ofNullable(RequestCycle.get())
      .map(RequestCycle::getRequest)
      .filter(ServletWebRequest.class::isInstance)
      .map(ServletWebRequest.class::cast)
      .map(servletWebRequest -> servletWebRequest.getContainerRequest().getSession(true))
      .ifPresent(httpSession -> httpSession.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        SecurityContextHolder.getContext()));
  }

  @Override
  public Roles getRoles() {
    // 認証結果OK(userName = ユーザー名)だった場合は、誰もが "USER" 権限を持っているとして返す
    // 認証結果NG(userName = null）だった場合は、権限なしとして返す
    // wicket-auth-roles　では、この権限の有無と種類でページを表示してよいか判断してくれる
    return isSignedIn() ? new Roles(Roles.USER) : new Roles();
  }

  @Override
  public boolean isSignedIn() {
    // 認証結果OK(userName = ユーザー名)だった場合は、trueを返す
    // 認証結果NG(userName = null）だった場合は、falseを返す
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (認証情報なし.or(認証未完了).or(匿名認証).test(authentication)) {
      // Spring Security にまだ認証情報が乗っていない場合は、従来通りセッションの値を参照する
      // （DB ログインした直後などを想定）
      return userName.is正常();
    }
    updateUserNameFromPrincipal(authentication);
    return true;
  }

  public UserName getUserName() {
    // ユーザー名 を返す
    return this.userName;
  }

  public static MySession get() {
    // サーバーの中から Session を取り出す
    return (MySession) Session.get();
  }

  // OAuth2 のクレーム値は Object 型なので、null 安全に文字列へ変換して返す
  private String attributeToString(OAuth2AuthenticatedPrincipal principal, String key) {
    var value = principal.getAttribute(key);
    return Objects.toString(value, null);
  }

  // 認証済みの Principal から Azure AD のクレーム優先でユーザー名を取り出す
  private void updateUserNameFromPrincipal(Authentication authentication) {
    var principal = authentication.getPrincipal();
    if (principal instanceof OAuth2AuthenticatedPrincipal oauthPrincipal) {
      // Azure AD のクレームがあれば優先的にユーザー名として採用する
      var preferredUserName = attributeToString(oauthPrincipal, "preferred_username");
      this.userName = UserName.of(Objects.requireNonNullElse(preferredUserName,
          authentication.getName()));
      return;
    }
    this.userName = UserName.of(authentication.getName());
  }

}
