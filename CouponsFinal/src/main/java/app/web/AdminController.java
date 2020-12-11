package app.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.beans.Company;
import app.beans.Customer;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;
import app.services.AdminService;

@RestController
@RequestMapping("adminManager")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	public AdminController() {
	}

	@Autowired
	private Map<String, Session> sessionsMap;

	@PostMapping("addCompany/{token}")
	public Company newCompany(@PathVariable String token, @RequestBody Company comp) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					adminEdit.addCompany(comp);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return comp;
				} catch (DuplicateException e) {
					return new Company(0, "DuplicateException", "fail@error", "Duplicate");
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@PostMapping("updateCompany/{token}")
	public int updateCompany(@PathVariable String token, @RequestBody Company comp) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					adminEdit.updateCompany(comp);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return 3; // ok
				} catch (DoesNotExistException e) {
					return 2; // no such company
				} catch (DuplicateException e) {
					return 1; // Duplicate email
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("companies/{token}")
	public List<Company> getAllCompanies(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return adminEdit.getAllCompanies();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("companies/{id}/{token}")
	public Company companyById(@PathVariable int id, @PathVariable String token) throws Exception  {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					Company x = adminEdit.getOneCompany(id);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return x;
				} catch (DoesNotExistException e) {
					return new Company(0, "DoesNotExistException", "fail@error", "DoesNotExist");
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@DeleteMapping("companies/delete/{id}/{token}")
	public void deleteCompany(@PathVariable int id, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				adminEdit.deleteCompany(id);
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException();
	}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping("addCustomer/{token}")
	public Customer newCustomer(@PathVariable String token, @RequestBody Customer buyer) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					adminEdit.addCustomer(buyer);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return buyer;
				} catch (DuplicateException e) {
					return new Customer(0, "DuplicateException", "error", "fail@error", "Duplicate", null);
				}
				
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@GetMapping("customers/{token}")
	public List<Customer> getAllCustomers(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return adminEdit.getAllCustomers();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@DeleteMapping("customers/delete/{id}/{token}")
	public void deleteCustomer(@PathVariable int id, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				adminEdit.deleteCustomer(id);;
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException();
	}
	
	@PostMapping("updateCustomer/{token}")
	public int updateCustomer(@PathVariable String token, @RequestBody Customer buyer) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					adminEdit.updateCustomer(buyer);;
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return 3; // ok
				} catch (DoesNotExistException e) {
					return 2; // no such company
				} catch (DuplicateException e) {
					return 1; // Duplicate email
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@GetMapping("customers/{id}/{token}")
	public Customer customerById(@PathVariable int id, @PathVariable String token) throws Exception  {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof AdminService) {
				AdminService adminEdit = (AdminService) ses.getService();
				try {
					Customer x = adminEdit.getOneCustomer(id);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return x;
				} catch (DoesNotExistException e) {
					return new Customer(0, "DoesNotExistException", "error", "fail@error", "DoesNotExist", null);
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

}
