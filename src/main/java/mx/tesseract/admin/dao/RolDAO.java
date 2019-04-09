package mx.tesseract.admin.dao;

import mx.tesseract.admin.model.Rol;
import mx.tesseract.dao.GenericDAO;

import org.hibernate.HibernateException;

public class RolDAO extends GenericDAO {
	

	public RolDAO() {
		super();
	}
	public Rol consultarRol(Integer id) {
		Rol rol = null;

		try {
			session.beginTransaction();
			rol = (Rol) session.get(Rol.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
		return rol;

	}
}
