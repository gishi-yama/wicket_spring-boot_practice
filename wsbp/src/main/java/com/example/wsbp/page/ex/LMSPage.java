package com.example.wsbp.page.ex;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MountPath("lms")
public class LMSPage extends ModelVariationPage {

  private IModel<List<Chat>> chatListModel;

  public LMSPage() {
    this(null);
  }

  public LMSPage(IModel<List<Chat>> initChatListModel) {
    if (Objects.nonNull(initChatListModel)) {
      chatListModel = initChatListModel;
    } else {
      // ページ間を受け渡すものは 通常のModel
      chatListModel = Model.ofList(new ArrayList<>());
    }

    // ページインスタンスのクラス名を取得する処理を、Modelの戻り値にする
    IModel<String> headerModel = LambdaModel.of(getClass()::getSimpleName);
    add(new Label("header", headerModel));


    Chat chat = new Chat();

    // Chat を Model に渡す
    IModel<Chat> chatModel = Model.of(chat);

    // Form の onSubmit() で getModelObject を使えるようにするために、 chatModel を渡す
    Form<Chat> newChatForm = new Form<Chat>("newChat", chatModel) {
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

    IModel<String> userNameModel = LambdaModel.of(chat::getUserName, chat::setUserName);
    newChatForm.add(new TextField<>("userName", userNameModel));

    IModel<String> msgBodyModel = LambdaModel.of(chat::getMsgBody, chat::setMsgBody);
    newChatForm.add(new TextField<>("msgBody", msgBodyModel));

    IModel<Integer> msgNumModel = LambdaModel.of(chatListModel.getObject()::size);
    add(new Label("chatNum", msgNumModel));

    ListView<Chat> msgListLV = new ListView<Chat>("chatList", chatListModel) {

      @Override
      protected void populateItem(ListItem<Chat> listItem) {
        Chat itemChat = listItem.getModelObject();
        // いちいちmodel型の変数にするのがもうめんどい
        listItem.add(new Label("userName", LambdaModel.of(itemChat::getUserName)));
        listItem.add(new Label("msgBody", LambdaModel.of(itemChat::getMsgBody)));
      }
    };
    add(msgListLV);
  }
}
