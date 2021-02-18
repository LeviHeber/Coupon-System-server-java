package couponsSystem.core.services.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Coupon.Category;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.services.CustomerService;

/**
 * The action tools needed by customer to manage coupon purchases.
 * <p>
 * Each operation requires compliance with established logical conditions, and
 * they are tested within the operation methods before it is performed.
 * <p>
 * If the action does not meet one of the rules, an
 * {@link CouponsSystemException} is thrown if the failure detail is specified.
 * 
 * @author Levi Heber
 *
 */
@Service
@Transactional
public class CustomerServiceImpl extends ClientServiceImpl implements CustomerService {

	private int customerId;

	/**
	 * customer login verification according to the email and password.
	 * <p>
	 * The accuracy of the information is checked in the DAO.
	 * 
	 * @param email The checked company email address.
	 * @param name  The checked company name.
	 * @return boolean of the login verification.
	 * @throws CouponsSystemException When the login process fails.
	 */
	@Override
	public boolean login(String email, String password) throws CouponsSystemException {
		try {
			Optional<Customer> optional = customersReposetory.findByEmailAndPassword(email, password);
			if (optional.isPresent()) {
				customerId = optional.get().getId();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new CouponsSystemException("company login fails", e);
		}
	}

	/**
	 * get of all coupons associated with the client by ID.
	 * 
	 * @return All coupons associated with the client.
	 * @throws CouponsSystemException when get all client coupons fails.
	 */
	@Override
	public ArrayList<Coupon> getCoupons() throws CouponsSystemException {
		return new ArrayList<Coupon>(get(customersReposetory.findById(customerId), "customer coupons").getCoupons());
	}

	/**
	 * get of all coupons from a particular category associated with the client by
	 * ID.
	 * 
	 * @param category The selected category for getting the coupons.
	 * @return All category coupons associated with the client.
	 * @throws CouponsSystemException when get all client category coupons fails.
	 */
	@Override
	public ArrayList<Coupon> getCoupons(Category category) throws CouponsSystemException {
		return getAll(couponsReposetory.findByCustomerIdAndCategory(customerId, category),
				"customer " + category.toString() + " coupon");
	}

	/**
	 * get of all coupons Up to the maximum price associated with the client by ID.
	 * 
	 * @param maxPrice The maximum price for getting the coupons.
	 * @return All coupons Up to the maximum price associated with the client.
	 * @throws CouponsSystemException when get all client maximum price coupons
	 *                                fails.
	 */
	@Override
	public ArrayList<Coupon> getCoupons(double maxPrice) throws CouponsSystemException {
		return getAll(couponsReposetory.findByCustomerIdAndPriceLessThan(customerId, maxPrice),
				"customer maxPrice " + maxPrice + " coupons");
	}

	/**
	 * Add a coupon purchase for this customer to the DAO.
	 * <p>
	 * You can only purchase a coupon that has a amount left, and before it expires.
	 * <p>
	 * It is not possible to purchase a coupon twice.
	 * <p>
	 * Upon purchase, the balance of the coupon must be updated. All these actions
	 * are carried out in one transaction
	 * 
	 * @param coupon the coupon to purchase.
	 * @throws CouponsSystemException
	 *                                <li>purchase coupon fails.
	 *                                <li>this coupon is expierd.
	 *                                <li>the amount of coupons that can be
	 *                                purchased is over.
	 *                                <li>The customer has already purchased this
	 *                                coupon.
	 */
	@Override
	public void purchaseCoupon(int couponId) throws CouponsSystemException {
		Coupon couponDB = get(couponsReposetory.findById(couponId), "coupon");
		if (couponDB.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponsSystemException("this coupon is expierd");
		}
		if (couponDB.getAmount() <= 0) {
			throw new CouponsSystemException("the amount of coupons that can be purchased is over");
		}
		if (couponsReposetory.findByIdAndCustomerId(couponId, customerId).isPresent()) {
			throw new CouponsSystemException("The customer has already purchased this coupon");
		}
		try {
			couponDB.setAmount(couponDB.getAmount() - 1);
			getDetails().getCoupons().add(couponDB);
		} catch (Exception e) {
			throw new CouponsSystemException("purchase coupon fails", e);
		}

	}

	/**
	 * getting this customer
	 * 
	 * @return this customer
	 * @throws CouponsSystemException when get customer details fails.
	 */
	@Override
	public Customer getDetails() throws CouponsSystemException {
		return get(customersReposetory.findById(customerId), "customer details");
	}

}
