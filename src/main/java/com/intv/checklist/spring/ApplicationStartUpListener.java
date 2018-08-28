package com.intv.checklist.spring;

import com.intv.checklist.cache.ApplicationCache;
import com.intv.checklist.domain.User;
import com.intv.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUpListener
        implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationCache applicationCache;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        createTestUsers();
        applicationCache.loadUsers();
    }

    private void createTestUsers() {
        for (int i = 1; i < 5; i++) {
            User user = new User();
            String userId = "user" + i;
            user.setUserId(userId);
            user.setEmail(userId + "@abc.com");
            user.setActive(Boolean.TRUE);
            user.setName(userId);
            userRepository.save(user);
        }
    }
}