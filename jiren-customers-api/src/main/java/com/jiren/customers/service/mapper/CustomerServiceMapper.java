package com.jiren.customers.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jiren.customers.adapter.rest.dto.customers.PageRenderDTO;
import com.jiren.customers.domain.model.types.StatusEnumerator;
import com.jiren.customers.service.dto.*;
import com.jiren.shared.models.types.SubWorkAreaEnumerator;
import com.jiren.shared.models.types.WorkAreaEnumerator;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.jiren.customers.domain.model.Business;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.CustomerPaymentType;
import com.jiren.customers.domain.model.Document;
import com.jiren.customers.domain.model.User;

@Mapper(componentModel = "spring")
public interface CustomerServiceMapper {
	
	default GetCustomerServiceDTO toCustomerServiceDTO(Customer customer) {
		if (customer == null)
			return null;

		return GetCustomerServiceDTO.builder()
				.adviser(customer.getAdviser())
		        .allowElectronicInvoice(customer.getAllowElectronicInvoice())
		        .businessName(customer.getBusinessName())
		        .channelOrigin(customer.getChannelOrigin())
		        .currency(customer.getCurrency())
		        .dexCode(customer.getDexCode())
		        .dischargeDate(customer.getDischargeDate())
		        .erpCode(customer.getErpCode())
		        .id(customer.getGuid())
		        .image(customer.getImage())
		        .minAmount(customer.getMinAmount())
		        .newCustomer(customer.getNewCustomer())
		        .perfectCustomer(customer.getPerfectCustomer())
		        .prefixProductCode(customer.getPrefixProductCode())
		        .registryDate(customer.getRegistryDate())
		        .segment(customer.getSegment())
		        .state(customer.getState())
				.stateDescription(StatusEnumerator.of(customer.getState()).getName())
		        .subWorkArea(subWorkAreaToSubWorkAreaDTO(customer.getSubWorkArea()))
		        .tradeName(customer.getTradeName())
		        .updatedCustomer(customer.getUpdatedCustomer())
		        .wave(customer.getWave())
		        .workArea(workAreaToWorkAreaDTO(customer.getWorkArea()))
		        .documents(documentListToDocumentServiceDTOList(customer.getDocuments()))
		        .business(businessListToBusinessServiceDTOList(customer.getBusiness()))        
		        .users(userListToUserServiceDTOList(customer.getUsers()))
		        .sellIns(paymentTypeListToPaymentTypeServiceDTOList(customer.getSellIns()))
		        .sellOuts(paymentTypeListToPaymentTypeServiceDTOList(customer.getSellOuts()))
		        .build();
	}

	default SubWorkAreaServiceDTO subWorkAreaToSubWorkAreaDTO(String subWorkAreaCode){
		SubWorkAreaEnumerator subWorkAreaEnumerator = SubWorkAreaEnumerator.of(subWorkAreaCode);
		return SubWorkAreaServiceDTO.builder()
				.id(subWorkAreaEnumerator.getCode())
				.description(subWorkAreaEnumerator.getDescription())
				.build();
	}

	default WorkAreaServiceDTO workAreaToWorkAreaDTO(String workAreaCode){
		WorkAreaEnumerator workAreaEnumerator = WorkAreaEnumerator.of(workAreaCode);
		return WorkAreaServiceDTO.builder()
				.id(workAreaEnumerator.getCode())
				.description(workAreaEnumerator.getDescription())
				.build();
	}

	default List<PaymentTypeServiceDTO> paymentTypeListToPaymentTypeServiceDTOList(List<CustomerPaymentType> customerPaymentTypes) {
		if (customerPaymentTypes == null)
			return null;
		
		List<PaymentTypeServiceDTO> list = new ArrayList<PaymentTypeServiceDTO>();
		customerPaymentTypes.forEach(x -> list.add(
				new PaymentTypeServiceDTO(
						x.getPaymentType().getGuid(), 
						x.getPaymentType().getName())));		
		return list;
	}
	
	default List<UserServiceDTO> userListToUserServiceDTOList(List<User> users) {
		if (users == null)
			return null;
		
		List<UserServiceDTO> list = new ArrayList<UserServiceDTO>();
		users.forEach(x -> list.add(
				new UserServiceDTO(
						x.getGuid(), x.getTypeDocument(), 
						x.getNumberDocument(), x.getName(),
						x.getFirstLastName(), x.getSecondLastName(), 
						x.getEmail(), x.getPhone(), x.getOwner())));		
		return list;
	}
	
	default List<DocumentServiceDTO> documentListToDocumentServiceDTOList(List<Document> documents) {
		if (documents == null)
			return null;
		
		List<DocumentServiceDTO> list = new ArrayList<DocumentServiceDTO>();
		documents.forEach(x -> list.add(
				new DocumentServiceDTO(
						x.getGuid(), x.getType(), 
						x.getNumber(), x.getDocFiscal()))
				);		
		return list;
	}
	
	default List<BusinessServiceDTO> businessListToBusinessServiceDTOList(List<Business> business) {
		if (business == null)
			return null;
		
		List<BusinessServiceDTO> list = new ArrayList<BusinessServiceDTO>();
		business.forEach(x -> list.add(
				new BusinessServiceDTO(
					x.getGuid(), x.getDeliveryDistance(), 
					x.getDeliveryCost(), x.getUbigeo().getGuid(), 
					x.getAddress(), x.getPostalCode(), 
					x.getReference(), x.getLatitude(), 
					x.getLongitude(), x.getZone(), 
					x.getAnnex(), null, null)) 
				);
		return list;
	}
	
	default CustomerPageServiceDTO toCustomerPageServiceDTO(Page<Customer> customerPage) {
		if (customerPage == null)
			return null;
		
		CustomerPageServiceDTO customerPageServiceDTO = CustomerPageServiceDTO.builder()
				.customers(customerPage.getContent().stream()
						.map(this::toCustomerServiceDTO)
						.collect(Collectors.toList()))
				.pageRender(new PageRenderDTO(
						customerPage.getNumber(), 
						customerPage.getTotalElements(), 
						customerPage.getTotalPages()))
				.build();
		return customerPageServiceDTO;
	}
}
