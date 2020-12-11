package app.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.beans.CategoryType;

import app.beans.Coupon;
import app.beans.Customer;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;
import app.exceptions.ExpiredException;
import app.exceptions.OutOfStockException;

@Service
public class CustomerService extends ClientService {

	private int customerId;

	/**
	 * @return customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	
	/**
	 * @param id
	 * @return coupon with that id
	 * @throws DoesNotExistException
	 */
	public Coupon findCoupon(int id) throws DoesNotExistException {
		if (editcoupons.findById(id).isPresent())
			return editcoupons.findById(id).get();
		else
			throw new DoesNotExistException();
	}

	/**
	 * @return "true", if the parameters received are those of a customer and sets
	 *         the value of "customerId" (internal parameter of Customer object) to
	 *         the equivalent value from the "customers" table. otherwise returns
	 *         "false".
	 */
	@Override
	public boolean login(String email, String password) throws SQLException {
		if (editcustomers.existsByEmailAndPassword(email, password)) {
			Customer check = editcustomers.findByEmailAndPassword(email, password);
			this.customerId = check.getId();
			return true;
		}
		System.out.println("Customer login: wrong password or email entered");
		return false;
	}

	/**
	 * allows purchase of coupon (from received coupon parameter) if it exists, not
	 * out of stock, not expired and wasn't purchased before. removes 1 unit from
	 * the amount of that coupon and adds a purchase to the customers_vs_coupons
	 * table.
	 * 
	 * @param coupon
	 * @throws DoesNotExistException
	 * @throws OutOfStockException
	 * @throws ExpiredException
	 * @throws DuplicateException
	 */
	public void purchaseCoupon(Coupon coupon, String email)
			throws DoesNotExistException, OutOfStockException, ExpiredException, DuplicateException {
		if (editcoupons.existsById(coupon.getId())) {
			if (coupon.getAmount() < 1)
				throw new OutOfStockException();
			if (coupon.getEndDate().before(new Date()))
				throw new ExpiredException();
			List<Coupon> customerCoupons = getCustomerCoupons();
			for (int i = 0; i < customerCoupons.size(); i++) {
				if (customerCoupons.get(i).getId() == coupon.getId())
					throw new DuplicateException();
			}
			coupon.setAmount(coupon.getAmount() - 1);
			customerCoupons.add(coupon);
			updateCustomerCoupons(email,customerCoupons);
		} else {
			throw new DoesNotExistException();

		}
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
	 * @return List of coupons purchased by the current customer (via customerId).
	 */
	public List<Coupon> getCustomerCoupons() {
		Optional<Customer> current = editcustomers.findById(customerId);
		return current.get().getCoupons();
	}
	
	/**
	 * @return List of all available coupons;
	 */
	public List<Coupon> seeAllCoupons() {
		return editcoupons.findAll();
	}

	/**
	 * @param cat
	 * @return List of coupons purchased by the current customer (via customerId),
	 *         from a single category of products (defined by the value of parameter
	 *         "cat") .
	 */
	public List<Coupon> getCustomerCoupons(CategoryType cat) {
		List<Coupon> customerCoupons = getCustomerCoupons();
		List<Coupon> categoryCoupons = new ArrayList<>();
		for (int i = 0; i < customerCoupons.size(); i++) {
			if (customerCoupons.get(i).getType() == cat)
				categoryCoupons.add(customerCoupons.get(i));
		}
		return categoryCoupons;
	}

	/**
	 * @param maxPrice
	 * @return List of coupons purchased by the current customer (via customerId),
	 *         beneath a price limit as defined by by the value of parameter
	 *         "maxPrice"
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> customerCoupons = getCustomerCoupons();
		List<Coupon> cheapCoupons = new ArrayList<>();
		for (int i = 0; i < customerCoupons.size(); i++) {
			if (customerCoupons.get(i).getPrice() < maxPrice)
				cheapCoupons.add(customerCoupons.get(i));
		}
		return cheapCoupons;
	}
	
	/**
	 * @return current company details.
	 */
	public Customer getCustomerDetails() {
		Optional<Customer> temp = editcustomers.findById(customerId);
		return temp.get();
	}

}
