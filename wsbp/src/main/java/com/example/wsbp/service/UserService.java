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

}
