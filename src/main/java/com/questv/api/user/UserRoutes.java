package com.questv.api.user;


import com.questv.api.config.EndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = EndPoint.USER_ROUTE)
public class UserRoutes {

    @Autowired
    UserService userService;

    @PostMapping({ "/" })
    public void postUser(@Valid @RequestBody UserModel userModel) {
        this.userService.create(userModel);
    }

}
