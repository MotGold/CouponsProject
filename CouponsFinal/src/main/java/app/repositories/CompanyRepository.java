package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findCompanyByEmail(String email);
	
	boolean existsByEmail(String email);

	Company findCompanyByName(String name);
	
	Company findByEmailAndPassword(String email, String password);
	
	boolean existsByEmailAndPassword(String email, String password);
	
	boolean existsByName(String Name);
}
