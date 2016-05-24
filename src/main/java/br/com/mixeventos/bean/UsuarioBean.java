package br.com.mixeventos.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.mixeventos.dao.UsuarioDAO;
import br.com.mixeventos.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	UsuarioDAO userDao = new UsuarioDAO();
	
	@PostConstruct
	public void inicializar(){		
		usuarios = userDao.listar();
	}
	
	public void novo(){
		usuario = new Usuario();
	}
	
	// SALVAR NOVO USUÁRIO	
	public void salvar(){
		try {			
			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaNaoCriptografada());
			usuario.setSenha(hash.toHex());
			userDao.salvar(usuario);
			usuario = new Usuario();
			usuarios = userDao.listar();
			Messages.addGlobalInfo("Cadastro efetuado com sucesso!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao tentar cadastrar!");
		}
	}
	
	public void excluir(ActionEvent event){		
		try {
			usuario = (Usuario)event.getComponent().getAttributes().get("usuarioSelecionado");
			userDao.excluir(usuario);
			usuario = new Usuario();
			usuarios = userDao.listar();
			Messages.addGlobalInfo("O usuário foi excluído com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("O usuário foi excluído com sucesso!");
		}
	}
	
	public void capturarUsuario(ActionEvent event){
		usuario = (Usuario)event.getComponent().getAttributes().get("usuarioSelecionado");		
	}
	
	public void salvarEdicao(){
		try {
			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaNaoCriptografada());
			usuario.setSenha(hash.toHex());
			userDao.salvar(usuario);
			usuario = new Usuario();
			Messages.addGlobalInfo("Usuário editado com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Error ao salvar edição do usuário!");
		}
	}
	
	
	// GETTER E SETTER
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
