package com.ittr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.ittr.entity.Contact;
import com.ittr.entity.Customer;
import com.ittr.repository.ContactRepository;
import com.ittr.repository.CustomerRepository;

@Service
//@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ContactRepository contactRepository;

	public long getCount() {
		long count = customerRepository.count();
		return count;
	}

	public List<Customer> findAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customers::add);
		return customers;
	}

	public boolean insertCustomer(Customer customer) {
		try {
			customerRepository.save(customer);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Customer findCustomerById(Long id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		return customer;
	}

	public boolean deleteCustomerById(Long id) {
		try {
			customerRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveOrUpdateContact(Contact contact) {
		try {
			contactRepository.save(contact);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Contact findContactById(Long id) {
		Contact contact = contactRepository.findById(id).orElse(null);
		return contact;
	}

	public boolean deleteContactById(Long id) {
		try {
			contactRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
