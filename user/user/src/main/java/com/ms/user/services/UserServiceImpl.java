package com.ms.user.services;

import com.ms.user.Producers.UserProducer;
import com.ms.user.models.UserModel;
import com.ms.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserServiceImpl(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Override
    @Transactional
    public UserModel save(UserModel userModel) {
        try {
            userRepository.save(userModel);
            userProducer.publishMessageEmail(userModel);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }finally {
            return userModel;
        }
    }
}
