package couponsSystem.core.controllers.sessions;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SessionContext {

	@Autowired
	private ApplicationContext ctx;

	private Map<String, Session> sessions = new ConcurrentHashMap<>();
	private Timer timer = new Timer();
	@Value("${session.remove.expired.second.period:20}")
	private int removeExpiredPeriod;

	@PostConstruct
	private void init() {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				System.out.println(">>>removing expired sessions");
				for (Session session : sessions.values()) {
					if (session.isExpired()) {
						System.out.println("delete token " + session.token);
						invalidate(session);
					}
				}
			}
		};
		this.timer.schedule(timerTask, TimeUnit.SECONDS.toMillis(removeExpiredPeriod));
	}

	@PreDestroy
	private void destroy() {
		timer.cancel();
	}

	public Session createSession() {
		Session session = ctx.getBean(Session.class);
		sessions.put(session.token, session);
		return session;
	}

	public Session invalidate(Session session) {
		
		return sessions.remove(session.token);
	}

	public Session getSession(String token) {
		Session session = sessions.get(token);
		if (session != null) {
			if (session.isExpired()) {
				invalidate(session);
			} else {
				session.resetLastAccsses();
			}
		}
		return session;
	}

	@Override
	public String toString() {
		return "SessionContext [sessions=" + sessions + "]";
	}

}
