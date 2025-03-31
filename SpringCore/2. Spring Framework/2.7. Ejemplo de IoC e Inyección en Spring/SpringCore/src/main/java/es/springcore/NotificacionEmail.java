package es.springcore;

import org.springframework.stereotype.Component;

@Component("emailService")
public class NotificacionEmail implements ServicioNotificacion{

	@Override
	public void enviarNotificación(String mensaje) {
		System.out.println("Enviando Email: " + mensaje);
	}
}


