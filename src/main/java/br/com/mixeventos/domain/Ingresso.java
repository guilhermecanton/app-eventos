package br.com.mixeventos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Ingresso extends GenericDomain{
	
	@OneToOne
	private Evento evento;
	
	@NotNull
	@Column(nullable=false)
	private Float valor;
	
	
	// GETTER E SETTER
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public Float getValor() {
		return valor;
	}
	
	public void setValor(Float valor) {
		this.valor = valor;
	}
}
