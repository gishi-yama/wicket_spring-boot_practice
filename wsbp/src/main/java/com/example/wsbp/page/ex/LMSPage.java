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

//  private IModel<List<Chat>> chatListModel;
//
//  public LMSPage() {
//    this(null);
//  }
//
//  public LMSPage(IModel<List<Chat>> initChatListModel) {
//    if (Objects.nonNull(initChatListModel)) {
//      chatListModel = initChatListModel;
//    } else {
//      // ページ間を受け渡すものは 通常のModel
//      chatListModel = Model.ofList(new ArrayList<>());
//    }
//
//    // ページインスタンスのクラス名を取得する処理を、Modelの戻り値にする
//    var headerModel = LambdaModel.of(getClass()::getSimpleName);
//    add(new Label("header", headerModel));
//
//
//    var chat = new Chat();
//
//    // Chat を Model に渡す
//    var chatModel = Model.of(chat);
//
//    // Form の onSubmit() で getModelObject を使えるようにするために、 chatModel を渡す
//    var newChatForm = new Form<>("newChat", chatModel) {
//      @Override
//      protected void onSubmit() {
//        super.onSubmit();
//        // 送信ボタン押下後のModelの中身
//        // （つまり、フィールドにデータがsetされたchatインスタンス）
//        var updatedChat = getModelObject();
//        updatedChat.print();
//
//        // 次ページに渡すための ChatList を作る
//        var nextChatList = chatListModel.getObject();
//        nextChatList.add(updatedChat);
//
//        // 次ページに渡すModelを準備
//        var nextPageModel = Model.ofList(nextChatList);
//
//        // 次ページにModelを渡して遷移
//        setResponsePage(new CPMSPage(nextPageModel));
//      }
//    };
//    add(newChatForm);
//
//    var userNameModel = LambdaModel.of(chat::getUserName, chat::setUserName);
//    newChatForm.add(new TextField<>("userName", userNameModel));
//
//    var msgBodyModel = LambdaModel.of(chat::getMsgBody, chat::setMsgBody);
//    newChatForm.add(new TextField<>("msgBody", msgBodyModel));
//
//    var msgNumModel = LambdaModel.of(chatListModel.getObject()::size);
//    add(new Label("chatNum", msgNumModel));
//
//    var msgListLV = new ListView<>("chatList", chatListModel) {
//
//      @Override
//      protected void populateItem(ListItem<Chat> listItem) {
//        var itemChat = listItem.getModelObject();
//        // いちいちmodel型の変数にするのがもうめんどい
//        listItem.add(new Label("userName", LambdaModel.of(itemChat::getUserName)));
//        listItem.add(new Label("msgBody", LambdaModel.of(itemChat::getMsgBody)));
//      }
//    };
//    add(msgListLV);
//  }
}
