package br.com.mixeventos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class TipoCategoria extends GenericDomain{
	
	@Column(length=50,nullable=false)
	private String nomeTipoCategoria;
	
	@ManyToOne
	private Categoria categoria;
	
	
	
	public String getNomeTipoCategoria() {
		return nomeTipoCategoria;
	}
	
	public void setNomeTipoCategoria(String nomeTipoCategoria) {
		this.nomeTipoCategoria = nomeTipoCategoria;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
