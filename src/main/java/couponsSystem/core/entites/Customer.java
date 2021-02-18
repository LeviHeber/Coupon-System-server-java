package couponsSystem.core.entites;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import couponsSystem.core.reposetory.CustomerReposetory;
import couponsSystem.core.services.Impl.CustomerServiceImpl;

/**
 * A coupon customer that is one of the system's client's.
 * <p>
 * The customer purchases {@link Coupon} issued by a {@link Company}.
 * 
 * @author Levi Heber
 *
 * @see CustomerReposetory
 * @see CustomerServiceImpl
 */
@Entity
public class Customer {

	/**
	 * The customer unique identification number for the purposes of operating the
	 * system.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * customer first name.
	 */
	private String firstName;

	/**
	 * customer last name.
	 */
	private String lastName;

	/**
	 * customer email address.
	 */
	private String email;

	/**
	 * The customer login password.
	 */
	private String password;

	/**
	 * A collection of {@link Coupon} purchases by the customer.
	 * <p>delete customer will not effect the coupons he purchase.
	 */
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "coupon_customer", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private List<Coupon> coupons;

	public Customer() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}

}
