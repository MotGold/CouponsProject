package app;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.services.AdminService;
import app.services.ClientService;
import app.services.CompanyService;
import app.services.CustomerService;

@Component
public class LoginManager {

	@Autowired
	private ApplicationContext ctx;

	/**
	 * 
	 * @param email
	 * @param password
	 * @param client
	 * @return Correct Service instance for the received login data (for company or
	 *         customer it will also contain equivalent id for the received login
	 *         data) or null if no matches are found.
	 * @throws SQLException
	 */
	public ClientService login(String email, String password, ClientType client)
			throws SQLException {

		switch (client) {
		case Administrator:
			AdminService admin = ctx.getBean(AdminService.class);
			if (admin.login(password, email))
				return admin;
			break;
		case Company:
			CompanyService company = ctx.getBean(CompanyService.class);
			if (company.login(email, password))
				return company;
			break;
		case Customer:
			CustomerService customer = ctx.getBean(CustomerService.class);
			if (customer.login(email, password))
				return customer;
			break;
		default:
			break;
		}
		return null;
	}
}
