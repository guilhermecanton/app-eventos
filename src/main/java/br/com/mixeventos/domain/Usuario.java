package br.com.mixeventos.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain{	
		
	@Size(min = 3, max = 100)
	@Column(length=100,nullable=false)
	private String nome;
	
	@Size(min = 7, max = 50) 
	@Column(length=50,nullable=false, unique=true)
	private String email;
	
	@Size(max = 32) 
	@Column(length=32,nullable=false)	
	private String senha;
	
	@Transient
	private String senhaNaoCriptografada;
	
	@Column(length=14,nullable=false,unique=true)	
	private String cpf;	
	
	@Column
	private Character tipo;
	
	@Transient
	private String tipoFormatado;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Mensagem> mensagens;
	
	// GETTER E SETTER
	
	public String getSenhaNaoCriptografada() {
		return senhaNaoCriptografada;
	}
	
	public void setSenhaNaoCriptografada(String senhaNaoCriptografada) {
		this.senhaNaoCriptografada = senhaNaoCriptografada;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Character getTipo() {
		return tipo;
	}	
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	public String getTipoFormatado() {
		tipoFormatado = null;
		if(tipo == 'A'){
			tipoFormatado = "Administrador";
		}else if(tipo == 'M'){
			tipoFormatado = "Master";
		}else if(tipo == 'C'){
			tipoFormatado = "Comum";
		}
		return tipoFormatado;
	}
	public void setTipoFormatado(String tipoFormatado) {
		this.tipoFormatado = tipoFormatado;
	}
	
	public List<Mensagem> getMensagens() {
		return mensagens;
	}
	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
}
