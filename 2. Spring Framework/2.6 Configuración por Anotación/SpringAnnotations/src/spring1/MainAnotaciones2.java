package spring1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAnotaciones2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// leer el XML de configuracion
		ClassPathXmlApplicationContext contexto = new ClassPathXmlApplicationContext("applicationContext.xml");

		// leer el class de configuracion
		AnnotationConfigApplicationContext contexto2 = new AnnotationConfigApplicationContext(EmpleadosConfig.class);

		// pedir un bean al contenedor
		Empleados empleado1 = contexto2.getBean("directorFinanciero", Empleados.class);
		System.out.println(empleado1.getTareas());
		System.out.println(empleado1.getInforme());

		System.out.println("------------------------------------------------------------");

		DirectorFiananciero empleado2 = contexto2.getBean("directorFinanciero", DirectorFiananciero.class);
		System.out.println("Email del director: " + empleado2.getEmail());
		System.out.println("Nombre de la empresa: " + empleado2.getNombreEmpresa());

		System.out.println("------------------------------------------------------------");

		Empleados Antonio = contexto.getBean("comercialExperimentado", Empleados.class);
		Empleados Lucia = contexto.getBean("comercialExperimentado", Empleados.class);

		// APUNTAN A LA MISMA DIRECCIÓN DE MEMORÍA?
		if (Antonio == Lucia) {
			System.out.println("Apuntan al mismo lugar de memoria");
			System.out.println(Antonio + "\n" + Lucia);
		} else {
			System.out.println("No apuntan al mismo lugar de memoria");
			System.out.println(Antonio + "\n" + Lucia);
		}

		// crerrar el contexto
		contexto.close();
		contexto2.close();
	}

}
