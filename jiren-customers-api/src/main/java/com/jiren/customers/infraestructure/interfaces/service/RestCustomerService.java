package com.jiren.customers.infraestructure.interfaces.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jiren.customers.domain.model.types.StatusEnumerator;
import com.jiren.customers.domain.model.types.UserEventEnumerator;
import com.jiren.customers.handler.helper.TenantHelper;
import com.jiren.customers.service.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiren.customers.domain.dao.CustomerDAO;
import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.types.DocumentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.customers.infraestructure.dao.repository.DocumentRepository;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.customers.infraestructure.strategy.CustomerCreationStrategy;
import com.jiren.customers.infraestructure.strategy.CustomerUpdateStrategy;
import com.jiren.customers.service.CustomerService;
import com.jiren.customers.service.mapper.CustomerServiceMapper;
import com.jiren.customers.service.strategy.CustomerStrategy;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.guid.GUIDGenerator;
import com.jiren.shared.utils.Helper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestCustomerService implements CustomerService{

	public static final int OWNER_USERS_ALLOWED = 1;
	public static final int MAX_INVOICE_DOCUMENTS_ALLOWED = 2;
	private final CustomerDAO customerDAO;
	private final GUIDGenerator guidGenerator;
	private final CustomerRepository customerRepository;
	private final BusinessRepository businessRepository;
	private final UserRepository userRepository;
	private final DocumentRepository documentRepository;
	
	private final CustomerServiceMapper customerServiceMapper;
	private final RestUserService userService;
	private final TenantHelper tenantHelper;

	/*
	 * Método para la creación de un customer
	 */
	@Override
	@Transactional
	public void createCustomer(CustomerServiceDTO customerServiceDTO) {
		validateCreation(customerServiceDTO);
		CustomerStrategy strategy = new CustomerCreationStrategy(customerServiceDTO, guidGenerator);
		Customer customer = strategy.generate();
		String customerGuid = customerDAO.create(customer);
		userService.saveExternal(tenantHelper.getTenant(),customerGuid, UserEventEnumerator.CREATE);
	}

	private void validateCreation(CustomerServiceDTO customerServiceDTO) {
		if (customerRepository.findByBusinessName(customerServiceDTO.getBusinessName()) != null)
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_CUSTOMER_BUSINESS_NAME, customerServiceDTO.getBusinessName());
		
		validateLists(customerServiceDTO);
		validateUserCreation(customerServiceDTO.getUsers());
		validateDocumentCreation(customerServiceDTO.getDocuments());
		validateBusinessCreation(customerServiceDTO.getBusiness());
	}

	private void validateLists(CustomerServiceDTO customerServiceDTO) {
		if (customerServiceDTO.getBusiness() == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_BUSINESS);
		if (customerServiceDTO.getDocuments() == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_DOCUMENT);
		if (customerServiceDTO.getUsers() == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_USER);		
	}	
	
	private void validateUserCreation(List<UserServiceDTO> listUser) {
		List<String> duplicateDnis = Helper.findDuplicate(listUser.stream()
				.map(x -> x.getTypeDocument() + " " + x.getNumberDocument()).collect(Collectors.toList()));
        if (!duplicateDnis.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_USER_MYPE, duplicateDnis.get(0));
        
        List<String> duplicatePhones = Helper.findDuplicate(listUser.stream()
        		.map(x -> x.getPhone()).collect(Collectors.toList()));
        if (!duplicatePhones.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_PHONE_MYPE, duplicatePhones.get(0));
        
        List<String> duplicateEmails = Helper.findDuplicate(listUser.stream()
        		.map(x -> x.getEmail()).filter(y -> y!=null).collect(Collectors.toList()));
        if (!duplicateEmails.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_EMAIL_MYPE, duplicateEmails.get(0));
        
        listUser.forEach( x -> {
        	validateDocumentPerson(x.getNumberDocument(), x.getTypeDocument());

			userRepository.findByTypeDocumentAndNumberDocument(x.getTypeDocument(), x.getNumberDocument())
					.ifPresent(s->{
						throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER, x.getTypeDocument() + " " + x.getNumberDocument());
					});

        	if (userRepository.findByPhone(x.getPhone()) != null)
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_PHONE, x.getPhone());
        	if (x.getEmail() != null) {  
	        	if (userRepository.findByEmail(x.getEmail()) != null)   		
	        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_EMAIL, x.getEmail());
        	}
        });

		validateUserOwner(listUser);
	}

	private void validateUserOwner(List<UserServiceDTO> listUser){
		long countUserOwner = listUser.stream().filter(userServiceDTO -> userServiceDTO.getOwner())
				.count();

		if(countUserOwner > OWNER_USERS_ALLOWED){
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_INVALID_USERS_OWNER);
		}
	}

	private void validateDocumentPerson(String documentNumber, String documentType){
        DocumentTypeEnumerator type = DocumentTypeEnumerator.of(documentType);        
        switch (type){
	        case CE:
	        	if (!Helper.isValidCe(documentNumber))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, "Número de CE " + documentNumber);
	            break;
	        case DNI:
	        	if (!Helper.isValidDni(documentNumber))
	                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, "Número de DNI " + documentNumber);
	            break;
            case RUC:
            	if (documentNumber.equals(DocumentTypeEnumerator.RUC.getCode()))
                	throw new MPlusApiException(CustomerExceptionEnumerator.TYPE_DOCUMENT_INVALID, "RUC");
                break;
        }
    }

	private void validateDocumentCreation(List<DocumentServiceDTO> listDocument) {
		if(listDocument.size()> MAX_INVOICE_DOCUMENTS_ALLOWED){
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_INVALID_DOCUMENT_MAX);
		}

		List<String> typeDocument = listDocument.stream().map(x -> x.getType()).collect(Collectors.toList());
		List<String> duplicates = Helper.findDuplicate(typeDocument);
		if (!duplicates.isEmpty()) {
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_INVALID_DOCUMENT_TYPE);
		}
		listDocument.forEach( x -> {
			validateDocument(x.getNumber(), x.getType());
        	if (documentRepository.findByTypeAndNumber(x.getType(), x.getNumber()) != null)
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_DOCUMENT, x.getType() + " " + x.getNumber());

        });

	}
	
	private void validateDocument(String documentNumber, String documentType){
        DocumentTypeEnumerator type = DocumentTypeEnumerator.of(documentType);        
        switch (type){
	        case CE:
	        	if (!Helper.isValidCe(documentNumber))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, "Número de CE " + documentNumber);
	            break;
	        case DNI:
	        	if (!Helper.isValidDni(documentNumber))
	                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, "Número de DNI " + documentNumber);
	            break;
            case RUC:
            	if (!Helper.isValidRuc(documentNumber))
            		throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, "Número de RUC " + documentNumber);
                break;
        }
    }
	
	private void validateBusinessCreation(List<BusinessServiceDTO> listBusiness) {
		List<String> address = listBusiness.stream().map(x -> x.getAddress()).collect(Collectors.toList());
		List<String> duplicates = Helper.findDuplicate(address);
		if (!duplicates.isEmpty()) {
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_ADDRESS_MYPE, duplicates.get(0));
		}
		listBusiness.forEach( x -> {
			if (businessRepository.findByAddress(x.getAddress()) != null)
	            throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_BUSINESS_ADDRESS, x.getAddress());
		});
	}

	
	/*
	 * Método para la actualización de un customer
	 */
	@Override
	@Transactional
	public void updateCustomer(CustomerServiceDTO customerServiceDTO) {		
		validateUpdate(customerServiceDTO);
		CustomerStrategy strategy = new CustomerUpdateStrategy(customerServiceDTO, 
				guidGenerator, customerRepository, businessRepository, userRepository);
		Customer customer = strategy.generate();
		customerDAO.update(customer);
		customerDAO.getCustomer(customer.getGuid());
		userService.saveExternal(tenantHelper.getTenant(),customer.getGuid(), UserEventEnumerator.UPDATE);
	}

	private void validateUpdate(CustomerServiceDTO customerServiceDTO) {
		if (customerServiceDTO.getId() == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_ID_CUSTOMER);		
		if (customerRepository.findByGuid(customerServiceDTO.getId()) == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_CUSTOMER, customerServiceDTO.getId());

		validateLists(customerServiceDTO);
		validateUserUpdate(customerServiceDTO.getUsers(),customerServiceDTO.getId());
		validateDocumentUpdate(customerServiceDTO.getDocuments());
		validateBusinessUpdate(customerServiceDTO.getBusiness());
	}

	private void validateUserUpdate(List<UserServiceDTO> listUser, String customerId) {
		List<String> duplicateDnis = Helper.findDuplicate(
				listUser.stream().map(x -> x.getTypeDocument() + " " + x.getNumberDocument()).collect(Collectors.toList()));
        if (!duplicateDnis.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_USER_MYPE, duplicateDnis.get(0));
        
        List<String> duplicatePhones = Helper.findDuplicate(
        		listUser.stream().map(x -> x.getPhone()).collect(Collectors.toList()));
        if (!duplicatePhones.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_PHONE_MYPE, duplicatePhones.get(0));
        
        List<String> duplicateEmails = Helper.findDuplicate(
        		listUser.stream().map(x -> x.getEmail()).filter(y -> y!=null).collect(Collectors.toList()));
        if (!duplicateEmails.isEmpty())
        	throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_EMAIL_MYPE, duplicateEmails.get(0));

		listUser.forEach( x -> {
			validateDocumentPerson(x.getNumberDocument(), x.getTypeDocument());

			userRepository.findByTypeDocumentAndNumberDocumentAndCustomer_GuidNot(x.getTypeDocument(), x.getNumberDocument(),customerId)
					.ifPresent(s->{
						throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER, x.getTypeDocument() + " " + x.getNumberDocument());
					});

			if (userRepository.findByPhoneAndCustomer_GuidNot(x.getPhone(),customerId) != null)
				throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_PHONE, x.getPhone());
			if (x.getEmail() != null) {
				if (userRepository.findByEmailAndCustomer_GuidNot(x.getEmail(),customerId) != null)
					throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_EMAIL, x.getEmail());
			}
		});

		validateUserOwner(listUser);
	}
	
	private void validateDocumentUpdate(List<DocumentServiceDTO> listDocument) {		
		List<String> duplicates = Helper.findDuplicate(
				listDocument.stream().map(x -> x.getType() + " " + x.getNumber()).collect(Collectors.toList()));
		if (!duplicates.isEmpty())
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_DOCUMENT_MYPE, duplicates.get(0));
		
		listDocument.forEach( x -> validateDocument(x.getNumber(), x.getType()) );
	}

	private void validateBusinessUpdate(List<BusinessServiceDTO> listBusiness) {
		List<String> duplicates = Helper.findDuplicate(
				listBusiness.stream().map(x -> x.getAddress()).collect(Collectors.toList()));
		if (!duplicates.isEmpty())
			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_LIST_ADDRESS_MYPE, duplicates.get(0));		
	}

	@Override
	public CustomerPageServiceDTO getPageCustomers(CustomerFilter filter, PageRequest pageable) {
		return customerServiceMapper.toCustomerPageServiceDTO(customerDAO.getPageCustomers(filter, pageable));
	}
	
	@Override
	public GetCustomerServiceDTO getCustomer(String guid) {
		Customer customer = customerDAO.getCustomer(guid);		
		if (customer == null)
			throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_CUSTOMER, guid);
		
		return customerServiceMapper.toCustomerServiceDTO(customer);
	}

	@Override
	public List<GetCustomerServiceDTO> getCustomers(List<String> guids) {
		return customerDAO.getCustomers(guids)
				.stream()
				.map(customerServiceMapper::toCustomerServiceDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<CustomerStatusDTO> listCustomerStatus() {
		return Stream.of(StatusEnumerator.values())
				.map(statusEnumerator -> new CustomerStatusDTO(statusEnumerator.getCode(), statusEnumerator.getName()))
				.collect(Collectors.toList());
	}

}
