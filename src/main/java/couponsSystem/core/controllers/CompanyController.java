package couponsSystem.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponsSystem.core.entites.Coupon;
import couponsSystem.core.entites.Coupon.Category;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.services.CompanyService;

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
@RestController
@CrossOrigin
@RequestMapping("company")
public class CompanyController extends ClientController<CompanyService> {

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
	@PostMapping("coupon")
	public ResponseEntity<?> addCoupon(@RequestHeader String token, @RequestBody Coupon coupon)
			throws CouponsSystemException {
		getService(token).addCoupon(coupon);
		return okResponse();
	}

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
	@PutMapping("coupon")
	public ResponseEntity<?> updateCoupon(@RequestHeader String token, @RequestBody Coupon coupon)
			throws CouponsSystemException {
		getService(token).updateCoupon(coupon);
		return okResponse();
	}

	/**
	 * get of all coupons associated with the client by ID.
	 * 
	 * @return All coupons associated with the client.
	 * @throws CouponsSystemException when get all client coupons fails.
	 */
	@GetMapping("coupon")
	public ResponseEntity<?> getCoupons(@RequestHeader String token) throws CouponsSystemException {
		return objectOkResponse(getService(token).getCoupons());
	}

	/**
	 * get of all coupons from a particular category associated with the client by
	 * ID.
	 * 
	 * @param category The selected category for getting the coupons.
	 * @return All category coupons associated with the client.
	 * @throws CouponsSystemException when get all client category coupons fails.
	 */
	@GetMapping("coupon-category/{category}")
	public ResponseEntity<?> getCoupons(@RequestHeader String token, @PathVariable Category category)
			throws CouponsSystemException {
		return objectOkResponse(getService(token).getCoupons(category));
	}

	/**
	 * get of all coupons Up to the maximum price associated with the client by ID.
	 * 
	 * @param maxPrice The maximum price for getting the coupons.
	 * @return All coupons Up to the maximum price associated with the client.
	 * @throws CouponsSystemException when get all client maximum price coupons
	 *                                fails.
	 */
	@GetMapping("coupon-price/{maxPrice}")
	public ResponseEntity<?> getCoupons(@RequestHeader String token, @PathVariable Double maxPrice)
			throws CouponsSystemException {
		System.out.println(maxPrice);
		return objectOkResponse(getService(token).getCoupons(maxPrice));
	}

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
	@DeleteMapping("coupon/{couponID}")
	public ResponseEntity<?> deleteCoupon(@RequestHeader String token, @PathVariable int couponID)
			throws CouponsSystemException {
		getService(token).deleteCoupon(couponID);
		return okResponse();
	}

	/**
	 * getting this company
	 * 
	 * @return this company
	 * @throws CouponsSystemException when get company details fails.
	 */
	@GetMapping()
	public ResponseEntity<?> getDetails(@RequestHeader String token) throws CouponsSystemException {
		return objectOkResponse(getService(token).getDetails());
	}
}
