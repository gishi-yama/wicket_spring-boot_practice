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
  private IModel<Chat> chatModel;

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

    IModel<String> headerModel = LambdaModel.of(() -> "LambdaModel(1)");
    add(new Label("header", headerModel));

    chatModel = CompoundPropertyModel.of(new Chat());
    Form<Chat> newChatForm = new Form<Chat>("newChat", chatModel) {
      @Override
      protected void onSubmit() {
        super.onSubmit();
        getModelObject().print();
        chatListModel.getObject().add(getModelObject());
        setResponsePage(new CPMSPage(chatListModel));
      }
    };
    add(newChatForm);

    newChatForm.add(new TextField<>("userName"));
    newChatForm.add(new TextField<>("msgBody"));

    // メソッドを毎回使うようなものは LambdaModelのままがよい
    IModel<Integer> msgNumModel = LambdaModel.of(chatListModel.getObject()::size);
    add(new Label("chatNum", msgNumModel));

    ListView<Chat> msgListLV = new ListView<Chat>("chatList", chatListModel) {

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
