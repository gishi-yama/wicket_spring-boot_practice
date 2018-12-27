package com.example.wsbp.page.ex;

import java.io.Serializable;

public class Chat implements Serializable {

  private String userName;
  private String msgBody;

  public Chat() {
    this.userName = "";
    this.msgBody = "";
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getMsgBody() {
    return msgBody;
  }

  public void setMsgBody(String msgBody) {
    this.msgBody = msgBody;
  }

  public void print() {
    System.out.println(userName + "," + msgBody);
  }
}
