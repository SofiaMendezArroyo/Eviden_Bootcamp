package ioc;

public class NotificacionEmail implements ServicioNotificacion{

	@Override
	public void enviarNotificación(String mensaje) {
		System.out.println("Enviando SMS: " + mensaje);
	}
}


