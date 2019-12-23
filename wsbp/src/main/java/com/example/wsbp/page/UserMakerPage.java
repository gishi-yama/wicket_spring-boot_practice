package com.example.wsbp.page;

import com.example.wsbp.service.IUserService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MountPath("UserMaker")
public class UserMakerPage extends WebPage {

  @SpringBean
  private IUserService userService;

  public UserMakerPage() {
    var userNameModel = Model.of("");
    var userPassModel = Model.of("");

    var toHomeLink = new BookmarkablePageLink<>("toHome", HomePage.class);
    add(toHomeLink);

    var userInfoForm = new Form<>("userInfo") {
      @Override
      protected void onSubmit() {
        String userName = userNameModel.getObject();
        String userPass = userPassModel.getObject();
        String msg = "送信データ："
          + userName
          + ","
          + userPass;
        System.out.println(msg);

        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(userPass);
        if (m.find()) {
          error("数字だけのパスワードはダメ");
          return;
        }

        userService.registerUser(userName, userPass);
        setResponsePage(new UserMakerCompPage(userNameModel));
      }
    };
    add(userInfoForm);

    var fbMsgPanel = new FeedbackPanel("fbMsg");
    userInfoForm.add(fbMsgPanel);

    var userNameField = new TextField<>("userName", userNameModel) {
      @Override
      protected void onInitialize() {
        super.onInitialize();
        // 文字列の長さを8〜32文字に制限するバリデータ
        add(StringValidator.lengthBetween(8, 32));
      }
    };

    userInfoForm.add(userNameField);

    var userPassField = new PasswordTextField("userPass", userPassModel) {
      @Override
      protected void onInitialize() {
        super.onInitialize();
        // 文字列の長さを8〜32文字に制限するバリデータ
        add(StringValidator.lengthBetween(8, 32));
      }
    };
    userInfoForm.add(userPassField);
  }

}
