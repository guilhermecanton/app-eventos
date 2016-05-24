package br.com.mixeventos.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Compra extends GenericDomain{
	
	@OneToOne
	private Usuario usuario;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Ingresso> ingressos;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataCompra;
	
	@NotNull
	@Column(nullable=false)
	private Float total;
	
	@NotNull
	@Column(nullable=false)
	private Float valorIngresso;
	
	@Transient
	private Integer quantidadeIngressos;	
	
	
	
	// GETTER E SETTER
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
	
	public Integer getQuantidadeIngressos() {
		return quantidadeIngressos;
	}
	
	public void setQuantidadeIngressos(Integer quantidadeIngressos) {
		this.quantidadeIngressos = quantidadeIngressos;
	}
	
	public Float getValorIngresso() {
		return valorIngresso;
	}
	
	public void setValorIngresso(Float valorIngresso) {
		this.valorIngresso = valorIngresso;
	}
	
	
}
