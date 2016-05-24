package br.com.mixeventos.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.mixeventos.dao.CompraDAO;
import br.com.mixeventos.domain.Compra;
import br.com.mixeventos.domain.Usuario;
import br.com.mixeventos.util.SessionUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UltimasComprasBean implements Serializable{
	
	private List<Compra> ultimasCompras;
	private Usuario usuario;
	
	@PostConstruct
	public void inicializar(){
		SessionUtil su = new SessionUtil();
		usuario = (Usuario) su.getParam("USUARIOLogado");
		CompraDAO cDAO = new CompraDAO();
		ultimasCompras = cDAO.listarUltimasCompras(usuario.getCodigo());
		
	}
	
	public List<Compra> getUltimasCompras() {
		return ultimasCompras;
	}
	
	public void setUltimasCompras(List<Compra> ultimasCompras) {
		this.ultimasCompras = ultimasCompras;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
