package com.example.wsbp.page;

import com.example.wsbp.service.ISampleService;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@WicketHomePage
@MountPath("Home")
public class HomePage extends WebPage {

  @SpringBean
  private ISampleService service;

  public HomePage() {
    IModel<String> youModel = Model.of("Wicket-Spring-Boot");
    Label youLabel = new Label("you", youModel);
    add(youLabel);

    IModel<String> gakusekiModel = Model.of("b1970010");
    Label gakusekiLabel = new Label("gakuseki", gakusekiModel);
    add(gakusekiLabel);

    IModel<String> nameModel = Model.of("千歳 光");
    Label nameLabel = new Label("name", nameModel);
    add(nameLabel);

    IModel<String> timeModel = Model.of(service.makeCurrentHMS());
    Label timeLabel = new Label("time", timeModel);
    add(timeLabel);

    IModel<Integer> randModel = Model.of(service.makeRandInt());
    Label randLabel = new Label("rand", randModel);
    add(randLabel);

    Link<Void> toUserMakerLink = new BookmarkablePageLink<>("toUserMaker", UserMakerPage2.class);
    add(toUserMakerLink);
  }

}