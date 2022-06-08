package com.jiren.customers.service;

import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.CustomerProfileResponseDTO;
import com.jiren.customers.domain.model.types.UserEventEnumerator;
import com.jiren.customers.service.dto.UserServiceDTO;

public interface UserService {

	CustomerProfileResponseDTO getProfile(String userGuid);

	UserServiceDTO checkUser(UserServiceDTO userServiceDTO);

	void saveExternal(String tenant,String customerGuid, UserEventEnumerator eventType);

}
