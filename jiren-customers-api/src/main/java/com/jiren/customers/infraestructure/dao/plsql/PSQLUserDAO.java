package com.jiren.customers.infraestructure.dao.plsql;

import com.jiren.customers.domain.dao.UserDAO;
import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.User;
import com.jiren.customers.infraestructure.dao.jpa.JPAUser;
import com.jiren.customers.infraestructure.dao.mapper.UserMapper;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.shared.exception.MPlusApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PSQLUserDAO implements UserDAO {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getUser(String guid) {
        JPAUser jpaUser = Optional.ofNullable(userRepository.findByGuid(guid))
                .orElseThrow(()->new MPlusApiException(CustomerExceptionEnumerator.NOT_REGISTER_USER));

        return userMapper.toDomain(jpaUser);
    }
}
