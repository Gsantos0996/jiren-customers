package com.jiren.customers.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Business {	
	String guid;	
	Customer customer;
	Ubigeo ubigeo;
	
	Integer deliveryDistance;
	BigDecimal deliveryCost;
	String address;
	String postalCode;
	String reference;
	BigDecimal latitude;
	BigDecimal longitude;
	Integer zone;
	String annex;
	Boolean active;
	
	String userCreated;
	LocalDateTime dateCreated;
	String userUpdated;
	LocalDateTime dateUpdated;
	
	List<Schedule> schedules;
	List<TimeWindow> timeWindows;
	
	@Override
	public String toString() {
		return "Business [guid=" + guid + ", deliveryDistance=" + deliveryDistance + ", deliveryCost=" + deliveryCost
				+ ", address=" + address + ", postalCode=" + postalCode + ", reference=" + reference + ", latitude="
				+ latitude + ", longitude=" + longitude + ", zone=" + zone + ", annex=" + annex + ", active=" + active
				+ ", userCreated=" + userCreated + ", dateCreated=" + dateCreated + ", userUpdated=" + userUpdated
				+ ", dateUpdated=" + dateUpdated + ", schedules=" + schedules + ", timeWindows=" + timeWindows + "]";
	}
	
	
}
