package com.example.wsbp.page;

import com.example.wsbp.data.AuthUser;
import com.example.wsbp.service.IUserService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Objects;

@MountPath("PullDown")
public class PullDownPage extends WebPage {

  @SpringBean
  private IUserService userService;

  public PullDownPage() {

    // プルダウンメニューに表示するためのリストのModelを作成
    // ここでは、LoadableDetachableModelを利用している。
    // このModelは、Pageの表示準備の開始から終了の間まで、Modelのデータをキャッシュし、表示準備の終了時にデータを破棄する。
    // DBから取得するデータは、大量になる場合がある。
    // そのような場合、LoadableDetachableModelを利用することで、データの取得とキャッシュ（使い回し）、メモリ節約の管理を自動化できる。
    var selectionModel = LoadableDetachableModel.of(() -> userService.findAuthUsers());
    // プルダウンメニューから選択したものを入れるためのModelを作成
    var selectedModel = new Model<AuthUser>();

    var form = new Form<>("form") {
      @Override
      protected void onSubmit() {
        // 次ページに渡すModelを用意する。
        // Wicketではさまざまな機能をもったModelを利用できるが、この機能が別ページで想定していない動きをすると動作不良の要因になる場合がある。
        // そのため、次ページにデータを渡す時には新しいModelに設定し直すことを心がけると、バグの防止になる。
        var sendingModel = new Model<>(selectedModel.getObject());
        if (Objects.isNull(sendingModel.getObject())) {
          // 選択肢が選ばれていない場合の処理
          // nullをそのまま渡すと、次ページでNullPointerExceptionが発生する要因になるので、ダミーのデータを渡す。
          var dummyData = new AuthUser("ユーザー名未設定", "パスワード未設定");
          sendingModel.setObject(dummyData);
        }
        // 次ページに渡すModelを使って、次ページ（ChoiceResultPage）を作成し移動する。
        setResponsePage(new ChoiceCompPage(sendingModel));
      }
    };
    add(form);

    var feedback = new FeedbackPanel("feedback");
    form.add(feedback);

    // 第1引数の文字列は、AuthUserのuserNameを選択肢の表示用として取り出すことを設定
    var renderer = new ChoiceRenderer<>("userName");

    // プルダウンメニューを作成するためのDropDownChoiceコンポーネント
    // 第1引数は wicket:id, 第2引数は選択したものを入れるためのModel, 第3引数は表示するためのリストのModel, 第4引数は選択肢の表示用の設定。
    // なお、こちらは最初から何かが選ばれている状態のDropDownChoice。
//    var userSelection = new DropDownChoice<>("userSelection", selectedModel, selectionModel, renderer){
//      @Override
//      protected void onInitialize() {
//        // このDropDownChoiceの初期化用の処理
//        super.onInitialize();
//        // selectionModelの最初の一つを取り出す（なければ null）
//        var currentChoice = getChoices().stream()
//          .findFirst().orElse(null);
//        // 取り出した最初の一つを selectedModel に入れておく
//        setModelObject(currentChoice);
//      }
//    };

    // こちらは、必ず「選択してください」が選択できるようになっている状態のDropDownChoice。
    var userSelection = new DropDownChoice<>("userSelection", selectedModel, selectionModel, renderer) {
      @Override
      protected void onInitialize() {
        // このDropDownChoiceの初期化用の処理
        super.onInitialize();
        // 必ず空欄の選択肢を用意するように設定
        setNullValid(true);
        // 空欄の選択肢の送信を許可しないバリデーション
//        setRequired(true);
        // エラーメッセージに表示する名前を設定
//        setLabel(Model.of("学籍番号の選択肢"));
      }

      @Override
      protected String getNullValidDisplayValue() {
        // 何も選択されていない状態の表示用の文字列を設定
        // .propertiesファイルに nullValid の値を設定できるようになっているが、面倒なのでデフォルトをとってくる
        return getNullKeyDisplayValue();
      }
    };

    form.add(userSelection);

  }
}
