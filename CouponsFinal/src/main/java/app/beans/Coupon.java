package app.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "coupons")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String image;
	@ManyToOne
	private Company companyId;
	@Column
	private int amount;
	@Column
	private double price;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private CategoryType type;

	public Coupon(String title, String description, String image, Company companyId, int amount, double price,
			Date startDate, Date endDate, CategoryType type) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
		this.companyId = companyId;
		this.amount = amount;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public Coupon() {
	}
	
	/** 
	 * only used to return exceptions as "fake" Company Objects
	 */
	public Coupon(int id, String title, Company companyId, int amount, double price) {
		super();
		this.id = id;
		this.title = title;
		this.companyId = companyId;
		this.amount = amount;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public Company getCompanyId() {
		return companyId;
	}

	@Override
	public String toString() {
		return "Coupon " + id + ": title=" + title + ", amount=" + amount + ", price="
				+ price +", endDate=" + endDate + ", type=" + type;
	}

}
