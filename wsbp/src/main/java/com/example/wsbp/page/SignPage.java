package com.example.wsbp.page;

import com.example.wsbp.MySession;
import com.example.wsbp.page.signed.SignedPage;
import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Objects;

@WicketSignInPage
@MountPath("Sign")
public class SignPage extends WebPage {

  public SignPage() {

    IModel<String> userNameModel = Model.of("");
    IModel<String> userPassModel = Model.of("");

    Form<Void> userInfoForm = new Form<Void>("userInfo") {
      @Override
      protected void onSubmit() {
        String userName = userNameModel.getObject();
        String userPass = userPassModel.getObject();
        if (Objects.equals(userName, "b1970010")
          && Objects.equals(userPass, "qwertyui")) {
          MySession.get().sign(userName);
        }
        setResponsePage(new SignedPage());
      }
    };
    add(userInfoForm);

    TextField<String> userNameField = new TextField<String>("userName", userNameModel) {
      @Override
      protected void onInitialize() {
        super.onInitialize();
        // 文字列の長さを8〜32文字に制限するバリデータ
        add(StringValidator.lengthBetween(8, 32));
      }
    };

    userInfoForm.add(userNameField);

    PasswordTextField userPassField = new PasswordTextField("userPass", userPassModel) {
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
