package br.com.mixeventos.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.mixeventos.bean.AutenticacaoBean;
import br.com.mixeventos.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	
	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();
	
		boolean ehPaginaDeAutenticacao = paginaAtual.contains("autenticacao.xhtml");
	
		if(!ehPaginaDeAutenticacao){
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

			if(autenticacaoBean == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
			
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			if(usuario == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
		}		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	// CHECAGEM DE PAGINA EVENTOS
	public boolean checarPagina(){
		String paginaAtiva = Faces.getViewId();	
		
		if(paginaAtiva.equals("/pages/eventos.xhtml")){
			return true;
		}else if(paginaAtiva.equals("/pages/lista-eventos.xhtml")){
			return false;
		}
		return false;		
	}
	
	// CHECAGEM DE PAGINA EVENTOS
	public boolean checarPaginaBuscaEventos(){
		String paginaAtiva = Faces.getViewId();	
		
		if(paginaAtiva.equals("/pages/eventos-encontrados.xhtml")){
			return true;
		}else if(paginaAtiva.equals("/pages/eventos-encontrados-categoria-e-sub.xhtml")){
			return false;
		}
		return false;		
	}

}
