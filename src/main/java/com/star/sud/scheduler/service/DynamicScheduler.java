package com.star.sud.scheduler.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.star.sud.model.TConfiguration;
import com.star.sud.repository.ConfigurationRepository;

@Service
public class DynamicScheduler implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(DynamicScheduler.class);

	@Autowired
	private ConfigurationRepository repository;

	@PostConstruct
	public void initDatabase() {
		List<TConfiguration> entitys = new ArrayList<>();
		TConfiguration entity = new TConfiguration("NEXT_EXEC_TIME", "4", 'A');
		TConfiguration entity2 = new TConfiguration("CHRON_EXPRESSION", "0/10 * * * * ?", 'A');
		entitys.add(entity);
		entitys.add(entity2);
		repository.saveAll(entitys);
	}

	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		scheduler.setPoolSize(1);
		scheduler.initialize();
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

		taskRegistrar.setScheduler(poolScheduler());

		// Random next execution time
		taskRegistrar.addTriggerTask(() -> scheduleDynamically(), t -> {
			Calendar nextExecutionTime = new GregorianCalendar();
			Date lastActualExecutionTime = t.lastActualExecutionTime();
			nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
			nextExecutionTime.add(Calendar.SECOND, getNextExecutionTime());
			return nextExecutionTime.getTime();
		});

		// Fixed next execution time.
		taskRegistrar.addTriggerTask(() -> scheduleFixed(), t -> {
			Calendar nextExecutionTime = new GregorianCalendar();
			Date lastActualExecutionTime = t.lastActualExecutionTime();
			nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
			nextExecutionTime.add(Calendar.SECOND, 7); // This is where we set the next execution time.
			return nextExecutionTime.getTime();
		});

		// Next execution time is taken from DB, so if the value in DB changes, next
		// execution time will change too.
		taskRegistrar.addTriggerTask(
				() -> scheduledDatabase(repository.findById("NEXT_EXEC_TIME").get().getConfigValue()), t -> {
					Calendar nextExecutionTime = new GregorianCalendar();
					Date lastActualExecutionTime = t.lastActualExecutionTime();
					nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
					nextExecutionTime.add(Calendar.SECOND,
							Integer.parseInt(repository.findById("NEXT_EXEC_TIME").get().getConfigValue()));
					return nextExecutionTime.getTime();
				});

		// Cron expression
		// CronTrigger cronTrigger = new CronTrigger("0/10 * * * * ?",
		// TimeZone.getDefault());
		// taskRegistrar.addTriggerTask(() -> scheduleCron("0/10 * * * * ?"),
		// cronTrigger);

		String expression = repository.findById("CHRON_EXPRESSION").get().getConfigValue();
		CronTrigger cronTrigger = new CronTrigger(expression, TimeZone.getDefault());
		taskRegistrar.addTriggerTask(() -> scheduleCron(expression), cronTrigger);

	}

	private int getNextExecutionTime() {
		return new Random().nextInt(5) + 1;
	}

	private void scheduleDynamically() {
		log.info("scheduleDynamically: Next execution time of this changes every time between 1 and 5 seconds");
	}

	private void scheduleFixed() {
		log.info("scheduleFixed: Next execution time of this will always be 7 seconds");
	}

	private void scheduledDatabase(String time) {
		log.info("scheduledDatabase: Next execution time of this will be taken from DB -> {}", time);
	}

	private void scheduleCron(String cron) {
		log.info("scheduleCron: Next execution time of this taken from cron expression -> {}", cron);
	}

}
