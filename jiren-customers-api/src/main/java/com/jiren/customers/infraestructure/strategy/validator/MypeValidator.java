package com.jiren.customers.infraestructure.strategy.validator;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.types.CurrencyEnumerator;
import com.jiren.customers.domain.model.types.SegmentEnumerator;
import com.jiren.customers.domain.model.types.StatusEnumerator;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.models.types.SubWorkAreaEnumerator;
import com.jiren.shared.models.types.WorkAreaEnumerator;
import com.jiren.shared.utils.Helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MypeValidator {

	static final int SIZE_MAX_3 = 3;
	static final int SIZE_MAX_6 = 6;
	static final int SIZE_MAX_25 = 25;
	static final int SIZE_MAX_22 = 22;
	static final int SIZE_MAX_50 = 50;
	static final int SIZE_MAX_255 = 255;
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String TRUE = "VERDADERO";
	static final String FALSE = "FALSO";
	
    public static List<String> businessName(String businessName, List<String> listBusiness, CustomerRepository customerRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Razón Social";
            if (Helper.isNullOrEmpty(businessName))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
            if (listBusiness.contains(businessName))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_BUSINESS_NAME_MYPE, businessName);
            if (!Helper.isValidLength(businessName, SIZE_MAX_255))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
            if (customerRepository.findByBusinessName(businessName) != null)
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_CUSTOMER_BUSINESS_NAME, businessName);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }
    
    public static List<String> tradeName(String tradeName) {
    	List<String> errors = new ArrayList<>();
    	try {
    		String field = "Nombre Comercial";
    		if (!Helper.isNullOrEmpty(tradeName)) {
	    		if (!Helper.isValidLength(tradeName, SIZE_MAX_255))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
    		}
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }
    
    public static List<String> dexCode(String dexCode) {
    	List<String> errors = new ArrayList<>();
    	try {
    		String field = "Código DEX";
    		if (!Helper.isNullOrEmpty(dexCode)) {
	    		if (!Helper.isValidLength(dexCode, SIZE_MAX_6))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
    		}
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }
    
    public static List<String> workArea(String workArea) {
    	List<String> errors = new ArrayList<>();
    	try {
    		if (!Helper.isNullOrEmpty(workArea)) {
    			WorkAreaEnumerator.of(workArea);
    		}
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }

    public static List<String> subWorkArea(String subWorkArea) {
    	List<String> errors = new ArrayList<>();
    	try {
    		if (!Helper.isNullOrEmpty(subWorkArea)) {
    			SubWorkAreaEnumerator.of(subWorkArea);
    		}
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }
    
	public static List<String> adviser(String adviser) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Vendedor asignado";
			if (!Helper.isNullOrEmpty(adviser)) {
				if (!Helper.isValidLength(adviser, SIZE_MAX_255))
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> channelOrigin(String channelOrigin) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Canal de origen";
			if (!Helper.isNullOrEmpty(channelOrigin)) {
				if (!Helper.isValidLength(channelOrigin, SIZE_MAX_25))
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> registryDate(String registryDate) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Fecha empadronamiento";
			if (!Helper.isNullOrEmpty(registryDate)) {
				if(!Helper.isValidDate(registryDate, DATE_PATTERN)){
	                throw new MPlusApiException(CustomerExceptionEnumerator.FORMAT_DATE_INVALID, field);
	            }
			}
		} catch (Exception e) {
            errors.add(e.getMessage());
        }
		return errors;
	}	
	
	public static List<String> dischargeDate(String dischageDate) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Fecha de alta";
			if (!Helper.isNullOrEmpty(dischageDate)) {
				if(!Helper.isValidDate(dischageDate, DATE_PATTERN)){
	                throw new MPlusApiException(CustomerExceptionEnumerator.FORMAT_DATE_INVALID, field);
	            }
			}
		} catch (Exception e) {
            errors.add(e.getMessage());
        }
		return errors;
	}
	
	public static List<String> wave(String wave) {
        List<String> errors = new ArrayList<>();
        try{
        	if (!Helper.isNullOrEmpty(wave)) {
        		Integer.parseInt(wave);
            }
        } catch (Exception e){
        	errors.add("El campo Ola es inválido");
        }
        return errors;
    }

    public static List<String> segment(String segment) {
        List<String> errors = new ArrayList<>();
		try {
			if (!Helper.isNullOrEmpty(segment)) {
				SegmentEnumerator.of(segment);
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
        return errors;
    }
    
    public static List<String> status(String status) {
    	List<String> errors = new ArrayList<>();
    	try {
    		if (!Helper.isNullOrEmpty(status)) {
    			StatusEnumerator.byName(status);
    		}
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }
    
    public static List<String> minAmount(String minAmount) {
    	List<String> errors = new ArrayList<>();
    	String field = "Compra mínima";
    	try {
    		if (!Helper.isNullOrEmpty(minAmount)) {
	    		if(!Helper.isValidLength(minAmount, SIZE_MAX_22))
	                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
	    		
	    		BigDecimal.valueOf(Double.parseDouble(minAmount));
    		}
    	} catch (NumberFormatException e){
            errors.add("El campo Compra mínima es inválido");
    	} catch (Exception e) {
    		errors.add(e.getMessage());
        }
    	return errors;
    }
    
    public static List<String> electronicInvoice(String electronicInvoice) {
        List<String> errors = new ArrayList<>();
        String field = "Emisión B Electrónica";
        try{
        	if (!Helper.isNullOrEmpty(electronicInvoice)) {
	            if ( !(electronicInvoice.equals(TRUE) || electronicInvoice.equals(FALSE)) )
	                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
        	}
        } catch (Exception e){
            errors.add(e.getMessage());
        }
        return errors;
    }
    
    public static List<String> currency(String currency) {
    	List<String> errors = new ArrayList<>();
    	try{            
    		if (!Helper.isNullOrEmpty(currency)) {
    			CurrencyEnumerator.of(currency);
        	}
    	} catch (Exception e){
    		errors.add(e.getMessage());
    	}
    	return errors;
    }

}
