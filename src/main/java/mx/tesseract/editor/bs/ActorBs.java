package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.bs.CatalogoBs;
import mx.tesseract.bs.AnalisisEnum.CU_Actores;
import mx.tesseract.bs.ReferenciaEnum.TipoCatalogo;
import mx.tesseract.editor.bs.ElementoBs.Estado;
import mx.tesseract.editor.dao.ActorDAO;
import mx.tesseract.editor.dao.ActualizacionDAO;
import mx.tesseract.editor.dao.CardinalidadDAO;
import mx.tesseract.editor.dao.CasoUsoActorDAO;
import mx.tesseract.editor.dao.ReferenciaParametroDAO;
import mx.tesseract.editor.model.Actor;
import mx.tesseract.editor.model.Cardinalidad;
import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.editor.model.CasoUsoActor;
import mx.tesseract.editor.model.Paso;
import mx.tesseract.editor.model.PostPrecondicion;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class ActorBs {
	private static final String CLAVE = "ACT";
	public static void registrarActor(Actor model) throws Exception {
		try {
			model.setCardinalidad(new CardinalidadDAO().consultarCardinalidad(model.getCardinalidad().getId()));	
			validar(model);
			model.setClave(CLAVE);
			model.setNumero(new ActorDAO().siguienteNumeroActor(model
					.getProyecto().getId()));
			model.setEstadoElemento(ElementoBs.consultarEstadoElemento(Estado.EDICION));
			
			new ActorDAO().registrarActor(model);
			//actualizacion = new Actualizacion();
			new ActualizacionDAO();
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new TESSERACTValidacionException("El actor "
						+ model.getNombre() + " ya existe.", "MSG7",
						new String[] { "El", "actor", model.getNombre() },
						"model.nombre");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<Actor> consultarActoresProyecto(Proyecto proyecto) {
		List<Actor> listActores = new ActorDAO()
				.consultarActores(proyecto.getId());
		if (listActores == null) {
			throw new TESSERACTException("No se pueden consultar los actores.",
					"MSG13");
		}
		return listActores;
	}

	public static List<Cardinalidad> consultarCardinalidades() {
		List<Cardinalidad> listCardinalidad = new CardinalidadDAO().consultarCardinalidades();
		if (listCardinalidad == null) {
			throw new TESSERACTException(
					"No se pueden consultar las cardinalidades.", "MSG13");
		}
		
		CatalogoBs.opcionOtro(listCardinalidad, TipoCatalogo.CARDINALIDAD);
		return listCardinalidad;
	}

	private static void validar(Actor model) {
		// Validaciones del nombre
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó el nombre del actor.", "MSG4",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 200)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "200", "caracteres" }, "model.nombre");
		}
		if (Validador.contieneCaracterInvalido(model.getNombre())) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso un nombre con caracter inválido.",
					"MSG23", new String[] { "El", "nombre" }, "model.nombre");
		}
		List<Actor> actoresBD = ActorBs.consultarActoresProyecto(model.getProyecto());
		for(Actor actor : actoresBD) {
			if(actor.getId() != model.getId()) {
				if(actor.getNombre().equals(model.getNombre())) {
					throw new TESSERACTValidacionException(
							"El nombre del actor ya existe.",
							"MSG7",
							new String[] { "El", "Actor", model.getNombre() },
							"model.nombre");
				}
			}
		}
		// Validaciones de la Descripción
		if (Validador.esNuloOVacio(model.getDescripcion())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la descripción del actor.",
					"MSG4", null, "model.descripcion");
		}

		if (Validador.validaLongitudMaxima(model.getDescripcion(), 999)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una descripcion muy larga.", "MSG6",
					new String[] { "999", "caracteres" }, "model.descripcion");
		}	
		
		if (Validador.esNulo(model.getCardinalidad())) {
			throw new TESSERACTValidacionException(
					"El usuario no seleccionó la cardinalidad del actor",
					"MSG4", null, "model.cardinalidad.id");
		}
		
		if (model.getCardinalidad().getNombre().equals("Otra")) {
			if (Validador.esNuloOVacio(model.getOtraCardinalidad())) {
				throw new TESSERACTValidacionException(
						"El usuario no ingresó la cardinalidad del actor",
						"MSG4", null, "model.otraCardinalidad");
			}
		} else {
			model.setOtraCardinalidad(null);
		}
	}

	public static Actor consultarActor(int idActor) {
		Actor actor = null;
		actor = new ActorDAO().consultarActor(idActor);
		if (actor == null) {
			throw new TESSERACTException("No se puede consultar el actor.",
					"MSG13");
		}
		return actor;
	}

	public static void eliminarActor(Actor model) throws Exception {
		try {
			ElementoBs.verificarEstado(model, CU_Actores.ELIMINARACTOR7_3);
			new ActorDAO().eliminarElemento(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1451)
				{
					throw new TESSERACTException("No se puede eliminar el actor.", "MSG14");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static List<String> verificarReferencias(Actor model) {

		List<ReferenciaParametro> referenciasParametro;
		List<CasoUsoActor> referenciasCasoUsoActor = new ArrayList<CasoUsoActor>();

		List<String> listReferenciasVista = new ArrayList<String>();
		Set<String> setReferenciasVista = new HashSet<String>(0);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;

		String casoUso = "";

		referenciasParametro = new ReferenciaParametroDAO()
				.consultarReferenciasParametro(model);
		referenciasCasoUsoActor = new CasoUsoActorDAO().consultarReferencias(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null) {
				casoUso = postPrecondicion.getCasoUso().getClave()
						+ postPrecondicion.getCasoUso().getNumero() + " "
						+ postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					linea = "Precondiciones del caso de uso " + casoUso;
				} else {
					linea = "Postcondiciones del caso de uso "
							+ postPrecondicion.getCasoUso().getClave()
							+ postPrecondicion.getCasoUso().getNumero() + " "
							+ postPrecondicion.getCasoUso().getNombre();
				}

			} else if (paso != null) {
				casoUso = paso.getTrayectoria().getCasoUso().getClave()
						+ paso.getTrayectoria().getCasoUso().getNumero() + " "
						+ paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso "
						+ paso.getNumero()
						+ " de la trayectoria "
						+ ((paso.getTrayectoria().isAlternativa()) ? "alternativa "
								+ paso.getTrayectoria().getClave()
								: "principal") + " del caso de uso " + casoUso;
			}
			if (linea != "") {
				setReferenciasVista.add(linea);
			}
		}

		for (CasoUsoActor casoUsoActor : referenciasCasoUsoActor) {
			String linea = "";
			casoUso = casoUsoActor.getCasouso().getClave()
					+ casoUsoActor.getCasouso().getNumero() + " "
					+ casoUsoActor.getCasouso().getNombre();
			linea = "Actores del caso de uso " + casoUso;
			if (linea != "") {
				setReferenciasVista.add(linea);
			}
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}
	
	public static List<CasoUso> verificarCasosUsoReferencias(Actor model) {

		List<ReferenciaParametro> referenciasParametro;
		List<CasoUsoActor> referenciasCasoUsoActor = new ArrayList<CasoUsoActor>();

		List<CasoUso> listReferenciasVista = new ArrayList<CasoUso>();
		Set<CasoUso> setReferenciasVista = new HashSet<CasoUso>(0);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;

		referenciasParametro = new ReferenciaParametroDAO()
				.consultarReferenciasParametro(model);
		referenciasCasoUsoActor = new CasoUsoActorDAO().consultarReferencias(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null) {
				setReferenciasVista.add(postPrecondicion.getCasoUso());
			} else if (paso != null) {
				setReferenciasVista.add(paso.getTrayectoria().getCasoUso());
			}
		}

		for (CasoUsoActor casoUsoActor : referenciasCasoUsoActor) {
			setReferenciasVista.add(casoUsoActor.getCasouso());
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}

	/*public static void modificarActor(Actor model, Actualizacion actualizacion) throws Exception {
		try {
			validar(model);
			ElementoBs
					.verificarEstado(model, CU_Actores.MODIFICARACTOR7_2);
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			model.setNombre(model.getNombre().trim());

			new ActorDAO().modificarElemento(model, actualizacion);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}*/
	
	public static void modificarActor(Actor model) throws Exception {
		try {
			validar(model);
			ElementoBs
					.verificarEstado(model, CU_Actores.MODIFICARACTOR7_2);
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			model.setNombre(model.getNombre().trim());

			new ActorDAO().modificarElemento(model);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

}
