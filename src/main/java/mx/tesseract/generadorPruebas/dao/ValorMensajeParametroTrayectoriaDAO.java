package mx.tesseract.generadorPruebas.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametroTrayectoria;

public class ValorMensajeParametroTrayectoriaDAO extends GenericDAO  {
	public ValorMensajeParametroTrayectoria consultarValor(Integer id) {
		ValorMensajeParametroTrayectoria valor = null;
		try {
			session.beginTransaction();
			valor = (ValorMensajeParametroTrayectoria) session.get(ValorMensajeParametroTrayectoria.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return valor;
	}
	
	public List<ValorMensajeParametroTrayectoria> findByReferenciaParametro(ReferenciaParametro referencia) {
		List<ValorMensajeParametroTrayectoria> results = null;
		
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from ValorMensajeParametroTrayectoria where ReferenciaParametroTrayectoriaid = :referencia");
			query.setParameter("referencia", referencia);

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(results == null) {
			return null;
		} else if (results.isEmpty()) {
			return null;
		} else {
			return results;
		}
	}
}
