package com.jiren.customers.domain.dao;

import com.jiren.customers.domain.model.User;

public interface UserDAO {

    User getUser(String guid);
}
