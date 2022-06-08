package com.jiren.customers.infraestructure.strategy.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.types.ZoneEnumerator;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.UbigeoRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.utils.Helper;

public class AddressValidator {

	static final int SIZE_MAX_255 = 255;
	static final int SIZE_MAX_6 = 6;
	static final int SIZE_MAX_5 = 5;

	public static List<String> address(String address, List<String> listAdresses, BusinessRepository businessRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Dirección";
            if (Helper.isNullOrEmpty(address))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
            
            if (listAdresses.contains(address))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_ADDRESS_MYPE, address);
            
            if (!Helper.isValidLength(address, SIZE_MAX_255))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
            if (businessRepository.findByAddress(address) != null)
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_BUSINESS_ADDRESS, address);
        }catch(Exception e){
            errors.add(e.getMessage());
        }
        return errors;
    }
    
	public static List<String> reference(String reference) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Referencia";
    		if (!Helper.isNullOrEmpty(reference)) {
	    		if (!Helper.isValidLength(reference, SIZE_MAX_255))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
    		}
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }
	
	public static List<String> latitude(String latitude) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Latitud";
			if (Helper.isNullOrEmpty(latitude))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
			
			BigDecimal.valueOf(Double.parseDouble(latitude));
			
		} catch (NumberFormatException e){
            errors.add("El campo Latitud es inválido");
    	} catch (Exception e) {
    		errors.add(e.getMessage());
        }
		return errors;
	}
	
	
	public static List<String> longitude(String longitude) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Longitud";
			if (Helper.isNullOrEmpty(longitude))
				throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
			
			BigDecimal.valueOf(Double.parseDouble(longitude));
			
		} catch (NumberFormatException e){
			errors.add("El campo Longitud es inválido");
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> zone(String zone) {
		List<String> errors = new ArrayList<>();
		try {
			if (!Helper.isNullOrEmpty(zone)) {
				ZoneEnumerator.byName(zone);
        	}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}

	public static List<String> startTimeWindow1(String startTimeWindow1) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Ventana Horaria Inicio 1";
        	if (!Helper.isNullOrEmpty(startTimeWindow1)) {
	        	if (!Helper.isValidTime(startTimeWindow1)) {
	        		throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
	        	}
        	}
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }	
	
	public static List<String> endTimeWindow1(String endTimeWindow1, String startTimeWindow1) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Ventana Horaria Fin 1";
        	if (!Helper.isNullOrEmpty(endTimeWindow1)) {        		
	        	if (!Helper.isValidTime(endTimeWindow1))
	        		throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);	        	
        	}
        	if (Helper.isNullOrEmpty(startTimeWindow1) && !Helper.isNullOrEmpty(endTimeWindow1))
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_MANDATORY_TIME, "Inicio 1");
        	
        	if (Helper.isNullOrEmpty(endTimeWindow1) && !Helper.isNullOrEmpty(startTimeWindow1))
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_MANDATORY_TIME, "Fin 1");
        	
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> startTimeWindow2(String startTimeWindow2) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Ventana Horaria Inicio 2";
        	if (!Helper.isNullOrEmpty(startTimeWindow2)) {
	        	if (!Helper.isValidTime(startTimeWindow2)) {
	        		throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
	        	}
        	}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}	
	
	public static List<String> endTimeWindow2(String endTimeWindow2, String startTimeWindow2) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Ventana Horaria Fin 2";
        	if (!Helper.isNullOrEmpty(endTimeWindow2)) {        		
	        	if (!Helper.isValidTime(endTimeWindow2)) 
	        		throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);	        	
        	}
        	if (Helper.isNullOrEmpty(startTimeWindow2) && !Helper.isNullOrEmpty(endTimeWindow2))
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_MANDATORY_TIME, "Inicio 2");
        	
        	if (Helper.isNullOrEmpty(endTimeWindow2) && !Helper.isNullOrEmpty(startTimeWindow2))
        		throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_MANDATORY_TIME, "Fin 2");
        	
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> codePostal(String codePostal) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Código postal";
			if (!Helper.isNullOrEmpty(codePostal)) {
				if(codePostal.length() != SIZE_MAX_5){
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
                }				
        		Integer.parseInt(codePostal);
            }
		} catch (NumberFormatException e){
            errors.add("El campo Código postal es inválido");
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> codeUbigeo(String codeUbigeo, UbigeoRepository ubigeoRepository) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Ubigeo";
            if (Helper.isNullOrEmpty(codeUbigeo))
            	throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
            
            if(!Helper.isValidLength(codeUbigeo, SIZE_MAX_6))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
    		
            if (ubigeoRepository.findByGuid(codeUbigeo) == null) 
            	throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_UBIGEO, codeUbigeo);
            
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
}
