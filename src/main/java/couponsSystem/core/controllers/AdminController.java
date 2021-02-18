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
@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController extends ClientController<AdminService> {

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
	@PostMapping("company")
	public ResponseEntity<?> addCompany(@RequestHeader String token, @RequestBody Company company)
			throws CouponsSystemException {
		getService(token).addCompany(company);
		return okResponse();
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
	@PutMapping("company")
	public ResponseEntity<?> updateCompany(@RequestHeader String token, @RequestBody Company company)
			throws CouponsSystemException {
		getService(token).updateCompany(company);
		return okResponse();
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
	@DeleteMapping("company/{companyID}")
	public ResponseEntity<?> deleteCompany(@RequestHeader String token, @PathVariable int companyID)
			throws CouponsSystemException {
		getService(token).deleteCompany(companyID);
		return okResponse();
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
	@GetMapping("company/{companyID}")
	public ResponseEntity<?> getCompany(@RequestHeader String token, @PathVariable int companyID)
			throws CouponsSystemException {
		return objectOkResponse(getService(token).getCompany(companyID));
	}

	/**
	 * Get all the existing companies from the DAO.
	 * 
	 * @return all the existing companies from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all companies failed.
	 *                                <li>There are no companies to get.
	 */
	@GetMapping("companies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader String token) throws CouponsSystemException {
		return objectOkResponse(getService(token).getAllCompanies());
	}

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
	@PostMapping("customer")
	public ResponseEntity<?> addCustomer(@RequestHeader String token, @RequestBody Customer customer)
			throws CouponsSystemException {
		getService(token).addCustomer(customer);
		return okResponse();
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
	@PutMapping("customer")
	public ResponseEntity<?> updateCustomer(@RequestHeader String token, @RequestBody Customer customer)
			throws CouponsSystemException {
		getService(token).updateCustomer(customer);
		return okResponse();
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
	@DeleteMapping("customer/{companyID}")
	public ResponseEntity<?> deleteCustomer(@RequestHeader String token, @PathVariable int customerID)
			throws CouponsSystemException {
		return okResponse();
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
	@GetMapping("customer/{companyID}")
	public ResponseEntity<?> getCustomer(@RequestHeader String token, @PathVariable int customerID)
			throws CouponsSystemException {
		return objectOkResponse(getService(token).getCustomer(customerID));
	}

	/**
	 * Get all the existing customers from the DAO.
	 * 
	 * @return all the existing customers from the DAO.
	 * @throws CouponsSystemException
	 *                                <li>get all customers failed.
	 *                                <li>There are no customers to get.
	 */
	@GetMapping("customers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) throws CouponsSystemException {
		return objectOkResponse(getService(token).getAllCustomers());
	}
}
