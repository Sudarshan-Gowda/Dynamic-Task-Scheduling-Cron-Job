package com.star.sud.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class AnnotationScheduler {

	private static Logger logger = LoggerFactory.getLogger(AnnotationScheduler.class);

	@Scheduled(fixedRate = 5000, initialDelay = 10000)
	public void runAtFixedRate() {
		logger.info("runAtFixedRate: Print every 5 seconds with one time initial delay of 10 seconds");
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void runCron() {
		logger.info("runCron: Print for every 5 seconds using cron expression");
	}

}
