package mx.tesseract.editor.dao;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.EstadoElemento;

import org.hibernate.HibernateException;

public class EstadoElementoDAO extends GenericDAO {
	
	public EstadoElementoDAO() {
		super();
	}

	public EstadoElemento consultarEstadoElemento(int identificador) {
		EstadoElemento estadoElemento = null;

		try {
			session.beginTransaction();
			estadoElemento = (EstadoElemento) session.get(EstadoElemento.class,
					identificador);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return estadoElemento;

	}
}
