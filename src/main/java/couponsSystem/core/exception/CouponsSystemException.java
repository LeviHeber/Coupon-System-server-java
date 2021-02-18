package couponsSystem.core.exception;

/**
 * General adjustment for exceptions at all stages of the coupon system.
 * 
 * @author Levi Heber
 *
 */
public class CouponsSystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CouponsSystemException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CouponsSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CouponsSystemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CouponsSystemException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CouponsSystemException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
