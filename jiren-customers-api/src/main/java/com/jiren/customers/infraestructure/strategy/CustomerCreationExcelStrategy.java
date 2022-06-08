package com.jiren.customers.infraestructure.strategy;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.jiren.customers.domain.model.Business;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.Customer.CustomerBuilder;
import com.jiren.customers.domain.model.CustomerPaymentType;
import com.jiren.customers.domain.model.Document;
import com.jiren.customers.domain.model.PaymentType;
import com.jiren.customers.domain.model.TimeWindow;
import com.jiren.customers.domain.model.Ubigeo;
import com.jiren.customers.domain.model.User;
import com.jiren.customers.domain.model.types.PaymentTypeEnumerator;
import com.jiren.customers.domain.model.types.StatusEnumerator;
import com.jiren.customers.domain.model.types.ZoneEnumerator;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.customers.infraestructure.dao.repository.DocumentRepository;
import com.jiren.customers.infraestructure.dao.repository.PaymentTypeRepository;
import com.jiren.customers.infraestructure.dao.repository.UbigeoRepository;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.customers.infraestructure.strategy.validator.AddressValidator;
import com.jiren.customers.infraestructure.strategy.validator.DocumentValidator;
import com.jiren.customers.infraestructure.strategy.validator.MypeValidator;
import com.jiren.customers.infraestructure.strategy.validator.PaymentTypeValidator;
import com.jiren.customers.infraestructure.strategy.validator.UserValidator;
import com.jiren.customers.infraestructure.strategy.wb.SheetAddress;
import com.jiren.customers.infraestructure.strategy.wb.SheetDocument;
import com.jiren.customers.infraestructure.strategy.wb.SheetMype;
import com.jiren.customers.infraestructure.strategy.wb.SheetPaymentType;
import com.jiren.customers.infraestructure.strategy.wb.SheetUser;
import com.jiren.customers.service.dto.CustomerExcelDTO;
import com.jiren.customers.service.dto.workBookExcelDTO;
import com.jiren.customers.service.dto.wb.SheetAddressDTO;
import com.jiren.customers.service.dto.wb.SheetDocumentDTO;
import com.jiren.customers.service.dto.wb.SheetMypeDTO;
import com.jiren.customers.service.dto.wb.SheetPaymentTypeDTO;
import com.jiren.customers.service.dto.wb.SheetTimeDTO;
import com.jiren.customers.service.dto.wb.SheetUserDTO;
import com.jiren.customers.service.strategy.CustomerExcelStrategy;
import com.jiren.shared.guid.GUIDGenerator;

public class CustomerCreationExcelStrategy implements CustomerExcelStrategy{

	private final GUIDGenerator guidGenerator;
	private final workBookExcelDTO workbook;
	private final String userAudit;
	private final Boolean save;
	private final CustomerRepository customerRepository;
	private final BusinessRepository businessRepository;
	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	private final UbigeoRepository ubigeoRepository;
	private final PaymentTypeRepository paymentTypeRepository;
	
	private List<String> errors = new ArrayList<>();
	
	private LinkedHashMap<String, SheetMypeDTO> mypesDictionary;
	
	private final String IS_TRUE = "VERDADERO";
	private final boolean ACTIVE = true;
	
	private final String xlsxDateFormat = "dd/MM/yyyy";
    private final ZoneId zoneId = ZoneId.of("America/Lima");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(xlsxDateFormat).withZone(zoneId);
	
    private DataFormatter formatter = new DataFormatter();
    private List<String> ownerDocumentNumbersToCreate;
    private List<String> numberDocumentsToCreate;
    private List<String> numberDocumentsUserToCreate;
    private List<String> phoneToCreate;
    private List<String> emailToCreate;
    private List<String> bussinesNameToCreate;
    private List<String> adressesToCreate;
    
    private List<String> typeDocumentsUnique;
    private List<String> sellInsUnique;
    private List<String> sellOutsUnique;

