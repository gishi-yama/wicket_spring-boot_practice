package com.example.wsbp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMakerComp")
public class UserMakerCompPage extends WebPage {

  public UserMakerCompPage(IModel<String> userNameModel) {
    Label userNameLabel = new Label("userName", userNameModel);
    add(userNameLabel);

    Link<Void> toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);
  }
}
