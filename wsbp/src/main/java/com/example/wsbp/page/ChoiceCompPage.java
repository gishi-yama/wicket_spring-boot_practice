package com.example.wsbp.page;

import com.example.wsbp.data.AuthUser;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("ChoiceComp")
public class ChoiceCompPage extends WebPage {
  public ChoiceCompPage(IModel<AuthUser> model) {

    var choicedAuthUser = model.getObject();

    var userName = new Label("userName", Model.of(choicedAuthUser.getUserName()));
    add(userName);
    var userPass = new Label("userPass", Model.of(choicedAuthUser.getUserPass()));
    add(userPass);

  }
}
