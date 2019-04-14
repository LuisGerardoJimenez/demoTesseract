package mx.tesseract.editor.model;

/*
 * Luis Gerardo Jiménez
 */
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.util.Constantes;

@Entity
@Table(name = "Modulo", catalog = "PRISMA", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "clave", "Proyectoid" }) })
public class Modulo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String clave;
	private String nombre;
	private String descripcion;
	private Proyecto proyecto;
	private Set<CasoUso> casosdeuso = new HashSet<CasoUso>(0);
	private Set<Pantalla> pantallas = new HashSet<Pantalla>(0);


	public Modulo() {
	}

	public Modulo(String clave, String nombre, String descripcion,
			Proyecto proyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.proyecto = proyecto;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'10', 'caracteres'})}", trim = true, maxLength = "10", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS, shortCircuit = true)
	@Column(name = "clave", unique = true, nullable = false, length = 10)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	@Column(name = "descripcion", nullable = false, length = 999)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Proyectoid", nullable = false)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo", orphanRemoval = true)
	public Set<CasoUso> getCasosdeuso() {
		return casosdeuso;
	}

	public void setCasosdeuso(Set<CasoUso> casosdeuso) {
		this.casosdeuso = casosdeuso;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo", orphanRemoval = true)
	public Set<Pantalla> getPantallas() {
		return pantallas;
	}

	public void setPantallas(Set<Pantalla> pantallas) {
		this.pantallas = pantallas;
	}



}
