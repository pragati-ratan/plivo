package com.plivo.contactBook.cache.services;

import com.plivo.contactBook.cache.models.CurrentUser;
import com.plivo.contactBook.cache.repositories.UserCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCache {

    @Autowired
    UserCacheRepository userCacheRepository;

    public void addUserInfoInCache(CurrentUser user) {
        userCacheRepository.save(user);
    }

    public CurrentUser fetchUserInfoFromCache(String username) {
        return userCacheRepository.getByUsername(username);
    }

    public void removeUserInfoFromCache(String username) {
        CurrentUser currentUser = userCacheRepository.getByUsername(username);
        if (currentUser != null)
            userCacheRepository.delete(userCacheRepository.getByUsername(username));
    }
}
