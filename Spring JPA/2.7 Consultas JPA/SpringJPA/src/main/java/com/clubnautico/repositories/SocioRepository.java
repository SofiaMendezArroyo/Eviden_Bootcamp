package com.clubnautico.repositories;

import com.clubnautico.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
	// Consulta derivada del nombre del método
	Optional<Socio> findByDni(String dni);

	List<Socio> findByNombreContainingIgnoreCase(String nombre);

	List<Socio> findByApellidoContainingIgnoreCase(String apellido);

	// JPQL personalizado
	@Query("SELECT s FROM Socio s WHERE SIZE(s.barcos) > 0")
	List<Socio> findSociosConBarcos();

	@Query("SELECT s FROM Socio s JOIN FETCH s.barcos WHERE s.id = :id")
	Optional<Socio> findByIdWithBarcos(Long id);

	// Consulta nativa
	@Query(value = "SELECT * FROM socios WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) "
			+ "AND LOWER(apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))", nativeQuery = true)
	List<Socio> buscarPorNombreYApellido(String nombre, String apellido);
}
