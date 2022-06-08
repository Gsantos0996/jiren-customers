package com.jiren.customers.infraestructure.strategy.validator;

import java.util.ArrayList;
import java.util.List;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.types.DocumentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.utils.Helper;

public class UserValidator {
	
	static final int SIZE_MAX_16 = 16;
	static final int SIZE_MAX_255 = 255;

	public static List<String> documentTypePerson(String documentType) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Tipo documento";
            if (Helper.isNullOrEmpty(documentType))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
            
            DocumentTypeEnumerator.of(documentType);
            
            if (documentType.equals(DocumentTypeEnumerator.RUC.getCode()))
            	throw new MPlusApiException(CustomerExceptionEnumerator.TYPE_DOCUMENT_INVALID, "RUC");
            
        }catch(Exception e){
            errors.add(e.getMessage());
        }
        return errors;
    }
	
	public static List<String> documentNumberPerson(String documentNumber, 
			String documentType, List<String> listDocumentPerson, UserRepository userRepository){	
		
        List<String> errors = new ArrayList<>();
        if (Helper.isNullOrEmpty(documentType)) {
            return errors;        	
        }
        DocumentTypeEnumerator type = DocumentTypeEnumerator.of(documentType);        
        switch (type){
	        case CE:
	            errors.addAll(validateCE(documentNumber, listDocumentPerson, userRepository));
	            break;
	        case DNI:
	            errors.addAll(validateDni(documentNumber, listDocumentPerson, userRepository));
	            break;
            case RUC:
                errors.addAll(validateRuc(documentNumber));
                break;
        }
        return errors;
    }
    
    private static List<String> validateCE(String ce, List<String> listCe, UserRepository userRepository) {
    	List<String> errors = new ArrayList<>();
    	try {
    		String field = "Número de CE";
    		if (!Helper.isValidCe(ce))
    			throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
    		if (listCe.contains(ce))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_TYPE_DOCUM, ce);
    		if (!Helper.isValidLength(ce, SIZE_MAX_16))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
    		String type = DocumentTypeEnumerator.CE.getCode();

			userRepository.findByTypeDocumentAndNumberDocument(type,ce)
					.ifPresent(s->new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_ALREADY_EXIST_MYPE, ce));
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }

    private static List<String> validateDni(String dni, List<String> listDni, UserRepository userRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Número de DNI";
            if (!Helper.isValidDni(dni))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            if (listDni.contains(dni))
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_TYPE_DOCUM, dni);
            if (!Helper.isValidLength(dni, SIZE_MAX_16))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
            String type = DocumentTypeEnumerator.DNI.getCode();
			userRepository.findByTypeDocumentAndNumberDocument(type,dni)
					.ifPresent(s->new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_ALREADY_EXIST_MYPE, dni));
        } catch (Exception e) {
        	System.out.println(e);
            errors.add(e.getMessage());
        }
        return errors;
    }
    

    private static List<String> validateRuc(String ruc) {
        List<String> errors = new ArrayList<>();
        try {        	
            if (ruc.equals(DocumentTypeEnumerator.RUC.getCode()))
            	throw new MPlusApiException(CustomerExceptionEnumerator.TYPE_DOCUMENT_INVALID, "RUC");
            
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }
    
	public static List<String> name(String name) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Nombres";
            if (Helper.isNullOrEmpty(name))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
            if (!Helper.isValidLength(name, SIZE_MAX_255))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }
	
	public static List<String> firstLastName(String firstLastName) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Apellido Paterno";
			if (Helper.isNullOrEmpty(firstLastName))
				throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
			if (!Helper.isValidLength(firstLastName, SIZE_MAX_255))
				throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
	public static List<String> secondLastName(String secondLastname) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Apellido Materno";
			if (!Helper.isNullOrEmpty(secondLastname)) {
				if (!Helper.isValidLength(secondLastname, SIZE_MAX_255))
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}

	public static List<String> phone(String phone, List<String> listPhone, UserRepository userRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Teléfono";
        	if (Helper.isNullOrEmpty(phone))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
        	if (listPhone.contains(phone))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_PHONE_MYPE, phone);
            if (!Helper.isValidLength(phone, SIZE_MAX_255))
				throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
            if (userRepository.findByPhone(phone) != null)
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_PHONE, phone);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }	
	
	public static List<String> email(String email, List<String> listEmail, UserRepository userRepository) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Correo";
			if (!Helper.isNullOrEmpty(email)) {
				if (!Helper.isValidEmail(email))
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
				if (listEmail.contains(email))
	    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_EMAIL_MYPE, email);
				if (!Helper.isValidLength(email, SIZE_MAX_255))
					throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
				
				if (userRepository.findByEmail(email) != null)
	                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_USER_EMAIL, email);
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		return errors;
	}
	
}
