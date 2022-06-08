package com.jiren.customers.service.dto;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class workBookExcelDTO {
	Workbook workbook;
	Sheet mypeSheet;
	Sheet documentSheet;
	Sheet userSheet;
	Sheet addressSheet;
	Sheet sellInSheet;
	Sheet sellOutSheet;	
}
