package app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.beans.CategoryType;
import app.beans.Company;
import app.beans.Coupon;
import app.beans.Customer;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;

@Service
public class CompanyService extends ClientService {

	private int CompanyId;

	/**
	 * @return "true", if the parameters received are those of a company and sets
	 *         the value of "CompanyId" (internal parameter of Company object) to
	 *         the equivalent value from the "companies" table. otherwise returns
	 *         "false".
	 */
	@Override
	public boolean login(String email, String password) {
		if (editcompanies.existsByEmailAndPassword(email, password)) {
			Company check = editcompanies.findByEmailAndPassword(email, password);
			this.CompanyId = check.getId();
			return true;
		}
		System.out.println("Company login: wrong password or email entered");
		return false;
	}

	/**
	 * @return CompanyId
	 */
	public int getCompanyId() {
		return CompanyId;
	}

	/**
	 * adds a coupon to "coupons" table if the coupon received (via parameter
	 * coupon) is not a duplicate.
	 * 
	 * @param coupon
	 * @throws DuplicateException
	 */
	public void addCoupon(Coupon coupon) throws DuplicateException {
		if (editcoupons.existsByTitle(coupon.getTitle())) 
			throw new DuplicateException();
		editcoupons.save(coupon);
	}

	/**
	 * @param id
	 * @return coupon with that id
	 * @throws DoesNotExistException
	 */
	public Coupon findCoupon(int id) throws DoesNotExistException { 
		if (editcoupons.findById(id).isPresent()) //same as: if(editcoupons.existsById(id))
			return editcoupons.findById(id).get();
		else
			throw new DoesNotExistException();
	}

	/**
	 * updates a coupon in "coupons" table if the coupon received (via parameter
	 * coupon) exists in that table.
	 * 
	 * @param coupon
	 * @throws DoesNotExistException
	 */
	public void updateCoupon(Coupon coupon) throws DoesNotExistException {
		Coupon check = findCoupon(coupon.getId());
		if (check != null) {
			check.setAmount(coupon.getAmount());
			check.setTitle(coupon.getTitle());
			check.setDescription(coupon.getDescription());
			check.setImage(coupon.getImage());
			check.setPrice(coupon.getPrice());
			check.setStartDate(coupon.getStartDate());
			check.setEndDate(coupon.getEndDate());
			check.setType(coupon.getType());
			editcoupons.save(check);
		} else
			throw new DoesNotExistException();
	}

	/**
	 * checks by the parameter received if a coupon exists in "coupons" table, if so
	 * it then deletes it and any purchases of it .
	 * 
	 * @param id
	 */
	public void deleteCoupon(int id) {
		if (editcoupons.existsById(id)) {
			List<Customer> Customers = getAllCustomers();
			for (int i = 0; i < Customers.size(); i++) {
				List<Coupon> customerCoupons = Customers.get(i).getCoupons();
				for (int j = 0; j < customerCoupons.size(); j++) {
					if(customerCoupons.get(j).getId()==id) 
						customerCoupons.remove(j);
				}
				updateCustomerCoupons(Customers.get(i).getEmail(), customerCoupons);
			}
			editcoupons.deleteById(id); // if coupon deleted does it deletes its value automatically from linked table?- NO!!
		} else
			System.out.println("No coupon exists with the entered id");
	}
	

	/**
	 * updates a customers coupon list
	 * @param email
	 * @param customerCoupons
	 */
	public void updateCustomerCoupons(String email, List<Coupon> customerCoupons){
			Customer check = editcustomers.findByEmail(email);
			check.setCoupons(customerCoupons);
			editcustomers.save(check);
	}
	

	/**
	 * @return List of existing customers
	 */
	public List<Customer> getAllCustomers() {//<---
		return editcustomers.findAll();
	}

	/**
	 * @return List of coupons produced by the current company (via CompanyId).
	 */
	public List<Coupon> getCompanyCoupons() {
		Optional<Company> current = editcompanies.findById(CompanyId);
		return current.get().getCompanyCoupons();
		// Alternatively use coupon table directly <---- אבל סיבוכיות יוצר גדולה
	}

	/**
	 * @param cat
	 * @return List of coupons produced by the current company (via CompanyId), from
	 *         a single category of products (defined by the value of parameter
	 *         "cat") .
	 */
	public List<Coupon> getCompanyCoupons(CategoryType cat) {
		return editcoupons.findByCompanyIdAndType(getCompanyDetails(), cat);
	}

	/**
	 * @param maxPrice
	 * @return List of coupons produced by the current company (via CompanyId),
	 *         beneath a price limit as defined by by the value of parameter
	 *         "maxPrice".
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return editcoupons.findByCompanyIdAndPriceLessThan(getCompanyDetails(), maxPrice);
	}

	/**
	 * @return current company details.
	 */
	public Company getCompanyDetails() {
		Optional<Company> temp = editcompanies.findById(CompanyId);
		return temp.get();
	}
}
