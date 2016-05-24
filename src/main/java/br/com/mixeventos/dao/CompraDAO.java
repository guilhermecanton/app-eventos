package br.com.mixeventos.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.mixeventos.domain.Compra;
import br.com.mixeventos.util.HibernateUtil;

public class CompraDAO extends GenericDAO<Compra> {
	
	@SuppressWarnings("unchecked")
	public List<Compra> listarUltimasCompras(Long codigoUsuario){
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Compra.class);
			consulta.add(Restrictions.eq("usuario.codigo", codigoUsuario));
			List<Compra> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
