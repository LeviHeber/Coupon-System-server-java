package couponsSystem.core.controllers.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Session {

	private static final int TOKEN_MAX_LENGTH = 15;
	public final String token = UUID.randomUUID().toString().replace("-", "").substring(0, TOKEN_MAX_LENGTH);
	private Map<String, Object> attributes = new HashMap<>();
	private long lastAccess;
	@Value("${session.max.inactive.interval:1}")
	private long maxInactiveInterval;

	@PostConstruct
	private void init() {
		resetLastAccsses();
		this.maxInactiveInterval = TimeUnit.MINUTES.toMillis(maxInactiveInterval);
	}

	public void setAttributes(String name, Object value) {
		attributes.put(name, value);
	}

	public Object getAttributes(String name) {
		return attributes.get(name);
	}

	public void resetLastAccsses() {
		this.lastAccess = System.currentTimeMillis();
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - lastAccess > maxInactiveInterval;
	}

	@Override
	public String toString() {
		return "Session [token=" + token + ", attributes=" + attributes + ", lastAccess=" + lastAccess
				+ ", maxInactiveInterval=" + maxInactiveInterval + "]";
	}

	
}
