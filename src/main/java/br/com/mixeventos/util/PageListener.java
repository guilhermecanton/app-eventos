package br.com.mixeventos.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

public class PageListener implements PhaseListener {

	// CHECAGEM DE PAGINA EVENTOS
	public boolean checarPagina() {
		String paginaAtiva = Faces.getViewId();

		if (paginaAtiva.equals("/pages/eventos.xhtml")) {
			return true;
		} else if (paginaAtiva.equals("/pages/lista-eventos.xhtml")) {
			return false;
		}
		return false;
	}

	// CHECAGEM DE PAGINA EVENTOS
	public boolean checarPaginaBuscaEventos() {
		String paginaAtiva = Faces.getViewId();

		if (paginaAtiva.equals("/pages/eventos-encontrados.xhtml")) {
			return true;
		} else if (paginaAtiva.equals("/pages/eventos-encontrados-categoria-e-sub.xhtml")) {
			return false;
		}
		return false;
	}

	// CHECAGEM DE PAGINA EVENTOS
	public boolean checarPaginaMensagem() {
		String paginaAtiva = Faces.getViewId();

		if (paginaAtiva.equals("/pages/mensagens.xhtml")) {
			return true;
		} else if (paginaAtiva.equals("/pages/mensagens-enviadas.xhtml")) {
			return false;
		}
		return false;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return null;
	}

}
