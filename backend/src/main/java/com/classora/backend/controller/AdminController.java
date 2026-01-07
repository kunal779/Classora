package com.classora.backend.controller;

import com.classora.backend.entity.User;
import com.classora.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class    AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createAdmin(@RequestParam String email,
                            @RequestParam String password) {
        return userService.createAdmin(email, password);
    }
}
