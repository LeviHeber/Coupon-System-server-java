package couponsSystem.core.entites;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import couponsSystem.core.reposetory.CompanyReposetory;
import couponsSystem.core.services.CompanyService;
import couponsSystem.core.services.Impl.CompanyServiceImpl;

/**
 * A Coupon issuing company that is one of the system's client's.
 * <p>
 * The company is responsible for issuance {@link Coupon}.
 * 
 * @author Levi Heber
 *
 * @see CompanyReposetory
 * @see CompanyService
 * @see CompanyServiceImpl
 */
@Entity
public class Company {

	/**
	 * The company unique identification number for the purposes of operating the
	 * system.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Company Name.
	 */
	private String name;

	/**
	 * Company email address.
	 */
	private String email;

	/**
	 * The company login password.
	 */
	private String password;

	/**
	 * A collection of {@link Coupon} produced by the company.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;

	public Company() {
		super();
	}

	public Company(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

}
