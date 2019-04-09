package mx.tesseract.util;

public class ErrorManager {
	public static void agregaMensajeError(ActionSupportTESSERACT ap, Exception ex) {
		if(ex instanceof TESSERACTException) {
		TESSERACTException pe = (TESSERACTException) ex;
			if(pe instanceof TESSERACTValidacionException) {
				TESSERACTValidacionException pve = (TESSERACTValidacionException) pe;
				if(pve.getCampo() != null) {
					if(pe.getParametros() != null){
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje(), pe.getParametros()));
					} else {
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje()));
					}
				} else {
					if(pe.getParametros() != null){
						ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
					} else {
						ap.addActionError(ap.getText(pe.getIdMensaje()));
					}
				}
			} else { 
				if(pe.getParametros() != null){
					ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
				} else {
					ap.addActionError(ap.getText(pe.getIdMensaje()));
				}
			}
		} else {
			ap.addActionError(ap.getText("MSG13"));
		}
		
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}

