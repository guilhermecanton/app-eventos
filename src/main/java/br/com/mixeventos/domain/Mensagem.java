package br.com.mixeventos.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
public class Mensagem extends GenericDomain{
	
	@Size(min = 2, max = 40)
	@Column(length=40,nullable=false)
	private String assunto;
	
	@Size(min = 2, max = 100)
	@Column(length=100,nullable=false)
	private String texto;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@OneToOne
	private Usuario remetente;
	
	@OneToOne
	private Usuario destinatario;
	
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Usuario getRemetente() {
		return remetente;
	}
	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}
	public Usuario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	
	
}
