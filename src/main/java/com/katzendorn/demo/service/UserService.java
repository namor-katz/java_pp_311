package com.katzendorn.demo.service;

import com.katzendorn.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;
    @Autowired

}
