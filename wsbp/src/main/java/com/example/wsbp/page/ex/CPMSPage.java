package com.example.wsbp.page.ex;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MountPath("cpms")
public class CPMSPage extends ModelVariationPage {

  private IModel<List<Chat>> chatListModel;

  public CPMSPage() {
    this(null);
  }

  public CPMSPage(IModel<List<Chat>> initChatListModel) {
    if (Objects.nonNull(initChatListModel)) {
      chatListModel = initChatListModel;
    } else {
      // ページ間を受け渡すものは 通常のModel
      chatListModel = Model.ofList(new ArrayList<>());
    }

    // ページインスタンスのクラス名を取得する処理を、Modelの戻り値にする
    var headerModel = LambdaModel.of(getClass()::getSimpleName);
    add(new Label("header", headerModel));

    var chat = new Chat();

    // Chat を CompoundPropertyModel に渡す
    var chatModel = CompoundPropertyModel.of(chat);


    // Form の onSubmit() で getModelObject を使えるようにするために、 chatModel を渡す
    // また、CompoundPropertyModelの能力で、Formの子どものModelは自動的に設定される
    var newChatForm = new Form<>("newChat", chatModel) {
      @Override
      protected void onSubmit() {
        super.onSubmit();
        // 送信ボタン押下後のModelの中身
        // （つまり、フィールドにデータがsetされたchatインスタンス）
        Chat updatedChat = getModelObject();
        updatedChat.print();

        // 次ページに渡すための ChatList を作る
        List<Chat> nextChatList = chatListModel.getObject();
        nextChatList.add(updatedChat);

        // 次ページに渡すModelを準備
        IModel<List<Chat>> nextPageModel = Model.ofList(nextChatList);

        // 次ページにModelを渡して遷移
        setResponsePage(new CPMSPage(nextPageModel));
      }
    };
    add(newChatForm);

    // Form に CompoundPropertyModel が渡されているので、
    // 子となる userName, msgBody は、上のChatインスタンスのフィールド変数の中で
    // 同じ名前のものを自動的にデータの set/get 先にする
    newChatForm.add(new TextField<>("userName"));
    newChatForm.add(new TextField<>("msgBody"));

    // メソッドを毎回使うようなものは LambdaModelのままがよい
    var msgNumModel = LambdaModel.of(chatListModel.getObject()::size);
    add(new Label("chatNum", msgNumModel));

    var msgListLV = new ListView<>("chatList", chatListModel) {

      @Override
      protected void populateItem(ListItem<Chat> listItem) {
        // ListItemのModelをCompoundPropertyModelに切り替え;
        IModel<Chat> listItemModel = listItem.getModel();
        listItem.setModel(CompoundPropertyModel.of(listItemModel));
        listItem.add(new Label("userName"));
        listItem.add(new Label("msgBody"));
      }
    };
    add(msgListLV);
  }
}
