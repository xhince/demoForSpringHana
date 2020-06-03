package com.ittr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "CUSTOMER_AUTO", schema = "xhince")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Customer implements Serializable {

	private static final long serialVersionUID = -1152856493607506962L;

	@Id
	//not use this // @GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	// not use this // @GeneratedValue(strategy=GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
    private Long id;

    @Column(length = 20, name = "NAME")
    private String name;

    @Column(length = 20, name = "PASSWORD")
    private String password;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
    
    public void addContact2(Contact contact) {
    	contacts.add(contact);
    	contact.setCustomer(this);
    }
    
    public void removeContact2(Contact contact) {
    	contacts.remove(contact);
    	contact.setCustomer(null);
    }
}


