package mx.tesseract.generadorPruebas.dao;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.generadorPruebas.model.Escenario;

import org.hibernate.HibernateException;

public class EscenarioDAO extends GenericDAO {

	public void registrarEscenario(Escenario escenario) {
		try {
			session.beginTransaction();
			session.save(escenario);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}
}
