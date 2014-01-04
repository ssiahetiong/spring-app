package tutorial.spring.awesome.admin.service;

import tutorial.spring.awesome.admin.dto.UserInfo;
import tutorial.spring.awesome.admin.exception.UserNotFoundException;

public interface UserService {

    void saveUser(UserInfo userInfo);

    UserInfo getUser(String username) throws UserNotFoundException;

}
