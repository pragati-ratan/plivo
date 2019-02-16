package com.plivo.contactBook.cache.repositories;

import com.plivo.contactBook.cache.models.CurrentUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCacheRepository extends CrudRepository<CurrentUser, String> {
    CurrentUser getByUsername(String username);
}
