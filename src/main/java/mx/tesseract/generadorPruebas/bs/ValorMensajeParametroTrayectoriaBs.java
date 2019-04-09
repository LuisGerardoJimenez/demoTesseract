package mx.tesseract.generadorPruebas.bs;

import java.util.List;

import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.generadorPruebas.dao.ValorMensajeParametroDAO;
import mx.tesseract.generadorPruebas.dao.ValorMensajeParametroTrayectoriaDAO;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametroTrayectoria;


public class ValorMensajeParametroTrayectoriaBs {
	public static ValorMensajeParametroTrayectoria consultarValor(
			int id) {
		return new ValorMensajeParametroTrayectoriaDAO().consultarValor(id);
	}
	
	public static List<ValorMensajeParametroTrayectoria> consultarValores(ReferenciaParametro referencia) {
		List<ValorMensajeParametroTrayectoria> vmp = new ValorMensajeParametroTrayectoriaDAO().findByReferenciaParametro(referencia);
		if(vmp != null) {
			return vmp;
		}
		
		return null;
	}
		
}
