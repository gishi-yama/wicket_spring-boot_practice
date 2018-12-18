package com.example.wsbp.page;

import com.example.wsbp.service.IUserService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMaker")
public class UserMakerPage extends WebPage {

  @SpringBean
  private IUserService userService;

  public UserMakerPage() {
    IModel<String> userNameModel = Model.of("");
    IModel<String> userPassModel = Model.of("");

    Link<Void> toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);

    Form<Void> userInfoForm = new Form<Void>("userInfo") {
      @Override
      protected void onSubmit() {
        String userName = userNameModel.getObject();
        String userPass = userPassModel.getObject();
        String msg = "送信データ："
          + userName
          + ","
          + userPass;
        System.out.println(msg);
        userService.registerUser(userName, userPass);
        setResponsePage(new UserMakerCompPage(userNameModel));
      }
    };
    add(userInfoForm);

    TextField<String> userNameField = new TextField<>("userName", userNameModel);
    userInfoForm.add(userNameField);

    PasswordTextField userPassField = new PasswordTextField("userPass", userPassModel);
    userInfoForm.add(userPassField);
  }

}
