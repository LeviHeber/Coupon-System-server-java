package couponsSystem.core.entites;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Coupon {

	/**
	 * The coupon unique identification number for the purposes of operating the
	 * system.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * The unique identification number of the {@link Company} that issued the
	 * coupon.
	 */
	@ManyToOne()
	private Company company;

	/**
	 * The coupon {@link Category}.
	 */
	@Enumerated(EnumType.STRING)
	private Category category;

	/**
	 * Coupon title.
	 */
	private String title;

	/**
	 * Description of the coupon contents.
	 */
	private String description;

	/**
	 * inception date of the coupon and the start of the purchase option.
	 */
	private LocalDate startDate;

	/**
	 * Expiration date of the coupon and closing of the purchase option.
	 */
	private LocalDate endDate;

	/**
	 * The amount of possible purchases of the coupon.
	 */
	private Integer amount;

	/**
	 * Coupon price.
	 */
	private Double price;

	/**
	 * Coupon image.
	 */
	private String image;

	/**
	 * A collection of {@link Customer} that purchase the coupon.
	 * <p>deleted coupon will remove it from the customer purchases.
	 */
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "coupon_customer", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private List<Customer> customers;

	/**
	 * A final collection of the categories in which {@link Coupon} can be generated
	 * 
	 * @author Levi Heber
	 *
	 */
	public enum Category {
		FOOD, ELECTRICITY, RESTAURANT, VACATION;
	}

	public Coupon() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coupon)) {
			return false;
		}
		Coupon other = (Coupon) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", company=" + company + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}

}
