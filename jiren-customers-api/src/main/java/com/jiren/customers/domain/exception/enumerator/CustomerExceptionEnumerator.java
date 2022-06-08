package com.jiren.customers.domain.exception.enumerator;

import org.springframework.http.HttpStatus;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("jiren-customers-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
	DOCUMENT_FILTER_INVALID("customer-400-1", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("DOCUMENT_FILTER_INVALID", "El filtro para obtener documentos no es permitido")),
	API_KEY_NOT_FOUND("customer-401", HttpStatus.UNAUTHORIZED, new MPlusErrorResponse("API_KEY_NOT_FOUND", "No se encontró API en la llamada")),

	NOT_FOUND_CUSTOMER("customer-404", HttpStatus.NOT_FOUND, new MPlusErrorResponse("NOT_FOUND_CUSTOMER", "No existe el cliente %s")),
    NOT_FOUND_ID_CUSTOMER("customer-404-1", HttpStatus.NOT_FOUND, new MPlusErrorResponse("NOT_FOUND_ID_CUSTOMER", "Debe ingresar el id cliente")),
	NOT_FOUND_BUSINESS("customer-412-2", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_BUSINESS", "Debe existir al menos una dirección")),
	NOT_FOUND_LIST_SELL_IN("customer-412-3", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_LIST_SELL_IN", "Debe existir al menos un medio de pago sell in")),
	NOT_FOUND_LIST_SELL_OUT("customer-412-4", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_LIST_SELL_OUT", "Debe existir al menos un medio de pago sell out")),
	NOT_FOUND_DOCUMENT("customer-412-5", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_DOCUMENT", "Debe existir al menos un documento")),
	NOT_FOUND_USER("customer-412-6", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_USER", "Debe existir al menos un usuario")),
	
	FILE_INVALID("customer-412-7", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("FILE_INVALID", "El archivo no es válido")),
	SHEET_MYPE_INVALID("customer-412-8", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_MYPE_INVALID", "La hoja MYPES no es válida")),
	SHEET_DOCUMENT_INVALID("customer-412-9", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_DOCUMENT_INVALID", "La hoja Documentos no es válida")),
	SHEET_USER_INVALID("customer-412-10", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_USER_INVALID", "La hoja Usuarios no es válida")),
	SHEET_ADDRESS_INVALID("customer-412-11", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_ADDRESS_INVALID", "La hoja Direcciones no es válida")),
	SHEET_SELL_IN_INVALID("customer-412-12", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_SELL_IN_INVALID", "La hoja Medios de Pago(Sell In) no es válida")),
	SHEET_SELL_OUT_INVALID("customer-412-13", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("SHEET_SELL_OUT_INVALID", "La hoja Medios de Pago(Sell Out) no es válida")),
	EXTENSION_EXCEL_INVALID("customer-412-14", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("EXTENSION_EXCEL_INVALID", "La extensión del archivo debe ser .XLSX")),
	
	BAD_REQUEST_MANDATORY_FIELD("customer-412-15", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("MANDATORY_FIELD_BAD_REQUEST", "El campo %s no puede ser nulo o vacío")),
	NOT_FOUND_DOCUMENT_TYPE("customer-412-16", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("DOCUMENT_NOT_FOUND_TYPE", "No existe el tipo de documento %s")),
	BAD_REQUEST_FIELD_INVALID("customer-412-17", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("BAD_REQUEST_FIELD_INVALID", "El campo %s es inválido")),
	CONFLICT_DUPLICATED_TYPE_DOCUM("customer-412-18", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_TYPE_DOCUM_TYPE", "Número de documento %s duplicado")),
	CONFLICT_ALREADY_EXIST_MYPE("customer-412-19", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_ALREADY_EXIST_MYPE", "Ya existe una mype registrada con el documento %s")),
	NOT_FOUND_WORK_AREA("customer-412-20", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_WORK_AREA", "No existe un rubro con el código %s")),
	CONFLICT_DUPLICATED_CREDIT("customer-412-21", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_CREDIT", "No se puede cargar el tipo de pago %s porque ya se tiene un tipo de pago a crédito registrado en la MYPE %s")),
	NOT_FOUND_PAYMENT_TYPE("customer-412-22", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_PAYMENT_TYPE", "No existe un medio de pago con el código %s")),
	NOT_FOUND_SEGMENT("customer-412-23", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_SEGMENT", "No existe el segmento %s")),
	NOT_FOUND_STATUS("customer-412-24", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_STATUS", "No existe el estado %s")),
	CONFLICT_DUPLICATED_USER("customer-412-25", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_USER", "El usuario %s ya se encuentra registrado")),
	CONFLICT_DUPLICATED_FIELD("customer-412-26", HttpStatus.CONFLICT, new MPlusErrorResponse("CONFLICT_DUPLICATED_FIELD", "El campo %s ya se encuentra registrado")),
	NOT_FOUND_CURRENCY("customer-412-27", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_CURRENCY", "No existe la moneda %s")),
	CONFLICT_DUPLICATED_PHONE_MYPE("customer-412-28", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_PHONE_MYPE", "Mype con telefono %s duplicado")),
	CONFLICT_DUPLICATED_EMAIL_MYPE("customer-412-29", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_EMAIL_MYPE", "Mype con email %s duplicado")),
	FORMAT_DATE_INVALID("customer-412-30", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("FORMAT_DATE_INVALID", "El formato de %s debe ser dd/MM/yyyy")),
	TYPE_DOCUMENT_INVALID("customer-412-31", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("TYPE_DOCUMENT_INVALID", "El tipo de documento %s no es permitido")),
	NOT_FOUND_ZONE("customer-412-32", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_ZONE", "No existe Cono/Zona con el código %s")),
	NOT_FOUND_UBIGEO("customer-412-33", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_UBIGEO", "No existe ubigeo con el código de %s")),
	CONFLICT_DUPLICATED_ADDRESS_MYPE("customer-412-34", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_ADDRESS_MYPE", "Mype con dirección %s duplicada")),
	CONFLICT_DUPLICATED_PAYMENT_TYPE_MYPE("customer-412-36", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_PAYMENT_TYPE_MYPE", "Mype del %s con medio de pago %s duplicado")),
	CONFLICT_DUPLICATED_DOCUMENT_TYPE_MYPE("customer-412-37", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_PAYMENT_TYPE_MYPE", "Mype del %s no puede tener más de un %s")),
	CONFLICT_MANDATORY_TIME("customer-412-38", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_MANDATORY_TIME", "Se requiere ingresar la Ventana Horaria %s")),
	CONFLICT_DUPLICATED_DOCUMENT("customer-412-39", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_DOCUMENT", "El documento %s ya se encuentra registrado")),
	CONFLICT_DUPLICATED_LIST_USER_MYPE("customer-412-40", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_LIST_USER_MYPE", "El usuario con %s se encuentra repetido")),
	CONFLICT_DUPLICATED_LIST_ADDRESS_MYPE("customer-412-41", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_LIST_ADDRESS_MYPE", "La dirección %s se encuentra repetido")),
	CONFLICT_DUPLICATED_LIST_PHONE_MYPE("customer-412-42", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_USER_PHONE", "El teléfono %s se encuentra repetido")),
	CONFLICT_DUPLICATED_LIST_EMAIL_MYPE("customer-412-43", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_LIST_EMAIL_MYPE", "El email %s se encuentra repetido")),
	CONFLICT_DUPLICATED_LIST_DOCUMENT_MYPE("customer-412-44", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_LIST_DOCUMENT_MYPE", "El número de documento %s se encuentra repetido")),
	CONFLICT_DUPLICATED_BUSINESS_NAME_MYPE("customer-412-45", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_BUSINESS_NAME_MYPE", "Mype con razón social %s duplicado")),
	CONFLICT_DUPLICATED_CUSTOMER_BUSINESS_NAME("customer-412-46", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_CUSTOMER_BUSINESS_NAME", "La razón social %s ya se encuentra registrada")),
	CONFLICT_DUPLICATED_BUSINESS_ADDRESS("customer-412-47", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_BUSINESS_ADDRESS", "La dirección %s ya se encuentra registrada")),
	CONFLICT_DUPLICATED_USER_PHONE("customer-412-48", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_USER_PHONE", "El teléfono %s ya se encuentra registrado")),
	CONFLICT_DUPLICATED_USER_EMAIL("customer-412-49", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_DUPLICATED_USER_EMAIL", "El email %s ya se encuentra registrado")),
	NOT_REGISTER_USER("customer-412-50", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_REGISTER_USER", "Usuario no encontrado, vuelve a intentarlo por favor")),
	CONFLICT_INVALID_USERS_OWNER("customer-412-51", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_INVALID_USERS_OWNER", "Solo se puede ingresar un usuario con rol Propietario")),
	CONFLICT_INVALID_DOCUMENT_MAX("customer-412-52", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_INVALID_DOCUMENT_MAX", "Solo se permiten dos documentos de facturación")),
	CONFLICT_INVALID_DOCUMENT_TYPE("customer-412-52", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CONFLICT_INVALID_DOCUMENT_TYPE", "Solo se permiten documentos de facturación de diferentes tipos"));

	private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
