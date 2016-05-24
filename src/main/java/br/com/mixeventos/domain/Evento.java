package br.com.mixeventos.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Evento extends GenericDomain{	
	
	
	@Column(length=30,nullable=false)
	private String descricao;	
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataEvento;
	
	@Column(length=200)
	private String resumo;	
	
	@OneToOne
	private TipoCategoria tipoCategoria;  	
    
    @OneToOne
    private Ambiente ambiente;
    
    @Transient
    @Column
    private String caminho;
    
    @Column(nullable=false)
    @Min(20)
    @Max(60000)
    private int quantidadeIngressos;    
    
    @Column
    @Max(60000)
    private int quantidadeIngressosVendidos;
    
	@NotNull
	@Column(nullable=false)
	private Float valor;
	
	private Boolean ativo;
	
	@Transient
	private String ativoFormatado;
    
    
    // GETTER E SETTER
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}
	
	public void setTipoCategoria(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}
	
	public Ambiente getAmbiente() {
		return ambiente;
	}
	
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}	
	
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public int getQuantidadeIngressos() {
		return quantidadeIngressos;
	}
	
	public void setQuantidadeIngressos(int quantidadeIngressos) {
		this.quantidadeIngressos = quantidadeIngressos;
	}
	
	public Float getValor() {
		return valor;
	}
	
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getAtivoFormatado() {
		if(ativo==true){
			ativoFormatado = "Ativo";
		}
		else{
			ativoFormatado = "Desativo";
		}
		return ativoFormatado;
	}	
	
	public void setAtivoFormatado(String ativoFormatado) {
		this.ativoFormatado = ativoFormatado;
	}
	
	public int getQuantidadeIngressosVendidos() {
		return quantidadeIngressosVendidos;
	}
	
	public void setQuantidadeIngressosVendidos(int quantidadeIngressosVendidos) {
		this.quantidadeIngressosVendidos = quantidadeIngressosVendidos;
	}

}
