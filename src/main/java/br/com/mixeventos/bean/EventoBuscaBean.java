package br.com.mixeventos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.mixeventos.dao.EventoDAO;
import br.com.mixeventos.domain.Evento;
import br.com.mixeventos.util.AutenticacaoListener;
import br.com.mixeventos.util.PageListener;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EventoBuscaBean implements Serializable {

	private List<Evento> eventosCapturados;
	private Evento evento;
	private List<Evento> eventosCapturadosBuscaSimples;
	private String termoBuscado;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		evento = new Evento();
		PageListener pl = new PageListener();
		eventosCapturadosBuscaSimples = (List<Evento>) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get("eventosBuscaSimples");	
		Boolean ehEventosComCategoria = pl.checarPaginaBuscaEventos();	
		if (ehEventosComCategoria == false) {
			eventosCapturados = (List<Evento>) FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.get("eventosPorCategoriaSub");			
		} else {
			eventosCapturados = (List<Evento>) FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.get("eventosPorCategoria");
		}
	}

	// MÉTODO CAPTURAR EVENTO
	public void capturarEvento(ActionEvent event) {
		try {
			evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eventoNome", evento);
			FacesContext.getCurrentInstance().getExternalContext().redirect("detail-evento.xhtml?codigo=" + evento.getCodigo());

		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar capturar o evento!");
			e.printStackTrace();
		}
	}
	
	// MÉTODO teste
	public void procurarEventoBuscaSimples() throws ParseException {
		try {
			eventosCapturadosBuscaSimples = new ArrayList<Evento>();
			EventoDAO edao = new EventoDAO();
			eventosCapturadosBuscaSimples = edao.listarEventosAtivosBuscados(termoBuscado);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eventosBuscaSimples", eventosCapturadosBuscaSimples);
			Faces.redirect("./pages/eventos-encontrados-busca-simples.xhtml");

		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// getter e setter
			
	
	public List<Evento> getEventosCapturados() {
		return eventosCapturados;
	}

	public void setEventosCapturados(List<Evento> eventosCapturados) {
		this.eventosCapturados = eventosCapturados;
	}

	public Evento getEvento() {
		if (evento == null){
			evento = new Evento();
		}
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public String getTermoBuscado() {
		return termoBuscado;
	}
	public void setTermoBuscado(String termoBuscado) {
		this.termoBuscado = termoBuscado;
	}
	
	public List<Evento> getEventosCapturadosBuscaSimples() {
		return eventosCapturadosBuscaSimples;
	}
	
	public void setEventosCapturadosBuscaSimples(List<Evento> eventosCapturadosBuscaSimples) {
		this.eventosCapturadosBuscaSimples = eventosCapturadosBuscaSimples;
	}
}
