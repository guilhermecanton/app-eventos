package br.com.mixeventos.dao.teste;

import java.util.List;

import org.junit.Test;

import br.com.mixeventos.dao.CompraDAO;
import br.com.mixeventos.domain.Compra;

public class CompraDAOTest {
	
	
	
	@Test
	public void listarUltimasCompras(){
		
		Long idUsuario = 3L;
		CompraDAO cdao = new CompraDAO();
		List<Compra> ultimasCompras = cdao.listarUltimasCompras(idUsuario);
		for (Compra compra : ultimasCompras) {
			System.out.println("Código da compra: " + compra.getCodigo());
			System.out.println("Data: " + compra.getDataCompra());
			System.out.println("Valor do ingresso: " + compra.getValorIngresso());
			System.out.println("Total: " + compra.getTotal());
			
		}
		
	}

}
