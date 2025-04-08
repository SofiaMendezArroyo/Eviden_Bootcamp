package com.clubnautico.tests;

import com.clubnautico.entities.*;
import com.clubnautico.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClubNauticoApplicationTests {

	@Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private BarcoRepository barcoRepository;
    
    @Autowired
    private SalidaRepository salidaRepository;
    
    @Test
    void contextLoads() {
        assertNotNull(socioRepository);
        assertNotNull(barcoRepository);
        assertNotNull(salidaRepository);
    }
    
    @Test
    void testCrearSocio() {
        Socio socio = new Socio();
        socio.setNombre("Test");
        socio.setApellido("Socio");
        socio.setDni("99999999R");
        socio.setTelefono("600000000");
        socio.setEmail("test@email.com");
        
        Socio saved = socioRepository.save(socio);
        assertNotNull(saved.getId());
        assertEquals("Test", saved.getNombre());
    }
    
    @Test
    void testFindBarcosBySocio() {
        Socio socio = socioRepository.findByDni("12345678A").orElseThrow();
        List<Barco> barcos = barcoRepository.findBySocioId(socio.getId());
        
        assertEquals(2, barcos.size());
        assertTrue(barcos.stream().anyMatch(b -> b.getNombre().equals("Velero Azul")));
    }
    
    @Test
    void testSalidasFuturas() {
        List<Salida> salidas = salidaRepository.findByFechaHoraSalidaAfter(LocalDateTime.now());
        assertFalse(salidas.isEmpty());
        assertTrue(salidas.get(0).getFechaHoraSalida().isAfter(LocalDateTime.now()));
    }
    
    @Test
    void testNamedQuery() {
        List<Barco> barcos = barcoRepository.findByNumeroAmarre(15);
        assertEquals(1, barcos.size());
        assertEquals("Velero Azul", barcos.get(0).getNombre());
    }
    
    @Test
    void testDeleteBarco() {
        Barco barco = barcoRepository.findById("ABC1234567").orElseThrow();
        barcoRepository.delete(barco);
        
        Optional<Barco> deleted = barcoRepository.findById("ABC1234567");
        assertFalse(deleted.isPresent());
    }
    
    @Test
    void testValidation() {
        Barco barco = new Barco();
        barco.setMatricula("INVALID"); // Matrícula demasiado corta
        barco.setNombre("Test");
        barco.setNumeroAmarre(1);
        barco.setCuota(100.0);
        
        assertThrows(Exception.class, () -> barcoRepository.save(barco));
    }
}
