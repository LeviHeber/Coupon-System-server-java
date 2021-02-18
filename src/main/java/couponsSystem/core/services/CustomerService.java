package couponsSystem.core.services;

import java.util.ArrayList;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.entites.Coupon.Category;
import couponsSystem.core.exception.CouponsSystemException;

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
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface CustomerService extends ClientService {

	/**
	 * get of all coupons associated with the client by ID.
	 * 
	 * @return All coupons associated with the client.
	 * @throws CouponsSystemException when get all client coupons fails.
	 */
	ArrayList<Coupon> getCoupons() throws CouponsSystemException;

	/**
	 * get of all coupons from a particular category associated with the client by
	 * ID.
	 * 
	 * @param category The selected category for getting the coupons.
	 * @return All category coupons associated with the client.
	 * @throws CouponsSystemException when get all client category coupons fails.
	 */
	ArrayList<Coupon> getCoupons(Category category) throws CouponsSystemException;

	/**
	 * get of all coupons Up to the maximum price associated with the client by ID.
	 * 
	 * @param maxPrice The maximum price for getting the coupons.
	 * @return All coupons Up to the maximum price associated with the client.
	 * @throws CouponsSystemException when get all client maximum price coupons
	 *                                fails.
	 */
	ArrayList<Coupon> getCoupons(double maxPrice) throws CouponsSystemException;

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
	void purchaseCoupon(int couponId) throws CouponsSystemException;

	/**
	 * getting this customer
	 * 
	 * @return this customer
	 * @throws CouponsSystemException when get customer details fails.
	 */
	Customer getDetails() throws CouponsSystemException;

}
