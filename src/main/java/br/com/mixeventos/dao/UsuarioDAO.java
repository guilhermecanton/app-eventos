package br.com.mixeventos.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.mixeventos.domain.Mensagem;
import br.com.mixeventos.domain.Usuario;
import br.com.mixeventos.util.HibernateUtil;


public class UsuarioDAO extends GenericDAO<Usuario> {
	
	// MÉTODO AUTENTICAR

		public Usuario autenticar(String email, String senha) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(Usuario.class);
				consulta.add(Restrictions.eq("email", email));	
				
				SimpleHash hash = new SimpleHash("md5", senha);
				consulta.add(Restrictions.eq("senha", hash.toHex()));		
				

				Usuario resultado = (Usuario) consulta.uniqueResult();
				
				return resultado;
			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {
				sessao.close();
			}
			return null;

		}
		
		// MÉTODO BUSCAR POR ID (Conta) consulta.setFetchMode("movimentacoesFavorecedor", FetchMode.JOIN).uniqueResult();

		public Usuario buscarPorEmail(String email) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(Usuario.class);
				consulta.add(Restrictions.eq("email", email));
				Usuario resultado =  (Usuario)consulta.uniqueResult();
				return resultado;
			} catch (RuntimeException e) {
				throw e;
			} finally {
				sessao.close();
			}
		}
		
		@SuppressWarnings("unchecked")
		public List<Mensagem> buscarMensagensUsuario(Long codigoUsuario){
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
		
		
}
