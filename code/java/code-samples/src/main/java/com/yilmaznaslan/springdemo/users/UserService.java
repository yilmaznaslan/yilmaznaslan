package com.yilmaznaslan.springdemo.users;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    public List<UserEntity> getAllUsersWithPagination(int page, int size) {
        final var pagination = PageRequest.of(page, size);
        return userRepository.findAll(pagination).toList();
    }

}
