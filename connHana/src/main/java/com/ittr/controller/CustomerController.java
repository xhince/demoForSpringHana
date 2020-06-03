package com.ittr.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ittr.entity.Contact;
import com.ittr.entity.Customer;
import com.ittr.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping(value = { "", "/" }, produces = "application/json")
	public String welcome(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("welcome to hana connection %s!", name);
	}

	@GetMapping(value = "/getCustomers", produces = "application/json")
	public List<Customer> findAllCustomers() {
		return customerService.findAllCustomers();
	}

	@RequestMapping("/count")
	public long count() {
		log.info("Search total number of customers");
		return customerService.getCount();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public boolean addCustomers(@RequestBody Customer customer) {

		log.info("Creation/Updating Customer - " + customer.toString());
		return customerService.insertCustomer(customer);
	}

	@RequestMapping("/id/{id}")
	public Customer findById(@PathVariable long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		log.info("Searching customer with ID - " + id);
		return customerService.findCustomerById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public boolean deleteCustomer(@PathVariable long id) {
		return customerService.deleteCustomerById(id);
	}

	@PostMapping(value = "/{customer_id}/addContact", produces = "application/json")
	public Contact addContact(@PathVariable(value = "customer_id") Long customerId, @RequestBody Contact contact) {
		Customer customer = customerService.findCustomerById(customerId);
		contact.setCustomer(customer);
		customerService.saveOrUpdateContact(contact);
		return contact;
	}
	
	@PostMapping(value = "/{customer_id}/addContact2", produces = "application/json")
	public Contact addContact2(@PathVariable(value = "customer_id") Long customerId, @RequestBody Contact contact) {
		Customer customer = customerService.findCustomerById(customerId);
		customer.addContact2(contact);
		customerService.saveOrUpdateContact(contact);
		return contact;
	}
	
	@PutMapping(value = "/{customer_id}/updateContact/{contact_id}", produces = "application/json")
	public Contact updateContact(@PathVariable(value = "customer_id") Long customerId,
			@PathVariable(value = "contact_id") Long contactId, @RequestBody Contact reqContact) {
		Contact dbContact = customerService.findContactById(contactId);
		if (dbContact != null && dbContact.getCustomer().getId() == customerId) {
			dbContact.setPhone(reqContact.getPhone());
			customerService.saveOrUpdateContact(dbContact);
			return dbContact;
		} else {
			throw new ResourceNotFoundException("Contact Id " + contactId + " not found");
		}
	}
	@DeleteMapping(value="/{customer_id}/deleteContact/{contact_id}")
	public boolean deleteContact(@PathVariable(value= "customer_id") Long customerId,
			@PathVariable(value="contact_id") Long contactId){
		Contact contact = customerService.findContactById(contactId);
		if(contact != null && contact.getCustomer().getId() == customerId) {
			return customerService.deleteContactById(contact.getId());
		} else {
			throw new ResourceNotFoundException("Contact Id " + contactId + " not found");
		}
	}
	
	@DeleteMapping(value="/{customer_id}/deleteContact2/{contact_id}")
	public boolean deleteContact2(@PathVariable(value= "customer_id") Long customerId,
			@PathVariable(value="contact_id") Long contactId){
		Contact contact = customerService.findContactById(contactId);
		if(contact != null && contact.getCustomer().getId() == customerId) {
			contact.getCustomer().removeContact2(contact);			
			// customer.removeContact2(contact);
			customerService.saveOrUpdateContact(contact);
			return true;
		} else {
			throw new ResourceNotFoundException("Contact Id " + contactId + " not found");
		}
	}
}
