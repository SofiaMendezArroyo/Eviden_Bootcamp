package es.springcore;

import org.springframework.stereotype.Component;

@Component("smsService")
public class NotificacionSMS implements ServicioNotificacion {

	@Override
	public void enviarNotificación(String mensaje) {
		System.out.println("Enviando SMS: " + mensaje);
	}
}


