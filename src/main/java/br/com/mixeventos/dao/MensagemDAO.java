package br.com.mixeventos.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.mixeventos.domain.Compra;
import br.com.mixeventos.domain.Mensagem;
import br.com.mixeventos.util.HibernateUtil;

public class MensagemDAO extends GenericDAO<Mensagem> {
	
	@SuppressWarnings("unchecked")
	public List<Mensagem> listarMsgs(Long codigoUsuario){
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Mensagem.class);
			consulta.add(Restrictions.eq("destinatario.codigo", codigoUsuario));
			List<Mensagem> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Mensagem> listarMsgsEnviadas(Long codigoUsuario){
		Session sessao = (Session) HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Mensagem.class);
			consulta.add(Restrictions.eq("remetente.codigo", codigoUsuario));
			List<Mensagem> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	
}
