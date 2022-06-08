package com.jiren.legalsections.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LegalSections {	
	String guid;
	Customer customer;
	
	Integer type;
	LocalDateTime date;
	Boolean active;
}
