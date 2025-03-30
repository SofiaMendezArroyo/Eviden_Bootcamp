package spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UsoDemoSingletonPrototype {

	public static void main(String[] args) {
		
		// Carga de XML de configuració
		
		ClassPathXmlApplicationContext contexto = new ClassPathXmlApplicationContext("applicationContext2.xml");

		// peticion de beans al contenedor String
		SecretarioEmpleado Luis = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		SecretarioEmpleado Laura = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		SecretarioEmpleado Maria = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		SecretarioEmpleado Pedro = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		SecretarioEmpleado Marta = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		SecretarioEmpleado Manolo = contexto.getBean("miSecretarioEmpleado", SecretarioEmpleado.class);
		
		System.out.println(Luis);
		System.out.println(Laura);
		System.out.println(Maria);
		System.out.println(Pedro);
		System.out.println(Marta);
		System.out.println(Manolo);
		
		if (Luis == Laura) System.out.println("Apuntan al mismo objeto");
		else System.out.println("No se trata del mismo objeto");
		
		contexto.close();
	}

}
