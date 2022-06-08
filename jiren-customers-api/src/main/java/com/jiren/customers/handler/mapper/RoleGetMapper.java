package com.jiren.customers.handler.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jiren.customers.adapter.rest.dto.customers.GetRoleResponseDTO;
import com.jiren.customers.service.dto.GetRoleServiceDTO;


@Mapper(componentModel = "spring")
public interface RoleGetMapper {

	List<GetRoleResponseDTO> toGetRoleResponseDtos(List<GetRoleServiceDTO> listGetRoleServiceDTO);
}
