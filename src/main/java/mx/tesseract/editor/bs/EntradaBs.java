package mx.tesseract.editor.bs;

import mx.tesseract.editor.dao.EntradaDAO;
import mx.tesseract.editor.model.Entrada;
import mx.tesseract.generadorPruebas.model.ValorEntrada;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

public class EntradaBs {
	public static Entrada consultarEntrada(Integer id) {
		Entrada entrada = new EntradaDAO().findById(id);
		return entrada;
	}

	public static void modificarEntrada(Entrada entrada, boolean validarObligatorios) throws Exception{
		validar(entrada, validarObligatorios);
		new EntradaDAO().modificarEntrada(entrada);
		
	}

	private static void validar(Entrada entrada, boolean validarObligatorios) {
		for(ValorEntrada valorEntrada : entrada.getValores()){
			if(valorEntrada.getValido()) {
				String valor = valorEntrada.getValor();
				if (validarObligatorios && Validador.esNuloOVacio(valor)) {
					throw new TESSERACTValidacionException(
							"El usuario no ingresó el valor de una entrada", "MSG38", null,
							"campos");
				}
				if (Validador.validaLongitudMaxima(valor, 2000)) {
					throw new TESSERACTValidacionException(
							"El usuario ingreso un valor muy largo.", "MSG39",
							new String[] { "2000", "caracteres", "el valor de " + entrada.getAtributo().getNombre() }, "campos");
				}
			}
		}
		String etiqueta = entrada.getNombreHTML();
		if (validarObligatorios && Validador.esNuloOVacio(etiqueta)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la etiqueta de una entrada.", "MSG38", null,
					"campos");
		}
		if (Validador.validaLongitudMaxima(etiqueta, 200)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una etiqueta muy larga.", "MSG39",
					new String[] { "200", "caracteres", "la etiqueta de " + entrada.getAtributo().getNombre() }, "campos");
		}
	}
}
