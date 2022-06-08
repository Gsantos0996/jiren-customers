package com.jiren.customers.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {	
	String guid;	
	Customer customer;
	
	String typeDocument;
	String numberDocument;
	String name;
	String firstLastName;	
	String secondLastName;	
	String email;
	String phone;
	Boolean owner;
	Boolean active;
	
	String userCreated;
	LocalDateTime dateCreated;
	String userUpdated;
	LocalDateTime dateUpdated;
	
	@Override
	public String toString() {
		return "User [guid=" + guid + ", typeDocument=" + typeDocument + ", numberDocument=" + numberDocument
				+ ", name=" + name + ", firstLastName=" + firstLastName + ", secondLastName=" + secondLastName
				+ ", email=" + email + ", phone=" + phone + ", owner=" + owner + ", active=" + active + ", userCreated="
				+ userCreated + ", dateCreated=" + dateCreated + ", userUpdated=" + userUpdated + ", dateUpdated="
				+ dateUpdated + "]";
	}
	
}
