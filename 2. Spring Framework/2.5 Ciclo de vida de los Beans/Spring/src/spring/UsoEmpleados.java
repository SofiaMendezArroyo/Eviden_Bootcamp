package spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UsoEmpleados {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext contexto = new ClassPathXmlApplicationContext("applicationContext.xml");

		Empleados Juan = contexto.getBean("miEmpleado", Empleados.class);
		System.out.println(Juan.getTareas());
		System.out.println(Juan.getInforme());

		System.out.println("--------------------------------");

		Empleados María = contexto.getBean("miSecretarioEmpleado", Empleados.class);
		System.out.println(María.getTareas());
		System.out.println(María.getInforme());

		System.out.println("--------------------------------");

		SecretarioEmpleado Luis = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		System.out.println("Email: " + Luis.getEmail());
		System.out.println("Empresa: " + Luis.getNombreEmpresa());

		System.out.println("--------------------------------");
		
		DirectorEmpleado Ana = contexto.getBean("miEmpleado", DirectorEmpleado.class);
		System.out.println(Ana.getTareas());
		System.out.println(Ana.getInforme());
		System.out.println("Email: " + Ana.getEmail());
		System.out.println("Empresa: " + Ana.getNombreEmpresa());

		System.out.println("--------------------------------");
		
		contexto.close();

	}

}
