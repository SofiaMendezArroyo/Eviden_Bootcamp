package spring1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("spring1")
@PropertySource("classpath:datosEmpresa.propiedades")
public class EmpleadosConfig {

	// definir el bean para InformeFinancieroDtoCompras
	@Bean
	public CreacionInformeFinanciero informeFinancieroDtoCompras() {
		return new InformeFinancieroDtoCompras();
	}
	
	// definir el bean para DirectorioFinanciero e inyectar dependencias
	@Bean
	public Empleados directorFinanciero() {
		return new DirectorFiananciero(informeFinancieroDtoCompras());
	}
}
