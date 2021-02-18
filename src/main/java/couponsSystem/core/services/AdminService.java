package couponsSystem.core.services;

import java.util.ArrayList;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.exception.CouponsSystemException;

/**
 * The action tools needed by an administrator to manage companies, customers
 * and coupons.
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
public interface AdminService extends ClientService {

	/**
	 * Adding a new company to the DAO.
	 * <p>
	 * A company cannot use an email or name that is already exists.
	 * 
	 * @param company the company to add.
	 * @throws CouponsSystemException
	 *                                <li>add company failed.
	 *                                <li>the company name or email already exists.
	 */
	void addCompany(Company company) throws CouponsSystemException;

	/**
	 * Update an existing company from the DAO.
	 * <p>
	 * The company ID and email cannot be changed and updated.
	 * 
	 * @param company the company to update.
	 * @throws CouponsSystemException
	 *                                <li>update company failed.
	 *                                <li>the company dose not exists.
	 */
	void updateCompany(Company company) throws CouponsSystemException;

	/**
	 * Delete an existing company from the DAO.
	 * <p>
	 * Before deleting, all the coupons belonging to it and their purchases will be
	 * deleted.
	 * <p>
	 * All these actions are carried out in one transaction
	 * 
	 * @param companyID the ID of the company to delete.
	 * @throws CouponsSystemException
	 *                                <li>delete company, it's coupons and purchases
	 *                                failed.
	 *                                <li>the company dose not exists.
	 */
	void deleteCompany(int companyID) throws CouponsSystemException;

	/**
	 * Get all the existing companies from the DAO.
	 * 
	 * @return all the existing companies from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all companies failed.
	 *                                <li>There are no companies to get.
	 */
	ArrayList<Company> getAllCompanies() throws CouponsSystemException;

	/**
	 * get an existing company from the DAO.
	 * <p>
	 * 
	 * @param companyID the company ID to get.
	 * @return The appropriate company from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get company failed.
	 *                                <li>the company ID dose not exists.
	 */
	Company getCompany(int companyID) throws CouponsSystemException;

	/**
	 * Adding a new customer to the DAO.
	 * <p>
	 * A customer cannot use an that is already exists.
	 * 
	 * @param customer the customer to add.
	 * @throws CouponsSystemException
	 *                                <li>add customer failed.
	 *                                <li>the customer email already exists.
	 */
	void addCustomer(Customer customer) throws CouponsSystemException;

	/**
	 * Update an existing customer from the DAO.
	 * <p>
	 * The customer ID cannot be changed and updated.
	 * 
	 * @param customer the customer to update.
	 * @throws CouponsSystemException
	 *                                <li>update customer failed.
	 *                                <li>the customer dose not exists.
	 */
	void updateCustomer(Customer customer) throws CouponsSystemException;

	/**
	 * Delete an existing customer from the DAO.
	 * <p>
	 * Before deleting, all the coupons purchases will be deleted.
	 * <p>
	 * All these actions are carried out in one transaction
	 * 
	 * @param customerID the ID of the customer to delete.
	 * @throws CouponsSystemException
	 *                                <li>delete customer and his coupons purchases
	 *                                failed.
	 *                                <li>the customer dose not exists.
	 */
	void deleteCustomer(int customerID) throws CouponsSystemException;

	/**
	 * get an existing customer from the DAO.
	 * <p>
	 * 
	 * @param companyID the customer ID to get.
	 * @return The appropriate customer from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get customer failed.
	 *                                <li>the customer ID dose not exists.
	 */
	Customer getCustomer(int customerID) throws CouponsSystemException;

	/**
	 * Get all the existing customers from the DAO.
	 * 
	 * @return all the existing customers from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all customers failed.
	 *                                <li>There are no customers to get.
	 */
	ArrayList<Customer> getAllCustomers() throws CouponsSystemException;
	}
