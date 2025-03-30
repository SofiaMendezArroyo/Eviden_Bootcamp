package ioc;

public class ManagerNotificacion {

	private ServicioNotificacion servicioNotificacion;
	
	public ManagerNotificacion(ServicioNotificacion servicioNotificacion) {
		this.servicioNotificacion = servicioNotificacion;
	}
	
	public void notificarUser(String mensaje) {
		servicioNotificacion.enviarNotificación(mensaje);
	}
}


