package spring1;

import org.springframework.beans.factory.annotation.Value;

public class DirectorFiananciero implements Empleados {

	private CreacionInformeFinanciero informeFinanciero;
	@Value("${email}")
	private String email;
	@Value("${nombreEmpresa}")
	private String nombreEmpresa;
	
	public DirectorFiananciero(CreacionInformeFinanciero informeFinanciero) {
		this.informeFinanciero = informeFinanciero;
	}
	
	@Override
	public String getTareas() {
		// TODO Auto-generated method stub
		return "Gestión y direccion de las operaciones financieras de la empresa";
	}

	@Override
	public String getInforme() {
		// TODO Auto-generated method stub
		return informeFinanciero.getInformeFinanciero();
	}

	public String getEmail() {
		return email;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	
}
