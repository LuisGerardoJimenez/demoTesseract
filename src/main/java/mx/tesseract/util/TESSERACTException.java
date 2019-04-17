package mx.tesseract.util;

public class TESSERACTException extends RuntimeException {

	/**
	 * 
	 */
	private String idMensaje;
	private String[] parametros;
	private static final long serialVersionUID = 1L;

	public TESSERACTException(String message, String idMensaje) {
		super(message);
		this.idMensaje = idMensaje;
	}

	public TESSERACTException(String message, String idMensaje, String[] parametros) {
		super(message);
		this.idMensaje = idMensaje;
		this.parametros = parametros;
	}

	public TESSERACTException(String message) {
		super(message);
	}

	public TESSERACTException(Throwable cause) {
		super(cause);
	}

	public TESSERACTException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String[] getParametros() {
		return parametros;
	}

	public void setParametros(String[] parametros) {
		this.parametros = parametros;
	}

}
