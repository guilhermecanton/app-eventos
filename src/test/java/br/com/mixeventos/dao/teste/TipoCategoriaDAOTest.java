package br.com.mixeventos.dao.teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.mixeventos.dao.TipoCategoriaDAO;
import br.com.mixeventos.domain.TipoCategoria;

public class TipoCategoriaDAOTest {
	
	@Test	
	public void listar() {
		Long categoriaCodigo = 2L;
		TipoCategoriaDAO tcDAO = new TipoCategoriaDAO();
		List<TipoCategoria> resultado = tcDAO.buscarPorCategoria(categoriaCodigo);

		for (TipoCategoria tipoCategoria : resultado) {
			System.out.println(tipoCategoria.getNomeTipoCategoria());
		}
	}
}
