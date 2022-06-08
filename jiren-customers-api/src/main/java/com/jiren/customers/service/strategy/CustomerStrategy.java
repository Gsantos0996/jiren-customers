package com.jiren.customers.service.strategy;

import com.jiren.customers.domain.model.Customer;

public interface CustomerStrategy {
	Boolean validate();
	Customer generate();
}
