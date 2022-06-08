package com.jiren.customers.infraestructure.interfaces.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import com.jiren.customers.domain.model.types.UserEventEnumerator;
import com.jiren.customers.handler.helper.TenantHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.jiren.customers.domain.dao.CustomerDAO;
import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.infraestructure.dao.repository.BusinessRepository;
import com.jiren.customers.infraestructure.dao.repository.CustomerRepository;
import com.jiren.customers.infraestructure.dao.repository.DocumentRepository;
import com.jiren.customers.infraestructure.dao.repository.PaymentTypeRepository;
import com.jiren.customers.infraestructure.dao.repository.UbigeoRepository;
import com.jiren.customers.infraestructure.dao.repository.UserRepository;
import com.jiren.customers.infraestructure.strategy.CustomerCreationExcelStrategy;
import com.jiren.customers.infraestructure.strategy.wb.SheetAddress;
import com.jiren.customers.infraestructure.strategy.wb.SheetDocument;
import com.jiren.customers.infraestructure.strategy.wb.SheetMype;
import com.jiren.customers.infraestructure.strategy.wb.SheetPaymentType;
import com.jiren.customers.infraestructure.strategy.wb.SheetUser;
import com.jiren.customers.service.AmazonS3Service;
import com.jiren.customers.service.CustomerExcelService;
import com.jiren.customers.service.dto.CustomerExcelDTO;
import com.jiren.customers.service.dto.CustomerSuccessServiceDTO;
import com.jiren.customers.service.dto.CustomersExcelServiceDTO;
import com.jiren.customers.service.dto.ParallelSaveResponse;
import com.jiren.customers.service.dto.workBookExcelDTO;
import com.jiren.customers.service.strategy.CustomerExcelStrategy;
import com.jiren.shared.aws.s3.S3Utilities;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.guid.GUIDGenerator;
import com.jiren.shared.utils.Helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestCustomerExcelService implements CustomerExcelService{

	private final AmazonS3Service amazonS3Service;
	
	private final GUIDGenerator guidGenerator;	
	private final CustomerRepository customerRepository;
	private final BusinessRepository businessRepository;
	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	private final UbigeoRepository ubigeoRepository;
	private final PaymentTypeRepository paymentTypeRepository;

	private final CustomerDAO customerDAO;
	private final RestUserService userService;
	private final TenantHelper tenantHelper;

	@Value("${amazon.s3.customersTemplateDirectory}")
	private String directoryPath;

	@Value("${customers.batch.createThreads}")
	private Integer threads;

	@Override
	@Transactional
	public CustomersExcelServiceDTO createCustomers(MultipartFile file, String userAudit, Boolean save) throws IOException, InterruptedException {
		workBookExcelDTO workbook = validateCreationExcel(file);
		CustomerExcelStrategy strategy = new CustomerCreationExcelStrategy(guidGenerator, workbook, userAudit, 
				save, customerRepository, businessRepository, documentRepository, userRepository, ubigeoRepository, paymentTypeRepository);

		List<CustomerSuccessServiceDTO> success = new ArrayList<>();
		List<String> errors = new ArrayList<>();
        
		CustomerExcelDTO customerExcel = strategy.generate();
		
        if (customerExcel.getErrors().isEmpty()) {
        	saveExcel(tenantHelper.getTenant(),customerExcel.getCustomers(), save, errors, success);
        }        

		String message = ( customerExcel.getErrors().isEmpty() && errors.isEmpty() ) ?
				save ? "¡Todas los clientes se subieron con éxito!" : "Validación exitosa. Archivo listo para subir." :
				save ? "Hubieron problemas en la subida, por favor consultar detalle" : "Se encontraron errores en la validación";

        return new CustomersExcelServiceDTO(message, !customerExcel.getErrors().isEmpty() ? customerExcel.getErrors() : errors, success);
	}

	private workBookExcelDTO validateCreationExcel(MultipartFile file) throws IOException {	
		
		if (file == null) {
			throw new MPlusApiException(CustomerExceptionEnumerator.FILE_INVALID);
		}
		if (!Helper.validaExtensionExcel(file.getOriginalFilename())) {
			throw new MPlusApiException(CustomerExceptionEnumerator.EXTENSION_EXCEL_INVALID);
		}
        return validateWorkbook(file);        
	}

	private workBookExcelDTO validateWorkbook(MultipartFile file) throws IOException {		
		Workbook wb = new XSSFWorkbook(file.getInputStream());

		Sheet mypeSheet = wb.getSheet(SheetMype.CAPTION);
		Sheet documentSheet = wb.getSheet(SheetDocument.CAPTION);
		Sheet userSheet = wb.getSheet(SheetUser.CAPTION);
		Sheet addressSheet = wb.getSheet(SheetAddress.CAPTION);
		Sheet sellInSheet = wb.getSheet(SheetPaymentType.CAPTION_SELL_IN);
		Sheet sellOutSheet = wb.getSheet(SheetPaymentType.CAPTION_SELL_OUT);

	    if ( mypeSheet != null && documentSheet != null && userSheet != null && addressSheet != null && sellInSheet!= null && sellOutSheet != null ) {
	    	if (mypeSheet.getRow(SheetMype.FIRST_ROW) == null || mypeSheet.getRow(SheetMype.FIRST_ROW).getLastCellNum() != SheetMype.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_MYPE_INVALID);
	    	if (documentSheet.getRow(SheetDocument.FIRST_ROW) == null || documentSheet.getRow(SheetDocument.FIRST_ROW).getLastCellNum() != SheetDocument.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_DOCUMENT_INVALID);
	    	if (userSheet.getRow(SheetUser.FIRST_ROW) == null || userSheet.getRow(SheetUser.FIRST_ROW).getLastCellNum() != SheetUser.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_USER_INVALID);
	    	if (addressSheet.getRow(SheetAddress.FIRST_ROW) == null || addressSheet.getRow(SheetAddress.FIRST_ROW).getLastCellNum() != SheetAddress.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_ADDRESS_INVALID);
	    	if (sellInSheet.getRow(SheetPaymentType.FIRST_ROW) == null || sellInSheet.getRow(SheetPaymentType.FIRST_ROW).getLastCellNum() != SheetPaymentType.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_SELL_IN_INVALID);
	    	if (sellOutSheet.getRow(SheetPaymentType.FIRST_ROW) == null || sellOutSheet.getRow(SheetPaymentType.FIRST_ROW).getLastCellNum() != SheetPaymentType.COLUMNS )
	    		throw new MPlusApiException(CustomerExceptionEnumerator.SHEET_SELL_OUT_INVALID);
	    }
	    return new workBookExcelDTO(wb, mypeSheet, documentSheet, userSheet, addressSheet, sellInSheet, sellOutSheet);
	}

	public void saveExcel(String tenant,List<Customer> customers, Boolean save, List<String> errors, List<CustomerSuccessServiceDTO> success) {

		ForkJoinPool customThreadPool;

        if (CollectionUtils.isNotEmpty(customers)) {
            customThreadPool = new ForkJoinPool(threads);
            try {
            	customThreadPool.submit( () -> customers.parallelStream()
            		.map(customer -> {
                        ParallelSaveResponse response = new ParallelSaveResponse();

            			if (customer.getUsers().isEmpty()) {
	                        response.addError("Error al cargar Mype. No tiene propietario");
            			}else {
	                        String typeDocumentOwner = customer.getUsers().get(0).getTypeDocument();
	                        String ownerDocumentNumber = customer.getUsers().get(0).getNumberDocument();

	                        if (customer.getDocuments().isEmpty())
	                            response.addError("Error al cargar Mype del " + ownerDocumentNumber + " no tiene documentos");
	                        if (customer.getBusiness().isEmpty())
	                        	response.addError("Error al cargar Mype del " + ownerDocumentNumber + " no tiene direcciones");
	                        if (customer.getSellIns().isEmpty())
	                            response.addError("Error al cargar Mype del " + ownerDocumentNumber + " no tiene métodos de pago (Sell In)");
	                        if (customer.getSellOuts().isEmpty())
	                        	response.addError("Error al cargar Mype del " + ownerDocumentNumber + " no tiene métodos de pago (Sell Out)");

	                        if (response.isValid()) {
	                            try {
	                                String guid = "";
	                                if (save) {
	                                    guid = customerDAO.create(customer);
										userService.saveExternal(tenant,guid, UserEventEnumerator.CREATE);
	                                }
	                                response.setSuccess(new CustomerSuccessServiceDTO(typeDocumentOwner, ownerDocumentNumber, guid));
	                            }catch (Exception e) {
	                            	e.printStackTrace();
	                                response.addError("Error al intentar cargar Mype del " + ownerDocumentNumber );
	                            }
	                        }
	                    }
            			return response;
            		})
            		.collect(Collectors.toList())
	            ).get().forEach(
	                resp -> {
	                    if (resp.isValid())
	                    	success.add(resp.getSuccess());
	                    else
	                        errors.addAll(resp.getErrors());
	                }
	            );
            } catch (InterruptedException | ExecutionException e) {
                log.error("validateSheets()-ForkJoinPool fail: ", e);
            } finally {
                customThreadPool.shutdown();
            }
        }
	}

	@Override
	public ByteArrayInputStream getTemplateExcel(String fileName) throws IOException {
		S3Utilities s3Utilities = amazonS3Service.getS3Utilities();
        AmazonS3 amazonS3 = amazonS3Service.getAmazonS3();
        String bucketName = amazonS3Service.getBucketNameS3(amazonS3.listBuckets());

        String fileNameTemp = createUniqueFilename(fileName);
        File convertFile = createTempFile(fileNameTemp);

        s3Utilities.downloadObject(amazonS3, convertFile, bucketName, directoryPath + fileName);
        return new ByteArrayInputStream(FileUtils.readFileToByteArray(convertFile));
	}
	
	private String createUniqueFilename(String originalFileName){
        String fileName = Helper.reemplazarEspaciosString(originalFileName);
        fileName = String.valueOf(Math.abs(new Random().nextLong())).concat(fileName);
        return fileName;
    }

    private File createTempFile(String fileName){
        String filePath = System.getProperty("java.io.tmpdir");
        filePath = filePath.concat(File.separator).concat(fileName);
        log.info("Path of temporary file saved in: " + filePath);
		return new File(filePath);
    }
}
