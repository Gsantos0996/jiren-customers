package com.jiren.customers.infraestructure.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jiren.customers.domain.model.Business;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.Customer.CustomerBuilder;
import com.jiren.customers.domain.model.CustomerPaymentType;
import com.jiren.customers.domain.model.Document;
import com.jiren.customers.domain.model.PaymentType;
import com.jiren.customers.domain.model.Schedule;
import com.jiren.customers.domain.model.TimeWindow;
import com.jiren.customers.domain.model.Ubigeo;
import com.jiren.customers.domain.model.User;
import com.jiren.customers.domain.model.types.PaymentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.jpa.JPABusiness;
import com.jiren.customers.infraestructure.dao.jpa.JPACustomer;
import com.jiren.customers.infraestructure.dao.jpa.JPAUser;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.customers.service.dto.CustomerServiceDTO;
import com.jiren.customers.service.strategy.CustomerStrategy;
import com.jiren.shared.guid.GUIDGenerator;

public class CustomerUpdateStrategy implements CustomerStrategy{

	private final CustomerServiceDTO customerServiceDTO;
	private final GUIDGenerator guidGenerator;
	private final CustomerRepository customerRepository;
	private final BusinessRepository businessRepository;
	private final UserRepository userRepository;
	private final boolean ACTIVE = true;
		
