package com.hibernate.dao;

import com.hibernate.entities.Socio;
import javax.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class SocioDAO {
	private EntityManager em;
	private Validator validator;

	public SocioDAO(EntityManager em) {
		this.em = em;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	// CREATE
	public void create(Socio socio) {
		Set<ConstraintViolation<Socio>> violations = validator.validate(socio);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		em.getTransaction().begin();
		try {
			em.persist(socio);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
	}

	// READ (by ID)
	public Socio findById(Long id) {
		return em.find(Socio.class, id);
	}

	// READ (all)
	public List<Socio> findAll() {
		TypedQuery<Socio> query = em.createQuery("SELECT s FROM Socio s", Socio.class);
		return query.getResultList();
	}

	// UPDATE
	public Socio update(Socio socio) {
		Set<ConstraintViolation<Socio>> violations = validator.validate(socio);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		em.getTransaction().begin();
		try {
			Socio updatedSocio = em.merge(socio);
			em.getTransaction().commit();
			return updatedSocio;
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
			Socio socio = em.find(Socio.class, id);
			if (socio != null) {
				em.remove(socio);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		}
	}

	// Consultas específicas
	public List<Socio> findByNombre(String nombre) {
		TypedQuery<Socio> query = em.createQuery("SELECT s FROM Socio s WHERE s.nombre LIKE :nombre", Socio.class);
		query.setParameter("nombre", "%" + nombre + "%");
		return query.getResultList();
	}

	// Consulta JPQL
	public List<Socio> findSociosConBarcos() {
	    return em.createQuery(
	        "SELECT DISTINCT s FROM Socio s JOIN FETCH s.barcos", Socio.class)
	        .getResultList();
	}
}
