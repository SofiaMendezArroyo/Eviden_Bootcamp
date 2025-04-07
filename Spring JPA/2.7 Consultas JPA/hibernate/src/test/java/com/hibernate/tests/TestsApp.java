package com.hibernate.tests;

import com.hibernate.dao.*;
import com.hibernate.entities.*;
import javax.persistence.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestsApp {

	private EntityManagerFactory emf;
	private EntityManager em;
	private SocioDAO socioDAO;
	private BarcoDAO barcoDAO;
	private SalidaDAO salidaDAO;

	@BeforeAll
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("ClubNauticoPU"); // Tu nombre en persistence.xml
		em = emf.createEntityManager();

		socioDAO = new SocioDAO(em);
		barcoDAO = new BarcoDAO(em);
		salidaDAO = new SalidaDAO(em);

		insertarDatosDePrueba();
	}

	private void insertarDatosDePrueba() {
		Socio socio = new Socio("Juan", "Pérez", "12345678A", "666555444", "juan@mail.com");
		Barco barco = new Barco("ABC1234567", "La Perla", 12, 250.0, socio);
		Salida salida = new Salida(LocalDateTime.now().plusDays(1), "Ibiza", "Carlos", "López", "87654321Z", barco);

		em.getTransaction().begin();
		em.persist(socio);
		em.persist(barco);
		em.persist(salida);
		em.getTransaction().commit();
	}

	@Test
	public void testSociosConBarcos() {
		List<Socio> socios = socioDAO.findSociosConBarcos();
		Assertions.assertFalse(socios.isEmpty(), "No se encontraron socios con barcos");
		socios.forEach(s -> System.out.println(s.getNombre() + " tiene " + s.getBarcos().size() + " barcos."));
	}

	@Test
	public void testBarcosPorSocio() {
		Long socioId = socioDAO.findAll().get(0).getId();
		List<Barco> barcos = barcoDAO.findBySocio(socioId);
		Assertions.assertFalse(barcos.isEmpty());
		barcos.forEach(b -> System.out.println("Barco: " + b.getNombre()));
	}

	@Test
	public void testSalidasPorBarco() {
		String matricula = "ABC1234567";
		List<Salida> salidas = salidaDAO.findByBarco(matricula);
		Assertions.assertFalse(salidas.isEmpty());
		salidas.forEach(s -> System.out.println("Salida a: " + s.getDestino()));
	}

	@Test
	public void testSalidasFuturas() {
		List<Salida> futuras = salidaDAO.findSalidasFuturas();
		Assertions.assertFalse(futuras.isEmpty());
		futuras.forEach(s -> System.out.println("Futura salida a: " + s.getDestino()));
	}

	@Test
	public void testSalidasPorDniPatron() {
		List<Salida> salidas = salidaDAO.findByPatron("87654321Z");
		Assertions.assertFalse(salidas.isEmpty());
		salidas.forEach(s -> System.out.println("Patrón: " + s.getNombrePatron()));
	}

	@Test
	public void testBarcosConCuotaMayor() {
		List<Barco> barcos = barcoDAO.findBarcosConCuotaMayorQue(100.0);
		Assertions.assertFalse(barcos.isEmpty());
		barcos.forEach(b -> System.out.println("Barco caro: " + b.getNombre() + " - " + b.getCuota()));
	}

	@Test
	public void testSalidasPorDestinoConConteo() {
		List<Object[]> resultado = salidaDAO.countSalidasPorDestino();
		Assertions.assertFalse(resultado.isEmpty());
		resultado.forEach(r -> System.out.println("Destino: " + r[0] + " - Total salidas: " + r[1]));
	}

	@Test
	public void testSalidasPorRangoYporSocio() {
		Long socioId = socioDAO.findAll().get(0).getId();
		LocalDateTime desde = LocalDateTime.now().minusDays(1);
		LocalDateTime hasta = LocalDateTime.now().plusDays(10);
		List<Salida> salidas = salidaDAO.findSalidasPorSocioYRangoFechas(socioId, desde, hasta);
		Assertions.assertFalse(salidas.isEmpty());
		salidas.forEach(s -> System.out.println("Destino: " + s.getDestino() + ", Fecha: " + s.getFechaHoraSalida()));
	}

	@AfterAll
	public void tearDown() {
		if (em != null)
			em.close();
		if (emf != null)
			emf.close();
	}

}
