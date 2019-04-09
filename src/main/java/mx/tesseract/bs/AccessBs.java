package mx.tesseract.bs;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.model.Colaborador;
import mx.tesseract.admin.model.ColaboradorProyecto;
import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.Correo;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

public class AccessBs {

	public static Colaborador verificarLogin(String userName, String password) {
		Colaborador colaborador = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG4", null,
					"userName");
		}
		if (Validador.esNuloOVacio(password)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la contraseña.", "MSG4", null,
					"password");
		}
		if (Validador.validaLongitudMaxima(userName, Constantes.NUMERO_TREINTA)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG6", 
					new String[] { Constantes.NUMERO_TREINTA.toString(), "caracteres"},
					"userName");
		}
		if (Validador.validaLongitudMaxima(password, Constantes.NUMERO_VEINTE)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la contraseña.", "MSG6", 
					new String[] { Constantes.NUMERO_VEINTE.toString(), "caracteres"},
					"password");
		}
		try {
			colaborador = new ColaboradorDAO().consultarColaboradorCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (colaborador == null || !colaborador.getContrasenia().equals(password)) {
			throw new TESSERACTValidacionException("Colaborador no encontrado o contraseña incorrecta", "MSG31");
		}
		return colaborador;
	}

	public static boolean isLogged(Map<String, Object> userSession) {
		boolean logged = false;
		if (userSession != null) {
			if (userSession.get("login") != null) {
				logged = (Boolean) userSession.get("login");
				System.out.println(logged);
				return logged;
			}
		} 
		return false;
	}

	public static void recuperarContrasenia(String userName) throws AddressException, MessagingException {
		Colaborador colaborador = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG4", null,
					"userName");
		}
		if (!Validador.esCorreo(userName)) {
			throw new TESSERACTValidacionException("Colaborador no encontrado", "MSG33");

		}
		try {
			colaborador = new ColaboradorDAO().consultarColaboradorCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (colaborador == null) {
			throw new TESSERACTValidacionException("Colaborador no encontrado", "MSG33");
		}
		Correo.enviarCorreo(colaborador, 1);
		
	}
	
	public static boolean verificarPermisos(Proyecto proyecto, Colaborador colaborador) {
		boolean acceso = false;
		for (ColaboradorProyecto colaboradorProyecto : colaborador.getColaborador_proyectos()) {
			if (colaboradorProyecto.getProyecto().getId() == proyecto.getId()) {
				acceso = true;
				break;
			}
		}
		return acceso;
	}
}
