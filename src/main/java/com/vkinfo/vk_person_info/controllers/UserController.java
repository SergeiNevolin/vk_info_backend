package com.vkinfo.vk_person_info.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.vkinfo.vk_person_info.model.request.UserInfoRequest;
import com.vkinfo.vk_person_info.model.response.UserResponse;
import com.vkinfo.vk_person_info.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/vk")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Информация о пользователе
    @PostMapping(value = "/info", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponse userInfo(@Valid @RequestHeader("vk_service_token") String vkServiceToken, @Valid @RequestBody UserInfoRequest request) {
        return userService.userInfo(vkServiceToken, request);
    }
}
