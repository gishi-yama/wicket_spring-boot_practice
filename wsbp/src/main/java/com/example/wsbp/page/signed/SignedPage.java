package com.example.wsbp.page.signed;

import com.example.wsbp.MySession;
import com.example.wsbp.data.AuthUser;
import com.example.wsbp.service.IUserService;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AuthorizeInstantiation(Roles.USER)
@MountPath("Signed")
public class SignedPage extends WebPage {

  @SpringBean
  private IUserService userService;

  public SignedPage() {
    var signedUserName = MySession.get().getUserName();
    var name = Model.of(signedUserName);
    var userNameLabel = new Label("userName", name);
    add(userNameLabel);

    var signoutLink = new Link<Void>("signout") {

      @Override
      public void onClick() {
        setResponsePage(new RedirectPage("/logout"));
      }
    };
    add(signoutLink);

    // Azure AD でログインした際に得られるクレーム情報を保持するモデル
    IModel<List<AttributeItem>> azureAttributes = Model.ofList(new ArrayList<>());
    // クレーム情報を表示するコンテナ。未サインインやDBログイン時は非表示にする
    var azureContainer = new WebMarkupContainer("azureContainer") {
      @Override
      protected void onConfigure() {
        super.onConfigure();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        loadAzureAttributes(authentication, azureAttributes);
        setVisible(!azureAttributes.getObject().isEmpty());
      }
    };
    add(azureContainer);

    var azureSourceLabel = new Label("azureSource", () -> {
      var authentication = SecurityContextHolder.getContext().getAuthentication();
      return authentication != null ? authentication.getName() : "";
    });
    azureContainer.add(azureSourceLabel);

    var attributesView = new ListView<AttributeItem>("azureAttributes", azureAttributes) {
      @Override
      protected void populateItem(ListItem<AttributeItem> item) {
        var entry = item.getModelObject();
        item.add(new Label("attributeKey", entry::key));
        item.add(new Label("attributeValue", entry::value));
      }
    };
    azureContainer.add(attributesView);

    // Service からデータベースのユーザ一覧をもらい、Modelにする
    // List型のモデルは Model.ofList(...) で作成する。
    // なお、DBや外部のWEB-APIなどのデータを取得する場合、通常はLoadableDetachableModelを利用する
    // 参考：https://ci.apache.org/projects/wicket/guide/9.x/single.html#_detachable_models
    var authUsersModel = Model.ofList(userService.findAuthUsers());
//    var authUsersModel = LoadableDetachableModel.of(() -> userService.findAuthUsers());

    // List型のモデルを表示する ListView
    var usersLV = new ListView<>("users", authUsersModel) {
      @Override
      protected void populateItem(ListItem<AuthUser> listItem) {
        var itemModel = listItem.getModel();
        var authUser = itemModel.getObject();

        var userNameModel = Model.of(authUser.getUserName());
        var userNameLabel = new Label("userName", userNameModel);
        listItem.add(userNameLabel);

        var userPassModel = Model.of(authUser.getUserPass());
        var userPassLabel = new Label("userPass", userPassModel);
        listItem.add(userPassLabel);
      }
    };
    add(usersLV);
  }

  // Azure AD から返ってきたクレーム情報を ListView 用のモデルに詰め替える補助メソッド
  private void loadAzureAttributes(Authentication authentication,
      IModel<List<AttributeItem>> targetModel) {
    // onConfigure() が呼ばれるたびに最新の内容へ差し替えるため、一度リストを空にする
    targetModel.getObject().clear();
    if (authentication == null) {
      return;
    }
    var principal = authentication.getPrincipal();
    if (principal instanceof OAuth2AuthenticatedPrincipal oauthPrincipal) {
      // OAuth2 認証で得られた属性を画面表示用にシリアライズ可能な形へコピーする
      oauthPrincipal.getAttributes().forEach((key, value) ->
          targetModel.getObject().add(new AttributeItem(key, String.valueOf(value))));
    }
  }

  // Wicket のページはシリアライズされるため、独自のシリアライズ可能な DTO を用意する
  private record AttributeItem(String key, String value) implements Serializable {}

}
