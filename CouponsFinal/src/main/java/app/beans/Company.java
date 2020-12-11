package app.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@OneToMany(mappedBy = "companyId", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Coupon> companyCoupons;

	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	/** 
	 * only used to return exceptions as "fake" Company Objects
	 */
	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Company() {

	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCompanyCoupons() {
		return companyCoupons;
	}

	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Company " + id + ": name=" + name + ", email=" + email + ", password=" + password + ", companyCoupons="
				+ companyCoupons;
	}

}
