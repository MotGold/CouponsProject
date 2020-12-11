package app.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repositories.CompanyRepository;
import app.repositories.CouponRepository;
import app.repositories.CustomerRepository;

@Service
public abstract class ClientService {
	
	@Autowired
	protected CompanyRepository editcompanies;
	@Autowired
	protected CouponRepository editcoupons;
	@Autowired
	protected CustomerRepository editcustomers;

	public abstract boolean login(String email, String password) throws SQLException;

}
