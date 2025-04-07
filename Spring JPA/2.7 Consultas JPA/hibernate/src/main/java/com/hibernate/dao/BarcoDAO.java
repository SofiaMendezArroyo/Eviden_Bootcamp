package com.hibernate.dao;

import com.hibernate.entities.Barco;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;

public class BarcoDAO {
	private EntityManager em;
	private Validator validator;

	public BarcoDAO(EntityManager em) {
		this.em = em;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	// Operaciones CRUD básicas similares a SocioDAO

	// Consultas específicas
	public List<Barco> findBySocio(Long socioId) {
		TypedQuery<Barco> query = em.createQuery("SELECT b FROM Barco b WHERE b.socio.id = :socioId", Barco.class);
		query.setParameter("socioId", socioId);
		return query.getResultList();
	}

	public List<Barco> findByMatricula(String matricula) {
		TypedQuery<Barco> query = em.createQuery("SELECT b FROM Barco b WHERE b.matricula LIKE :matricula",
				Barco.class);
		query.setParameter("matricula", "%" + matricula + "%");
		return query.getResultList();
	}

	// Consulta con Criteria API
	public List<Barco> findBarcosConCuotaMayorQue(Double cuotaMinima) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Barco> cq = cb.createQuery(Barco.class);
		Root<Barco> barco = cq.from(Barco.class);

		cq.select(barco).where(cb.greaterThan(barco.get("cuota"), cuotaMinima)).orderBy(cb.asc(barco.get("nombre")));

		return em.createQuery(cq).getResultList();
	}
	
	// Consulta JPQL
	public List<Barco> findBarcosConSalidasEnMes(int mes) {
	    return em.createQuery(
	        "SELECT DISTINCT b FROM Barco b JOIN b.salidas s " +
	        "WHERE MONTH(s.fechaHoraSalida) = :mes", Barco.class)
	        .setParameter("mes", mes)
	        .getResultList();
	}
}
