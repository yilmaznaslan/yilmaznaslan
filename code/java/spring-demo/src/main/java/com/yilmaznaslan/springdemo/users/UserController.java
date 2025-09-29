package com.yilmaznaslan.springdemo.users;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserEntity createUser(UserEntity userEntity){
        return userService.save(userEntity);
    }

    @GetMapping("/{userId}")
    public UserEntity getUserById(@PathVariable Long userId){
        return userService.findById(userId);
    }


    @GetMapping
    public List<UserEntity> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return userService.getAllUsersWithPagination(page, size);
    }


}
