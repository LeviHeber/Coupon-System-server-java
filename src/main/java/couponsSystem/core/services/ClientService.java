package couponsSystem.core.services;

import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.reposetory.CompanyReposetory;
import couponsSystem.core.reposetory.CustomerReposetory;
import couponsSystem.core.reposetory.CouponsReposetory;

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
public interface ClientService {

	/**
	 * client's login verification According to the email and password.
	 * 
	 * @param email The checked client email address.
	 * @param name  The checked client name.
	 * @return boolean of the login verification.
	 * @throws CouponsSystemException When the login process fails.
	 */
	boolean login(String email, String password) throws CouponsSystemException;

}
