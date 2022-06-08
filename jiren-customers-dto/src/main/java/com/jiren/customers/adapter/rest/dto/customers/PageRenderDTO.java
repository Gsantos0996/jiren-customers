package com.jiren.customers.adapter.rest.dto.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageRenderDTO {
    private long page;
    private long totalRecords;
    private long totalPages;
}