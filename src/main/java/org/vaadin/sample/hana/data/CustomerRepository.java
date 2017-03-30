package org.vaadin.sample.hana.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
