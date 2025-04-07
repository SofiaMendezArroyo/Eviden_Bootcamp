package com.hibernate.dao;

import com.hibernate.entities.Barco;
import javax.persistence.*;
import javax.persistence.criteria.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class BarcoDAO {
    
    private EntityManager em;
    private Validator validator;
    
    public BarcoDAO(EntityManager em) {
        this.em = em;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }
    
    // CREATE
    public void create(Barco barco) {
        Set<ConstraintViolation<Barco>> violations = validator.validate(barco);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        
        em.getTransaction().begin();
        try {
            em.persist(barco);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    // READ (by matrícula)
    public Barco findByMatricula(String matricula) {
        return em.find(Barco.class, matricula);
    }
    
    // READ (all)
    public List<Barco> findAll() {
        TypedQuery<Barco> query = em.createQuery("SELECT b FROM Barco b", Barco.class);
        return query.getResultList();
    }
    
    // UPDATE
    public Barco update(Barco barco) {
        Set<ConstraintViolation<Barco>> violations = validator.validate(barco);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        
        em.getTransaction().begin();
        try {
            Barco updatedBarco = em.merge(barco);
            em.getTransaction().commit();
            return updatedBarco;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    // DELETE
    public void delete(String matricula) {
        em.getTransaction().begin();
        try {
            Barco barco = em.find(Barco.class, matricula);
            if (barco != null) {
                em.remove(barco);
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
    
    // JPQL: Barcos por socio
    public List<Barco> findBySocio(Long socioId) {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT b FROM Barco b WHERE b.socio.id = :socioId", Barco.class);
        query.setParameter("socioId", socioId);
        return query.getResultList();
    }
    
    // JPQL: Barcos por nombre (búsqueda parcial)
    public List<Barco> findByNombre(String nombre) {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT b FROM Barco b WHERE LOWER(b.nombre) LIKE LOWER(:nombre)", Barco.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
    // JPQL: Barcos con cuota mayor a un valor
    public List<Barco> findBarcosConCuotaMayorQue(Double cuotaMinima) {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT b FROM Barco b WHERE b.cuota > :cuotaMinima ORDER BY b.cuota DESC", Barco.class);
        query.setParameter("cuotaMinima", cuotaMinima);
        return query.getResultList();
    }
    
    // JPQL: Barcos con sus salidas (JOIN FETCH para evitar N+1)
    public List<Barco> findAllWithSalidas() {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT DISTINCT b FROM Barco b LEFT JOIN FETCH b.salidas", Barco.class);
        return query.getResultList();
    }
    
    // Consulta nativa: Total de barcos por socio
    public List<Object[]> countBarcosPorSocio() {
        return em.createNativeQuery(
            "SELECT s.nombre, s.apellido, COUNT(b.matricula) as total_barcos " +
            "FROM socios s LEFT JOIN barcos b ON s.id = b.socio_id " +
            "GROUP BY s.nombre, s.apellido " +
            "ORDER BY total_barcos DESC")
            .getResultList();
    }
    
    // Criteria API: Búsqueda avanzada de barcos
    public List<Barco> findBarcosConFiltros(String nombre, Integer amarreDesde, Integer amarreHasta, 
                                          Double cuotaMin, Double cuotaMax, Long socioId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Barco> cq = cb.createQuery(Barco.class);
        Root<Barco> barco = cq.from(Barco.class);
        
        // Predicados dinámicos
        Predicate predicate = cb.conjunction();
        
        if (nombre != null && !nombre.isEmpty()) {
            predicate = cb.and(predicate, 
                cb.like(cb.lower(barco.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        
        if (amarreDesde != null) {
            predicate = cb.and(predicate, 
                cb.ge(barco.get("numeroAmarre"), amarreDesde));
        }
        
        if (amarreHasta != null) {
            predicate = cb.and(predicate, 
                cb.le(barco.get("numeroAmarre"), amarreHasta));
        }
        
        if (cuotaMin != null) {
            predicate = cb.and(predicate, 
                cb.ge(barco.get("cuota"), cuotaMin));
        }
        
        if (cuotaMax != null) {
            predicate = cb.and(predicate, 
                cb.le(barco.get("cuota"), cuotaMax));
        }
        
        if (socioId != null) {
            predicate = cb.and(predicate, 
                cb.equal(barco.get("socio").get("id"), socioId));
        }
        
        cq.select(barco)
          .where(predicate)
          .orderBy(cb.asc(barco.get("nombre")));
        
        return em.createQuery(cq).getResultList();
    }
    
    // Consulta con named query (debe estar definida en la entidad Barco)
    public List<Barco> findBarcosPorAmarreYCuota(Integer numeroAmarre, Double cuota) {
        TypedQuery<Barco> query = em.createNamedQuery("Barco.findByAmarreAndCuota", Barco.class);
        query.setParameter("numeroAmarre", numeroAmarre);
        query.setParameter("cuota", cuota);
        return query.getResultList();
    }
    
    // Consulta para obtener barcos con sus socios (JOIN FETCH)
    public List<Barco> findAllWithSocios() {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT b FROM Barco b JOIN FETCH b.socio", Barco.class);
        return query.getResultList();
    }
    
    // Consulta paginada de barcos
    public List<Barco> findBarcosPaginados(int pageNumber, int pageSize) {
        TypedQuery<Barco> query = em.createQuery(
            "SELECT b FROM Barco b ORDER BY b.nombre", Barco.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}