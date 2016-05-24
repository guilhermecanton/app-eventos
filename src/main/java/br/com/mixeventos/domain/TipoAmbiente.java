package br.com.mixeventos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class TipoAmbiente extends GenericDomain{
	
	@Column(length=50,nullable=false)
	private String descricao;
	
	// GETTER E SETTER
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
