package app.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.beans.CategoryType;
import app.beans.Company;
import app.beans.Coupon;
import app.beans.Customer;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;
import app.exceptions.ExpiredException;
import app.exceptions.OutOfStockException;
import app.services.CompanyService;
import app.services.CustomerService;

@RestController
@RequestMapping("customerManager")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	public CustomerController() {

	}

	@Autowired
	private Map<String, Session> sessionsMap;

	@GetMapping("customerInfo/{token}")
	public Customer customerInfo(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.getCustomerDetails();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("allCustomerCoupons/{token}")
	public List<Coupon> getAllCustomerCoupons(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.getCustomerCoupons();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("allCoupons/{token}")
	public List<Coupon> getAllCoupons(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.seeAllCoupons();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("findCoupon/{id}/{token}")
	public Coupon couponById(@PathVariable int id, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.findCoupon(id);
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@PostMapping("purchaseCoupon/{email}/{token}")
	public String purchaseACoupon(@PathVariable String email, @PathVariable String token, @RequestBody Coupon coupon)
			throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				try {
					customerEdit.purchaseCoupon(coupon, email);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return "1";
				} catch (DoesNotExistException e) {
					return "2";
				} catch (OutOfStockException e) {
					return "3";
				} catch (ExpiredException e) {
					return "4";
				} catch (DuplicateException e) {
					return "5";
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("clientCoupons/{token}")
	public List<Coupon> getCustomerAllCoupons(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.getCustomerCoupons();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("typeCoupons/{type}/{token}")
	public List<Coupon> getCustomerTypeCoupons(@PathVariable String type, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.getCustomerCoupons(CategoryType.valueOf(type));
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("cheapCoupons/{price}/{token}")
	public List<Coupon> getCheapCoupons(@PathVariable double price, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CustomerService) {
				CustomerService customerEdit = (CustomerService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return customerEdit.getCustomerCoupons(price);
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

}
