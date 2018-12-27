package com.example.wsbp;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ↓ スーパークラスを WicketBootSecuredWebApplication に変更する
@SpringBootApplication
public class WSBPApplication extends WicketBootSecuredWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WSBPApplication.class, args);
  }

  // 認証OK/NGを判定するセッションクラスを返値にする
  @Override
  protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
    return MySession.class;
  }
}
