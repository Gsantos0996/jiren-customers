package com.jiren.customers.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jiren.customers.service.dto.CustomersExcelServiceDTO;

public interface CustomerExcelService {
	CustomersExcelServiceDTO createCustomers(MultipartFile file, String userAudit, Boolean save) throws IOException, InterruptedException;
	ByteArrayInputStream getTemplateExcel(String fileName) throws IOException;
}
