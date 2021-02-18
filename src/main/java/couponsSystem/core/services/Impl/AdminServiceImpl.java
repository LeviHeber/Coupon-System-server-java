package couponsSystem.core.services.Impl;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.services.AdminService;

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
@Service
@Transactional
public class AdminServiceImpl extends ClientServiceImpl implements AdminService, ApplicationContextAware {

	private Environment env;

	/**
	 * Admin login verification according to the email and password.
	 * <p>
	 * admin information is hard-coded in the system.
	 * 
	 * @param email The checked Admin email address.
	 * @param name  The checked Admin name.
	 * @return boolean of the login verification.
	 * @throws CouponsSystemException When the login process fails.
	 */
	@Override
	public boolean login(String email, String password) {
		String adminEmail = env.getProperty("coupons_system.admin.email");
		String adminPassword = env.getProperty("coupons_system.admin.password");
		return email.equals(adminEmail) && password.equals(adminPassword);
	}

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
	public void addCompany(Company company) throws CouponsSystemException {
		add(company, !companiesReposetory.existsByEmailOrName(company.getEmail(), company.getName()), "name or email");
	}

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
	public void updateCompany(Company company) throws CouponsSystemException {
		Company companyDB = get(companiesReposetory.findById(company.getId()), "company");
		company.setId(companyDB.getId());
		company.setEmail(companyDB.getEmail());
		update(company);
	}

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
	public void deleteCompany(int companyID) throws CouponsSystemException {
		delete(companiesReposetory, companyID, "company");
	}

	/**
	 * Get all the existing companies from the DAO.
	 * 
	 * @return all the existing companies from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all companies failed.
	 *                                <li>There are no companies to get.
	 */
	public ArrayList<Company> getAllCompanies() throws CouponsSystemException {
		return getAll(companiesReposetory.findAll(), "companies");
	}

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
	public Company getCompany(int companyID) throws CouponsSystemException {
		return get(companiesReposetory.findById(companyID), "company");
	}

	/**
	 * Adding a new customer to the DAO.
	 * <p>
	 * A customer cannot use an email that is already exists.
	 * 
	 * @param customer the customer to add.
	 * @throws CouponsSystemException
	 *                                <li>add customer failed.
	 *                                <li>the customer email already exists.
	 */
	public void addCustomer(Customer customer) throws CouponsSystemException {
		add(customer, !customersReposetory.existsByEmail(customer.getEmail()), "email");
	}

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
	public void updateCustomer(Customer customer) throws CouponsSystemException {
		customer.setId(get(customersReposetory.findById(customer.getId()), "customer").getId());
		update(customer);
	}

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
	public void deleteCustomer(int customerID) throws CouponsSystemException {
		delete(customersReposetory, customerID, "customer");
	}

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
	public Customer getCustomer(int customerID) throws CouponsSystemException {
		return get(customersReposetory.findById(customerID), "customer");
	}

	/**
	 * Get all the existing customers from the DAO.
	 * 
	 * @return all the existing customers from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all customers failed.
	 *                                <li>There are no customers to get.
	 */
	public ArrayList<Customer> getAllCustomers() throws CouponsSystemException {
		return getAll(customersReposetory.findAll(), "customers");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.env = applicationContext.getEnvironment();
	}
}
