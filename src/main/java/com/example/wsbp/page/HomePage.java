package com.example.wsbp.page;

import com.example.wsbp.service.ISampleService;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@WicketHomePage
@MountPath("Home")
public class HomePage extends WebPage {

  @SpringBean
  private ISampleService service;

  public HomePage() {
    var youModel = Model.of("Wicket-Spring-Boot");
    var youLabel = new Label("you", youModel);
    add(youLabel);

    var gakusekiModel = Model.of("b1970010");
    var gakusekiLabel = new Label("gakuseki", gakusekiModel);
    add(gakusekiLabel);

    var nameModel = Model.of("千歳 光");
    var nameLabel = new Label("name", nameModel);
    add(nameLabel);

    var timeModel = Model.of(service.makeCurrentHMS());
    var timeLabel = new Label("time", timeModel);
    add(timeLabel);
  }

}