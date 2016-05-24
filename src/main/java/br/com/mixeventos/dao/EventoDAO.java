package br.com.mixeventos.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.mixeventos.domain.Evento;
import br.com.mixeventos.util.HibernateUtil;

public class EventoDAO extends GenericDAO<Evento> {

	// MÉTODO LISTAR

	@SuppressWarnings("unchecked")
	public List<Evento> listarEventosAtivos() throws ParseException {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery(
					"SELECT e FROM Evento e WHERE e.ativo = :ativo AND e.dataEvento >= CURRENT_DATE AND e.quantidadeIngressos > 0");
			query.setParameter("ativo", true);
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();

			return results;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> listarEventosAtivosBuscados(String termoBuscado) throws ParseException {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery(
					"SELECT e FROM Evento e WHERE e.ativo = :ativo AND e.dataEvento >= CURRENT_DATE AND e.quantidadeIngressos > 0 AND e.descricao like :termoBuscado");
			query.setParameter("ativo", true);
			query.setParameter("termoBuscado", "%" +termoBuscado + "%");
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();

			return results;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")	
	public List<Evento> buscarEventosEspecificos(Long categoriaCodigo, Long tipoCategoriaCodigo) {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery(
					"SELECT e FROM Evento e WHERE e.ativo = :ativo AND e.dataEvento >= CURRENT_DATE AND e.quantidadeIngressos > 0 AND e.tipoCategoria.categoria.codigo = :categoriaCodigo  AND e.tipoCategoria.codigo = :tipoCategoriaCod");
			query.setParameter("ativo", true);
			query.setParameter("categoriaCodigo", categoriaCodigo);
			query.setParameter("tipoCategoriaCod", tipoCategoriaCodigo);
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();

			return results;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")		
	public List<Evento> buscarEventosEspecificos(Long categoriaCodigo) {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery(
					"SELECT e FROM Evento e WHERE e.ativo = :ativo AND e.dataEvento >= CURRENT_DATE AND e.quantidadeIngressos > 0 AND e.tipoCategoria.categoria.codigo = :categoriaCodigo");
			query.setParameter("ativo", true);
			query.setParameter("categoriaCodigo", categoriaCodigo);
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();

			return results;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
	
	

	

}
