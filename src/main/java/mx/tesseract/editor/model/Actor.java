package mx.tesseract.editor.model;

/*
 * Luis Gerardo Jiménez
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.util.Constantes;

@Entity
@Table(name = "Actor", catalog = "TESSERACT")
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
public class Actor extends Elemento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String otraCardinalidad;
	private Cardinalidad cardinalidad;
	private Integer cardinalidadIdInteger;

	public Actor() {
	}

	public Actor(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, Cardinalidad cardinalidad) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
		this.cardinalidad = cardinalidad;
	}

	public Actor(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, String otraCardinalidad, Cardinalidad cardinalidad) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
		this.otraCardinalidad = otraCardinalidad;
		this.cardinalidad = cardinalidad;
	}
	
	@Override
	@Transient
	//@StringLengthFieldValidator(message = "%{getText('MSG6',{'100', 'caracteres'})}", trim = true, maxLength = "100", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return super.getNombre();
	}
	
	@Override
	@Transient
	public void setNombre (String nombre) {
		super.setNombre(nombre);
	}
	
	@Override
	@Transient
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	//@StringLengthFieldValidator(message = "%{getText('MSG6',{'200', 'caracteres'})}", trim = true, maxLength = "200", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return super.getDescripcion();
	}
	
	@Override
	@Transient
	public void setDescripcion(String descripcion) {
		super.setDescripcion(descripcion);
	}
	
	@Column(name = "otraCardinalidad", length = 10)
	public String getOtraCardinalidad() {
		return this.otraCardinalidad;
	}

	public void setOtraCardinalidad(String otraCardinalidad) {
		this.otraCardinalidad = otraCardinalidad;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Cardinalidadid", nullable = false)
	public Cardinalidad getCardinalidad() {
		return this.cardinalidad;
	}

	public void setCardinalidad(Cardinalidad cardinalidad) {
		this.cardinalidad = cardinalidad;
	}
	
	@Transient
	public Integer getCardinalidadIdInteger() {
		return this.cardinalidadIdInteger;
	}
	
	@Transient
	public void setCardinalidadIdInteger(Integer cardinalidadIdInteger) {
		this.cardinalidadIdInteger = cardinalidadIdInteger;
	}

}
