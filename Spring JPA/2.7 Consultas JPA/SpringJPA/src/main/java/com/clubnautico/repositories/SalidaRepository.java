package com.clubnautico.repositories;

import com.clubnautico.entities.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Long> {
	List<Salida> findByBarcoMatricula(String matricula);

	List<Salida> findByDniPatron(String dniPatron);

	List<Salida> findByFechaHoraSalidaAfter(LocalDateTime fecha);

	List<Salida> findByFechaHoraSalidaBetween(LocalDateTime inicio, LocalDateTime fin);

	@Query("SELECT s FROM Salida s JOIN FETCH s.barco WHERE s.id = :id")
	Optional<Salida> findByIdWithBarco(Long id);

	@Query("SELECT s.destino, COUNT(s) FROM Salida s GROUP BY s.destino ORDER BY COUNT(s) DESC")
	List<Object[]> countSalidasPorDestino();

	@Query("SELECT s FROM Salida s WHERE s.barco.socio.id = :socioId")
	List<Salida> findBySocioId(Long socioId);
}
