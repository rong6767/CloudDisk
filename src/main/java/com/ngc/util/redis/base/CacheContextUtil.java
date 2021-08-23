package com.ngc.util.redis.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@SuppressWarnings("static-access")
@Component
public class CacheContextUtil implements ApplicationContextAware {
	private static ApplicationContext commonApplicationContext;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.commonApplicationContext = context;
	}

	public static Object getBean(String beanId) {
		return commonApplicationContext.getBean(beanId);
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		return commonApplicationContext.getBean(beanId, clazz);
	}
}