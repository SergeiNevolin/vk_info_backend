package com.vkinfo.vk_person_info.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VkIsMemberResponse {
    
    @JsonProperty("response")
    private int member;

    private VkError error;
}
