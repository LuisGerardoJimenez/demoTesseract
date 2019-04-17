package mx.tesseract.generadorPruebas.dao;

import java.util.List;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class ValorMensajeParametroDAO extends GenericDAO {

	public void registrarValorMensajeParametro(ValorMensajeParametro valor) {
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
	public void modificarValorMensajeParametro(ValorMensajeParametro valor) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(valor);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}
	public List<ValorMensajeParametro> findByReferenciaParametro(ReferenciaParametro referencia) {
		List<ValorMensajeParametro> results = null;
		
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from ValorMensajeParametro where referenciaParametro = :referencia");
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

	public ValorMensajeParametro consultarValor(Integer id) {
		ValorMensajeParametro valor = null;
		try {
			session.beginTransaction();
			valor = (ValorMensajeParametro) session.get(ValorMensajeParametro.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return valor;
	}
	public List<ValorMensajeParametro> consultarValores_(Integer id) throws HibernateException {		
		List<ValorMensajeParametro> listValorMensajeParametro = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery("from ValorMensajeParametro where Mensaje_Parametroid= :id");
			query.setParameter("id", id);
			listValorMensajeParametro = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return listValorMensajeParametro;

	}
}
