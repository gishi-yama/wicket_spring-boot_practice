package com.example.wsbp.service;

import com.example.wsbp.repository.IAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private IAuthUserRepository authUserRepos;

  @Autowired
  public UserService(IAuthUserRepository authUserRepos) {
    this.authUserRepos = authUserRepos;
  }

  @Override
  public void registerUser(String userName, String userPass) {
    int n = authUserRepos.insert(userName, userPass);
    System.out.println("記録行数：" + n);
  }

  @Override
  public boolean existsUser(String userName, String userPass) {
    boolean result = authUserRepos.exists(userName, userPass);
    System.out.println(userName + ", " + userPass + " のユーザ照合結果：" + result);
    return result;
  }
}
