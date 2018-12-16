package com.example.wsbp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMaker")
public class UserMakerPage extends WebPage {

  public UserMakerPage() {
    Link<Void> toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);

    Form<Void> userInfoForm = new Form<>("userInfo");
    add(userInfoForm);

    TextField<String> userNameField = new TextField<>("userName");
    userInfoForm.add(userNameField);

    PasswordTextField userPassField = new PasswordTextField("userPass");
    userInfoForm.add(userPassField);
  }

}
