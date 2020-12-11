package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import app.web.SessionExpirationChecker;

@SpringBootApplication
public class CouponsFinalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext boot = SpringApplication.run(CouponsFinalApplication.class, args);
		
		ExpirationChecker ec = boot.getBean(ExpirationChecker.class);
		ec.start();
		SessionExpirationChecker sec = boot.getBean(SessionExpirationChecker.class);
		sec.start();
		//ec.terminate();
		//sec.terminate();
	}

}
