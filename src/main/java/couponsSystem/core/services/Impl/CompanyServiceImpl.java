package couponsSystem.core.services.Impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import couponsSystem.core.entites.Company;
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
@Service
@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyServiceImpl extends ClientServiceImpl implements CompanyService {

	private int companyId;

	/**
	 * company login verification according to the email and password.
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
			Optional<Company> optional = companiesReposetory.findByEmailAndPassword(email, password);
			if (optional.isPresent()) {
				companyId = optional.get().getId();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new CouponsSystemException("company login fails", e);
		}
	}

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
	@Override
	public void addCoupon(Coupon coupon) throws CouponsSystemException {
		if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
			throw new CouponsSystemException("the coupon start date is before the end date");
		}
		Coupon coupon2 = new Coupon();
		coupon2.setTitle(coupon.getTitle());
		coupon2.setCompany(new Company(companyId));
		if (couponsReposetory.exists(Example.of(coupon2))) {
			throw new CouponsSystemException("the coupon title already exists");
		}
		coupon.setCompany(getDetails());
		add(coupon, coupon.getId() == null || !couponsReposetory.existsById(coupon.getId()), "");
	}

	/**
	 * Update an existing coupon from the DAO.
	 * <p>
	 * The coupon ID cannot be changed and updated.
	 * 
	 * @param coupon the company to update.
	 * @throws CouponsSystemException
	 * @throws Exception
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponsSystemException {
		Coupon couponDB = get(couponsReposetory.findById(coupon.getId()), "coupon");
		if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
			throw new CouponsSystemException("the coupon start date is before the end date");
		}
		Coupon coupon2 = new Coupon();
		coupon2.setTitle(coupon.getTitle());
		coupon2.setCompany(new Company(companyId));
		if (coupon.getTitle() != couponDB.getTitle() && couponsReposetory.exists(Example.of(coupon2))) {
			throw new CouponsSystemException("the coupon title already exists");
		}
		if (coupon.getCompany().getId() != companyId) {
			throw new CouponsSystemException("The coupon does not belong to this company");
		}
		coupon.setId(couponDB.getId());
		coupon.setCompany(couponDB.getCompany());
		update(coupon);
	}

	/**
	 * get of all coupons associated with the client by ID.
	 * 
	 * @return All coupons associated with the client.
	 * @throws CouponsSystemException when get all client coupons fails.
	 */
	@Override
	public ArrayList<Coupon> getCoupons() throws CouponsSystemException {
		return getAll(couponsReposetory.findByCompanyId(companyId), "company coupons");
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
		return getAll(couponsReposetory.findByCompanyIdAndCategory(companyId, category), "company coupons");
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
	public ArrayList<Coupon> getCoupons(Double maxPrice) throws CouponsSystemException {
		return getAll(couponsReposetory.findByCompanyIdAndPriceLessThan(companyId, maxPrice),
				"company coupons maxPrice " + maxPrice);
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
	@Override
	public void deleteCoupon(int couponID) throws CouponsSystemException {
		delete(couponsReposetory, couponID, "coupon");
	}

	/**
	 * getting this company
	 * 
	 * @return this company
	 * @throws CouponsSystemException when get company details fails.
	 */
	@Override
	public Company getDetails() throws CouponsSystemException {
		return get(companiesReposetory.findById(companyId), "company details");
	}

}
