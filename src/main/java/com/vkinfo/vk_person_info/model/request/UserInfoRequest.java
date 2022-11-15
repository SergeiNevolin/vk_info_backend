package com.vkinfo.vk_person_info.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoRequest {
    @JsonProperty("user_id")
    @NotNull
    @Min(1)
    private Integer userId;

    @JsonProperty("group_id")
    @NotNull
    @Min(1)
    private Integer groupId;
}
