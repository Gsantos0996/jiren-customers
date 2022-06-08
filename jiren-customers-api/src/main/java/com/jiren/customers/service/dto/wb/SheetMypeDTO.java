package com.jiren.customers.service.dto.wb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetMypeDTO {

	String id;
	
	String b2bBusinessName;
	String b2bTradeName;
	String b2bDexCode;
	String b2bworkArea;
	String b2bSubWorkArea;
	String b2bAdviser;
	String b2bChannelOrigin;
	LocalDate b2bRegistryDate;
	LocalDate b2bDischargeDate;
	Integer b2bWave;
	String b2bSegment;
	Integer b2bStatus;
	BigDecimal b2bMinAmount;

	Boolean saasElectronicInvoice;
	String saasCurrency;
	
	SheetUserDTO owner;
	
	List<SheetDocumentDTO> documents = new ArrayList<>();
	List<SheetUserDTO> users = new ArrayList<>();
	List<SheetAddressDTO> addresses = new ArrayList<>();
	List<SheetPaymentTypeDTO> sellIns = new ArrayList<>();
	List<SheetPaymentTypeDTO> sellOuts = new ArrayList<>();
}
