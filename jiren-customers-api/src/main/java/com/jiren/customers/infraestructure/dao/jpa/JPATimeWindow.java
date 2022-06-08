package com.jiren.customers.infraestructure.dao.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "customers", name = "time_window")
public class JPATimeWindow {
	@Id
	String guid;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_guid", referencedColumnName = "guid", nullable = false)
	JPABusiness business;

	String startTime;
	String endTime;
	Boolean active;
}
