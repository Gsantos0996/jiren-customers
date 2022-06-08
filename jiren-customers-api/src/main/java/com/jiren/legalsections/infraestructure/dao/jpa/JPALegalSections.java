package com.jiren.legalsections.infraestructure.dao.jpa;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(schema = "customers", name = "legal_sections")
public class JPALegalSections {
	@Id
	String guid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", nullable = false)
	JpaUser user;
	
	Integer type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime date;
	Boolean active;
}
