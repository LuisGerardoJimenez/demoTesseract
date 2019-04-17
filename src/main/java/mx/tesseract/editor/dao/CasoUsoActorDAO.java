package mx.tesseract.editor.dao;

import java.util.List;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.Actor;
import mx.tesseract.editor.model.CasoUsoActor;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class CasoUsoActorDAO extends GenericDAO{
	
	public CasoUsoActorDAO() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<CasoUsoActor> consultarReferencias(Actor actor) {
		List<CasoUsoActor> results = null;
		Query query = null;
		String queryCadena = null;

		queryCadena = "FROM CasoUsoActor WHERE actor.id = :idActor";

		try {
			session.beginTransaction();
			query = session.createQuery(queryCadena);
			query.setParameter("idActor", actor.getId());
			results = query.list();
			session.getTransaction().commit();

		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}

		return results;
	}
}
