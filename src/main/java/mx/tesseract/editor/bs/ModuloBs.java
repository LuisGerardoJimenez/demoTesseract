package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.editor.dao.CasoUsoActorDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.dao.ReferenciaParametroDAO;
import mx.tesseract.editor.model.Actor;
import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.editor.model.CasoUsoActor;
import mx.tesseract.editor.model.Modulo;
import mx.tesseract.editor.model.Pantalla;
import mx.tesseract.editor.model.Paso;
import mx.tesseract.editor.model.PostPrecondicion;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class ModuloBs {
	public static void registrarModulo(Modulo model)
			throws Exception {
		try {
			validar(model);
			new ModuloDAO().registrarModulo(model);
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static List<Modulo> consultarModulosProyecto(
			Proyecto proyecto) {
		List<Modulo> listModulos = new ModuloDAO()
				.consultarModulos(proyecto.getId());
		if (listModulos == null) {
			throw new TESSERACTException(
					"No se pueden consultar los módulos.",
					"MSG13");
		}
		return listModulos;
	}

	private static void validar(Modulo model) {
		//Validaciones Negocio
		//Se asegura la unicidad del nombre y clave
		List<Modulo> modulosBD = consultarModulosProyecto(model.getProyecto());
		for(Modulo modulo : modulosBD) {
			if(model.getId() != modulo.getId()) {
				if(model.getClave().equals(modulo.getClave())) {
					throw new TESSERACTValidacionException(
							"La clave del módulo ya existe.",
							"MSG7",
							new String[] { "El", "módulo", model.getClave() },
							"model.clave");
				}
				if(model.getNombre().equals(modulo.getNombre())) {
					throw new TESSERACTValidacionException(
							"El nombre del módulo ya existe.",
							"MSG7",
							new String[] { "El", "módulo", model.getNombre() },
							"model.nombre");
				}
			}
		}
	}

	public static Modulo consultarModulo(int idActor) {
		Modulo modulo = null;
		modulo = new ModuloDAO()
				.consultarModulo(idActor);
		if (modulo == null) {
			throw new TESSERACTException(
					"No se pueden consultar los módulos.",
					"MSG13");
		}
		return modulo;
	}

	public static void eliminarTermino(Modulo model) throws Exception {
		try {
			new ModuloDAO().eliminarModulo(model);
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

	}


	public static void modificarModulo(Modulo model) throws Exception {
		try {
			validar(model);
			new ModuloDAO().modificarModulo(model);
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static void eliminarModulo(Modulo model) {
		new ModuloDAO().eliminarModulo(model);
		
	}

	public static List<String> verificarReferencias(Modulo model) {

		System.out.println("entramos a verificar Referencias");
		List<String> listReferenciasVista = new ArrayList<String>();
		Set<String> setReferenciasVista = new HashSet<String>(0);
		
		List<CasoUso> casosUso = CuBs.consultarCasosUsoModulo(model);
		List<Pantalla> pantallas = PantallaBs.consultarPantallasModulo(model);
		System.out.println("Se buscan los CU y pantallas");
		for(CasoUso casoUso : casosUso) {
			setReferenciasVista.addAll(CuBs.verificarReferencias(casoUso, model));
		}
		
		for(Pantalla pantalla : pantallas) {
			setReferenciasVista.addAll(PantallaBs.verificarReferencias(pantalla, model));
		}
		

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}

}
