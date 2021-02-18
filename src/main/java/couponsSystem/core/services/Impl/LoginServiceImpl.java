package couponsSystem.core.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import couponsSystem.core.entites.Coupon;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.reposetory.CouponsReposetory;
import couponsSystem.core.services.AdminService;
import couponsSystem.core.services.ClientService;
import couponsSystem.core.services.CompanyService;
import couponsSystem.core.services.CustomerService;
import couponsSystem.core.services.LoginService;

/**
 * Managing the login process.
 * <p>
 * Proper entry will return the facade object suitable and ready for use
 * 
 * @author Levi Heber
 *
 */
@Component
public class LoginServiceImpl implements LoginService {

	/**
	 * Singleton ready to operate that will return proper facade to each correct
	 * login.
	 * <p>
	 * This field will be initialized by loading the class.
	 */
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	protected CouponsReposetory couponsReposetory;

	/**
	 * Fixed collection of system clients.
	 * 
	 * @author Levi Heber
	 *
	 */
	public enum ClientType {
		ADMINISTRATOR, COMPANY, CUSTOMER
	}

	/**
	 * Verification and approval of the login process.
	 * <p>
	 * Client details are checked in a pass object corresponding to its type, and it
	 * is returned when the details are correct.
	 * 
	 * @param clientType The type of client who wants to login.
	 * @param email      The client email address as it appears in the data storage.
	 * @param password   The client password as it appears in the data storage.
	 * @return Correct login: Facade object suitable for use. Incorrect login:
	 *         <code>null</code>.
	 * @throws CouponsSystemException when:
	 *                                <li>login fail.
	 *                                <li>client type not detected
	 * 
	 */
	@Override
	public ClientService login(ClientType clientType, String email, String password) throws CouponsSystemException {
		ClientService clientFacade;
		try {
			switch (clientType) {
			case ADMINISTRATOR:
				clientFacade = ctx.getBean(AdminService.class);
				return clientFacade.login(email, password) ? clientFacade : null;

			case COMPANY:
				clientFacade = ctx.getBean(CompanyService.class);
				return clientFacade.login(email, password) ? clientFacade : null;

			case CUSTOMER:
				clientFacade = ctx.getBean(CustomerService.class);
				return clientFacade.login(email, password) ? clientFacade : null;

			default:
				break;
			}

		} catch (CouponsSystemException e) {
			throw new CouponsSystemException(clientType + " login fail", e);
		}
		throw new CouponsSystemException("client type not detected");

	}

	@Override
	public ArrayList<Coupon> getCoupons() throws CouponsSystemException {
		List<Coupon> list = couponsReposetory.findAll();
		if (!list.isEmpty()) {
			return new ArrayList<Coupon>(list);
		}
		throw new CouponsSystemException("There is no coupons");
	}

}
