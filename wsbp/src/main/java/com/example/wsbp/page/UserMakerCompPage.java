package com.example.wsbp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMakerComp")
public class UserMakerCompPage extends WebPage {

  public UserMakerCompPage(IModel<String> userNameModel) {
    var userNameLabel = new Label("userName", userNameModel);
    add(userNameLabel);

    var toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);
  }
}
