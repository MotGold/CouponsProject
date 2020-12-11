package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findByEmail(String email);

	Customer findByEmailAndPassword(String email, String password);

	boolean existsByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

}
