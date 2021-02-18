package couponsSystem.core.services;

import java.util.ArrayList;

import couponsSystem.core.entites.Coupon;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.reposetory.CompanyReposetory;
import couponsSystem.core.reposetory.CouponsReposetory;
import couponsSystem.core.reposetory.CustomerReposetory;
import couponsSystem.core.services.Impl.LoginServiceImpl.ClientType;

/**
 * Primary Facade and logics for all types of system clients, containing
 * variables and methods common to all.
 * <p>
 * Facade-approved operations are performed using the appropriate DAO classes.
 * 
 * @author Levi Heber
 * @see AdminService
 * @see CompanyService
 * @see CustomerService
 * @see CustomerReposetory
 * @see CompanyReposetory
 * @see CouponsReposetory
 */
public interface LoginService {

	/**
	 * get all coupons of all the companies.
	 * 
	 * @return ArrayList<Coupon> of all the coupons.
	 * @throws CouponsSystemException When get all coupons fails.
	 */
	ArrayList<Coupon> getCoupons() throws CouponsSystemException;

	ClientService login(ClientType clientType, String email, String password) throws CouponsSystemException;

}
