package mx.tesseract.admin.model;

/*
 * Luis Gerardo Jiménez
 */

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;


@Entity
@Table(name = "Colaborador", catalog = "TESSERACT")
public class Colaborador implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String curp;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correoElectronico;
	private String contrasenia;
	private boolean administrador;
	private Set<ColaboradorProyecto> colaborador_proyectos = new HashSet<ColaboradorProyecto>(0);
	private Set<Telefono> telefonos = new HashSet<Telefono>(0);
	
	public Colaborador() {
	}

	public Colaborador(String curp, String nombre, String apellidoPaterno,
			String apellidoMaterno, String correoElectronico, String contrasenia, boolean administrador) {
		this.curp = curp;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.correoElectronico = correoElectronico;
		this.contrasenia = contrasenia;
		this.administrador = administrador;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG51')}", trim = true, minLength = "18", maxLength = "18", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG52')}", regex= Constantes.REGEX_CURP, shortCircuit = true)
	@Id
	@Column(name = "CURP", unique = true, nullable = false, length = 18)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_SIN_ESPACIOS, shortCircuit = true)
	@Column(name = "apellidoPaterno", nullable = false, length = 45)
	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_SIN_ESPACIOS, shortCircuit = true)
	@Column(name = "apellidoMaterno", nullable = false, length = 45)
	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit= true)
	@EmailValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", shortCircuit = true)
	@Column(name = "correoElectronico", nullable = false, length = 45)
	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CONTRASENIA, shortCircuit = true)
	@Column(name = "contrasenia", nullable = false, length = 20)
	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", cascade = CascadeType.ALL)
	public Set<ColaboradorProyecto> getColaborador_proyectos() {
		return colaborador_proyectos;
	}

	public void setColaborador_proyectos(
			Set<ColaboradorProyecto> colaborador_proyectos) {
		this.colaborador_proyectos = colaborador_proyectos;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "colaborador", cascade = CascadeType.ALL)
	public Set<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Set<Telefono> telefonos) {
		this.telefonos = telefonos;
	}
	
	@Column(name = "administrador", nullable = false, length = 20)
	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	
}
