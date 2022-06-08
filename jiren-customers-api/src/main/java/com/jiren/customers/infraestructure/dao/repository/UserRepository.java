package com.jiren.customers.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jiren.customers.infraestructure.dao.jpa.JPAUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<JPAUser, String>{
	
	@Modifying(clearAutomatically = false)
	@Query("update JPAUser u set u.active = false where u.customer.guid = :idCustomer")
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
	
	JPAUser findByGuid(String guid);
	
	Optional<JPAUser> findByTypeDocumentAndNumberDocument(String typeDocument, String numberDocument);

	Optional<JPAUser> findByTypeDocumentAndNumberDocumentAndCustomer_GuidNot(String typeDocument, String numberDocument,String idCustomer);

	JPAUser findByEmailAndCustomer_GuidNot(String email,String idCustomer);

	JPAUser findByPhoneAndCustomer_GuidNot(String phone,String idCustomer);

	JPAUser findByEmail(String email);
	
	JPAUser findByPhone(String phone);

	@Query(value = "select u.* from customers.user u,(select number_document,max(date_created) as maxDate " +
			"from customers.user where customer_guid=:idCustomer " +
			"group by number_document) doc " +
			"where u.number_document=doc.number_document " +
			"and u.date_created=doc.maxDate",nativeQuery = true)
	List<JPAUser> findByCustomer_Guid(@Param("idCustomer")  String customerGuid);
	
}
