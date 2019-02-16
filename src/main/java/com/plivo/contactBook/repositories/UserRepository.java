package com.plivo.contactBook.repositories;

import com.plivo.contactBook.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {
    User getByEmailId(String emailId);
}
