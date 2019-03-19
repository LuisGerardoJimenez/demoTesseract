package mx.prisma.util;

import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validador {

	public static boolean esNuloOVacio(String cadena) {
		return cadena == null || cadena.equals("");
	}

	public static boolean esNulo(Object objeto) {
		return objeto == null;
	}

	public static boolean validaLongitudMaxima(String cadena, int longitud) {
		if (cadena == null) {
			return false;
		}
		return cadena.length() > longitud;
	}

	public static boolean esDouble(Object object) {
		return object instanceof Double;
	}

	public static boolean esNuloOVacio(Set set) {
		return set == null || set.size() == 0;
	}

	public static boolean contieneCaracterInvalido(String cadena) {
		return cadena.contains("_") || cadena.contains(":") || cadena.contains("·") || cadena.contains(".")
				|| cadena.contains(",");
	}

	public static boolean esCorreo(String userName) {
		boolean correo = true;
		try {
			InternetAddress emailAddr = new InternetAddress(userName);
			emailAddr.validate();
		} catch (AddressException ex) {
			correo = false;
		}
		return correo;

	}

	public static boolean esInvalidoOrdenFechas(Date fechaInicio, Date fechaTermino) {
		return fechaInicio.after(fechaTermino) ? true : false;
	}

	public static boolean esInvalidaREGEX(String cadena, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher mat = pattern.matcher(cadena);
		if(mat.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean esInvalidoCurp(String cadena) {
		Pattern pattern = Pattern.compile(Constantes.REGEX_CURP);
		Matcher mat = pattern.matcher(cadena);
		if (mat.matches()) {
			System.out.println("Entro validador: ");
			String diccionario  = Constantes.DICCIONARIO;
			Double lngSuma = 0.0;
			Double lngDigito = 0.0;
			for(int i=Constantes.NUMERO_CERO; i<Constantes.NUMERO_DIECISIETE; i++) {
				lngSuma = lngSuma + diccionario.indexOf(cadena.charAt(i)) * (Constantes.NUMERO_DIECIOCHO - i);
				lngDigito = Constantes.NUMERO_DIEZ - lngSuma % Constantes.NUMERO_DIEZ;
			}
			System.out.println("Valor resultante: "+lngDigito);
			return true;
		}
		return true;
	}

}
