package couponsSystem.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponsSystem.core.entites.Coupon.Category;
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
@RestController
@CrossOrigin
@RequestMapping("customer")
public class CustomerController extends ClientController<CustomerService> {

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
	public ResponseEntity<?> getCoupons(@RequestHeader String token, @PathVariable double maxPrice)
			throws CouponsSystemException {
		return objectOkResponse(getService(token).getCoupons(maxPrice));
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
	@PutMapping("coupon-purchase")
	public ResponseEntity<?> purchaseCoupon(@RequestHeader String token, @RequestBody int couponId)
			throws CouponsSystemException {
		getService(token).purchaseCoupon(couponId);
		return okResponse();
	}

	/**
	 * getting this customer
	 * 
	 * @return this customer
	 * @throws CouponsSystemException when get customer details fails.
	 */
	@GetMapping()
	public ResponseEntity<?> getDetails(@RequestHeader String token) throws CouponsSystemException {
		return objectOkResponse(getService(token).getDetails());
	}
}
