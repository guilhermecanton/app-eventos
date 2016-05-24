package br.com.mixeventos.dao;

import java.util.List;

import javax.persistence.criteria.Order;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.mixeventos.domain.TipoCategoria;
import br.com.mixeventos.util.HibernateUtil;

public class TipoCategoriaDAO extends GenericDAO<TipoCategoria> {
		
	@SuppressWarnings("unchecked")
	public List<TipoCategoria> buscarPorCategoria(Long categoriaCodigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(TipoCategoria.class);
			consulta.add(Restrictions.eq("categoria.codigo", categoriaCodigo));			
			List<TipoCategoria> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
		
		
	}
}