	public CustomerCreationExcelStrategy(GUIDGenerator guidGenerator, workBookExcelDTO workbook, String userAudit, Boolean save, 
			CustomerRepository customerRepository, BusinessRepository businessRepository, DocumentRepository documentRepository, 
			UserRepository userRepository, UbigeoRepository ubigeoRepository, PaymentTypeRepository paymentTypeRepository) {
		this.guidGenerator = guidGenerator;
		this.workbook = workbook;
		this.userAudit = userAudit;
		this.save = save;
		this.customerRepository = customerRepository;
		this.businessRepository = businessRepository;
		this.documentRepository = documentRepository;
		this.userRepository = userRepository;
		this.ubigeoRepository = ubigeoRepository;
		this.paymentTypeRepository = paymentTypeRepository;
	}

	@Override
	public Boolean validate() {
		return guidGenerator != null && workbook != null && userAudit != null && save != null;
	}

	@Override
	public CustomerExcelDTO generate() throws IOException, InterruptedException{
		if (!validate())
            return null;

		mypesDictionary = new LinkedHashMap<>(); 
        errors = new ArrayList<>(); 
        
		ownerDocumentNumbersToCreate = new ArrayList<>();
	    numberDocumentsToCreate = new ArrayList<>();
	    numberDocumentsUserToCreate = new ArrayList<>();
	    phoneToCreate = new ArrayList<>();
	    emailToCreate = new ArrayList<>();
	    bussinesNameToCreate = new ArrayList<>();
	    adressesToCreate = new ArrayList<>();
	    typeDocumentsUnique = new ArrayList<>();
	    sellInsUnique = new ArrayList<>();
	    sellOutsUnique = new ArrayList<>();
	          
		validateSheets();
		
		return new CustomerExcelDTO(convertToCustomers(), errors);
	}

	private void validateSheets() {
		readMype();		
        if (!mypesDictionary.isEmpty()) {
        	readDocuments();
        	readUsers();
        	readAddress();
        	readPaymentType(PaymentTypeEnumerator.SELL_IN);
        	readPaymentType(PaymentTypeEnumerator.SELL_OUT);
        }
	}
	
