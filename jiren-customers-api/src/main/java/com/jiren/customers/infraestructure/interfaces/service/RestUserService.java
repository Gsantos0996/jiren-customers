package com.jiren.customers.infraestructure.interfaces.service;

//import com.jiren.customers.adapter.event.UserAdapterEvent;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.CustomerProfileResponseDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.BusinessDTO;
import com.jiren.customers.domain.dao.CustomerDAO;
import com.jiren.customers.domain.dao.UserDAO;
import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.Business;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.ExternalUser;
import com.jiren.customers.domain.model.User;
import com.jiren.customers.domain.model.types.UserEventEnumerator;
import com.jiren.customers.handler.mapper.UserProfileMapper;
import com.jiren.customers.infraestructure.dao.jpa.JPAUser;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.customers.service.UserService;
import com.jiren.customers.service.dto.UserServiceDTO;
import com.jiren.shared.exception.MPlusApiException;
//import com.jiren.shared.kafka.service.dto.MessageDTO;
import com.jiren.shared.utils.Helper;
import com.jiren.ubigeo.service.UbigeoService;
import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestUserService implements UserService {

    private final UserRepository userRepository;
    //private final UserAdapterEvent userAdapterEvent;
    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final UbigeoService ubigeoService;
    private final UserProfileMapper userProfileMapper;

    @Override
    public CustomerProfileResponseDTO getProfile(String guid) {
        User user = userDAO.getUser(guid);
        Customer customer = customerDAO.getProfileCustomer(user.getCustomer().getGuid());

        CustomerProfileResponseDTO customerProfileResponseDTO = userProfileMapper.toCustomerProfileResponseDto(user, customer);
        customerProfileResponseDTO.setBusiness(buildBusinessDTOs(customer.getBusiness()));

        return customerProfileResponseDTO;
    }

    private List<BusinessDTO> buildBusinessDTOs(List<Business> businesses){
        return businesses
                .stream()
                .map(business -> buildBusinessDTO(ubigeoService.getUbigeo(business.getUbigeo().getGuid()),business.getAddress()))
                .collect(Collectors.toList());
    }

    private BusinessDTO buildBusinessDTO(GetUbigeoLineServiceDTO ubigeoLineServiceDTO, String address){
        return BusinessDTO.builder()
                .id(ubigeoLineServiceDTO.getId())
                .address(Helper.formatAddress(address,ubigeoLineServiceDTO.getDistrict()))
                .district(ubigeoLineServiceDTO.getDistrict())
                .province(ubigeoLineServiceDTO.getProvince())
                .department(ubigeoLineServiceDTO.getDepartment())
                .build();
    }

    @Override
    public UserServiceDTO checkUser(UserServiceDTO userServiceDTO) {

        JPAUser user = userRepository.findByTypeDocumentAndNumberDocument(userServiceDTO.getTypeDocument(),userServiceDTO.getNumberDocument())
                .orElseThrow(()->new MPlusApiException(CustomerExceptionEnumerator.NOT_REGISTER_USER));

        return buildUserServiceDTO(user);
    }

    @Override
    public void saveExternal(String tenant, String customerGuid, UserEventEnumerator eventType) {
        List<JPAUser> users = userRepository.findByCustomer_Guid(customerGuid);
       // userAdapterEvent.sendUserSavedEvent(new MessageDTO<>(eventType.getCode(),buildExternalUsers(tenant,customerGuid, users)));
    }

    private UserServiceDTO buildUserServiceDTO(JPAUser jpaUser){
        return UserServiceDTO.builder()
                .id(jpaUser.getGuid())
                .typeDocument(jpaUser.getTypeDocument())
                .numberDocument(jpaUser.getNumberDocument())
                .name(jpaUser.getName())
                .firstLastName(jpaUser.getFirstLastName())
                .secondLastName(jpaUser.getSecondLastName())
                .email(jpaUser.getEmail())
                .phone(jpaUser.getPhone())
                .owner(jpaUser.getOwner())
                .build();
    }

    private List<ExternalUser> buildExternalUsers(String tenant,String customerGuid, List<JPAUser> users){
        return users.stream().map(jpaUser -> ExternalUser.builder()
                .customerGuid(customerGuid)
                .guid(jpaUser.getGuid())
                .typeDocument(jpaUser.getTypeDocument())
                .numberDocument(jpaUser.getNumberDocument())
                .tenant(tenant)
                .email(jpaUser.getEmail())
                .phone(jpaUser.getPhone())
                .active(jpaUser.getActive())
                .name(jpaUser.getName())
                .firstLastName(jpaUser.getFirstLastName())
                .secondLastName(jpaUser.getSecondLastName())
                .build()).collect(Collectors.toList());
    }
}
