package couponsSystem.core.test;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.entites.Coupon.Category;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.services.AdminService;
import couponsSystem.core.services.CompanyService;
import couponsSystem.core.services.CustomerService;
import couponsSystem.core.services.Impl.LoginServiceImpl;
import couponsSystem.core.services.Impl.LoginServiceImpl.ClientType;

/**
 * 
 * Activating all system processes, deleting coupons daily and all test methods.
 * 
 * @author Levi Heber
 *
 */
@Component
public class Test {

	@Autowired
	private LoginServiceImpl loginManager;

	/**
	 * 
	 * Activating all system processes, deleting coupons daily and all test methods.
	 */
	public void testAll() {
		try {
			System.out.println("=============");
			admin();
			System.out.println("=============");
			Coupon coupon = company();
			System.out.println("=============");
			customer(coupon);
			System.out.println("==========================================");
			finish(coupon);

		} catch (CouponsSystemException e) {
			System.out.println(">>>>>>>>>" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Success testing of all administrator methods.
	 * <p>
	 * The process produces a company and a customer, updates and deletes them for
	 * completion.
	 * <p>
	 * This method should not throw an Exception.
	 * 
	 * @throws CouponsSystemException
	 */
	private void admin() throws CouponsSystemException {
		Company company = new Company();
		company.setName("meir");
		company.setEmail("meir@meir");
		company.setPassword("meir password");

		Customer customer = new Customer();
		customer.setFirstName("meir");
		customer.setLastName("heber");
		customer.setFirstName("meir@meir");
		customer.setFirstName("meir password");

		AdminService facade = (AdminService) loginManager.login(ClientType.ADMINISTRATOR, "admin@admin.com", "admin");
		System.out.println("admin login successfully");
		facade.addCompany(company);
		System.out.print("admin add company successfully: ");
		System.out.println(facade.getCompany(company.getId()));
		facade.addCustomer(customer);
		System.out.print("admin add customer successfully: ");
		System.out.println(facade.getCustomer(customer.getId()));
		System.out.println("admin get all " + facade.getAllCompanies().size() + " companies successfully: ");
		System.out.println("admin get all " + facade.getAllCustomers().size() + " customers successfully: ");
		company.setEmail("meir2@meir");
		customer.setEmail("meir2@meir");
		facade.updateCompany(company);
		System.out.print("admin update company successfully: ");
		System.out.println(facade.getCompany(company.getId()));
		facade.updateCustomer(customer);
		System.out.print("admin update customer successfully: ");
		System.out.println(facade.getCustomer(customer.getId()));
		facade.deleteCompany(company.getId());
		System.out.print("admin delete company successfully: ");
		System.out.println(company);
		facade.deleteCustomer(customer.getId());
		System.out.print("admin delete customer successfully: ");
		System.out.println(customer);

	}

	/**
	 * Testing the success of all company methods, through coupon production and
	 * working with it.
	 * <p>
	 * This method should not throw an Exception.
	 * 
	 * @return The coupon created, so that the customer method can work with it.
	 * @throws CouponsSystemException
	 */
	private Coupon company() throws CouponsSystemException {
		Coupon coupon = new Coupon();
		Company company = new Company();
		company.setId(1);
		coupon.setCompany(company);
		coupon.setCategory(Category.ELECTRICITY);
		coupon.setTitle("title12");
		coupon.setDescription("description");
		coupon.setStartDate(LocalDate.of(1990, 05, 13));
		coupon.setEndDate(LocalDate.of(2030, 05, 13));
		coupon.setAmount(5);
		coupon.setPrice(10.8);
		coupon.setImage("");

		CompanyService facade = (CompanyService) loginManager.login(ClientType.COMPANY, "levi@levi", "levi password");
		System.out.println("company login successfully");
		facade.addCoupon(coupon);
		System.out.print("company add coupon successfully: ");
		System.out.println(coupon);
		coupon.setDescription("description2");
		facade.updateCoupon(coupon);
		System.out.print("company update coupon successfully: ");
		System.out.println(coupon);
		System.out.println("company got all " + facade.getCoupons().size() + " coupons successfully: ");
		System.out.println("company got all " + facade.getCoupons(Category.ELECTRICITY).size() + " category "
				+ Category.ELECTRICITY + " coupons  successfully: ");
		System.out.println(
				"company got all " + facade.getCoupons(20.0).size() + " max price " + 20 + " coupons  successfully: ");
		System.out.println(facade.getDetails());
		System.out.print("company delete coupon successfully: ");
		System.out.println(coupon);
		return facade.getCoupons().get(0);

	}

	/**
	 * Checking the success of customer methods by working on a particular coupon.
	 * <p>
	 * This method should not throw an Exception.
	 * 
	 * @param coupon
	 * @throws CouponsSystemException
	 */
	private void customer(Coupon coupon) throws CouponsSystemException {
		CustomerService facade = (CustomerService) loginManager.login(ClientType.CUSTOMER, "levi@levi",
				"levi password");
		System.out.println("customer login successfully");
		System.out.println(facade.getDetails());
		facade.purchaseCoupon(coupon.getId());
		System.out.print("customer purchase coupon successfully: ");
		System.out.println(coupon);
		System.out.println("customer got all " + facade.getCoupons().size() + " coupons successfully: ");
		System.out.println("customer got all " + facade.getCoupons(Category.ELECTRICITY).size() + " category "
				+ Category.ELECTRICITY + " coupons  successfully: ");
		System.out.println(
				"customer got all " + facade.getCoupons(20.0).size() + " max price " + 20 + " coupons  successfully: ");
	}

	/**
	 * Checking the job's ability to delete coupons, as well as the possibility of
	 * an error when performing an illegal operation
	 * 
	 * @param coupon
	 * @throws CouponsSystemException A planned error thrown away by adding a
	 *                                non-existent coupon.
	 */
	private void finish(Coupon coupon) throws CouponsSystemException {
		CompanyService facade = (CompanyService) loginManager.login(ClientType.COMPANY, "levi@levi", "levi password");
//		coupon.setEndDate(LocalDate.of(2010, 05, 13));
		coupon.setEndDate(LocalDate.of(2030, 05, 13));
		facade.updateCoupon(coupon);
		try {
			System.out.println("Waiting for the coupon deletion process...");
			Thread.sleep(3000);
			System.out.println("==========================================");
			System.out.println("Get ready to accept an Exception...");
			for (int i = 3; i > 0; i--) {
				System.out.println("in " + i + "...");
				Thread.sleep(1000);
			}
			facade.updateCoupon(coupon);
		} catch (InterruptedException e) {
		}
	}

}