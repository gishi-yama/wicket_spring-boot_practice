package com.example.wsbp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMaker")
public class UserMakerPage extends WebPage {

  public UserMakerPage() {
    IModel<String> userNameModel = Model.of("");
    IModel<String> userPassModel = Model.of("");

    Link<Void> toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);

    Form<Void> userInfoForm = new Form<Void>("userInfo") {
      @Override
      protected void onSubmit() {
        String msg = "送信データ："
          + userNameModel.getObject()
          + ","
          + userPassModel.getObject();
        System.out.println(msg);
      }
    };
    add(userInfoForm);

    TextField<String> userNameField = new TextField<>("userName", userNameModel);
    userInfoForm.add(userNameField);

    PasswordTextField userPassField = new PasswordTextField("userPass", userPassModel);
    userInfoForm.add(userPassField);
  }

}
