package com.jiren.ubigeo.infraestructure.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jiren.ubigeo.domain.model.UbigeoCustom;
import com.jiren.ubigeo.infraestructure.dao.jpa.JpaUbigeo;

public interface UbigeosRepository extends JpaRepository<JpaUbigeo, String> {

	@Query(value = 
			"SELECT DISTINCT SUBSTRING(guid,1,2) AS code, " +
				"	department AS name" +
				" FROM customers.ubigeo " +
				" ORDER BY 2", nativeQuery = true)
    List<UbigeoCustom> findAllDepartments();

	@Query(value = 
			"SELECT DISTINCT SUBSTRING(guid,3,2) AS code, " +
				"	province AS name" +
				" FROM customers.ubigeo " +
				" 	WHERE SUBSTRING(guid,1,2) = :department" +
				" ORDER BY 2", nativeQuery = true)
	List<UbigeoCustom> findProvincesByDepartment(@Param("department") String department);
	
	
	@Query(value = 
			"SELECT DISTINCT SUBSTRING(guid,5,2) AS code, " +
				"	district AS name" +
				" FROM customers.ubigeo " +
				" 	WHERE SUBSTRING(guid,1,2) = :department" +
				" 	  AND SUBSTRING(guid,3,2) = :province" +
				" ORDER BY 2", nativeQuery = true)
	List<UbigeoCustom> findDistricsByProvince(@Param("department") String department, @Param("province") String province);
	
}
