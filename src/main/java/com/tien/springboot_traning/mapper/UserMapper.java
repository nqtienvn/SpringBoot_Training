package com.tien.springboot_traning.mapper;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponse;
import com.tien.springboot_traning.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequestDTO request);
    void toUserUpdate(UserUpdateRequestDTO request, @MappingTarget User user);
    UserResponse toUserResponse(User user);
}
