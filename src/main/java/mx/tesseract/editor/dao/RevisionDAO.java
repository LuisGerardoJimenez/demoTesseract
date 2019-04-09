package mx.tesseract.editor.dao;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.Revision;

import org.hibernate.HibernateException;

public class RevisionDAO extends GenericDAO {
	public RevisionDAO() {
		super();
	}

	public void update(Revision revision) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(revision);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public void delete(Revision revision) {
		try {
			session.beginTransaction();
			session.delete(revision);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
}
