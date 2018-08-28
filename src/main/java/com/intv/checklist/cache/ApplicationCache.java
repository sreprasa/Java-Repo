package com.intv.checklist.cache;

import com.intv.checklist.domain.User;
import com.intv.checklist.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationCache {

    @Autowired
    private UserRepository userRepository;

    private Logger LOGGER = LoggerFactory.getLogger(ApplicationCache.class);

    private List<User> users = new ArrayList<>();

    public void loadUsers(){
        users= userRepository.findAll();
        LOGGER.info("Users cache {}",users.size());
    }

    public List<User> getUsers(){
        //This should return immutable copy
        return  users;
    }

}
