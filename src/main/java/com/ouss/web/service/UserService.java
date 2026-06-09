package com.ouss.web.service;

import com.ouss.web.doa.UserDOA;
import com.ouss.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDOA userDOA;

    public User getUserFromId(String id){
        return userDOA.getUserEmailFromId(id);
    }
}
