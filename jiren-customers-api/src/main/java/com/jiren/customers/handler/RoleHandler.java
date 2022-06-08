package com.jiren.customers.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jiren.customers.adapter.rest.dto.customers.GetRoleResponseDTO;
import com.jiren.customers.handler.mapper.RoleGetMapper;
import com.jiren.customers.service.RoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleHandler {
	
	private final RoleService roleService;
	private final RoleGetMapper roleMapper;

	public List<GetRoleResponseDTO> get() {
		return roleMapper.toGetRoleResponseDtos(roleService.get());
	}

}
