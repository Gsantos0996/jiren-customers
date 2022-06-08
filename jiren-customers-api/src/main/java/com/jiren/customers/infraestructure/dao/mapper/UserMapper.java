package com.jiren.customers.infraestructure.dao.mapper;

import com.jiren.customers.domain.model.User;
import com.jiren.customers.infraestructure.dao.jpa.JPAUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User toDomain(JPAUser user);
}
