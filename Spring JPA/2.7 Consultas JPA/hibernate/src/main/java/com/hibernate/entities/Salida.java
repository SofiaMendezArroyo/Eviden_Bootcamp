package com.hibernate.entities;

import javax.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salidas")
public class Salida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "La fecha y hora de salida no pueden ser nulas")
	@FutureOrPresent(message = "La fecha y hora de salida deben ser en el presente o futuro")
	@Column(name = "fecha_hora_salida", nullable = false)
	private LocalDateTime fechaHoraSalida;

	@NotBlank(message = "El destino no puede estar vacío")
	@Size(max = 200, message = "El destino no puede exceder los 200 caracteres")
	@Column(name = "destino", nullable = false)
	private String destino;

	@NotBlank(message = "El nombre del patrón no puede estar vacío")
	@Size(max = 100, message = "El nombre del patrón no puede exceder los 100 caracteres")
	@Column(name = "nombre_patron", nullable = false)
	private String nombrePatron;

	@NotBlank(message = "El apellido del patrón no puede estar vacío")
	@Size(max = 100, message = "El apellido del patrón no puede exceder los 100 caracteres")
	@Column(name = "apellido_patron", nullable = false)
	private String apellidoPatron;

	@NotBlank(message = "El DNI del patrón no puede estar vacío")
	@Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El DNI del patrón debe tener 8 números seguidos de una letra")
	@Column(name = "dni_patron", nullable = false)
	private String dniPatron;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "barco_matricula", nullable = false)
	private Barco barco;

	// Constructores, getters y setters
	public Salida() {
	}

	public Salida(LocalDateTime fechaHoraSalida, String destino, String nombrePatron, String apellidoPatron,
			String dniPatron, Barco barco) {
		this.fechaHoraSalida = fechaHoraSalida;
		this.destino = destino;
		this.nombrePatron = nombrePatron;
		this.apellidoPatron = apellidoPatron;
		this.dniPatron = dniPatron;
		this.barco = barco;
	}

	// Getters y setters para todos los campos

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaHoraSalida() {
		return fechaHoraSalida;
	}

	public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
		this.fechaHoraSalida = fechaHoraSalida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNombrePatron() {
		return nombrePatron;
	}

	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}

	public String getApellidoPatron() {
		return apellidoPatron;
	}

	public void setApellidoPatron(String apellidoPatron) {
		this.apellidoPatron = apellidoPatron;
	}

	public String getDniPatron() {
		return dniPatron;
	}

	public void setDniPatron(String dniPatron) {
		this.dniPatron = dniPatron;
	}

	public Barco getBarco() {
		return barco;
	}

	public void setBarco(Barco barco) {
		this.barco = barco;
	}

}
