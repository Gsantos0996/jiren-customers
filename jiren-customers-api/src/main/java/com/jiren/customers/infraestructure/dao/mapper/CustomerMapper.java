package com.jiren.customers.infraestructure.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.jiren.customers.domain.model.Business;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.CustomerPaymentType;
import com.jiren.customers.domain.model.Document;
import com.jiren.customers.domain.model.PaymentType;
import com.jiren.customers.domain.model.Ubigeo;
import com.jiren.customers.domain.model.User;
import com.jiren.customers.domain.model.types.PaymentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.jpa.JPACustomer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	
	default Customer toCustomerWithRelations(JPACustomer jpaCustomer) {
		
        if ( jpaCustomer == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setGuid( jpaCustomer.getGuid() );
        customer.setBusinessName( jpaCustomer.getBusinessName() );
        customer.setTradeName( jpaCustomer.getTradeName() );
        customer.setWave( jpaCustomer.getWave() );
        customer.setSegment( jpaCustomer.getSegment() );
        customer.setWorkArea( jpaCustomer.getWorkArea() );
        customer.setSubWorkArea( jpaCustomer.getSubWorkArea() );
        customer.setMinAmount( jpaCustomer.getMinAmount() );
        customer.setChannelOrigin( jpaCustomer.getChannelOrigin() );
        customer.setAdviser( jpaCustomer.getAdviser() );
        customer.setDexCode( jpaCustomer.getDexCode() );
        customer.setCurrency( jpaCustomer.getCurrency() );
        customer.setState( jpaCustomer.getState() );
        customer.setRegistryDate( jpaCustomer.getRegistryDate() );
        customer.setDischargeDate( jpaCustomer.getDischargeDate() );
        customer.setAllowElectronicInvoice( jpaCustomer.getAllowElectronicInvoice() );
        customer.setPerfectCustomer( jpaCustomer.getPerfectCustomer() );
        customer.setImage( jpaCustomer.getImage() );
        customer.setPrefixProductCode( jpaCustomer.getPrefixProductCode() );
        customer.setErpCode( jpaCustomer.getErpCode() );
        customer.setNewCustomer( jpaCustomer.getNewCustomer() );
        customer.setUpdatedCustomer( jpaCustomer.getUpdatedCustomer() );
        customer.setActive( jpaCustomer.getActive() );
        customer.setUserCreated( jpaCustomer.getUserCreated() );
        customer.setDateCreated( jpaCustomer.getDateCreated() );
        customer.setUserUpdated( jpaCustomer.getUserUpdated() );
        customer.setDateUpdated( jpaCustomer.getDateUpdated() );

        List<Business> listBusiness = new ArrayList<Business>();                
        jpaCustomer.getCustomerBusiness().forEach(
        		x -> {
        			listBusiness.add( new Business(
        					x.getGuid(), customer, 
        					new Ubigeo(x.getUbigeo().getGuid()),
        					x.getDeliveryDistance(), 
							x.getDeliveryCost(),
							x.getAddress(), x.getPostalCode(),
							x.getReference(), x.getLatitude(), 
							x.getLongitude(), x.getZone(), 
							x.getAnnex(), x.getActive(),
							x.getUserCreated(), x.getDateCreated(), 
							x.getUserUpdated(), x.getDateUpdated(), 
							null, null) );
        		});
        customer.getBusiness().addAll(listBusiness);
        
        List<CustomerPaymentType> listSellIns = new ArrayList<CustomerPaymentType>();
        List<CustomerPaymentType> listSellOuts = new ArrayList<CustomerPaymentType>();
        jpaCustomer.getCustomerSells().forEach(
        		x -> {
        			if (PaymentTypeEnumerator.SELL_IN.getCode().equals(x.getType())) {
        				listSellIns.add(new CustomerPaymentType(
	        					x.getGuid(), customer,
	        					new PaymentType(
        								x.getPaymentType().getGuid(), x.getPaymentType().getDescription(),
        								x.getPaymentType().getName(), x.getPaymentType().getAlternativeName(),
        								x.getPaymentType().getSellin(), x.getPaymentType().getSellout(),
        								x.getPaymentType().getMode(), x.getPaymentType().getIndex(),
        								x.getPaymentType().getActive()),
	        					x.getType(), x.getActive()));
        			}
        			if (PaymentTypeEnumerator.SELL_OUT.getCode().equals(x.getType())) {
        				listSellOuts.add(new CustomerPaymentType(
        						x.getGuid(), customer,
        						new PaymentType(
        								x.getPaymentType().getGuid(), x.getPaymentType().getDescription(),
        								x.getPaymentType().getName(), x.getPaymentType().getAlternativeName(),
        								x.getPaymentType().getSellin(), x.getPaymentType().getSellout(),
        								x.getPaymentType().getMode(), x.getPaymentType().getIndex(),
        								x.getPaymentType().getActive()),
        						x.getType(), x.getActive()));
        			}
        		});
        customer.getSellIns().addAll(listSellIns);
        customer.getSellOuts().addAll(listSellOuts);
        
        List<Document> listDocuments = new ArrayList<Document>();
        jpaCustomer.getCustomerDocuments().forEach(
        		x -> {
        			listDocuments.add(new Document(
        					x.getGuid(), customer, 
        					x.getType(), x.getNumber(), 
        					x.getDocFiscal(), x.getActive()));
        		});
        customer.getDocuments().addAll(listDocuments);

        List<User> listUsers = new ArrayList<User>();
        jpaCustomer.getCustomerUsers().forEach(
        		x -> {
        			listUsers.add(new User(
        					x.getGuid(), customer, 
        					x.getTypeDocument(),
        					x.getNumberDocument(),
        					x.getName(),
        					x.getFirstLastName(),
        					x.getSecondLastName(),
        					x.getEmail(),
        					x.getPhone(),
        					x.getOwner(),
        					x.getActive(),
        					x.getUserCreated(),
        					x.getDateCreated(),
        					x.getUserUpdated(),
        					x.getDateUpdated()));
        		});
        customer.getUsers().addAll(listUsers);
        
        return customer;	    
	}

	default Customer toCustomerProfile(JPACustomer jpaCustomer) {

		if ( jpaCustomer == null ) {
			return null;
		}

		Customer customer = new Customer();

		customer.setGuid( jpaCustomer.getGuid() );
		customer.setBusinessName( jpaCustomer.getBusinessName() );
		customer.setTradeName( jpaCustomer.getTradeName() );
		customer.setMinAmount( jpaCustomer.getMinAmount() );
		customer.setSegment(jpaCustomer.getSegment());
		customer.setChannelOrigin(jpaCustomer.getChannelOrigin());
		customer.setWorkArea(jpaCustomer.getWorkArea());
		customer.setSubWorkArea(jpaCustomer.getSubWorkArea());

		List<Business> listBusiness = new ArrayList<Business>();
		jpaCustomer.getCustomerBusiness().forEach(
				x -> {
					listBusiness.add(Business.builder()
									.guid(x.getGuid())
									.address(x.getAddress())
									.ubigeo(new Ubigeo(x.getUbigeo().getGuid()))
									.build());
				});
		customer.getBusiness().addAll(listBusiness);

		List<CustomerPaymentType> listSellIns = new ArrayList<CustomerPaymentType>();
		jpaCustomer.getCustomerSells().forEach(
				x -> {
					if (PaymentTypeEnumerator.SELL_IN.getCode().equals(x.getType())) {
						listSellIns.add(new CustomerPaymentType(
								x.getGuid(), customer,
								new PaymentType(
										x.getPaymentType().getGuid(), x.getPaymentType().getDescription(),
										x.getPaymentType().getName(), x.getPaymentType().getAlternativeName(),
										x.getPaymentType().getSellin(), x.getPaymentType().getSellout(),
										x.getPaymentType().getMode(), x.getPaymentType().getIndex(),
										x.getPaymentType().getActive()),
								x.getType(), x.getActive()));
					}
				});
		customer.getSellIns().addAll(listSellIns);

		List<Document> listDocuments = new ArrayList<Document>();
		jpaCustomer.getCustomerDocuments().forEach(
				x -> {
					listDocuments.add(new Document(
							x.getGuid(), customer,
							x.getType(), x.getNumber(),
							x.getDocFiscal(), x.getActive()));
				});
		customer.getDocuments().addAll(listDocuments);

		return customer;
	}

	JPACustomer toJPACustomer(Customer customer);
	
	Customer toCustomer(JPACustomer customer);
}
