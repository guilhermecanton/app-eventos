package br.com.mixeventos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.mixeventos.dao.AmbienteDAO;
import br.com.mixeventos.dao.CategoriaDAO;
import br.com.mixeventos.dao.EventoDAO;
import br.com.mixeventos.dao.TipoCategoriaDAO;
import br.com.mixeventos.domain.Ambiente;
import br.com.mixeventos.domain.Categoria;
import br.com.mixeventos.domain.Evento;
import br.com.mixeventos.domain.TipoCategoria;
import br.com.mixeventos.util.AutenticacaoListener;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EventoBean implements Serializable {

	private Evento evento;
	private List<Evento> eventos;
	private Ambiente ambiente;
	private List<Categoria> categorias;
	private List<Ambiente> ambientes;
	private List<TipoCategoria> tipoCategoria;
	private Categoria categoria;
	private Categoria categoriaBuscada;
	private Evento eventoBuscado;
	private List<Evento> eventosPorCategoria;
	private List<Evento> eventosPorCategoriaESub;	
	private List<Evento> eventosBuscaSimples;
	private String termoBuscado;

	@PostConstruct
	public void inicializar() {

		AutenticacaoListener al = new AutenticacaoListener();
		Boolean ehEventosAtivos = al.checarPagina();
		
		if (ehEventosAtivos == true) {
			EventoDAO eventoDao = new EventoDAO();
			try {
				eventos = eventoDao.listarEventosAtivos();
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			EventoDAO eventoDao = new EventoDAO();
			eventos = eventoDao.listar();
		}

		CategoriaDAO cdao = new CategoriaDAO();
		categorias = cdao.listar();

		AmbienteDAO adao = new AmbienteDAO();
		ambientes = adao.listar();

		eventoBuscado = new Evento();
		categoriaBuscada = new Categoria();

	}

	public void novo() {
		evento = new Evento();
		categoria = new Categoria();
		tipoCategoria = new ArrayList<>();

	}

	public void popular() {
		try {
			if (categoria != null) {
				TipoCategoriaDAO tcDAO = new TipoCategoriaDAO();
				tipoCategoria = tcDAO.buscarPorCategoria(categoria.getCodigo());
			} else {
				tipoCategoria = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os tipos de categoria");
			erro.printStackTrace();
		}
	}

	public void popularBusca() {
		try {
			if (categoriaBuscada != null) {
				TipoCategoriaDAO tcDAO = new TipoCategoriaDAO();
				tipoCategoria = tcDAO.buscarPorCategoria(categoriaBuscada.getCodigo());
			} else {
				tipoCategoria = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os tipos de categoria");
			erro.printStackTrace();
		}
	}

	public void procurarEvento() {
			eventosPorCategoria = new ArrayList<Evento>();
			eventosPorCategoriaESub = new ArrayList<Evento>();
		try {
			EventoDAO edao = new EventoDAO();

			if (eventoBuscado.getTipoCategoria() == null) {
				eventosPorCategoria = edao.buscarEventosEspecificos(categoriaBuscada.getCodigo());
				FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eventosPorCategoria", eventosPorCategoria);
				Faces.redirect("./pages/eventos-encontrados.xhtml");

			} else {
				eventosPorCategoriaESub = edao.buscarEventosEspecificos(categoriaBuscada.getCodigo(),
						eventoBuscado.getTipoCategoria().getCodigo());
				FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eventosPorCategoriaSub", eventosPorCategoriaESub);
				FacesContext.getCurrentInstance().getExternalContext().redirect("eventos-encontrados-categoria-e-sub.xhtml");

			}
		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// MÉTODO PARA SALVAR
	public void salvar() {
		try {
			EventoDAO eventoDao = new EventoDAO();
			evento.setAtivo(true);
			Evento eventoRetorno = eventoDao.merge(evento);

			Path origem = Paths.get(evento.getCaminho());
			Path destino = Paths.get("C:/Users/Guilherme/Uploads/" + "evento" + eventoRetorno.getCodigo() + ".jpg");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			evento = new Evento();
			eventos = eventoDao.listar();
			tipoCategoria = new ArrayList<>();
			categoria = new Categoria();
			Messages.addGlobalInfo("Evento salvo com sucesso");
		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// MÉTODO UPLOAD DE FOTO
	public void upload(FileUploadEvent event) {
		try {
			UploadedFile arquivoUpload = event.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			evento.setCaminho(arquivoTemp.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// MÉTODO EXCLUIR

	public void excluir(ActionEvent event) {
		try {
			Evento eventoParaExcluir = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");

			EventoDAO edao = new EventoDAO();
			edao.excluir(eventoParaExcluir);

			Path arquivo = Paths.get("C:/Users/Guilherme/Uploads/evento" + eventoParaExcluir.getCodigo() + ".jpg");
			Files.deleteIfExists(arquivo);

			eventos = edao.listar();
			Messages.addGlobalInfo("Evento excluído com sucesso!");
		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o evento!");
			e.printStackTrace();
		}
	}

	// MÉTODO CAPTURAR PARA EDITAR
	public void editar(ActionEvent event) {
		try {
			evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");

		} catch (RuntimeException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar capturar o evento!");
			e.printStackTrace();
		}
	}

	// MÉTODO PARA SALVAR A EDIÇÃO DA IMAGEM DO EVENTO
	public void salvarEdicaoFoto(ActionEvent event) {
		try {

			Path origem = Paths.get(evento.getCaminho());
			Path destino = Paths.get("C:/Users/Guilherme/Uploads/" + "evento" + evento.getCodigo() + ".jpg");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			EventoDAO eventoDao = new EventoDAO();
			evento = new Evento();
			eventos = eventoDao.listar();

			Messages.addGlobalInfo("A imagem do evento foi alterada com sucesso!");
		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro ao tentar editar a imagem do evento!");
		}

	}

	// MÉTODO PARA SALVAR EDIÇÃO DE DADOS DO EVENTO

	public void salvarEdicao(ActionEvent event) {
		try {
			Evento eventoSelecionado = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
			EventoDAO eventoDao = new EventoDAO();
			eventoSelecionado = eventoDao.merge(evento);
			evento = new Evento();
			eventos = eventoDao.listar();
			Messages.addGlobalInfo("O evento foi alterado com sucesso!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro ao alterar o evento");
		}

	}

	// MÉTODO CAPTURAR EVENTO
	public void capturarEvento(ActionEvent event) {
		try {
			evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eventoNome", evento);
			FacesContext.getCurrentInstance().getExternalContext().redirect("detalhes-evento.xhtml");

		} catch (RuntimeException | IOException e) {
			// TODO: handle exception
			Messages.addGlobalError("Ocorreu um erro ao tentar capturar o evento!");
			e.printStackTrace();
		}
	}
	
	// MÉTODO PARA DESATIVAR EVENTO
	
	public void desativar(ActionEvent event){
		try {
			evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
			evento.setAtivo(false);
			EventoDAO edao = new EventoDAO();
			edao.salvar(evento);
			evento = new Evento();
			eventos = edao.listar();
			Messages.addGlobalInfo("O evento foi desativado!");
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	// GETTER E SETTER

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(List<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}

	public List<TipoCategoria> getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(List<TipoCategoria> tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Categoria getCategoriaBuscada() {
		return categoriaBuscada;
	}

	public void setCategoriaBuscada(Categoria categoriaBuscada) {
		this.categoriaBuscada = categoriaBuscada;
	}

	public Evento getEventoBuscado() {
		return eventoBuscado;
	}

	public void setEventoBuscado(Evento eventoBuscado) {
		this.eventoBuscado = eventoBuscado;
	}

	public List<Evento> getEventosPorCategoria() {
		return eventosPorCategoria;
	}

	public void setEventosPorCategoria(List<Evento> eventosPorCategoria) {
		this.eventosPorCategoria = eventosPorCategoria;
	}

	public List<Evento> getEventosPorCategoriaESub() {
		return eventosPorCategoriaESub;
	}

	public void setEventosPorCategoriaESub(List<Evento> eventosPorCategoriaESub) {
		this.eventosPorCategoriaESub = eventosPorCategoriaESub;
	}
	
	public List<Evento> getEventosBuscaSimples() {
		return eventosBuscaSimples;
	}
	
	public void setEventosBuscaSimples(List<Evento> eventosBuscaSimples) {
		this.eventosBuscaSimples = eventosBuscaSimples;
	}
	
	public String getTermoBuscado() {
		return termoBuscado;
	}
	public void setTermoBuscado(String termoBuscado) {
		this.termoBuscado = termoBuscado;
	}
	
}
