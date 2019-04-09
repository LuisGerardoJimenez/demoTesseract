package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.bs.CatalogoBs;
import mx.tesseract.bs.AnalisisEnum.CU_CasosUso;
import mx.tesseract.bs.AnalisisEnum.CU_Entidades;
import mx.tesseract.bs.ReferenciaEnum.TipoCatalogo;
import mx.tesseract.editor.bs.ElementoBs.Estado;
import mx.tesseract.editor.dao.EntidadDAO;
import mx.tesseract.editor.dao.ReferenciaParametroDAO;
import mx.tesseract.editor.dao.TipoDatoDAO;
import mx.tesseract.editor.dao.UnidadTamanioDAO;
import mx.tesseract.editor.model.Atributo;
import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.editor.model.Entidad;
import mx.tesseract.editor.model.Paso;
import mx.tesseract.editor.model.PostPrecondicion;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.editor.model.TipoDato;
import mx.tesseract.editor.model.UnidadTamanio;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.PRISMAException;
import mx.tesseract.util.PRISMAValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class EntidadBs {
	private static final String CLAVE = "ENT";

	public static void registrarEntidad(Entidad model) throws Exception {
		try {
			validar(model);
			model.setClave(CLAVE);
			model.setNumero(new EntidadDAO().siguienteNumeroEntidad(model
					.getProyecto().getId()));
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			model.setNombre(model.getNombre().trim());
			new EntidadDAO().registrarEntidad(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == Constantes.MYSQL_ERROR_1062) {
				throw new PRISMAValidacionException("La la entidad "
						+ model.getNombre() + " ya existe.", "MSG7",
						new String[] { "La", "entidad", model.getNombre() },
						"model.nombre");
			}
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<Entidad> consultarEntidadesProyecto(Proyecto proyecto) {
		List<Entidad> listEntidades = new EntidadDAO()
				.consultarEntidades(proyecto.getId());
		if (listEntidades == null) {
			throw new PRISMAException("No se pueden consultar las entidades.",
					"MSG13");
		}
		return listEntidades;
	}

	public static List<TipoDato> consultarTiposDato() {
		List<TipoDato> listTiposDato = new TipoDatoDAO().consultarTiposDato();
		if (listTiposDato == null) {
			throw new PRISMAException(
					"No se pueden consultar los tipos de dato.", "MSG13");
		}
		CatalogoBs.opcionOtro(listTiposDato, TipoCatalogo.TIPODATO);
		return listTiposDato;
	}

	public static List<UnidadTamanio> consultarUnidadesTamanio() {
		List<UnidadTamanio> listUnidadTamanio = new UnidadTamanioDAO()
				.consultarUnidadesTamanio();
		if (listUnidadTamanio == null) {
			throw new PRISMAException("No se pueden consultar las unidades.",
					"MSG13");
		}
		return listUnidadTamanio;
	}

	private static void validar(Entidad model) {

		// Validaciones de nularidad
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó el nombre de la entidad.", "MSG4",
					null, "model.nombre");
		}
		if (Validador.esNuloOVacio(model.getDescripcion())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó la descripción de la entidad.",
					"MSG4", null, "model.descripcion");
		}
		
		if (Validador.esNuloOVacio(model.getAtributos())) {
			throw new PRISMAValidacionException(
					"El usuario no ingresó ningún atributo.", "MSG18",
					new String[] { "un", "atributo" }, "model.atributos");
		} else {
			for (Atributo atributo : model.getAtributos()) {
				if (Validador.esNuloOVacio(atributo.getNombre())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó el nombre del atributo.",
							"MSG4", null, "model.atributos");
				}
				if (Validador.esNuloOVacio(atributo.getDescripcion())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó la descripción.", "MSG4",
							null, "model.atributos");
				}
				if (Validador.esNulo(atributo.getTipoDato())) {
					throw new PRISMAValidacionException(
							"El usuario no ingresó el tipo de dato.", "MSG4",
							null, "model.atributos");
				}

				if (atributo.getTipoDato().getNombre().equals("Archivo")) {
					if (Validador.esNulo(atributo.getUnidadTamanio())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó la unidad.", "MSG4",
								null, "model.atributos");
					}
					if (Validador.esNuloOVacio(atributo.getFormatoArchivo())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó el formato del archivo.",
								"MSG4", null, "model.atributos");
					}
					if (Validador.esNulo(atributo.getTamanioArchivo())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó el tamaño del archivo.",
								"MSG4", null, "model.atributos");
					}
				} else if (!atributo.getTipoDato().getNombre()
						.equals("Booleano")
						&& !atributo.getTipoDato().getNombre().equals("Fecha")
						&& !atributo.getTipoDato().getNombre().equals("Otro")) {
					if (Validador.esNulo(atributo.getLongitud())) {
						throw new PRISMAValidacionException(
								"El usuario no ingresó la longitud.", "MSG4",
								null, "model.atributos");
					}
				} else if (atributo.getTipoDato().getNombre().equals("Otro")) {
					if (Validador.esNuloOVacio(atributo.getOtroTipoDato())) {
						throw new PRISMAValidacionException(
								"El usuario no especificó el tipo de dato.",
								"MSG4", null, "model.atributos");
					}
				}
			}
		}
		//Validacion de Longitud
		if (Validador.validaLongitudMaxima(model.getNombre(), Constantes.NUMERO_DOSCIENTOS)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "200", "caracteres" }, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getDescripcion(), Constantes.NUMERO_MIL)) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una descripcion muy larga.", "MSG6",
					new String[] { "999", "caracteres" }, "model.descripcion");
		}
		//Validacion de Formato
		if (Validador.contieneCaracterInvalido(model.getNombre())) {
			throw new PRISMAValidacionException(
					"El usuario ingreso un nombre con caracter inválido.",
					"MSG23", new String[] { "El", "nombre" }, "model.nombre");
		}
		
	}

	public static Entidad consultarEntidad(int idEntidad) {
		Entidad entidad = null;
		entidad = new EntidadDAO().consultarEntidad(idEntidad);
		if (entidad == null) {
			throw new PRISMAException("No se puede consultar la entidad.",
					"MSG13");
		}
		return entidad;
	}

	public static List<Entidad> consultarEntidadesProyectoConFecha(
			Proyecto proyecto) {
		List<Entidad> listEntidadesAux = new EntidadDAO()
				.consultarEntidades(proyecto.getId());
		List<Entidad> listEntidades = new ArrayList<Entidad>();
		for (Entidad entidad : listEntidadesAux) {
			if (contieneAtributoTipoFecha(entidad)) {
				listEntidades.add(entidad);
			}
		}
		return listEntidades;
	}

	private static boolean contieneAtributoTipoFecha(Entidad entidad) {
		Set<Atributo> atributos = entidad.getAtributos();
		for (Atributo atributo : atributos) {
			if (atributo.getTipoDato().getNombre().equals("Fecha")) {
				return true;
			}
		}
		return false;
	}

	public static List<Atributo> consultarAtributosTipoFecha(int idEntidad) {
		Set<Atributo> atributos = new EntidadDAO().consultarEntidad(idEntidad)
				.getAtributos();
		List<Atributo> listAtributos = new ArrayList<Atributo>();
		for (Atributo atributo : atributos) {
			if (atributo.getTipoDato().getNombre().equals("Fecha")) {
				listAtributos.add(atributo);
			}
		}
		return listAtributos;
	}
	
	public static List<String> verificarReferencias(Entidad model) {

		List<ReferenciaParametro> referenciasParametro;

		List<String> listReferenciasVista = new ArrayList<String>();
		Set<String> setReferenciasVista = new HashSet<String>(Constantes.NUMERO_CERO);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;

		String casoUso = "";

		referenciasParametro = new ReferenciaParametroDAO()
				.consultarReferenciasParametro(model);

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

		for (Atributo atributo : model.getAtributos()) {
			setReferenciasVista.addAll(AtributoBs
					.verificarReferencias(atributo));
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}
	
	public static List<CasoUso> verificarCasosUsoReferencias(Entidad model) {

		List<ReferenciaParametro> referenciasParametro;

		List<CasoUso> listReferenciasVista = new ArrayList<CasoUso>();
		Set<CasoUso> setReferenciasVista = new HashSet<CasoUso>(Constantes.NUMERO_CERO);
		PostPrecondicion postPrecondicion = null;
		Paso paso = null;

		referenciasParametro = new ReferenciaParametroDAO()
				.consultarReferenciasParametro(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null) {
				setReferenciasVista.add(postPrecondicion.getCasoUso());

			} else if (paso != null) {
				setReferenciasVista.add(paso.getTrayectoria().getCasoUso());
			}
		}

		for (Atributo atributo : model.getAtributos()) {
			setReferenciasVista.addAll(AtributoBs
					.verificarCasosUsoReferencias(atributo));
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}

//	public static void modificarEntidad(Entidad model,
//			Actualizacion actualizacion) throws Exception {
//		try {
//			validar(model);
//			ElementoBs
//					.verificarEstado(model, CU_Entidades.MODIFICARENTIDAD11_2);
//			model.setEstadoElemento(ElementoBs
//					.consultarEstadoElemento(Estado.EDICION));
//			model.setNombre(model.getNombre().trim());
//
//			new EntidadDAO().modificarEntidad(model, actualizacion);
//
//		} catch (JDBCException je) {
//			System.out.println("ERROR CODE " + je.getErrorCode());
//			je.printStackTrace();
//			throw new Exception();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			throw new Exception();
//		}
//	}
	
	public static void modificarEntidad(Entidad model) throws Exception {
		try {
			validar(model);
			ElementoBs
					.verificarEstado(model, CU_Entidades.MODIFICARENTIDAD11_2);
			model.setEstadoElemento(ElementoBs
					.consultarEstadoElemento(Estado.EDICION));
			model.setNombre(model.getNombre().trim());

			new EntidadDAO().modificarEntidad(model);

		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void eliminarEntidad(Entidad model) throws Exception {
		try {
			ElementoBs.verificarEstado(model, CU_CasosUso.ELIMINARCASOUSO5_3);
			new EntidadDAO().eliminarElemento(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == Constantes.MYSQL_ERROR_1451) {
				throw new PRISMAException("No se puede eliminar la entidad",
						"MSG14");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

	}
}
