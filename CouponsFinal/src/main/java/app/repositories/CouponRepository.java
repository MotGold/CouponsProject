package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.beans.CategoryType;
import app.beans.Company;
import app.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> findByCompanyIdAndType(Company companyId, CategoryType type);

	List<Coupon> findByCompanyIdAndPriceLessThan(Company companyId, double price);
	
	//List<Coupon> findByCompanyIdAndPriceLessThan(Company id, double price);

	Boolean existsByTitle(String title);

}
