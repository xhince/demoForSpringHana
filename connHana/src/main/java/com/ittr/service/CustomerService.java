package com.ittr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ittr.entity.Contact;
import com.ittr.entity.Customer;

@Service
public interface CustomerService {

	long getCount();

	List<Customer> findAllCustomers();

	boolean insertCustomer(Customer customer);

	Customer findCustomerById(Long id);

	boolean deleteCustomerById(Long id);
	
	boolean saveOrUpdateContact(Contact contact);
	
	Contact findContactById(Long id);
	
	boolean deleteContactById(Long id);
}
