package mx.tesseract.generadorPruebas.bs;

import java.util.List;

import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.generadorPruebas.dao.ErroresPruebaDAO;
import mx.tesseract.generadorPruebas.dao.PruebaDAO;
import mx.tesseract.generadorPruebas.model.ErroresPrueba;
import mx.tesseract.generadorPruebas.model.Prueba;

public class PruebaBs {
	public static List<Prueba> consultarPruebasxCasoUso(CasoUso casoUso){
		List<Prueba> listPruebas=null;
		listPruebas = new PruebaDAO().consultarPruebasxCasoUso(casoUso);
		return listPruebas;
	}
	public static List<Prueba> consultarPruebas(){
		List<Prueba> listPruebas=null;
		listPruebas = new PruebaDAO().consultarPruebas();
		return listPruebas;
	}
}
