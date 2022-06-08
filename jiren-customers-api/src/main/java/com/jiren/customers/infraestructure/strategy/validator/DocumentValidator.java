package com.jiren.customers.infraestructure.strategy.validator;

import java.util.ArrayList;
import java.util.List;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.types.DocumentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.repository.DocumentRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.utils.Helper;

public class DocumentValidator {

	static final int SIZE_MAX_16 = 16;

	public static List<String> ownerDocumentNumber(String documentNumber, List<String> listDocumentNumber) {
        List<String> errors = new ArrayList<>();
        try {
        	if (!listDocumentNumber.contains(documentNumber))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_TYPE_DOCUM, documentNumber);
            
        }catch(Exception e){
            errors.add(e.getMessage());
        }
        return errors;
    }
	
	public static List<String> documentType(String documentType, String ownerDocumentNumber, List<String> listDocument) {
		List<String> errors = new ArrayList<>();
		try {
			String field = "Tipo documento";
			if (Helper.isNullOrEmpty(documentType))
				throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);
			
			DocumentTypeEnumerator.of(documentType);
			
			if (listDocument.contains(ownerDocumentNumber + documentType))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_DOCUMENT_TYPE_MYPE, ownerDocumentNumber, documentType);
            
		}catch(Exception e){
			errors.add(e.getMessage());
		}
		return errors;
	}

	public static List<String> documentNumber(String documentNumber, 
			String documentType, List<String> listDocument, DocumentRepository documentRepository){
    	
        List<String> errors = new ArrayList<>();
        if (Helper.isNullOrEmpty(documentType)) {
            return errors;        	
        }
        DocumentTypeEnumerator type = DocumentTypeEnumerator.of(documentType);        
        switch (type){
            
            case CE:
                errors.addAll(validateCE(documentNumber, listDocument, documentRepository));
                break;
            case DNI:
                errors.addAll(validateDni(documentNumber, listDocument, documentRepository));
                break;
            case RUC:
                errors.addAll(validateRuc(documentNumber, listDocument, documentRepository));
                break;
        }
        return errors;
    }

    private static List<String> validateCE(String ce, List<String> listCe, DocumentRepository documentRepository) {
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
    		if (documentRepository.findByTypeAndNumber(type, ce) != null)
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_ALREADY_EXIST_MYPE, ce);    		
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    	}
    	return errors;
    }

    private static List<String> validateDni(String dni, List<String> listDni, DocumentRepository documentRepository) {
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
            
            if (documentRepository.findByTypeAndNumber(type, dni) != null)
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_ALREADY_EXIST_MYPE, dni);
            
        } catch (Exception e) {
        	System.out.println(e);
            errors.add(e.getMessage());
        }
        return errors;
    }
    
    private static List<String> validateRuc(String ruc, List<String> listRuc, DocumentRepository documentRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Número de RUC";
            if (!Helper.isValidRuc(ruc))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            if (listRuc.contains(ruc))
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_TYPE_DOCUM, ruc);
            if (!Helper.isValidLength(ruc, SIZE_MAX_16))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_FIELD_INVALID, field);
            
            String type = DocumentTypeEnumerator.RUC.getCode();
            if (documentRepository.findByTypeAndNumber(type, ruc) != null)
                throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_ALREADY_EXIST_MYPE, ruc);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }

}
