package couponsSystem.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import couponsSystem.core.controllers.sessions.Session;
import couponsSystem.core.controllers.sessions.SessionContext;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.services.ClientService;
import couponsSystem.core.services.Impl.LoginServiceImpl;
import couponsSystem.core.services.Impl.LoginServiceImpl.ClientType;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	private SessionContext sessionContext;
	@Autowired
	private LoginServiceImpl loginService;

	@GetMapping("login")
	public ResponseEntity<?> login(@RequestParam ClientType clientType, @RequestParam String email,
			@RequestParam String password) throws CouponsSystemException {
		ClientService service = loginService.login(clientType, email, password);
		if (service != null) {
			Session session = sessionContext.createSession();
			session.setAttributes("service", service);
			session.setAttributes("clientType", clientType);
			return ResponseEntity.ok(session.token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("client not found");
	}

	@GetMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader String token) throws CouponsSystemException {
		Session session = sessionContext.getSession(token);
		if (session != null) {
			sessionContext.invalidate(session);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("client not found");
	}

	@GetMapping("is-active")
	public ResponseEntity<?> isActive(@RequestHeader String token) {
		return ResponseEntity.ok(sessionContext.getSession(token) != null);
	}

	@GetMapping("coupons")
	public ResponseEntity<?> getCoupons() throws CouponsSystemException {
		return ResponseEntity.ok(loginService.getCoupons());
	}

}
