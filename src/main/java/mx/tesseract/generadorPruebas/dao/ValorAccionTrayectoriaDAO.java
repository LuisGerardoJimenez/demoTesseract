package mx.tesseract.generadorPruebas.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.Accion;
import mx.tesseract.editor.model.Trayectoria;
import mx.tesseract.generadorPruebas.model.ValorAccionTrayectoria;
import mx.tesseract.generadorPruebas.model.ValorEntradaTrayectoria;


public class ValorAccionTrayectoriaDAO extends GenericDAO {
	public void registrarValorAccionTrayectoria(ValorAccionTrayectoria valor) {
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
	
	public ValorAccionTrayectoria consultarValor(Accion accion, Trayectoria trayectoria) {
		List<ValorAccionTrayectoria> results = null;
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from ValorAccionTrayectoria where TrayectoriaAccionid = :trayectoria AND AccionTrayectoriaid = :accion");
			query.setParameter("trayectoria", trayectoria);
			query.setParameter("accion", accion);
			//System.out.println("La queriy es " + query.list().);
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
			return results.get(0);
		}
	}
}
