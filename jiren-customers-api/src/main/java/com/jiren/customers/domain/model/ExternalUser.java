package com.jiren.customers.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalUser {
	String customerGuid;
	String guid;
	String typeDocument;
	String numberDocument;
	String name;
	String firstLastName;	
	String secondLastName;	
	String email;
	String phone;
	Boolean active;
	String tenant;

}
