package mx.tesseract.editor.dao;


import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.Actualizacion;

import org.hibernate.HibernateException;

public class ActualizacionDAO extends GenericDAO {

	public ActualizacionDAO() {
		super();
	}

	public void registrarActualizacion(Actualizacion actualizacion) throws Exception {

		try {
			session.beginTransaction();
			session.save(actualizacion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

	}
}