	public CustomerUpdateStrategy(CustomerServiceDTO customerServiceDTO, GUIDGenerator guidGenerator, CustomerRepository customerRepository, 
			BusinessRepository businessRepository, UserRepository userRepository) {
		this.customerServiceDTO = customerServiceDTO;
		this.guidGenerator = guidGenerator;
		this.customerRepository = customerRepository;
		this.businessRepository = businessRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Boolean validate() {
		return customerServiceDTO != null && guidGenerator != null;
	}

	@Override
	public Customer generate() {
		if (!validate())
            return null;

		JPACustomer jpaCustomer = customerRepository.findByGuid(customerServiceDTO.getId());
		
		CustomerBuilder customerBuilder = Customer.builder();
		customerBuilder.guid( customerServiceDTO.getId() );
		customerBuilder.adviser( customerServiceDTO.getAdviser() );
		customerBuilder.allowElectronicInvoice( customerServiceDTO.getAllowElectronicInvoice() );
		customerBuilder.businessName( customerServiceDTO.getBusinessName() );
		customerBuilder.channelOrigin( customerServiceDTO.getChannelOrigin() );		
		customerBuilder.currency( customerServiceDTO.getCurrency() );
		customerBuilder.dexCode( customerServiceDTO.getDexCode() );
		customerBuilder.dischargeDate( customerServiceDTO.getDischargeDate() );
		customerBuilder.erpCode( customerServiceDTO.getErpCode() );
		customerBuilder.image( customerServiceDTO.getImage() );
		customerBuilder.minAmount( customerServiceDTO.getMinAmount() );
		customerBuilder.newCustomer( customerServiceDTO.getNewCustomer() );
		customerBuilder.perfectCustomer( customerServiceDTO.getPerfectCustomer() );
		customerBuilder.prefixProductCode( customerServiceDTO.getPrefixProductCode() );
		customerBuilder.registryDate( customerServiceDTO.getRegistryDate() );
		customerBuilder.segment( customerServiceDTO.getSegment() );
		customerBuilder.state( customerServiceDTO.getState() );
		customerBuilder.subWorkArea( customerServiceDTO.getSubWorkArea() );
		customerBuilder.tradeName( customerServiceDTO.getTradeName() );
		customerBuilder.updatedCustomer( customerServiceDTO.getUpdatedCustomer() );
		customerBuilder.wave( customerServiceDTO.getWave() );
		customerBuilder.active( ACTIVE );
		customerBuilder.userCreated( jpaCustomer.getUserCreated() );
		customerBuilder.dateCreated( jpaCustomer.getDateCreated() );
		customerBuilder.userUpdated( customerServiceDTO.getUserAudit() );
		customerBuilder.dateUpdated( LocalDateTime.now() );
		customerBuilder.workArea( customerServiceDTO.getWorkArea() );
		
		convertToModel(customerBuilder);
		
		return customerBuilder.build();
	}

	private void convertToModel(CustomerBuilder customerBuilder) {	
		if (customerServiceDTO.getBusiness() != null)
			convertBusiness(customerBuilder);
		if (customerServiceDTO.getSellIns() != null || customerServiceDTO.getSellOuts() != null)
			convertPaymentType(customerBuilder);
		if (customerServiceDTO.getDocuments() != null)
			convertDocument(customerBuilder);
		if (customerServiceDTO.getUsers() != null)
			convertUser(customerBuilder);
	}

	private void convertBusiness(CustomerBuilder customerBuilder) {
		List<Business> listBusiness = new ArrayList<Business>();
		customerServiceDTO.getBusiness().forEach(
				x -> {
					List<Schedule> listSchedule = new ArrayList<Schedule>();
					if (x.getSchedules() != null) {
						x.getSchedules().forEach(
								y -> listSchedule.add(
										new Schedule(
												y.getId(), 
												null, y.getDay(), 
												y.getStartTime(), y.getEndTime(), 
												y.getAttention(), 
												ACTIVE) )
								);
					}
					List<TimeWindow> listTimeWindow = new ArrayList<TimeWindow>();
					if (x.getTimeWindows() != null) {
						x.getTimeWindows().forEach(
								y -> listTimeWindow.add(
										new TimeWindow(
												y.getId(), 
												null, 
												y.getStartTime(), y.getEndTime(), 
												ACTIVE) )
								);
					}
					JPABusiness business = businessRepository.findByGuid(x.getId());
					listBusiness.add(
						new  Business(
								x.getId(), 
								customerBuilder.build(), 
								new Ubigeo(x.getUbigeoCode()),
								x.getDeliveryDistance(), x.getDeliveryCost(), 
								x.getAddress(), 
								x.getPostalCode(), x.getReference(), 
								x.getLatitude(), x.getLongitude(), 
								x.getZone(), x.getAnnex(), 
								ACTIVE, 
								business != null ? business.getUserCreated() : customerServiceDTO.getUserAudit(), 
								business != null ? business.getDateCreated() : LocalDateTime.now(),
								business != null ? customerServiceDTO.getUserAudit() : null, 
								business != null ? LocalDateTime.now() : null, 
								listSchedule, listTimeWindow )
						);
				});
		customerBuilder.business( listBusiness );
	}	

	private void convertPaymentType(CustomerBuilder customerBuilder) {
		List<CustomerPaymentType> sellIns = new ArrayList<CustomerPaymentType>();
		if (customerServiceDTO.getSellIns() != null) {
			customerServiceDTO.getSellIns().forEach(
					x -> {
						sellIns.add(
							new CustomerPaymentType(
									x.getId(),
									customerBuilder.build(), 
									new PaymentType(x.getPaymentType()), 
									PaymentTypeEnumerator.SELL_IN.getCode(), 
									ACTIVE) );
					});
		}
		customerBuilder.sellIns( sellIns );
		
		List<CustomerPaymentType> sellOuts = new ArrayList<CustomerPaymentType>();
		if (customerServiceDTO.getSellOuts() != null) {
			customerServiceDTO.getSellOuts().forEach(
					x -> {
						sellOuts.add(
								new CustomerPaymentType(
										x.getId(),
										customerBuilder.build(), 
										new PaymentType(x.getPaymentType()), 
										PaymentTypeEnumerator.SELL_OUT.getCode(), 
										ACTIVE) );
					});
		}
		customerBuilder.sellOuts( sellOuts );
	}
	
	private void convertDocument(CustomerBuilder customerBuilder) {
		List<Document> listDocument = new ArrayList<Document>();		
		customerServiceDTO.getDocuments().forEach(
				x -> {					
					listDocument.add(
						new Document(
								x.getId(), 
								customerBuilder.build(),
								x.getType(), 
								x.getNumber(), 
								x.getDocFiscal(), 
								ACTIVE) );				
				});
		customerBuilder.documents( listDocument );
	}

	private void convertUser(CustomerBuilder customerBuilder) {
		List<User> listUser = new ArrayList<User>();
		customerServiceDTO.getUsers().forEach(
				x -> {
					JPAUser user = userRepository.findByGuid(x.getId());
					listUser.add(
							new User(
									x.getId(), 
									customerBuilder.build(), 
									x.getTypeDocument(), 
									x.getNumberDocument(), 
									x.getName(), x.getFirstLastName(), 
									x.getSecondLastName(), x.getEmail(), 
									x.getPhone(), x.getOwner(),  
									ACTIVE, 
									user != null ? user.getUserCreated() : customerServiceDTO.getUserAudit(), 
									user != null ? user.getDateCreated() : LocalDateTime.now(),
									user != null ? customerServiceDTO.getUserAudit() : null,
									user != null ? LocalDateTime.now() : null) );
				});
		customerBuilder.users( listUser );
	}

}
