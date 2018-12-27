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
  private Chat chat;

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

    IModel<String> headerModel = LambdaModel.of(() -> "LambdaModel(1)");
    add(new Label("header", headerModel));

    chat = new Chat();
    Form<Void> newChatForm = new Form<Void>("newChat") {
      @Override
      protected void onSubmit() {
        super.onSubmit();
        chat.print();
        chatListModel.getObject().add(chat);
        setResponsePage(new LMSPage(chatListModel));
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
