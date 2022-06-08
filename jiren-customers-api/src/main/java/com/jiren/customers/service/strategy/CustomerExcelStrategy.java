package com.jiren.customers.service.strategy;

import java.io.IOException;

import com.jiren.customers.service.dto.CustomerExcelDTO;

public interface CustomerExcelStrategy {
	Boolean validate();
	CustomerExcelDTO generate() throws IOException, InterruptedException;
}