	private boolean readMype() {
		Sheet mypeSheet = workbook.getMypeSheet();
        Iterator<Row> rows = mypeSheet.iterator();
        int rowNumber = 0;
        boolean flagErrors = false;

        StringBuilder mypeErrors;

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (rowNumber == 0) {
                rowNumber ++;
                continue;
            }else if (rowNumber == 1) {
                if (currentRow.getLastCellNum() == SheetMype.COLUMNS) {
                    rowNumber ++;
                    continue;
                }else {
                    return false;
                }
            }            
            Iterator<Cell> cellsInRow = currentRow.iterator();
            
            SheetMypeDTO mype =  new SheetMypeDTO();
            
            SheetUserDTO owner = new SheetUserDTO();
            owner.setOwner(true);
            
            mypeErrors = new StringBuilder().append(String.format("MYPES [Fila %d]: ", rowNumber+1));
            
            while (cellsInRow.hasNext()) {
                
            	Cell currentCell = cellsInRow.next();
                String cellString = formatter.formatCellValue(currentCell).trim();
                
                switch (currentCell.getColumnIndex()) {
                	case SheetMype.OWNER_TYPE_DOCUMENT:                		
                		if (!addErrors(mypeErrors, UserValidator.documentTypePerson(cellString))) {                			
                			owner.setTypeDocument(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_NUMBER_DOCUMENT:
                		if (!addErrors(mypeErrors, UserValidator.documentNumberPerson(cellString, 
                				owner.getTypeDocument(), ownerDocumentNumbersToCreate, userRepository))) {                			
                			owner.setNumberDocument(cellString);
                			ownerDocumentNumbersToCreate.add(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_NAME:
                		if (!addErrors(mypeErrors, UserValidator.name(cellString))) {                			
                			owner.setName(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_FIRST_LAST_NAME:
                		if (!addErrors(mypeErrors, UserValidator.firstLastName(cellString))) {
                			owner.setFirstLastName(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_SECOND_LAST_NAME:
                		if (!addErrors(mypeErrors, UserValidator.secondLastName(cellString))) {
                			owner.setSecondLastName(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_PHONE:
                		if (!addErrors(mypeErrors, UserValidator.phone(cellString, phoneToCreate, userRepository))) {
                			owner.setPhone(cellString);
                			phoneToCreate.add(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.OWNER_EMAIL:
                		cellString = cellString.toLowerCase();
                		if (!addErrors(mypeErrors, UserValidator.email(cellString, emailToCreate, userRepository))) {
                			owner.setEmail(cellString.isEmpty() ? null : cellString);
                			if (!cellString.isEmpty()) 
                				emailToCreate.add(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_BUSINESS_NAME:
                		if (!addErrors(mypeErrors, MypeValidator.businessName(cellString, bussinesNameToCreate, customerRepository))) {                			
                			mype.setB2bBusinessName(cellString);
                			bussinesNameToCreate.add(cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_TRADE_NAME:
                		if (!addErrors(mypeErrors, MypeValidator.tradeName(cellString))) {                			
                			mype.setB2bTradeName(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_DEX_CODE:
                		if (!addErrors(mypeErrors, MypeValidator.dexCode(cellString))) {                			
                			mype.setB2bDexCode(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_WORK_AREA:
                		if (!addErrors(mypeErrors, MypeValidator.workArea(cellString))) {                			
                			mype.setB2bworkArea(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_SUB_WORK_AREA:
                		if (!addErrors(mypeErrors, MypeValidator.subWorkArea(cellString))) {                			
                			mype.setB2bSubWorkArea(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_ADVISER:
                		if (!addErrors(mypeErrors, MypeValidator.adviser(cellString))) {                			
                			mype.setB2bAdviser(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_CHANNEL_ORIGIN:
                		if (!addErrors(mypeErrors, MypeValidator.channelOrigin(cellString))) {                			
                			mype.setB2bChannelOrigin(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_REGISTRY_DATE:
                		if (!addErrors(mypeErrors, MypeValidator.registryDate(cellString))) {
                			mype.setB2bRegistryDate(cellString.isEmpty() ? null : LocalDate.parse(cellString, dateFormatter));
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_DISCHARGE_DATE:
                		if (!addErrors(mypeErrors, MypeValidator.dischargeDate(cellString))) {                			
                			mype.setB2bDischargeDate(cellString.isEmpty() ? null : LocalDate.parse(cellString, dateFormatter));
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_WAVE:
                		if (!addErrors(mypeErrors, MypeValidator.wave(cellString))) {                			
                			mype.setB2bWave(cellString.isEmpty() ? null : Integer.parseInt(cellString));
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_SEGMENT:
                		if (!addErrors(mypeErrors, MypeValidator.segment(cellString))) {                			
                			mype.setB2bSegment(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_STATUS:
                		if (!addErrors(mypeErrors, MypeValidator.status(cellString))) {                			
                			mype.setB2bStatus(cellString.isEmpty() ? null : StatusEnumerator.byName(cellString).getCode());
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.B2B_MIN_AMOUNT:
                		if (!addErrors(mypeErrors, MypeValidator.minAmount(cellString))) {                			
                			mype.setB2bMinAmount(cellString.isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(cellString)));
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.SAAS_ELECTRONIC_INVOICE:
                		if (!addErrors(mypeErrors, MypeValidator.electronicInvoice(cellString))) {                			
                			mype.setSaasElectronicInvoice(cellString.isEmpty() ? null : cellString.equals(IS_TRUE));
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	case SheetMype.SAAS_CURRENCY:
                		if (!addErrors(mypeErrors, MypeValidator.currency(cellString))) {                			
                			mype.setSaasCurrency(cellString.isEmpty() ? null : cellString);
                		}else{
                            flagErrors |= true;
                        }
                		break;
                	default:
                         break;
                 }
            }
            if (flagErrors) {
            	String err = mypeErrors.toString();
                errors.add( err.substring(0, err.length() - 2) );
                flagErrors = false;
            }else{
                mype.setOwner(owner);
                mypesDictionary.put(owner.getNumberDocument(), mype);
            }
            rowNumber ++;
        }
        return true;
    }
	
	private boolean readDocuments() {		
		Sheet documentSheet = workbook.getDocumentSheet();
		Iterator<Row> rows = documentSheet.iterator();
		int rowNumber = 0;
		boolean flagErrors = false;		
		StringBuilder documentErrors;
		
		while (rows.hasNext()) {
			Row currentRow = rows.next();
			if (rowNumber == 0) {
				rowNumber ++;
				continue;
			}else if (rowNumber == 1) {
				if (currentRow.getLastCellNum() == SheetDocument.COLUMNS) {
                    if(formatter.formatCellValue(currentRow.getCell(0)).trim().isEmpty()){
                    	return true;
                    }
                }
			}
			Iterator<Cell> cellsInRow = currentRow.iterator();
			SheetDocumentDTO document =  new SheetDocumentDTO();
			String ownerDocumentNumber = null;
			
			documentErrors = new StringBuilder().append(String.format("Documentos [Fila %d]: ", rowNumber+1));
			
			while (cellsInRow.hasNext()) {
				
				Cell currentCell = cellsInRow.next();
				String cellString = formatter.formatCellValue(currentCell).trim();
				
				switch (currentCell.getColumnIndex()) {
				case SheetDocument.OWNER_NUMBER_DOCUMENT:                		
					if (!addErrors(documentErrors, DocumentValidator.ownerDocumentNumber(cellString, ownerDocumentNumbersToCreate))) {                			
						ownerDocumentNumber = cellString;
					}else{
						ownerDocumentNumber = cellString;
						flagErrors |= true;
					}
					break;
				case SheetDocument.TYPE_DOCUMENT:                		
					if (!addErrors(documentErrors, DocumentValidator.documentType(cellString, 
							ownerDocumentNumber, typeDocumentsUnique))) {                			
						document.setTypeDocument(cellString);
						typeDocumentsUnique.add(ownerDocumentNumber + cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetDocument.NUMBER_DOCUMENT:
					if (!addErrors(documentErrors, DocumentValidator.documentNumber(cellString, 
							document.getTypeDocument(), numberDocumentsToCreate, documentRepository))) {                			
						document.setNumberDocument(cellString);
						numberDocumentsToCreate.add(cellString);
					}else{
						flagErrors |= true;
					}
					break;
				default:
					break;
				}
			}
			if (flagErrors) {
				String err = documentErrors.toString();
				errors.add( err.substring(0, err.length() - 2) );
				flagErrors = false;
			}else{
				if (mypesDictionary.containsKey(ownerDocumentNumber)) {
					SheetMypeDTO currentMype = mypesDictionary.get(ownerDocumentNumber);					
					currentMype.getDocuments().add(document);
                }
			}
			rowNumber ++;
		}
		return true;
	}
	
	private boolean readUsers() {		
		Sheet userSheet = workbook.getUserSheet();
		Iterator<Row> rows = userSheet.iterator();
		int rowNumber = 0;
		boolean flagErrors = false;		
		StringBuilder userErrors;
		
		while (rows.hasNext()) {
			Row currentRow = rows.next();
			if (rowNumber == 0) {
				rowNumber ++;
				continue;
			}else if (rowNumber == 1) {
				if (currentRow.getLastCellNum() == SheetUser.COLUMNS) {
					if(formatter.formatCellValue(currentRow.getCell(0)).trim().isEmpty()){
						return true;
					}
				}
			}
			Iterator<Cell> cellsInRow = currentRow.iterator();
			SheetUserDTO user =  new SheetUserDTO();
			user.setOwner(false);
			
			String ownerDocumentNumber = null;
			
			userErrors = new StringBuilder().append(String.format("Usuarios [Fila %d]: ", rowNumber+1));
			
			while (cellsInRow.hasNext()) {
				
				Cell currentCell = cellsInRow.next();
				String cellString = formatter.formatCellValue(currentCell).trim();
				
				switch (currentCell.getColumnIndex()) {
				case SheetUser.OWNER_NUMBER_DOCUMENT:                		
					if (!addErrors(userErrors, DocumentValidator.ownerDocumentNumber(cellString, ownerDocumentNumbersToCreate))) {                			
						ownerDocumentNumber = cellString;
					}else{
						ownerDocumentNumber = cellString;
						flagErrors |= true;
					}
					break;
				case SheetUser.TYPE_DOCUMENT:                		
					if (!addErrors(userErrors, UserValidator.documentTypePerson(cellString))) {
						user.setTypeDocument(cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetUser.NUMBER_DOCUMENT:
					if (!addErrors(userErrors, UserValidator.documentNumberPerson(cellString, 
							user.getTypeDocument(), numberDocumentsUserToCreate, userRepository))) {
						user.setNumberDocument(cellString);
						numberDocumentsUserToCreate.add(cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetUser.NAME:
            		if (!addErrors(userErrors, UserValidator.name(cellString))) {
            			user.setName(cellString);
            		}else{
                        flagErrors |= true;
                    }
            		break;
            	case SheetUser.FIRST_LAST_NAME:
            		if (!addErrors(userErrors, UserValidator.firstLastName(cellString))) {
            			user.setFirstLastName(cellString);
            		}else{
                        flagErrors |= true;
                    }
            		break;
            	case SheetUser.SECOND_LAST_NAME:
            		if (!addErrors(userErrors, UserValidator.secondLastName(cellString))) {
            			user.setSecondLastName(cellString.isEmpty() ? null : cellString);
            		}else{
                        flagErrors |= true;
                    }
            		break;
            	case SheetUser.PHONE:
            		if (!addErrors(userErrors, UserValidator.phone(cellString, phoneToCreate, userRepository))) {
            			user.setPhone(cellString);
            			phoneToCreate.add(cellString);
            		}else{
                        flagErrors |= true;
                    }
            		break;
            	case SheetUser.EMAIL:
            		cellString = cellString.toLowerCase();
            		if (!addErrors(userErrors, UserValidator.email(cellString, emailToCreate, userRepository))) {
            			user.setEmail(cellString.isEmpty() ? null : cellString);
            			if (!cellString.isEmpty()) 
            				emailToCreate.add(cellString);
            		}else{
                        flagErrors |= true;
                    }
            		break;
				default:
					break;
				}
			}
			if (flagErrors) {
				String err = userErrors.toString();
				errors.add( err.substring(0, err.length() - 2) );
				flagErrors = false;
			}else{
				if (mypesDictionary.containsKey(ownerDocumentNumber)) {
					SheetMypeDTO currentMype = mypesDictionary.get(ownerDocumentNumber);					
					currentMype.getUsers().add(user);
				}
			}
			rowNumber ++;
		}
		return true;
	}
	
	private boolean readAddress() {		
		Sheet addressSheet = workbook.getAddressSheet();
		Iterator<Row> rows = addressSheet.iterator();
		int rowNumber = 0;
		boolean flagErrors = false;		
		StringBuilder addressErrors;
		
		while (rows.hasNext()) {
			Row currentRow = rows.next();
			if (rowNumber == 0) {
				rowNumber ++;
				continue;
			}else if (rowNumber == 1) {
				if (currentRow.getLastCellNum() == SheetAddress.COLUMNS) {
					if(formatter.formatCellValue(currentRow.getCell(0)).trim().isEmpty()){
						return true;
					}
				}
			}
			Iterator<Cell> cellsInRow = currentRow.iterator();
			SheetAddressDTO address =  new SheetAddressDTO();
			
			SheetTimeDTO time1 = new SheetTimeDTO();
			SheetTimeDTO time2 = new SheetTimeDTO();
			
			String ownerDocumentNumber = null;
			
			addressErrors = new StringBuilder().append(String.format("Direcciones [Fila %d]: ", rowNumber+1));
			
			while (cellsInRow.hasNext()) {
				
				Cell currentCell = cellsInRow.next();
				String cellString = formatter.formatCellValue(currentCell).trim();
				
				switch (currentCell.getColumnIndex()) {
				case SheetAddress.OWNER_NUMBER_DOCUMENT:                		
					if (!addErrors(addressErrors, DocumentValidator.ownerDocumentNumber(cellString, ownerDocumentNumbersToCreate))) {                			
						ownerDocumentNumber = cellString;
					}else{
						ownerDocumentNumber = cellString;
						flagErrors |= true;
					}
					break;
				case SheetAddress.ADDRESS:
					if (!addErrors(addressErrors, AddressValidator.address(cellString, adressesToCreate, businessRepository))) {                			
						address.setAddress(cellString);
						adressesToCreate.add(cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.REFERENCE:
					if (!addErrors(addressErrors, AddressValidator.reference(cellString))) {
						address.setReference(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.LATITUDE:
					cellString = cellString.replace(',','.');
					if (!addErrors(addressErrors, AddressValidator.latitude(cellString))) {
						address.setLatitude(BigDecimal.valueOf(Double.parseDouble(cellString)));
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.LONGITUDE:
					cellString = cellString.replace(',','.');
					if (!addErrors(addressErrors, AddressValidator.longitude(cellString))) {
						address.setLongitude(BigDecimal.valueOf(Double.parseDouble(cellString)));
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.ZONE:
					if (!addErrors(addressErrors, AddressValidator.zone(cellString))) {
						address.setZone(cellString.isEmpty() ? null : ZoneEnumerator.byName(cellString).getCode());
					}else{
						flagErrors |= true;
					}
					break;
					
				case SheetAddress.START_TIME_WINDOW_1:
					if (!addErrors(addressErrors, AddressValidator.startTimeWindow1(cellString))) {
						time1.setStart(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.END_TIME_WINDOW_1:
					if (!addErrors(addressErrors, AddressValidator.endTimeWindow1(cellString, time1.getStart()))) {
						time1.setEnd(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.START_TIME_WINDOW_2:
					if (!addErrors(addressErrors, AddressValidator.startTimeWindow2(cellString))) {
						time2.setStart(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.END_TIME_WINDOW_2:
					if (!addErrors(addressErrors, AddressValidator.endTimeWindow2(cellString, time2.getStart()))) {
						time2.setEnd(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
					
				case SheetAddress.POSTAL_CODE:
					if (!addErrors(addressErrors, AddressValidator.codePostal(cellString))) {
						address.setPostalCode(cellString.isEmpty() ? null : cellString);
					}else{
						flagErrors |= true;
					}
					break;
				case SheetAddress.UBIGEO_CODE:
					if (!addErrors(addressErrors, AddressValidator.codeUbigeo(cellString, ubigeoRepository))) {                			
						address.setUbigeoCode(cellString);
					}else{
						flagErrors |= true;
					}
					break;
				default:
					break;
				}
			}
			
			if (flagErrors) {
				String err = addressErrors.toString();
				errors.add( err.substring(0, err.length() - 2) );
				flagErrors = false;
			}else{
				address.setTimeWindow1(time1.getStart() == null && time1.getEnd() == null ? null : time1);
				address.setTimeWindow2(time2.getStart() == null && time2.getEnd() == null ? null : time2);
				if (mypesDictionary.containsKey(ownerDocumentNumber)) {
					SheetMypeDTO currentMype = mypesDictionary.get(ownerDocumentNumber);
					currentMype.getAddresses().add(address);
				}
			}
			rowNumber ++;
		}
		return true;
	}
	
	private boolean readPaymentType(PaymentTypeEnumerator paymentEnum) {		
		Sheet paymentTypeSheet = (paymentEnum == PaymentTypeEnumerator.SELL_IN) ? workbook.getSellInSheet() : workbook.getSellOutSheet();
		Iterator<Row> rows = paymentTypeSheet.iterator();		
		int rowNumber = 0;
		boolean flagErrors = false;		
		StringBuilder paymentTypeErrors;
		
		while (rows.hasNext()) {
			Row currentRow = rows.next();
			if (rowNumber == 0) {
				rowNumber ++;
				continue;
			}else if (rowNumber == 1) {
				if (currentRow.getLastCellNum() == SheetAddress.COLUMNS) {
					if(formatter.formatCellValue(currentRow.getCell(0)).trim().isEmpty()){
						return true;
					}
				}
			}
			Iterator<Cell> cellsInRow = currentRow.iterator();
			SheetPaymentTypeDTO paymentType =  new SheetPaymentTypeDTO();
			
			String ownerDocumentNumber = null;
			
			paymentTypeErrors = new StringBuilder().append(String.format("%s [Fila %d]: ", paymentEnum.getDescription(), rowNumber+1));
			
			while (cellsInRow.hasNext()) {
				
				Cell currentCell = cellsInRow.next();
				String cellString = formatter.formatCellValue(currentCell).trim();
				
				switch (currentCell.getColumnIndex()) {
				case SheetPaymentType.OWNER_NUMBER_DOCUMENT:                		
					if (!addErrors(paymentTypeErrors, DocumentValidator.ownerDocumentNumber(cellString, ownerDocumentNumbersToCreate))) {                			
						ownerDocumentNumber = cellString;
					}else{
						ownerDocumentNumber = cellString;
						flagErrors |= true;
					}
					break;
				case SheetPaymentType.PAYMENT:
					if (paymentEnum == PaymentTypeEnumerator.SELL_IN) {
						if (!addErrors(paymentTypeErrors, PaymentTypeValidator.paymentType(cellString, ownerDocumentNumber, paymentEnum, sellInsUnique, paymentTypeRepository ))) {                			
							paymentType.setPayment(cellString);
							sellInsUnique.add(ownerDocumentNumber + cellString);
						}else{
							flagErrors |= true;
						}
					}else {
						if (!addErrors(paymentTypeErrors, PaymentTypeValidator.paymentType(cellString, ownerDocumentNumber, paymentEnum, sellOutsUnique, paymentTypeRepository ))) {                			
							paymentType.setPayment(cellString);
							sellOutsUnique.add(ownerDocumentNumber + cellString);
						}else{
							flagErrors |= true;
						}
					}
					break;
				default:
					break;
				}
			}			
			if (flagErrors) {
				String err = paymentTypeErrors.toString();
				errors.add( err.substring(0, err.length() - 2) );
				flagErrors = false;
			}else{
				if (paymentEnum == PaymentTypeEnumerator.SELL_IN) {
					if (mypesDictionary.containsKey(ownerDocumentNumber)) {
						SheetMypeDTO currentMype = mypesDictionary.get(ownerDocumentNumber);					
						currentMype.getSellIns().add(paymentType);
					}
				}else {
					if (mypesDictionary.containsKey(ownerDocumentNumber)) {
						SheetMypeDTO currentMype = mypesDictionary.get(ownerDocumentNumber);					
						currentMype.getSellOuts().add(paymentType);
					}
				}				
			}
			rowNumber ++;
		}
		return true;
	}
	
	private boolean addErrors(StringBuilder errorString, List<String> errors){
        if(errors.isEmpty())
            return false;
        for(String error : errors){
            errorString.append(error);
            errorString.append(", ");
        }
        return true;
    }

	private List<Customer> convertToCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		
		mypesDictionary.keySet().forEach(key -> {
        	CustomerBuilder customerBuilder = Customer.builder();
    		customerBuilder.guid( guidGenerator.generateAlphaID() );
    		customerBuilder.adviser( null );
    		customerBuilder.allowElectronicInvoice( mypesDictionary.get(key).getSaasElectronicInvoice() );
    		customerBuilder.businessName( mypesDictionary.get(key).getB2bBusinessName() );
    		customerBuilder.channelOrigin( mypesDictionary.get(key).getB2bChannelOrigin() );		
    		customerBuilder.currency( mypesDictionary.get(key).getSaasCurrency() );
    		customerBuilder.dexCode( null );
    		customerBuilder.dischargeDate( mypesDictionary.get(key).getB2bDischargeDate() );
    		customerBuilder.erpCode( null );
    		customerBuilder.image( null );
    		customerBuilder.minAmount( mypesDictionary.get(key).getB2bMinAmount() );
    		customerBuilder.newCustomer( null );
    		customerBuilder.perfectCustomer( null );
    		customerBuilder.prefixProductCode( null );
    		customerBuilder.registryDate( mypesDictionary.get(key).getB2bRegistryDate() );
    		customerBuilder.segment( mypesDictionary.get(key).getB2bSegment() );
    		customerBuilder.state( mypesDictionary.get(key).getB2bStatus() );
    		customerBuilder.subWorkArea( mypesDictionary.get(key).getB2bSubWorkArea() );
    		customerBuilder.tradeName( mypesDictionary.get(key).getB2bTradeName() );
    		customerBuilder.updatedCustomer( null );
    		customerBuilder.wave( mypesDictionary.get(key).getB2bWave() );
    		customerBuilder.active( ACTIVE );
    		customerBuilder.userCreated( userAudit );
    		customerBuilder.dateCreated( LocalDateTime.now() );
    		customerBuilder.workArea( mypesDictionary.get(key).getB2bworkArea() );
    		
    		convertToModel(mypesDictionary.get(key), customerBuilder);

    		customers.add(customerBuilder.build());    		
        });
		return customers;
	}
	
	private void convertToModel(SheetMypeDTO mypeDictionary, CustomerBuilder customerBuilder) {
		convertUser(mypeDictionary, customerBuilder);
		convertDocument(mypeDictionary, customerBuilder);
		convertBusiness(mypeDictionary, customerBuilder);
		convertPaymentType(mypeDictionary, customerBuilder);
	}
	
	private void convertUser(SheetMypeDTO mypeDictionary, CustomerBuilder customerBuilder) {
		List<User> listUser = new ArrayList<User>();
		listUser.add(
				new User(
						mypeDictionary.getOwner().getId(), 
						customerBuilder.build(), 
						mypeDictionary.getOwner().getTypeDocument(), 
						mypeDictionary.getOwner().getNumberDocument(), 
						mypeDictionary.getOwner().getName(), 
						mypeDictionary.getOwner().getFirstLastName(), 
						mypeDictionary.getOwner().getSecondLastName(), 
						mypeDictionary.getOwner().getEmail(), 
						mypeDictionary.getOwner().getPhone(), 
						mypeDictionary.getOwner().getOwner(),
						ACTIVE, userAudit,
						LocalDateTime.now(),
						null, null) );
		
		mypeDictionary.getUsers().forEach(
				x -> listUser.add(
						new User(
								x.getId(), 
								customerBuilder.build(), 
								x.getTypeDocument(), 
								x.getNumberDocument(), 
								x.getName(), x.getFirstLastName(), 
								x.getSecondLastName(), x.getEmail(), 
								x.getPhone(), x.getOwner(),
								ACTIVE, userAudit, 
								LocalDateTime.now(),
								null, null) )
				);
		customerBuilder.users( listUser );
	}
	
	private void convertDocument(SheetMypeDTO mypeDictionary, CustomerBuilder customerBuilder) {
		List<Document> listDocument = new ArrayList<Document>();		
		mypeDictionary.getDocuments().forEach(
				x -> listDocument.add(
						new Document(
								x.getId(), 
								customerBuilder.build(),
								x.getTypeDocument(), 
								x.getNumberDocument(),
								false,
								ACTIVE) )
				);
		customerBuilder.documents( listDocument );
	}
	
	private void convertBusiness(SheetMypeDTO mypeDictionary, CustomerBuilder customerBuilder) {
		List<Business> listBusiness = new ArrayList<Business>();
		mypeDictionary.getAddresses().forEach(
				x -> {
					List<TimeWindow> listTimeWindow = new ArrayList<TimeWindow>();
					if (x.getTimeWindow1() != null) {
						listTimeWindow.add(
							new TimeWindow(x.getTimeWindow1().getId(), 
									null, x.getTimeWindow1().getStart(), 
									x.getTimeWindow1().getEnd(), ACTIVE) );
					}
					if (x.getTimeWindow2() != null) {
						listTimeWindow.add(
								new TimeWindow(x.getTimeWindow2().getId(), 
										null, x.getTimeWindow2().getStart(), 
										x.getTimeWindow2().getEnd(), ACTIVE) );
					}
					listBusiness.add(
							new  Business(
									x.getId(), 
									customerBuilder.build(), 
									new Ubigeo(x.getUbigeoCode()), 
									null, 
									null, 
									x.getAddress(), 
									x.getPostalCode(), x.getReference(), 
									x.getLatitude(), x.getLongitude(), 
									x.getZone(), null, 
									ACTIVE, 
									userAudit, 
									LocalDateTime.now(), 
									null, null, null, listTimeWindow )
							);
				});
		customerBuilder.business( listBusiness );
	}

	private void convertPaymentType(SheetMypeDTO mypeDictionary, CustomerBuilder customerBuilder) {
		List<CustomerPaymentType> sellIns = new ArrayList<CustomerPaymentType>();
		
		mypeDictionary.getSellIns().forEach(
				x -> sellIns.add(
						new CustomerPaymentType(
								x.getId(), 
								customerBuilder.build(), 
								new PaymentType(x.getPayment()), 
								PaymentTypeEnumerator.SELL_IN.getCode(), 
								ACTIVE) ) 
				);
		customerBuilder.sellIns( sellIns );
		
		List<CustomerPaymentType> sellOuts = new ArrayList<CustomerPaymentType>();
		mypeDictionary.getSellOuts().forEach(
				x -> sellOuts.add(
						new CustomerPaymentType(
								x.getId(), 
								customerBuilder.build(), 
								new PaymentType(x.getPayment()), 
								PaymentTypeEnumerator.SELL_OUT.getCode(), 
								ACTIVE) ) 
				);
		customerBuilder.sellOuts( sellOuts );
	}
	
}
