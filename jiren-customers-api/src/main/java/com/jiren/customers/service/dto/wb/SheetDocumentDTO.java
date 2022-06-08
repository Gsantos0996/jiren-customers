package com.jiren.customers.service.dto.wb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetDocumentDTO {

	String id;
	
	String typeDocument;
	String numberDocument;	
}
