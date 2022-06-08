package com.jiren.customers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeWindow {	
	String guid;
	Business business;
	
	String startTime;
	String endTime;
	Boolean active;
	
	@Override
	public String toString() {
		return "TimeWindow [guid=" + guid + ", startTime=" + startTime + ", endTime=" + endTime + ", active=" + active
				+ "]";
	}
	
}
