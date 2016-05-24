package br.com.mixeventos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.mixeventos.dao.UsuarioDAO;
import br.com.mixeventos.domain.Usuario;
import br.com.mixeventos.util.SessionUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {
	private final String caminhoImg = "ajax-loader.gif";

	private Usuario usuario;
	private Usuario usuarioLogado;
	private Usuario usuarioRegistro;
	

	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
		usuarioRegistro = new Usuario();	
	}

	public void novo() {
		usuarioRegistro = new Usuario();
	}

	// MÉTODO AUTENTICAR
	public void autenticar() throws IOException {
		try {			
			UsuarioDAO udao = new UsuarioDAO();
			usuarioLogado = udao.autenticar(usuario.getEmail(), usuario.getSenha());
			SessionUtil.setParam("USUARIOLogado", usuarioLogado);

			if (usuarioLogado == null) {
				Messages.addGlobalError("Conta ou Senha incorretos!");
				return;
			}

			Messages.addGlobalInfo("Autenticado!");
			Faces.redirect("./pages/principal.xhtml");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

	// MÉTODO PARA DESLOGAR

	public void logout() {

		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			sessao.invalidate();
			Faces.redirect("./pages/autenticacao.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// SALVAR NOVO USUÁRIO
	public void salvarUsuario() {
		try {
			SimpleHash hash = new SimpleHash("md5", usuarioRegistro.getSenhaNaoCriptografada());
			usuarioRegistro.setSenha(hash.toHex());
			UsuarioDAO userDAO = new UsuarioDAO();
			usuarioRegistro.setTipo('C');
			userDAO.salvar(usuarioRegistro);
			usuarioRegistro = new Usuario();
			Messages.addGlobalInfo("Cadastro efetuado com sucesso!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao tentar cadastrar!");
		}
	}
	
	// VERIFICA PERMISSÕES DE USUARIOS
	public boolean temPermissoes(List<String> permissoes) {
		for(String permissao : permissoes){
			if(usuarioLogado.getTipo() == permissao.charAt(0)){
				return true;
			}
		}
		
		return false;
	}

	// GETTER E SETTER

	public String getCaminhoImg() {
		return caminhoImg;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Usuario usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
}
