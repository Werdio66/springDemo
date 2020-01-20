package com._520.spring.test;

import com._520.spring.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserController {

    @Autovierd
    private UserService userService;

    private User user;

}
