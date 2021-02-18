package couponsSystem.core.jobs;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import couponsSystem.core.exception.CouponsSystemException;
import couponsSystem.core.reposetory.CouponsReposetory;

/**
 * Repeat task of deleting expired coupons.
 * <p>
 * the timer run in In his own daemon thread.
 * <p>
 * The time between tasks is set in a properties file.
 * 
 * @author Levi Heber
 *
 */
@Component
public class ExpiredTask {

	/**
	 * Timer to perform the task
	 */
	private Timer timer;

	/**
	 * coupon repostory for deleting coupons
	 */
	@Autowired
	CouponsReposetory couponsReposetory;

	/**
	 * period Hours of waiting between tasks, from the property of the property. By
	 * default, the time is 24 hours
	 * 
	 */
	@Value("${coupons_system.delet.expierd.coupons.timer.task.period.second: 5}")
	private Long period;
	/**
	 * A timer task that implements the run method
	 */
	private TimerTask couponTask = new TimerTask() {

		/**
		 * Delete all coupons that expire before the current date
		 */
		@Override
		public void run() {
			try {
				System.out.println(">>>deleting expiration coupons: "
						+ couponsReposetory.countByEndDateBefore(LocalDate.now()) + "<<<");
				couponsReposetory.deleteByEndDateBefore(LocalDate.now());
			} catch (Exception e) {
				CouponsSystemException e1 = new CouponsSystemException("delete all expiration coupons faild");
				System.out.println(e1.getMessage());
			}
		}
	};

	/**
	 * Boot the timer as daemon and for immediate action, with time intervals set in
	 * the property file
	 */
	@PostConstruct
	public void init() {
		timer = new Timer(true);
		timer.scheduleAtFixedRate(couponTask, new GregorianCalendar().getTime(), TimeUnit.SECONDS.toMillis(period));
	}

	/**
	 * Closing the timer
	 */
	@PreDestroy
	public void destroy() {
		timer.cancel();
	}

}
