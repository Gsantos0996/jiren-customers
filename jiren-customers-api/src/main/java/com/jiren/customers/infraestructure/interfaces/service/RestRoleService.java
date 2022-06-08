package com.jiren.customers.infraestructure.interfaces.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.jiren.customers.domain.model.types.RoleEnumerator;
import com.jiren.customers.service.RoleService;
import com.jiren.customers.service.dto.GetRoleServiceDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestRoleService implements RoleService {

    @Override
    public List<GetRoleServiceDTO> get() {
        return Stream.of(RoleEnumerator.values())
                .map(x -> new GetRoleServiceDTO(x.getCode(),x.getName()))
                .collect(Collectors.toList());
    }
}
