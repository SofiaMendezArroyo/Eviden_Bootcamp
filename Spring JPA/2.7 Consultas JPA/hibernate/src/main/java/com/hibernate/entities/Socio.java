package com.hibernate.entities;

import javax.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "socios")
public class Socio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@NotBlank(message = "El apellido no puede estar vacío")
	@Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
	@Column(name = "apellido", nullable = false)
	private String apellido;

	@NotBlank(message = "El DNI no puede estar vacío")
	@Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El DNI debe tener 8 números seguidos de una letra")
	@Column(name = "dni", unique = true, nullable = false)
	private String dni;

	@NotBlank(message = "El teléfono no puede estar vacío")
	@Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
	@Column(name = "telefono", nullable = false)
	private String telefono;

	@Email(message = "Debe proporcionar un email válido")
	@Column(name = "email", unique = true)
	private String email;

	@OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Barco> barcos;

	// Constructores, getters y setters
	public Socio() {
	}

	public Socio(String nombre, String apellido, String dni, String telefono, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
	}

// Getters y setters para todos los campos
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Barco> getBarcos() {
		return barcos;
	}

	public void setBarcos(List<Barco> barcos) {
		this.barcos = barcos;
	}

}
