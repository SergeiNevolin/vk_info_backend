package com.vkinfo.vk_person_info.service;

import com.vkinfo.vk_person_info.model.request.UserInfoRequest;
import com.vkinfo.vk_person_info.model.response.UserResponse;

public interface UserService {

    UserResponse userInfo(String vkServiceToken, UserInfoRequest request);
}

