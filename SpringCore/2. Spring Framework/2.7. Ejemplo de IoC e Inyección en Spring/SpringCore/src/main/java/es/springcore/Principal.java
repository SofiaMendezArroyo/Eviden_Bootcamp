package es.springcore;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class Principal {

	public static void main(String[] args) {
		
		// Cargar el contexto de la aplicación
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			// Obtener los beans de ManagerNotificacion
			ManagerNotificacion managerEmail = context.getBean("managerEmail", ManagerNotificacion.class);
			managerEmail.notificarUser("Hola, este es un mensaje de prueba por Email");

			ManagerNotificacion managerSms = context.getBean("managerSms", ManagerNotificacion.class);
			managerSms.notificarUser("Hola, este es un mensaje de prueba por SMS");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			context.close();
		}

	}

}

