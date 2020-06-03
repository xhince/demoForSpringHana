package com.ittr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ittr.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
