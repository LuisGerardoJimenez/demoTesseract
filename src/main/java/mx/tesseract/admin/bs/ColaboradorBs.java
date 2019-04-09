package mx.tesseract.admin.bs;

import java.util.ArrayList; 
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.dao.ColaboradorProyectoDAO;
import mx.tesseract.admin.model.Colaborador;
import mx.tesseract.admin.model.ColaboradorProyecto;
import mx.tesseract.bs.RolBs;
import mx.tesseract.bs.RolBs.Rol_Enum;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.Correo;
import mx.tesseract.util.PRISMAException;
import mx.tesseract.util.PRISMAValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class ColaboradorBs {

	public static Colaborador consultarPersona(String idSel) {
		Colaborador col = new ColaboradorDAO().consultarColaborador(idSel);
		if(col == null) {
			throw new PRISMAException("No se puede consultar el colaborador.",
					"MSG13");
		}
		return col;
	}

	public static List<Colaborador> consultarPersonal() {
		List<Colaborador> colaboradores = new ColaboradorDAO().consultarColaboradores();
		if(colaboradores == null) {
			throw new PRISMAException("No se pueden consultar los colaboradores.",
					"MSG13");
		}
		return colaboradores;
	}

	public static void registrarColaborador(Colaborador model) throws Exception {
		try {
			validar(model, Constantes.VALIDACION_REGISTRAR);
			new ColaboradorDAO().registrarColaborador(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("La Persona con CURP"
						+ model.getCurp() + " ya existe.", "MSG7",
						new String[] { "La", "persona con CURP", model.getCurp() },
						"model.curp");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		
	}

	private static void validar(Colaborador model, String bandera) {
		//Validaciones tipo de dato
		if (bandera.equals(Constantes.VALIDACION_REGISTRAR) && Validador.esInvalidoCurp(model.getCurp())) {
			throw new PRISMAValidacionException(
					"El usuario ingreso una CURP invalida.", "MSG52", null, "model.curp");
		}
		//Validaciones Negocio
		Colaborador colaboradorBD;
		if (bandera.equals(Constantes.VALIDACION_REGISTRAR)) {
			colaboradorBD = new ColaboradorDAO().consultarColaboradorCURP(model.getCurp());
			if(colaboradorBD != null && colaboradorBD.getCurp().equals(model.getCurp())) {
				throw new PRISMAValidacionException(
						"El CURP ya existe.", "MSG7", new String[] { "El", "CURP", model.getCurp() }, "model.curp");
			}
		}
		
		colaboradorBD = new ColaboradorDAO().consultarColaboradorCorreo(model.getCorreoElectronico());
		if(colaboradorBD != null && !colaboradorBD.getCurp().equals(model.getCurp()) && colaboradorBD.getCorreoElectronico().equals(model.getCorreoElectronico())) {
			throw new PRISMAValidacionException(
					"El correo del colaborador ya existe.", "MSG7", 
					new String[] { "El", "correo electrónico", model.getCorreoElectronico() }, "model.correoElectronico");
		}
		
	}

	public static void enviarCorreo(Colaborador model,
			String contrasenaAnterior, String correoAnterior) throws AddressException, MessagingException {
		if(contrasenaAnterior == null || correoAnterior == null) {
			Correo.enviarCorreo(model, 0);
			System.out.println("Se envió un correo al usuario que se registró.");
		} else if(!contrasenaAnterior.equals(model.getContrasenia())) {
			Correo.enviarCorreo(model, 0);
			System.out.println("Se envió un correo porque cambió la contraseña.");
		} else if(!correoAnterior.equals(model.getCorreoElectronico())) {
			Correo.enviarCorreo(model, 0);
			System.out.println("Se envió un correo porque cambio el correo electrónico.");
		}
	}

	public static void modificarColaborador(Colaborador model) throws Exception {
		try {
			validar(model, Constantes.VALIDACION_EDITAR);
			new ColaboradorDAO().modificarColaborador(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new PRISMAValidacionException("La Persona con CURP"
						+ model.getCurp() + " ya existe.", "MSG7",
						new String[] { "La", "persona con CURP", model.getCurp() },
						"model.curp");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static void eliminarColaborador(Colaborador model) throws Exception {
		try {
			if(!esLiderProyecto(model)) {
				new ColaboradorDAO().eliminarColaborador(model);
			} else {
				throw new PRISMAException("No se puede eliminar la persona.", "MSG13");
			}
			
		} catch (JDBCException je) {
			if(je.getErrorCode() == 1451)
			{
				throw new PRISMAException("No se puede eliminar la persona.", "MSG14");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static boolean esLiderProyecto(Colaborador model) {
		Set<ColaboradorProyecto> colaboradoresProyecto = model.getColaborador_proyectos();
		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
		for(ColaboradorProyecto cp : colaboradoresProyecto) {
			if(cp.getRol().getId() == idLider) {
				return true;
			}
		}
		return false;
	}

	public static List<String> verificarProyectosLider(Colaborador model) {
		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
		List<String> proyectos = new ArrayList<String>();
		Set<String> setProyectos = new HashSet<String>(0);
		
		List<ColaboradorProyecto> colaboradoresProyecto = null;
		colaboradoresProyecto = new ColaboradorProyectoDAO().consultarLiderColaboradoresProyecto(model);
		
		for(ColaboradorProyecto cp : colaboradoresProyecto) {
			if(cp.getRol().getId() == idLider) {
				String linea = "";
				String proyecto = cp.getProyecto().getClave() + " " + cp.getProyecto().getNombre();
				linea = "Esta persona es líder del Proyecto " + proyecto + ".";
				setProyectos.add(linea);
			}
		}
		
		proyectos.addAll(setProyectos);
		return proyectos;
	}
}
