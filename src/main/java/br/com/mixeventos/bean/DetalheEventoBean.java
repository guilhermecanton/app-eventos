package br.com.mixeventos.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.EditableValueHolderAttachedObjectHandler;

import br.com.mixeventos.dao.EventoDAO;
import br.com.mixeventos.domain.Evento;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DetalheEventoBean implements Serializable{
	
	private Evento eventoCapturado;
	private Long codigoEvento;
	private EventoDAO edao;
	
	@PostConstruct
	public void init(){
		eventoCapturado = new Evento();
		edao = new EventoDAO();
	}
	
	public void carregarEvento(){
		eventoCapturado = edao.buscarPorId(codigoEvento);
	}
	
	public Evento getEventoCapturado() {
		return eventoCapturado;
	}
	public void setEventoCapturado(Evento eventoCapturado) {
		this.eventoCapturado = eventoCapturado;
	}
	
	public Long getCodigoEvento() {
		return codigoEvento;
	}
	
	public void setCodigoEvento(Long codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

}
