package com.hibernate.dao;

import com.hibernate.entities.Salida;
import javax.persistence.*;
import javax.persistence.criteria.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SalidaDAO {
	private EntityManager em;
	private Validator validator;

	public SalidaDAO(EntityManager em) {
		this.em = em;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	// CREATE
	public void create(Salida salida) {
		Set<ConstraintViolation<Salida>> violations = validator.validate(salida);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		em.getTransaction().begin();
		try {
			em.persist(salida);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
	}

	// READ (by ID)
	public Salida findById(Long id) {
		return em.find(Salida.class, id);
	}

	// READ (all)
	public List<Salida> findAll() {
		TypedQuery<Salida> query = em.createQuery("SELECT s FROM Salida s", Salida.class);
		return query.getResultList();
	}

	// UPDATE
	public Salida update(Salida salida) {
		Set<ConstraintViolation<Salida>> violations = validator.validate(salida);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		em.getTransaction().begin();
		try {
			Salida updatedSalida = em.merge(salida);
			em.getTransaction().commit();
			return updatedSalida;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
	}

	// DELETE
	public void delete(Long id) {
		em.getTransaction().begin();
		try {
			Salida salida = em.find(Salida.class, id);
			if (salida != null) {
				em.remove(salida);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
	}

	// CONSULTAS ESPECÍFICAS

	// JPQL: Salidas por barco
	public List<Salida> findByBarco(String matricula) {
		TypedQuery<Salida> query = em.createQuery(
				"SELECT s FROM Salida s WHERE s.barco.matricula = :matricula ORDER BY s.fechaHoraSalida DESC",
				Salida.class);
		query.setParameter("matricula", matricula);
		return query.getResultList();
	}

	// JPQL: Salidas por patrón (DNI)
	public List<Salida> findByPatron(String dniPatron) {
		TypedQuery<Salida> query = em.createQuery("SELECT s FROM Salida s WHERE s.dniPatron = :dniPatron",
				Salida.class);
		query.setParameter("dniPatron", dniPatron);
		return query.getResultList();
	}

	// JPQL: Salidas futuras
	public List<Salida> findSalidasFuturas() {
		TypedQuery<Salida> query = em.createQuery(
				"SELECT s FROM Salida s WHERE s.fechaHoraSalida > CURRENT_TIMESTAMP ORDER BY s.fechaHoraSalida",
				Salida.class);
		return query.getResultList();
	}

	// Consulta nativa: Conteo de salidas por destino
	public List<Object[]> countSalidasPorDestino() {
		return em
				.createNativeQuery(
						"SELECT destino, COUNT(*) as total FROM salidas GROUP BY destino ORDER BY total DESC")
				.getResultList();
	}

	// Criteria API: Salidas con filtros múltiples
	public List<Salida> findSalidasConFiltros(LocalDateTime desde, LocalDateTime hasta, String destino,
			String dniPatron) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Salida> cq = cb.createQuery(Salida.class);
		Root<Salida> salida = cq.from(Salida.class);

		// Predicados dinámicos
		Predicate predicate = cb.conjunction();

		if (desde != null) {
			predicate = cb.and(predicate, cb.greaterThanOrEqualTo(salida.get("fechaHoraSalida"), desde));
		}

		if (hasta != null) {
			predicate = cb.and(predicate, cb.lessThanOrEqualTo(salida.get("fechaHoraSalida"), hasta));
		}

		if (destino != null && !destino.isEmpty()) {
			predicate = cb.and(predicate, cb.like(salida.get("destino"), "%" + destino + "%"));
		}

		if (dniPatron != null && !dniPatron.isEmpty()) {
			predicate = cb.and(predicate, cb.equal(salida.get("dniPatron"), dniPatron));
		}

		cq.select(salida).where(predicate).orderBy(cb.desc(salida.get("fechaHoraSalida")));

		return em.createQuery(cq).getResultList();
	}

	// Consulta con JOIN FETCH para evitar N+1
	public List<Salida> findAllWithBarco() {
		TypedQuery<Salida> query = em.createQuery("SELECT s FROM Salida s JOIN FETCH s.barco", Salida.class);
		return query.getResultList();
	}

	// Salidas por rango de fechas y socio
	public List<Salida> findSalidasPorSocioYRangoFechas(Long socioId, LocalDateTime desde, LocalDateTime hasta) {
		TypedQuery<Salida> query = em.createQuery("SELECT s FROM Salida s JOIN s.barco b JOIN b.socio soc "
				+ "WHERE soc.id = :socioId AND s.fechaHoraSalida BETWEEN :desde AND :hasta "
				+ "ORDER BY s.fechaHoraSalida DESC", Salida.class);

		query.setParameter("socioId", socioId);
		query.setParameter("desde", desde);
		query.setParameter("hasta", hasta);

		return query.getResultList();
	}
}
