package couponsSystem.core.services;

import java.util.ArrayList;

import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Coupon.Category;
import couponsSystem.core.exception.CouponsSystemException;

/**
 * The action tools needed by company to manage coupons.
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
public interface CompanyService extends ClientService {

	/**
	 * Adding a new coupon for this company to the DAO.
	 * <p>
	 * A coupon with an existing title cannot be added to another coupon from this
	 * company.
	 * 
	 * @param coupon the coupon to add.
	 * @throws CouponsSystemException
	 *                                <li>the coupon already exists.
	 *                                <li>the coupon title already exists.
	 *                                <li>the coupon start date is before the end
	 *                                date.
	 *                                <li>add coupon faild.
	 */
	void addCoupon(Coupon coupon) throws CouponsSystemException;

	/**
	 * Update an existing coupon from the DAO.
	 * <p>
	 * The coupon ID cannot be changed and updated.
	 * 
	 * @param coupon the company to update.
	 * @throws CouponsSystemException
	 *                                <li>update coupon failed.
	 *                                <li>the coupon dose not exists.
	 */
	void updateCoupon(Coupon coupon) throws CouponsSystemException;

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
	ArrayList<Coupon> getCoupons(Double maxPrice) throws CouponsSystemException;

	/**
	 * Delete an existing coupon from the DAO.
	 * <p>
	 * Before deleting, all the coupon purchases will be deleted.
	 * <p>
	 * All these actions are carried out in one transaction
	 * 
	 * @param couponID the ID of the coupon to delete.
	 * @throws CouponsSystemException
	 *                                <li>delete coupon and it's purchases failed.
	 *                                <li>the company dose not exists.
	 */
	void deleteCoupon(int couponID) throws CouponsSystemException;

	/**
	 * getting this company
	 * 
	 * @return this company
	 * @throws CouponsSystemException when get company details fails.
	 */
	Company getDetails() throws CouponsSystemException;

}
