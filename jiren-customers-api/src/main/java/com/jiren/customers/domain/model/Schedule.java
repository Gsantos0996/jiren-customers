package com.jiren.customers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {	
	String guid;
	Business business;
	
	String day;
	String startTime;
	String endTime;
	Boolean attention;
	Boolean active;
	
	@Override
	public String toString() {
		return "Schedule [guid=" + guid + ", day=" + day + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", attention=" + attention + ", active=" + active + "]";
	}
	
	
}
