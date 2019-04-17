package mx.tesseract.editor.bs;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.tesseract.editor.dao.MensajeParametroDAO;
import mx.tesseract.editor.model.MensajeParametro;
import mx.tesseract.generadorPruebas.dao.ValorMensajeParametroDAO;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;
import mx.tesseract.util.TESSERACTException;

public class MensajeParametroBs {

	public static MensajeParametro consultarMensajeParametro(Integer id) {
		return new MensajeParametroDAO().findById(id);
	}
	

	
	public static List<MensajeParametro> consultarMensajeParametro_(Integer id){
		List<MensajeParametro> listMensajeParametro=null;
		try{
			listMensajeParametro = new MensajeParametroDAO().consultarMensajeParametro_(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		if (listMensajeParametro == null) {
			throw new TESSERACTException(
					"No se pueden consultar los pasos por el id.", "MSG16",
					new String[] { "La", "trayectoria" });
		}
		return listMensajeParametro;
	}

}
