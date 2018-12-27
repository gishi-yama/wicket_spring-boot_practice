package com.example.wsbp.page.signed;

import com.example.wsbp.MySession;
import com.example.wsbp.data.AuthUser;
import com.example.wsbp.page.SignPage;
import com.example.wsbp.service.IUserService;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

@AuthorizeInstantiation(Roles.USER)
@MountPath("Signed")
public class SignedPage extends WebPage {

  @SpringBean
  private IUserService userService;

  public SignedPage() {
    String signedUserName = MySession.get().getUserName();
    IModel<String> name = Model.of(signedUserName);
    Label userNameLabel = new Label("userName", name);
    add(userNameLabel);

    Link<Void> signoutLink = new Link<Void>("signout") {

      @Override
      public void onClick() {
        // セッションの破棄
        MySession.get().invalidate();
        // SignPageへ移動
        setResponsePage(SignPage.class);
      }
    };
    add(signoutLink);

    IModel<List<AuthUser>> authUsersModel = Model.ofList(userService.findAuthUsers());

    ListView<AuthUser> usersLV = new ListView<AuthUser>("users", authUsersModel) {
      @Override
      protected void populateItem(ListItem<AuthUser> listItem) {
        IModel<AuthUser> itemModel = listItem.getModel();
        AuthUser authUser = itemModel.getObject();

        IModel<String> userNameModel = Model.of(authUser.getUserName());
        Label userNameLabel = new Label("userName", userNameModel);
        listItem.add(userNameLabel);

        IModel<String> userPassModel = Model.of(authUser.getUserPass());
        Label userPassLabel = new Label("userPass", userPassModel);
        listItem.add(userPassLabel);
      }
    };
    add(usersLV);

  }
}
