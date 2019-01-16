package com.mmm.movienight.services;

import com.mmm.movienight.models.User;
import com.mmm.movienight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getActiveUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername( username );
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> findAllAndFilterCredentials(){
        return userRepository.findAllAndFilterCredentials();
    }

    public void saveUser( User user ) {
        userRepository.save( user );
    }

}
