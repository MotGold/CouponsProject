package app.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import app.beans.Company;
import app.beans.Customer;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;

@Service
public class AdminService extends ClientService {

	private static final String password = "admin";
	private static final String email = "admin@admin.com";

	/**
	 * @return true if the parameters received are those of an administrator
	 */
	public boolean login(String pass, String mail) throws SQLException {
		if (password.equals(pass) && email.equals(mail)) {
			return true;
		}
		System.out.println("Administrator login: wrong password or email entered");
		return false;
	}

	/**
	 * @return List of existing companies
	 */
	public List<Company> getAllCompanies() {
		return editcompanies.findAll();
	}

	/**
	 * adds a company to "companies" table if the company received (via parameter
	 * comp) is not a duplicate.
	 * 
	 * @param comp
	 */
	public void addCompany(Company comp) throws DuplicateException {
		if (editcompanies.existsByName(comp.getName()))
			throw new DuplicateException();
		else
			editcompanies.save(comp);
	}

	/**
	 * updates a company in "companies" table if the company received (via parameter
	 * comp) exists in that table.
	 * 
	 * @param comp
	 * @throws DoesNotExistException
	 * @throws DuplicateException
	 */
	public void updateCompany(Company comp) throws DoesNotExistException, DuplicateException {
		if (editcompanies.existsByName(comp.getName())) {
			Company check = editcompanies.findCompanyByName(comp.getName());
			check.setPassword(comp.getPassword());
			if (!check.getEmail().equals(comp.getEmail())) {
				if (editcompanies.existsByEmail(comp.getEmail()))
					throw new DuplicateException();
				else
					check.setEmail(comp.getEmail());
			}
			editcompanies.save(check);
		} else
			throw new DoesNotExistException();

	}

	/**
	 * checks by the parameter received if a company exists in "companies" table, if
	 * so it then deletes it and any coupon associated with it .
	 * 
	 * @param compId
	 */
	public void deleteCompany(int compId) {
		if (editcompanies.existsById(compId))
			editcompanies.deleteById(compId);
		else
			System.out.println("No company exists with the entered id.");
	}

	/**
	 * @param compId
	 * @return company that has id identical to the one contained in the parameter
	 *         received.
	 * @throws DoesNotExistException
	 */
	public Company getOneCompany(int compId) throws DoesNotExistException {
		if (editcompanies.findById(compId).isPresent())
			return editcompanies.findById(compId).get();
		else
			throw new DoesNotExistException();
	}

	/**
	 * adds a customer to "customers" table if the customer received (via parameter
	 * customer) is not a duplicate.
	 * 
	 * @param customer
	 * @throws DuplicateException
	 */
	public void addCustomer(Customer customer) throws DuplicateException {
		if (editcustomers.existsByEmail(customer.getEmail()))
			throw new DuplicateException();
		else
			editcustomers.save(customer);
	}

	/**
	 * updates a customer in "customers" table if the customer received (via
	 * parameter customer) exists in that table.
	 * 
	 * @param customer
	 * @throws DoesNotExistException
	 * @throws DuplicateException
	 */
	public void updateCustomer(Customer customer) throws DoesNotExistException, DuplicateException {
		if (editcustomers.existsById(customer.getId())) {
			if (editcustomers.existsByEmail(customer.getEmail()))
				if(editcustomers.findByEmail(customer.getEmail()).getId()!=customer.getId())
				throw new DuplicateException();
			editcustomers.save(customer);
		} else
			throw new DoesNotExistException();
	}

	/**
	 * checks by the parameter received if a customer exists in "customers" table,
	 * if so it then deletes that customer and his purchased coupons .
	 * 
	 * @param id
	 */
	public void deleteCustomer(int id) {
		if (editcustomers.existsById(id))
			editcustomers.deleteById(id);
		else
			System.out.println("No customer exists with the entered id");
	}

	/**
	 * @return List of existing customers
	 */
	public List<Customer> getAllCustomers() {
		return editcustomers.findAll();
	}

	/**
	 * @param id
	 * @return customer that has id identical to the one contained in the parameter
	 *         received.
	 */
	public Customer getOneCustomer(int id) throws DoesNotExistException {
		if (editcustomers.findById(id).isPresent())
			return editcustomers.findById(id).get();
		else
			throw new DoesNotExistException();
	}

}
