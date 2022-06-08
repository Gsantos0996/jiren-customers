package com.jiren.customers.handler.mapper;

import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO;
import com.jiren.customers.service.dto.UserServiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserGetMapper {

    UserProfileDTO toUserProfileResponseDto(UserServiceDTO userServiceDTO);
}
