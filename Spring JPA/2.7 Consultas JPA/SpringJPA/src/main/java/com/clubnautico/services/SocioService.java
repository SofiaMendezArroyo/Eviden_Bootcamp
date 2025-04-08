package com.clubnautico.services;

import com.clubnautico.entities.Socio;
import com.clubnautico.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SocioService {
	@Autowired
	private SocioRepository socioRepository;

	@Transactional
	public Socio save(@Valid Socio socio) {
		return socioRepository.save(socio);
	}

	public List<Socio> findAll() {
		return socioRepository.findAll();
	}

	public Optional<Socio> findById(Long id) {
		return socioRepository.findById(id);
	}

	public Optional<Socio> findByDni(String dni) {
		return socioRepository.findByDni(dni);
	}

	public List<Socio> findByNombre(String nombre) {
		return socioRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public List<Socio> findSociosConBarcos() {
		return socioRepository.findSociosConBarcos();
	}

	@Transactional
	public void deleteById(Long id) {
		socioRepository.deleteById(id);
	}

	public boolean existsById(Long id) {
		return socioRepository.existsById(id);
	}
}
