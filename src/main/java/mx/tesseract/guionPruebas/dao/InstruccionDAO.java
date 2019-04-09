package mx.tesseract.guionPruebas.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.tesseract.dao.GenericDAO;
import mx.tesseract.editor.model.Entrada;
import mx.tesseract.editor.model.Paso;
import mx.tesseract.guionPruebas.model.GuionPrueba;
import mx.tesseract.guionPruebas.model.Instruccion;
import mx.tesseract.guionPruebas.model.Sinonimo;

public class InstruccionDAO extends GenericDAO{
	
	public InstruccionDAO() {
		super();
	}
	
	public Instruccion consultarInstruccion(int paso) {
		Instruccion instruccion = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Instruccion where Paso_idPaso = :idPaso");
			query.setParameter("idPaso", paso);
			//Si se encuentra en la lista de sinónimos
			if(!query.list().isEmpty()){
				instruccion = (Instruccion) query.list().get(0);
			}else{
				instruccion = null;
			}
			
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return instruccion;
	}
	
	public void agregarInstruccion(Instruccion instruccion) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(instruccion);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		
	}
	
	public void delete(int id){
		try{
			session.beginTransaction();
			Query query = session
					.createQuery("delete from Instruccion where GuionPrueba_idGuionPrueba = :idGuionPrueba");
			query.setParameter("idGuionPrueba", id);
			query.executeUpdate();
			session.getTransaction().commit();
		}catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
}
