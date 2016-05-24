package br.com.mixeventos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.mixeventos.dao.MensagemDAO;
import br.com.mixeventos.dao.UsuarioDAO;
import br.com.mixeventos.domain.Mensagem;
import br.com.mixeventos.domain.Usuario;
import br.com.mixeventos.util.PageListener;
import br.com.mixeventos.util.SessionUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MensagemBean implements Serializable {

	private List<Mensagem> mensagens;
	private Mensagem mensagem;
	private Usuario user;
	private String emailDestinatario;
	private Mensagem mensagemSelecionada;

	private MensagemDAO mdao;

	@PostConstruct
	public void init() {
		PageListener pl = new PageListener();
		Boolean ehEventosAtivos = pl.checarPaginaMensagem();
		mdao = new MensagemDAO();
		user = (Usuario) SessionUtil.getParam("USUARIOLogado");
		if(ehEventosAtivos == true){
			mensagens = mdao.listarMsgs(user.getCodigo());
		}else{
			mensagens = mdao.listarMsgsEnviadas(user.getCodigo());
		}		
	}

	public void enviar() {
		try {
			if (verificarSeUsuarioExiste() == true) {
				UsuarioDAO udao = new UsuarioDAO();
				Usuario destinatario = udao.buscarPorEmail(emailDestinatario);
				List<Mensagem> msgsDestinatario = udao.buscarMensagensUsuario(destinatario.getCodigo());
				mensagem.setData(new Date());
				mensagem.setRemetente(user);
				mensagem.setDestinatario(destinatario);
				mensagem.setMsgLida(false);
				msgsDestinatario.add(mensagem);
				destinatario.setMensagens(msgsDestinatario);
				udao.salvar(destinatario);
				emailDestinatario = null;
				mensagem = new Mensagem();
				Messages.addGlobalInfo("A mensagem foi enviada com sucesso!");

			} else {
				Messages.addGlobalInfo("O usuário destinatário não existe!");
			}
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void capturarMensagem(ActionEvent event) {
		try {
			mensagemSelecionada = (Mensagem) event.getComponent().getAttributes().get("msgSelecionada");

		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean verificarSeUsuarioExiste() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			Usuario usuarioEncontrado = udao.buscarPorEmail(emailDestinatario);
			if(usuarioEncontrado != null ){
				return true;
			}else{
				return false;
			}
			
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	// MÉTODO PARA TROCAR ESTADO DA MENSAGEM AO SER LIDA
	public void trocarEstadoDaMsg() {
		try {
			mensagemSelecionada.setMsgLida(true);
			mdao.salvar(mensagemSelecionada);
			mensagemSelecionada = new Mensagem();
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void redirecionaMensagensEnviadas() throws IOException{
		try {
			Faces.redirect("./pages/mensagens-enviadas.xhtml");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void redirecionaMensagensRecebidas() throws IOException{
		try {
			Faces.redirect("./pages/mensagens.xhtml");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	


	// GETTERS E SETTERS

	public void novo() {
		mensagem = new Mensagem();
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public Mensagem getMensagemSelecionada() {
		return mensagemSelecionada;
	}

	public void setMensagemSelecionada(Mensagem mensagemSelecionada) {
		this.mensagemSelecionada = mensagemSelecionada;
	}
}
