package com.jiren.customers.domain.exception.enumerator.filter;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CustomerFilter {
    String query;
    Boolean active;
    String workArea;                   
    String subWorkArea;                   
    String segment;                   
    Integer state;
    Integer wave;
    LocalDate startRegistryDate;
    LocalDate endRegistryDate;
    LocalDate startDischargeDate;
    LocalDate endDischargeDate;

    String department;
    String province;                 
    String district;                   
}
