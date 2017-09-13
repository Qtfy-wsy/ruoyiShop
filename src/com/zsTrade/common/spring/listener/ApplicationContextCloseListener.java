package com.zsTrade.common.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextCloseListener implements ApplicationListener<ContextClosedEvent>{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		ApplicationContext parentContext = ((ContextClosedEvent) event).getApplicationContext().getParent();
	System.out.println(System.getProperty("user.dir"));
		if (null != parentContext) {
			logger.info("spring子容器关闭");
		} else {
			logger.info("spring父容器关闭");
		}
	}

}
