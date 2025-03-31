package ioc;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServicioNotificacion servicioCorreo = new NotificacionEmail();
		ManagerNotificacion managerCorreo = new ManagerNotificacion(servicioCorreo);
		managerCorreo.notificarUser("Hola, este es un mensaje de prueba por Email");
		
		ServicioNotificacion servicioSms = new NotificacionSMS();
		ManagerNotificacion managerSms = new ManagerNotificacion(servicioSms);
		managerSms.notificarUser("Hola, este es un mensaje de prueba por SMS");
	}

}


