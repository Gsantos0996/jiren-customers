package com.jiren.customers.handler;

import com.jiren.customers.adapter.rest.dto.customers.CheckUserResponseDTO;
import com.jiren.customers.adapter.rest.dto.customers.CheckUserRestDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.UserProfileResponseDTO;
import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.service.UserService;
import com.jiren.customers.service.dto.UserServiceDTO;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler {

	public static final String USERNAME_DOCUMENT_SEPARATOR="_";
	private final UserService userService;

	public CheckUserResponseDTO checkUser(CheckUserRestDTO checkUserRestDTO) {
		return buildCheckUserResponseDTO(userService.checkUser(buildUserServiceDTO(checkUserRestDTO)));
	}

	public UserProfileResponseDTO profileUser() {
		String userGuid = SecurityUtils.getUserGUID()
				.orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_REGISTER_USER));

		return UserProfileResponseDTO.builder()
				.customer(userService.getProfile(userGuid))
				.build();
	}

	private UserServiceDTO buildUserServiceDTO(CheckUserRestDTO checkUserRestDTO) {
		String [] documentValues = checkUserRestDTO.getUsername().split(USERNAME_DOCUMENT_SEPARATOR);

		return UserServiceDTO
				.builder()
				.typeDocument(documentValues[0])
				.numberDocument(documentValues[1])
				.build();
	}

	private CheckUserResponseDTO buildCheckUserResponseDTO(UserServiceDTO userServiceDTO){
		return CheckUserResponseDTO.builder()
				.typeDocument(userServiceDTO.getTypeDocument())
				.numberDocument(userServiceDTO.getNumberDocument())
				.build();
	}


}