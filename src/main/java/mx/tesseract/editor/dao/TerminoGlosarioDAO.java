package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import mx.tesseract.admin.model.Proyecto;
import mx.tesseract.bs.ReferenciaEnum;
import mx.tesseract.bs.ReferenciaEnum.TipoReferencia;
import mx.tesseract.editor.model.Elemento;
import mx.tesseract.editor.model.TerminoGlosario;

public class TerminoGlosarioDAO extends ElementoDAO{
	
	public TerminoGlosarioDAO () {
		super();
	}
	
	public TerminoGlosario consultarTerminoGlosario(String nombre, Proyecto proyecto) {
		return (TerminoGlosario) super.consultarElemento(nombre, proyecto, ReferenciaEnum.getTabla(TipoReferencia.TERMINOGLS));
	}
	public TerminoGlosario consultarTerminoGlosario(int id) {
		return (TerminoGlosario) super.consultarElemento(id);
	}
    public void registrarTerminoGlosario(TerminoGlosario terminoGlosario) throws Exception {
    	super.registrarElemento(terminoGlosario);
    }
    
    public List<TerminoGlosario> consultarTerminosGlosario(int idProyecto) {
		List<TerminoGlosario> terminosGlosario = new ArrayList<TerminoGlosario>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.TERMINOGLS,  idProyecto);
		if (elementos != null)
		for (Elemento elemento : elementos) {
			terminosGlosario.add((TerminoGlosario) elemento);
		}
		return terminosGlosario;
	}
	
	public String siguienteNumeroTerminoGlosario(int idProyecto) {
		return super.siguienteNumero(TipoReferencia.TERMINOGLS, idProyecto);
	}
}
