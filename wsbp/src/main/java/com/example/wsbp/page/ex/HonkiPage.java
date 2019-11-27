package com.example.wsbp.page.ex;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MountPath("honki")
public class HonkiPage extends ModelVariationPage {

  private IModel<List<Chat>> chatListModel;

  public HonkiPage() {
    this(null);
  }

  public HonkiPage(IModel<List<Chat>> initChatListModel) {
    chatListModel = Objects.nonNull(initChatListModel)
      ? initChatListModel
      : Model.ofList(new ArrayList<>());

    add(new Label("header", LambdaModel.of(getClass()::getSimpleName)));

    Form<Chat> newChatForm = new Form<Chat>("newChat", CompoundPropertyModel.of(new Chat())) {
      @Override
      protected void onSubmit() {
        super.onSubmit();
        getModelObject().print();
        chatListModel.getObject().add(getModelObject());
        setResponsePage(new HonkiPage(Model.ofList(chatListModel.getObject())));
      }
    };
    add(newChatForm);

    newChatForm.add(new TextField<>("userName"));
    newChatForm.add(new TextField<>("msgBody"));

    add(new Label("chatNum", LambdaModel.of(chatListModel.getObject()::size)));

    // CompoundPropertyModelが自動的に準備されるListView
    ListView<Chat> msgListLV = new PropertyListView<Chat>("chatList", chatListModel) {

      @Override
      protected void populateItem(ListItem<Chat> listItem) {
        listItem.add(new Label("userName"));
        listItem.add(new Label("msgBody"));
      }
    };
    add(msgListLV);
  }
}
