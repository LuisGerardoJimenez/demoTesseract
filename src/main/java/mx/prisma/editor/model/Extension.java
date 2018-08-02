package mx.prisma.editor.model;

/*
 * Sergio Ramírez Camacho 13/06/2015
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Extension generated by hbm2java
 */
@Entity
@Table(name = "Extension", catalog = "PRISMA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid_origen", "CasoUsoElementoid_destino"}))
public class Extension implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String causa;
	private String region;
	private CasoUso casoUsoOrigen;
	private CasoUso casoUsoDestino;
	private Set<ReferenciaParametro> referencias = new HashSet<ReferenciaParametro>(0);


	public Extension() {
	}

	public Extension(String causa, String region, CasoUso casoUsoOrigen, CasoUso casoUsoDestino) {
		this.causa = causa;
		this.region = region;
		this.casoUsoOrigen = casoUsoOrigen;
		this.casoUsoDestino = casoUsoDestino;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "causa", nullable = false, length = 999)
	public String getCausa() {
		return this.causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	@Column(name = "region", nullable = false, length = 500)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CasoUsoElementoid_origen", referencedColumnName = "Elementoid")
	public CasoUso getCasoUsoOrigen() {
		return casoUsoOrigen;
	}

	public void setCasoUsoOrigen(CasoUso casoUsoOrigen) {
		this.casoUsoOrigen = casoUsoOrigen;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CasoUsoElementoid_destino", referencedColumnName = "Elementoid")
	public CasoUso getCasoUsoDestino() {
		return casoUsoDestino;
	}

	public void setCasoUsoDestino(CasoUso casoUsoDestino) {
		this.casoUsoDestino = casoUsoDestino;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "extension", orphanRemoval = true)
	public Set<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(Set<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}
	
	

}
