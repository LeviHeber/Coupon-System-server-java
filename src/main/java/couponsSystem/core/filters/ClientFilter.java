package couponsSystem.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import couponsSystem.core.controllers.sessions.Session;
import couponsSystem.core.controllers.sessions.SessionContext;
import couponsSystem.core.services.Impl.LoginServiceImpl.ClientType;

public class ClientFilter implements Filter {

	private SessionContext sessionContext;
	private ClientType clientType;

	/**
	 * 
	 */
	public ClientFilter() {
		super();
	}

	/**
	 * @param sessionContext
	 * @param clientType
	 */
	public ClientFilter(SessionContext sessionContext, ClientType clientType) {
		super();
		this.sessionContext = sessionContext;
		this.clientType = clientType;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String token = req.getHeader("token");
		if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
			return;
		}
		if (token != null) {
			Session session = sessionContext.getSession(token);
			if (session != null) {
				ClientType cuurClient = (ClientType) session.getAttributes("clientType");
				if (cuurClient == this.clientType) {
					chain.doFilter(request, response);
					return;
				}
				res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
				res.sendError(HttpStatus.FORBIDDEN.value(), "not authorized");
				return;
			}
		}
		res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		res.sendError(HttpStatus.UNAUTHORIZED.value(), "not login");
		return;
	}

	/**
	 * @param clientType the clientType to set
	 */
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

}
