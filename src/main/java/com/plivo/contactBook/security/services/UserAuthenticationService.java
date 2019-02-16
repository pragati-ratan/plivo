package com.plivo.contactBook.security.services;

import com.plivo.contactBook.models.User;
import com.plivo.contactBook.repositories.UserRepository;
import com.plivo.contactBook.security.models.CurrentUserContext;
import com.plivo.contactBook.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public CurrentUserContext getCurrentLoggedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) {
            return (CurrentUserContext) context.getAuthentication().getPrincipal();
        }
        return null;
    }

    @Override
    public CurrentUserContext loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByEmailId(username);

        if (user != null) {
            return CurrentUserContext.createUserContext(user.getEmailId(), user.getPassword(), Constants.Roles.USER, user.getId());
        } else {
            throw new UsernameNotFoundException(Constants.ErrorMessage.INVALID_CREDENTIALS);
        }
    }
}
