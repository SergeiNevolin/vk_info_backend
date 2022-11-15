package com.vkinfo.vk_person_info.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vkinfo.vk_person_info.model.User;
import com.vkinfo.vk_person_info.model.VkError;
import com.vkinfo.vk_person_info.model.VkIsMemberResponse;
import com.vkinfo.vk_person_info.model.VkUserResponse;
import com.vkinfo.vk_person_info.model.request.UserInfoRequest;
import com.vkinfo.vk_person_info.model.response.UserResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    WebClient webClient;
    

    //Инфо о пользователе в вк
    @Override
    public UserResponse userInfo(String vkServiceToken, UserInfoRequest request){

        VkUserResponse vkUserResponse = getVkUserResponse(vkServiceToken, request).block();

        if (vkUserResponse.getError() != null)
            throw new RuntimeException(buildVkErrorString(vkUserResponse.getError()));
        
        if (vkUserResponse.getResponse().isEmpty())
            throw new EntityNotFoundException("User " + request.getUserId() + " is not found");

        User user = vkUserResponse.getResponse().get(0);
        VkIsMemberResponse vkIsMemberResponse = getVkIsMemberResponse(vkServiceToken, request).block();

        if (vkIsMemberResponse.getError() != null)
            throw new RuntimeException(buildVkErrorString(vkIsMemberResponse.getError()));

        int member = vkIsMemberResponse.getMember();

        return buildUserResponse(user, member);
    } 

    private Mono<VkUserResponse> getVkUserResponse(String vkServiceToken, UserInfoRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/method/users.get")
                    .queryParam("access_token", vkServiceToken)
                    .queryParam("user_ids", request.getUserId())
                    .queryParam("v", "5.131")
                    .build())
                .retrieve()
                .bodyToMono(VkUserResponse.class);
    }

    private Mono<VkIsMemberResponse> getVkIsMemberResponse(String vkServiceToken, UserInfoRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/method/groups.isMember")
                    .queryParam("access_token", vkServiceToken)
                    .queryParam("group_id", request.getGroupId())
                    .queryParam("user_id", request.getUserId())
                    .queryParam("v", "5.131")
                    .build())
                .retrieve()
                .bodyToMono(VkIsMemberResponse.class);
    }

    private UserResponse buildUserResponse(User user, int member) {
        return new UserResponse()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMiddleName(user.getMiddleName())
                .setMember(member == 1);
    }

    private String buildVkErrorString(VkError vkError){
        return "VkApiError: error_code=" + vkError.getErrorCode() + 
                ", ErrorMsg=" + vkError.getErrorMsg();
    }
}
