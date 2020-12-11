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

import app.beans.CategoryType;
import app.beans.Company;
import app.beans.Coupon;
import app.exceptions.DoesNotExistException;
import app.exceptions.DuplicateException;
import app.services.AdminService;
import app.services.CompanyService;

@RestController
@RequestMapping("companyManager")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	public CompanyController() {
	}

	@Autowired
	private Map<String, Session> sessionsMap;

	@GetMapping("companyInfo/{token}")
	public Company companyInfo(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return companyEdit.getCompanyDetails();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@GetMapping("allCoupons/{token}")
	public List<Coupon> getAllCoupons(@PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return companyEdit.getCompanyCoupons();
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@GetMapping("typeCoupons/{type}/{token}")
	public List<Coupon> getTypeCoupons(@PathVariable String token, @PathVariable String type) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return companyEdit.getCompanyCoupons(CategoryType.valueOf(type));
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@GetMapping("cheapCoupons/{maxPrice}/{token}")
	public List<Coupon> getCheapCoupons(@PathVariable String token, @PathVariable double maxPrice) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				return companyEdit.getCompanyCoupons(maxPrice);
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@PostMapping("addCoupon/{type}/{token}")
	public Coupon newCoupon(@PathVariable String token, @RequestBody Coupon coup, @PathVariable int type)
			throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				try {
					coup.setType(CategoryType.valueOf(type));
					companyEdit.addCoupon(coup);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return coup;
				} catch (DuplicateException e) {
					return new Coupon(0, "DuplicateException", companyEdit.getCompanyDetails(), 0, 0);
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

	@DeleteMapping("allCoupons/delete/{id}/{token}")
	public void deleteCoupon(@PathVariable int id, @PathVariable String token) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				ses.setLastAccessed(System.currentTimeMillis());
				sessionsMap.put(token, ses);
				companyEdit.deleteCoupon(id);
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException();
	}

	@PostMapping("updateCoupon/{type}/{token}")
	public int updateCoupon(@PathVariable String token, @RequestBody Coupon coupon, @PathVariable String type) throws Exception {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				try {
					coupon.setType(CategoryType.valueOf(type));
					companyEdit.updateCoupon(coupon);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return 1;
				} catch (DoesNotExistException e) {
					return 2;
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}
	
	@GetMapping("allCoupons/{id}/{token}")
	public Coupon companyById(@PathVariable int id, @PathVariable String token) throws Exception  {
		Session ses = sessionsMap.get(token);
		if (ses != null) {
			if (ses.getService() instanceof CompanyService) {
				CompanyService companyEdit = (CompanyService) ses.getService();
				try {
					Coupon x = companyEdit.findCoupon(id);
					ses.setLastAccessed(System.currentTimeMillis());
					sessionsMap.put(token, ses);
					return x;
				} catch (DoesNotExistException e) {
					return new Coupon(0, "DoesNotExistException", companyEdit.getCompanyDetails(), 0, 0);
				}
			} else
				throw new Exception();
		} else
			throw new DoesNotExistException(); // "Token expired"
	}

}
