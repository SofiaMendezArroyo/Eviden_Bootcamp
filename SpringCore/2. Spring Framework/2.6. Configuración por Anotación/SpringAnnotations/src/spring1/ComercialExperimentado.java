package spring1;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class ComercialExperimentado implements Empleados {

	@Autowired
	@Qualifier("informeFinancieroTrim2")
	private CreacionInformeFinanciero nuevoInforme;
	
	// ejecución de código después de creación del Bean
	@PostConstruct
	public void ejecutaDespuesCreacion() {
		System.out.println("Ejecutado tras creación de Bean");
	}
	
	// ejecución de código después de apagado contenedor Spring
	@PreDestroy
	public void ejecutaAntesDestruccion() {
		System.out.println("Ejecutando antes de la destrucción");
	}
	
	
	
	/*
	@Autowired
	public ComercialExperimentado(CreacionInformeFinanciero nuevoInforme) {
		this.nuevoInforme = nuevoInforme;
	}
	
	@Autowired
	public void setNuevoInforme(CreacionInformeFinanciero nuevoInforme) {
		this.nuevoInforme = nuevoInforme;
	}*/

	public ComercialExperimentado() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getTareas() {
		// TODO Auto-generated method stub
		return "Vender, vender y vender más!";
	}

	@Override
	public String getInforme() {
		// TODO Auto-generated method stub
//		return "Informe generado por el comercial";
		return nuevoInforme.getInformeFinanciero();
	}	
	
}
