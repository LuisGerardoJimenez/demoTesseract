package mx.tesseract.admin.dao;

import java.util.List;

import mx.tesseract.admin.model.EstadoProyecto;
import mx.tesseract.dao.GenericDAO;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class EstadoProyectoDAO extends GenericDAO {
	

	public EstadoProyectoDAO() {
		super();
	}
	
	public EstadoProyecto consultarEstadoProyecto(int idEstadoProyecto) {
		EstadoProyecto estadoProyecto = null;

		try {
			session.beginTransaction();
			estadoProyecto = (EstadoProyecto) session.get(EstadoProyecto.class, idEstadoProyecto);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return estadoProyecto;

	}

	@SuppressWarnings("unchecked")
	public List<EstadoProyecto> consultarEstadosProyecto() {
		List<EstadoProyecto> estados = null;
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from EstadoProyecto");
			estados = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (estados == null) {
			return null;
		} else {
			return estados;
		}
	}

}
