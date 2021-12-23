package com.example.wsbp.page.ex;

import java.io.Serializable;

public class Chat implements Serializable {

  private final String userName;
  private final String msgBody;

  public Chat(String userName, String msgBody) {
    this.userName = userName;
    this.msgBody = msgBody;
  }

  public String getUserName() {
    return userName;
  }

  public String getMsgBody() {
    return msgBody;
  }

  public void print() {
    System.out.println(userName + "," + msgBody);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Chat chat = (Chat) o;

    if (!userName.equals(chat.userName)) return false;
    return msgBody.equals(chat.msgBody);
  }

  @Override
  public int hashCode() {
    int result = userName.hashCode();
    result = 31 * result + msgBody.hashCode();
    return result;
  }
}
