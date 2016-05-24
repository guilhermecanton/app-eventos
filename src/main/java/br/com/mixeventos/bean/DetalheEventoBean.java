package br.com.mixeventos.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mixeventos.domain.Evento;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DetalheEventoBean implements Serializable{
	
	private Evento eventoCapturado;
	
	@PostConstruct
	public void init(){
		eventoCapturado =  (Evento) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("eventoNome");
	}
	
	public Evento getEventoCapturado() {
		return eventoCapturado;
	}
	public void setEventoCapturado(Evento eventoCapturado) {
		this.eventoCapturado = eventoCapturado;
	}

}
