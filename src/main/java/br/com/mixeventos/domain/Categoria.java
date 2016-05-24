package br.com.mixeventos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Categoria extends GenericDomain {

	@Column(length = 50, nullable = false)
	private String nomeCategoria;


	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}


}
