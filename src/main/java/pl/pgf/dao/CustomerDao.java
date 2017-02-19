package pl.pgf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pgf.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
	
	
	Customer findByName(String name);
}
