package com.example.wsbp.page;

import com.example.wsbp.MySession;
import com.example.wsbp.page.signed.SignedPage;
import com.example.wsbp.service.IUserService;
import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;

@WicketSignInPage
@MountPath("Sign")
public class SignPage extends WebPage {

  @SpringBean
  private IUserService service;

  public SignPage() {
    var userNameModel = Model.of("");
    var userPassModel = Model.of("");

    Form<Void> userInfoForm = new Form<>("userInfo") {
      @Override
      protected void onSubmit() {
        var userName = userNameModel.getObject();
        var userPass = userPassModel.getObject();
        // b1992490...の定数で照合していたものを、DB経由に変更
        if (service.existsUser(userName, userPass)) {
          MySession.get().sign(userName);
        }
        setResponsePage(new SignedPage());
      }
    };
    add(userInfoForm);

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
