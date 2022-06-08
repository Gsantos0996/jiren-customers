package com.jiren.customers.infraestructure.dao.plsql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jiren.customers.domain.dao.CustomerDAO;
import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.infraestructure.dao.jpa.JPABusiness;
import com.jiren.customers.infraestructure.dao.jpa.JPACustomer;
import com.jiren.customers.infraestructure.dao.jpa.JPACustomerPaymentType;
import com.jiren.customers.infraestructure.dao.jpa.JPADocument;
import com.jiren.customers.infraestructure.dao.jpa.JPAPaymentType;
import com.jiren.customers.infraestructure.dao.jpa.JPASchedule;
import com.jiren.customers.infraestructure.dao.jpa.JPATimeWindow;
import com.jiren.customers.infraestructure.dao.jpa.JPAUbigeo;
import com.jiren.customers.infraestructure.dao.jpa.JPAUser;
import com.jiren.customers.infraestructure.dao.mapper.CustomerMapper;
import com.jiren.customers.infraestructure.dao.mapper.UbigeoMapper;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerPaymentTypeRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.customers.infraestructure.dao.repository.DocumentRepository;
import com.jiren.customers.infraestructure.dao.repository.ScheduleRepository;
import com.jiren.customers.infraestructure.dao.repository.TimeWindowRepository;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.shared.guid.GUIDGenerator;
import com.jiren.shared.utils.Helper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PSQLCustomerDAO implements CustomerDAO{
	
	private final GUIDGenerator guidGenerator;
	
	private final CustomerRepository customerRepository;
	private final BusinessRepository businessRepository;
	private final ScheduleRepository scheduleRepository;
	private final TimeWindowRepository timeWindowRepository;
	private final CustomerPaymentTypeRepository customerPaymentTypeRepository;
	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	
	private final CustomerMapper customerMapper;
	private final UbigeoMapper ubigeoMapper;
	
	private final boolean ACTIVE = true;	


	@Override
	public Customer getCustomer(String guid) {
		return customerMapper.toCustomerWithRelations(customerRepository.findByGuid(guid));
	}

	@Override
	public Customer getProfileCustomer(String guid) {
		return customerMapper.toCustomerProfile(customerRepository.findByGuid(guid));
	}

	@Override
	public Page<Customer> getPageCustomers(CustomerFilter filter, Pageable pageable) {
		Specification<JPACustomer> specification = getSpecification(filter);
		return customerRepository.findAll(specification, pageable)
				.map(customerMapper::toCustomer);
	}	

	private Specification<JPACustomer> getSpecification(CustomerFilter filter) {
		return (root, query, builder) -> {
			query.distinct(true);
			
			List<Predicate> p = new ArrayList<>();
			
            if (!Helper.isNullOrEmpty(filter.getQuery())) {
            	Join<JPACustomer, JPADocument> jpaDocumentJoin = root.join("customerDocuments", JoinType.INNER);
            	p.add(builder.equal(jpaDocumentJoin.get("active"), true));
            	p.add(builder.or(
            			builder.like(jpaDocumentJoin.get("number"), "%" + filter.getQuery().toUpperCase() + "%"),
            			builder.like(builder.upper(root.get("businessName")), "%" + filter.getQuery().toUpperCase() + "%"),
            			builder.like(builder.upper(root.get("tradeName")), "%" + filter.getQuery().toUpperCase() + "%")
            		));
            }
			if (filter.getActive() != null) {
				p.add(builder.equal(root.get("active"), filter.getActive()));
			}            
			if (filter.getWorkArea() != null) {
				p.add(builder.equal(root.get("workArea"), filter.getWorkArea()));
			}
			if (filter.getSubWorkArea() != null) {
				p.add(builder.equal(root.get("subWorkArea"), filter.getSubWorkArea()));
			}
			if (filter.getSegment() != null) {
				p.add(builder.equal(root.get("segment"), filter.getSegment()));
			}
			if (filter.getState() != null) {
				p.add(builder.equal(root.get("state"), filter.getState()));
			}
			if (filter.getWave() != null) {
				p.add(builder.equal(root.get("wave"), filter.getWave()));
			}
			if (filter.getStartRegistryDate() != null){
                p.add(builder.greaterThanOrEqualTo(root.get("registryDate"), filter.getStartRegistryDate()));
            }
            if (filter.getEndRegistryDate() != null){
                p.add(builder.lessThanOrEqualTo(root.get("registryDate"), filter.getEndRegistryDate()));
            }
            if (filter.getStartDischargeDate() != null){
            	p.add(builder.greaterThanOrEqualTo(root.get("dischargeDate"), filter.getStartDischargeDate()));
            }
            if (filter.getEndDischargeDate() != null){
            	p.add(builder.lessThanOrEqualTo(root.get("dischargeDate"), filter.getEndDischargeDate()));
            }
            if (!Helper.isNullOrEmpty(filter.getDepartment()) 
            		|| !Helper.isNullOrEmpty(filter.getProvince()) 
            		|| !Helper.isNullOrEmpty(filter.getDistrict()) ) {
            	
            	Join<JPACustomer, JPABusiness> jpaBusinessJoin = root.join("customerBusiness", JoinType.INNER);
            	Join<JPABusiness, JPAUbigeo> jpaUbigeo = jpaBusinessJoin.join("ubigeo", JoinType.INNER);
            	
            	p.add(builder.equal(jpaBusinessJoin.get("active"), true));
            	
            	if (!Helper.isNullOrEmpty(filter.getDepartment())) {
            		p.add(builder.equal(builder.substring(jpaUbigeo.get("guid"), 1, 2), filter.getDepartment()));
            	}
            	if (!Helper.isNullOrEmpty(filter.getProvince())) {
            		p.add(builder.equal(builder.substring(jpaUbigeo.get("guid"), 3, 2), filter.getProvince()));
            	}
            	if (!Helper.isNullOrEmpty(filter.getDistrict())) {
            		p.add(builder.equal(builder.substring(jpaUbigeo.get("guid"), 5, 2), filter.getDistrict()));
            	}
            }
			return builder.and(p.toArray(new Predicate[p.size()]));
		};
	}
	
	/*
	 * Método para crear un customer y creación de registros en tablas relacionadas
	 */
	@Override
	public String create(Customer customer) {		
		JPACustomer jpaCustomer = customerRepository.save(customerMapper.toJPACustomer(customer));
		createRelationsToCustomer(customer, jpaCustomer);
		return jpaCustomer.getGuid();
	}

	private void createRelationsToCustomer(Customer customer, JPACustomer jpaCustomer) {	
		createBusiness(customer, jpaCustomer);	
		createPaymentTypes(customer, jpaCustomer);
		createDocuments(customer, jpaCustomer);
		createUsers(customer, jpaCustomer);
	}

	private void createBusiness(Customer customer, JPACustomer jpaCustomer) {
		customer.getBusiness().forEach(
				x -> {
					JPABusiness jpaBusiness = businessRepository.save(
						new JPABusiness(
								guidGenerator.generateAlphaID(), 
								jpaCustomer, 
								ubigeoMapper.toJPAUbigeo(x.getUbigeo()),
								x.getDeliveryDistance(), 
								x.getDeliveryCost(),
								x.getAddress(), x.getPostalCode(),
								x.getReference(), x.getLatitude(), 
								x.getLongitude(), x.getZone(), 
								x.getAnnex(), x.getActive(),
								x.getUserCreated(), x.getDateCreated(), 
								x.getUserUpdated(), x.getDateUpdated()) );
					
					if (x.getSchedules() != null) {
						x.getSchedules().forEach(
							y -> scheduleRepository.save(
									new JPASchedule(
											guidGenerator.generateAlphaID(), 
											jpaBusiness, 
											y.getDay(), y.getStartTime(), 
											y.getEndTime(), y.getAttention(), y.getActive()) )
							);
					}
					
					if (x.getTimeWindows() != null) {
						x.getTimeWindows().forEach(
							z -> timeWindowRepository.save(
									new JPATimeWindow(
											guidGenerator.generateAlphaID(),
											jpaBusiness, 
											z.getStartTime(), 
											z.getEndTime(), z.getActive()) )
							);
					}
				});
	}

	private void createPaymentTypes(Customer customer, JPACustomer jpaCustomer) {
		customer.getSellIns().forEach(
				x -> customerPaymentTypeRepository.save(
						new JPACustomerPaymentType(
								guidGenerator.generateAlphaID(), 
								jpaCustomer, 
								new JPAPaymentType(x.getPaymentType().getGuid()), 
								x.getType(), x.getActive()) ) 
				);
		customer.getSellOuts().forEach(
				x -> customerPaymentTypeRepository.save(
						new JPACustomerPaymentType(
								guidGenerator.generateAlphaID(), 
								jpaCustomer, 
								new JPAPaymentType(x.getPaymentType().getGuid()), 
								x.getType(), x.getActive()) ) 
				);
	}

	private void createDocuments(Customer customer, JPACustomer jpaCustomer) {
		customer.getDocuments().forEach(
				x -> documentRepository.save(
						new JPADocument(
								guidGenerator.generateAlphaID(), 
								jpaCustomer, 
								x.getType(), x.getNumber(), 
								x.getDocFiscal(), x.getActive()) )
				);
	}

	private void createUsers(Customer customer, JPACustomer jpaCustomer) {
		customer.getUsers().forEach(
				x -> userRepository.save(
						new JPAUser(
								guidGenerator.generateAlphaID(), 
								jpaCustomer, 
								x.getTypeDocument(), 
								x.getNumberDocument(), x.getName(), 
								x.getFirstLastName(), x.getSecondLastName(), 
								x.getEmail(), x.getPhone(), 
								x.getOwner(), x.getActive(), 
								x.getUserCreated(), x.getDateCreated(), 
								x.getUserUpdated(), x.getDateUpdated()) )
				);
	}

	
	/*
	 * Método para la actualización de un customer y se puede crear/actualizar de registros en tablas relacionadas
	 */
	@Override
	public void update(Customer customer) {
		JPACustomer jpaCustomer = customerRepository.save(customerMapper.toJPACustomer(customer));		
		deleteLogicRelationsToCustomer(customer);		
		saveRelationsToCustomer(customer, jpaCustomer);
	}

	@Override
	public List<Customer> getCustomers(List<String> guids) {
		return customerRepository.findByGuidIn(guids)
				.stream()
				.map(customerMapper::toCustomerWithRelations)
				.collect(Collectors.toList());
	}

	private void saveRelationsToCustomer(Customer customer, JPACustomer jpaCustomer) {
		if (customer.getBusiness() != null)
			saveBusiness(customer, jpaCustomer);
		if (customer.getSellIns() != null)
			saveSellIns(customer, jpaCustomer);
		if (customer.getSellOuts() != null)
			saveSellOuts(customer, jpaCustomer);
		if (customer.getDocuments() != null)
			saveDocuments(customer, jpaCustomer);
		if (customer.getUsers() != null)
			saveUsers(customer, jpaCustomer);
	}

	private void deleteLogicRelationsToCustomer(Customer customer) {
		businessRepository.inactiveBycustomerGuid(customer.getGuid());
		scheduleRepository.inactiveBycustomerGuid(customer.getGuid());
		timeWindowRepository.inactiveBycustomerGuid(customer.getGuid());
		customerPaymentTypeRepository.inactiveBycustomerGuid(customer.getGuid());
		documentRepository.inactiveBycustomerGuid(customer.getGuid());
		userRepository.inactiveBycustomerGuid(customer.getGuid());
	}
	
	private void saveBusiness(Customer customer, JPACustomer jpaCustomer) {		
		customer.getBusiness().forEach(
				x -> {												
					JPABusiness jpaBusiness = new JPABusiness(
							x.getGuid() == null ? guidGenerator.generateAlphaID() : x.getGuid(), 
							jpaCustomer, 
							ubigeoMapper.toJPAUbigeo(x.getUbigeo()),
							x.getDeliveryDistance(), 
							x.getDeliveryCost(),  
							x.getAddress(), x.getPostalCode(),
							x.getReference(), x.getLatitude(), 
							x.getLongitude(), x.getZone(), 
							x.getAnnex(), x.getActive(),
							x.getUserCreated(), 
							x.getDateCreated(), 
							x.getUserUpdated(), 
							x.getDateUpdated() );						
					businessRepository.save(jpaBusiness);
					
					if (x.getSchedules() != null) {
						x.getSchedules().forEach(
							y -> {
								JPASchedule jpaSchedule = new JPASchedule( 
										y.getGuid() == null ? guidGenerator.generateAlphaID() : y.getGuid(), 
										jpaBusiness, 
										y.getDay(), y.getStartTime(), 
										y.getEndTime(), y.getAttention(), y.getActive() );
								scheduleRepository.save(jpaSchedule);
							});
					}
					
					if (x.getTimeWindows() != null) {
						x.getTimeWindows().forEach(
							y -> {
								JPATimeWindow jpaTimeWindow = new JPATimeWindow( 
										y.getGuid() == null ? guidGenerator.generateAlphaID() : y.getGuid(), 
										jpaBusiness,
										y.getStartTime(), 
										y.getEndTime(), y.getActive() );
								timeWindowRepository.save(jpaTimeWindow);
							});
					}
				});
	}

	private void saveSellIns(Customer customer, JPACustomer jpaCustomer) {
		customer.getSellIns().forEach(
				x -> {
					JPACustomerPaymentType jpaCustomerPaymentType = customerPaymentTypeRepository
							.findByCustomerGuidAndPaymentTypeGuidAndType(jpaCustomer.getGuid(), 
									x.getPaymentType().getGuid(), x.getType());
					
					if (jpaCustomerPaymentType != null) {
						jpaCustomerPaymentType.setActive(ACTIVE);
					}else {
						jpaCustomerPaymentType = new JPACustomerPaymentType(
								x.getGuid() == null ? guidGenerator.generateAlphaID() : x.getGuid(), 
								jpaCustomer, 
								new JPAPaymentType(x.getPaymentType().getGuid()), 
								x.getType(), x.getActive());
					}
					customerPaymentTypeRepository.save(jpaCustomerPaymentType);					
				});
	}
	
	private void saveSellOuts(Customer customer, JPACustomer jpaCustomer) {
		customer.getSellOuts().forEach(
				x -> {
					JPACustomerPaymentType jpaCustomerPaymentType = customerPaymentTypeRepository
							.findByCustomerGuidAndPaymentTypeGuidAndType(jpaCustomer.getGuid(), 
									x.getPaymentType().getGuid(), x.getType());
					
					if (jpaCustomerPaymentType != null) {
						jpaCustomerPaymentType.setActive(ACTIVE);
					}else {
						jpaCustomerPaymentType = new JPACustomerPaymentType(
								x.getGuid() == null ? guidGenerator.generateAlphaID() : x.getGuid(), 
								jpaCustomer, 
								new JPAPaymentType(x.getPaymentType().getGuid()), 
								x.getType(), x.getActive());
					}
					customerPaymentTypeRepository.save(jpaCustomerPaymentType);					
				});
	}
	
	private void saveDocuments(Customer customer, JPACustomer jpaCustomer) {
		List<JPADocument> list = new ArrayList<JPADocument>();
		customer.getDocuments().forEach(
				x -> {
					list.add(
							new JPADocument(
									x.getGuid() == null ? guidGenerator.generateAlphaID() : x.getGuid(), 
									jpaCustomer, 
									x.getType(), x.getNumber(), 
									x.getDocFiscal(), x.getActive() ) 
							);					
				});		
		documentRepository.saveAll(list);		
	}
	
	private void saveUsers(Customer customer, JPACustomer jpaCustomer) {
		List<JPAUser> list = new ArrayList<JPAUser>();
		customer.getUsers().forEach(
				x -> {
					list.add(
							new JPAUser(
									x.getGuid() == null ? guidGenerator.generateAlphaID() : x.getGuid(), 
									jpaCustomer, 
									x.getTypeDocument(), 
									x.getNumberDocument(), x.getName(), 
									x.getFirstLastName(), x.getSecondLastName(), 
									x.getEmail(), x.getPhone(), 
									x.getOwner(), x.getActive(), 
									x.getUserCreated(), 
									x.getDateCreated(), 
									x.getUserUpdated(), 
									x.getDateUpdated() ) 
							);					
				});		
		userRepository.saveAll(list);		
	}

}
