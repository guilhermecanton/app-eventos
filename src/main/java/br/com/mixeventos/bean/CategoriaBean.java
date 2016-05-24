package br.com.mixeventos.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.mixeventos.dao.CategoriaDAO;
import br.com.mixeventos.domain.Categoria;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {
		
	private Categoria categoria;
	private List<Categoria> categorias;
	
	@PostConstruct
	public void inicializar(){
		CategoriaDAO cdao = new CategoriaDAO();
		categorias = cdao.listar();
	}
	
	public void novo(){
		categoria = new Categoria();
	}
	
	public void salvar(){
		try {
			CategoriaDAO cdao = new CategoriaDAO();
			cdao.salvar(categoria);
			categorias = cdao.listar();
			categoria = new Categoria();
			Messages.addGlobalInfo("Categoria salva com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Erro ao tentar salvar uma nova categoria!");
		}
	}
	
	public void salvarEdicao(){
		try {
			CategoriaDAO cdao = new CategoriaDAO();
			cdao.salvar(categoria);
			categorias = cdao.listar();
			categoria = new Categoria();
			Messages.addGlobalInfo("Categoria editada com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Erro ao tentar salvar uma nova categoria!");
		}
	}
	
	public void excluir(ActionEvent event){
		try {
			categoria = (Categoria) event.getComponent().getAttributes().get("categoriaSelecionada");
			CategoriaDAO cdao = new CategoriaDAO();
			cdao.excluir(categoria);
			categorias = cdao.listar();
			categoria = new Categoria();
			Messages.addGlobalInfo("Categoria excluída com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a categoria!");

		}
		
	}
	
	public void capturarCategoria(ActionEvent event){
		categoria = (Categoria) event.getComponent().getAttributes().get("categoriaSelecionada");		
	}
	
	//	getter e setter
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
}
