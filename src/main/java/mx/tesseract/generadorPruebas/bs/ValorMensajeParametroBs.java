package mx.tesseract.generadorPruebas.bs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.editor.dao.PasoDAO;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.editor.model.Trayectoria;
import mx.tesseract.generadorPruebas.dao.ValorMensajeParametroDAO;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;
import mx.tesseract.util.TESSERACTException;

public class ValorMensajeParametroBs {

	public static List<ValorMensajeParametro> consultarValores(ReferenciaParametro referencia) {
		List<ValorMensajeParametro> vmp = new ValorMensajeParametroDAO().findByReferenciaParametro(referencia);
		if(vmp != null) {
			return vmp;
		}
		
		return null;
	}

	public static ValorMensajeParametro consultarValor(int id) {
		return new ValorMensajeParametroDAO().consultarValor(id);
	}
	
	public static List<ValorMensajeParametro> consultarValores_(Integer id){
		List<ValorMensajeParametro> listValorMensajeParametro=null;
		try{
			listValorMensajeParametro = new ValorMensajeParametroDAO().consultarValores_(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		if (listValorMensajeParametro == null) {
			throw new TESSERACTException(
					"No se pueden consultar los pasos por el id.", "MSG16",
					new String[] { "La", "trayectoria" });
		}
		return listValorMensajeParametro;
	}

}
