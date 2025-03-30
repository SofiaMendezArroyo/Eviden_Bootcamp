package es.springcore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "es.springcore")
public class AppConfig {

	@Bean
	public ManagerNotificacion managerEmail(@Qualifier("emailService")ServicioNotificacion emailService) {
		return new ManagerNotificacion(emailService);
	}

	@Bean
	public ManagerNotificacion managerSms(@Qualifier("smsService")ServicioNotificacion smsService) {
		return new ManagerNotificacion(smsService);
	}
	
}
