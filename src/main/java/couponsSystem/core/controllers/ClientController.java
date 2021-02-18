package couponsSystem.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import couponsSystem.core.controllers.sessions.Session;
import couponsSystem.core.controllers.sessions.SessionContext;
import couponsSystem.core.reposetory.CompanyReposetory;
import couponsSystem.core.reposetory.CustomerReposetory;
import couponsSystem.core.services.ClientService;
import couponsSystem.core.reposetory.CouponsReposetory;

/**
 * Primary Facade and logics for all types of system clients, containing
 * variables and methods common to all.
 * <p>
 * Facade-approved operations are performed using the appropriate DAO classes.
 * 
 * @author Levi Heber
 * @see AdminController
 * @see CompanyController
 * @see CustomerController
 * @see CustomerReposetory
 * @see CompanyReposetory
 * @see CouponsReposetory
 */
public abstract class ClientController <SERVICE extends ClientService> {

	@Autowired
	protected SessionContext sessionContext;

	protected SERVICE getService(String token) {
		Session session = sessionContext.getSession(token);
		return (SERVICE) session.getAttributes("service");
	}
		
	protected ResponseEntity<?> okResponse() {
		return ResponseEntity.ok().build();
	}
	
	protected <T> ResponseEntity<?> objectOkResponse(T object) {
		return ResponseEntity.ok(object);
	}

}