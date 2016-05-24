package br.com.mixeventos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.mixeventos.dao.CompraDAO;
import br.com.mixeventos.dao.EventoDAO;
import br.com.mixeventos.dao.IngressoDAO;
import br.com.mixeventos.domain.Compra;
import br.com.mixeventos.domain.Evento;
import br.com.mixeventos.domain.Ingresso;
import br.com.mixeventos.domain.Usuario;
import br.com.mixeventos.util.SessionUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CompraBean implements Serializable{
	
	private Compra compra;
	private List<Ingresso> ingressos;
	private Usuario usuario;
	private Evento evento;
	private Integer quantidade;
	private Ingresso ingresso;
	private Boolean disponibilidadeCompra;
	

	
	// CAPTURA EVENTO PARA COMPRA DE INGRESSOS
	
	public void capturarEvento(ActionEvent event){			
		evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
		EventoDAO edao = new EventoDAO();
		Evento eventoAtualizado = edao.merge(evento);
		Integer quantidadeIngressoDisponivel = eventoAtualizado.getQuantidadeIngressos();
		
		if(quantidade <= quantidadeIngressoDisponivel){
		compra = new Compra();	
		Integer qt = this.quantidade;
		compra.setQuantidadeIngressos(qt);
		compra.setTotal(qt * evento.getValor());
		ingressos = new ArrayList<>();
		criaIngressos();
		disponibilidadeCompra = true;	
		exibir();
		}
		else{				
			disponibilidadeCompra = false;
			exibir();				
			Messages.addGlobalInfo("Não há tantos ingressos disponíveis, diminua a quantidade!");
		}
		
	}
	
	
	// DESATIVAR EVENTO SE NÃO HOUVER MAIS INGRESSOS DISPONIVEIS PARA VENDA
	
	public void validarEvento(){
		EventoDAO edao = new EventoDAO();		
		Integer quantidadeIng = evento.getQuantidadeIngressos();
		if (quantidadeIng == 0 || new Date().after(evento.getDataEvento()) ){
			evento.setAtivo(false);
			edao.salvar(evento);
			System.out.println("evento foi desativado");
		}
	}
	
	
	// EXIBE INGRESSOS SE ESTIVEREM DISPONIVEIS E OS OCULTA SE ESTIVEREM INDISPONIVEIS
	public void exibir(){
		if(disponibilidadeCompra == true){
			RequestContext.getCurrentInstance().execute("PF('dialogoCompra').show();");

		}else{
			RequestContext.getCurrentInstance().execute("PF('dialogoCompra').hide();");

		}
	}
	
	
	// CRIA O INGRESSOS
	public void criaIngressos(){
		Integer contador = 0;
		while(contador < quantidade){
			Ingresso i1 = new Ingresso();
			i1.setEvento(evento);
			i1.setValor(compra.getValorIngresso());
			ingressos.add(i1);
			contador++;
		}
	}
	
	// SALVA O INGRESSOS
	public void salvarIngressos(){
		for (Ingresso ingresso : ingressos) {
			ingresso.setEvento(evento);
			ingresso.setValor(evento.getValor());
			IngressoDAO idao = new IngressoDAO();
			idao.salvar(ingresso);
		}
	}
	
	
	// FINALIZA A COMPRA DE INGRESSOS
	public void finalizar(){
		Usuario usuarioLogado = (Usuario)SessionUtil.getParam("USUARIOLogado");
		compra.setDataCompra(new Date());
		compra.setUsuario(usuarioLogado);
		compra.setValorIngresso(evento.getValor());
		
		salvarIngressos();
		compra.setIngressos(ingressos);		
		
		EventoDAO eventoDao = new EventoDAO();
		evento.setQuantidadeIngressosVendidos(evento.getQuantidadeIngressosVendidos() + quantidade);
		evento.setQuantidadeIngressos(evento.getQuantidadeIngressos() - ingressos.size());
		eventoDao.salvar(evento);
		
		CompraDAO compraDao = new CompraDAO();	
		compraDao.merge(compra);
		
		Messages.addGlobalInfo("Ingressos comprados com sucesso!");	
		validarEvento();
		
	}
	
	// NOVA COMPRA
	public void novo(){
		compra = new Compra();	
		Integer qt = this.quantidade;
		compra.setQuantidadeIngressos(qt);
		compra.setTotal(qt * evento.getValor());
	}
	
	
	
	
	// GETTERS E SETTERS
	
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public List<Ingresso> getIngressos() {
		return ingressos;
	}
	public void setIngressos(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Ingresso getIngresso() {
		return ingresso;
	}
	
	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	public Boolean getDisponibilidadeCompra() {
		if(disponibilidadeCompra == null){
			disponibilidadeCompra = false;
		}
		return disponibilidadeCompra;
	}
	
	public void setDisponibilidadeCompra(Boolean disponibilidadeCompra) {
		this.disponibilidadeCompra = disponibilidadeCompra;
	}

}
