package mx.tesseract.generadorPruebas.dao;

import java.util.List;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.generadorPruebas.model.ConfiguracionBaseDatos;
import mx.tesseract.generadorPruebas.model.ConfiguracionHttp;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class ConfiguracionDAO extends GenericDAO {
	public void modificarConfiguracionGeneral(Object obj) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(obj);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}

	public ConfiguracionBaseDatos consultarConfiguracionBaseDatosByCasoUso(
			CasoUso casoUso) {
		List<ConfiguracionBaseDatos> cbds = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery("FROM ConfiguracionBaseDatos WHERE CasoUsoElementoid = :cuid");
			query.setParameter("cuid", casoUso.getId());
			cbds = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (cbds == null || cbds.isEmpty()) {
			return null;
		} else {
			return cbds.get(0);
		} 
	}

	public ConfiguracionHttp consultarConfiguracionHttpByCasoUso(CasoUso casoUso) {
		List<ConfiguracionHttp> chttp = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery("FROM ConfiguracionHttp WHERE CasoUsoElementoid = :cuid");
			query.setParameter("cuid", casoUso.getId());
			chttp = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		if (chttp == null || chttp.isEmpty()) {
			return null;
		} else {
			return chttp.get(0);
		} 
	}

}
