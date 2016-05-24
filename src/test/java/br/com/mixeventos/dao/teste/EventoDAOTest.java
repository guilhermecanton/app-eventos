package br.com.mixeventos.dao.teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Ignore;
import org.junit.Test;

import br.com.mixeventos.dao.EventoDAO;
import br.com.mixeventos.domain.Evento;
import br.com.mixeventos.util.HibernateUtil;

public class EventoDAOTest {

	@Test
	@Ignore
	public void listarEventosAtivos() {
		try {
			EventoDAO edao = new EventoDAO();

			List<Evento> eventos = edao.listarEventosAtivos();
			for (Evento evento : eventos) {
				System.out.println("Evento: " + evento.getDescricao());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void buscarEventosEspecificos() {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Long categoriaCodigo = 3L;
			Long tipoCategoriaCodigo = 6L;
			Criteria consulta = sessao.createCriteria(Evento.class);
			consulta.createAlias("tipoCategoria", "tp");
			consulta.add(Restrictions.eq("tp.categoria.codigo", categoriaCodigo));
			consulta.add(Restrictions.eq("tp.codigo", tipoCategoriaCodigo));
			List<Evento> resultado = consulta.list();
			for (Evento evento : resultado) {
				System.out.println(evento.getDescricao());
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unused")
	@Test
	@Ignore
	public void validarEvento() {

		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		EventoDAO edao = new EventoDAO();

		Evento evento = edao.buscarPorId(2L);
		Date dataDoEvento = evento.getDataEvento();

		if (new Date().after(dataDoEvento)) {
			System.out.println("já passou");
		} else {
			System.out.println("ainda vai rolar");
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void listarEventosAtivosTotal() {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			EventoDAO edao = new EventoDAO();

			Query query = sessao
					.createQuery("SELECT e FROM Evento e WHERE e.ativo = :ativo AND e.dataEvento >= CURRENT_DATE");
			query.setParameter("ativo", true);
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();

			for (Evento evento : results) {
				System.out.println(evento.getDescricao());
			}

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void buscarEventosEspecificosBuscaSimples() {
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		String termoBuscado = "ponto de equilibrio";
		try {
			
			Query query = sessao.createQuery("SELECT e FROM Evento e WHERE e.descricao like :termoBuscado");
			query.setParameter("termoBuscado", "%" +termoBuscado + "%");
			List<Evento> results = new ArrayList<Evento>();
			results = query.list();
			for (Evento evento : results) {
				System.out.println(evento.getDescricao());
			}

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}
