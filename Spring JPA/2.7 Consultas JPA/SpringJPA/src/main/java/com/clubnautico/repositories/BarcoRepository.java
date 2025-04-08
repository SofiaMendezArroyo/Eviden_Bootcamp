package com.clubnautico.repositories;

import com.clubnautico.entities.Barco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarcoRepository extends JpaRepository<Barco, String> {
	List<Barco> findBySocioId(Long socioId);

	List<Barco> findByNombreContainingIgnoreCase(String nombre);

	List<Barco> findByNumeroAmarre(Integer numeroAmarre);

	List<Barco> findByCuotaGreaterThan(Double cuotaMinima);

	@Query("SELECT b FROM Barco b JOIN FETCH b.socio WHERE b.matricula = :matricula")
	Optional<Barco> findByMatriculaWithSocio(String matricula);

	@Query("SELECT b FROM Barco b JOIN FETCH b.salidas WHERE b.matricula = :matricula")
	Optional<Barco> findByMatriculaWithSalidas(String matricula);

	@Query("SELECT DISTINCT b FROM Barco b LEFT JOIN FETCH b.salidas")
	List<Barco> findAllWithSalidas();
}
