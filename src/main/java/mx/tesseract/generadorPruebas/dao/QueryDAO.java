package mx.tesseract.generadorPruebas.dao;

import java.util.List;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.ReferenciaParametro;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class QueryDAO extends GenericDAO {

	public void registrarQuery(mx.tesseract.generadorPruebas.model.Query valor) {
		try {
			session.beginTransaction();
			session.save(valor);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}

	public List<mx.tesseract.generadorPruebas.model.Query> findByReferenciaParametro(ReferenciaParametro referencia) {
		List<mx.tesseract.generadorPruebas.model.Query> results = null;
		
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Query where referenciaParametro = :referencia");
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

	public mx.tesseract.generadorPruebas.model.Query findById(Integer id) {
		mx.tesseract.generadorPruebas.model.Query valor = null;
		try {
			session.beginTransaction();
			valor = (mx.tesseract.generadorPruebas.model.Query) session.get(mx.tesseract.generadorPruebas.model.Query.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return valor;
	}
}
