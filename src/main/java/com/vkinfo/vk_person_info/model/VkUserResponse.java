package com.vkinfo.vk_person_info.model;

import java.util.List;

import lombok.Data;

@Data
public class VkUserResponse {
    private List<User> response;
    private VkError error;
}
