package br.com.mixeventos.dao.teste;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.mixeventos.dao.UsuarioDAO;
import br.com.mixeventos.domain.Usuario;

public class UsuarioDAOTest {

	@Test
	
	public void adicionaUsuario() {
		Usuario user = new Usuario();
		user.setNome("Walter");
		user.setCpf("333.800.166-90");
		user.setSenha("xxx");
		user.setEmail("walter@hotmail.com");
		UsuarioDAO userDao = new UsuarioDAO();
		userDao.salvar(user);
		System.out.println("Usuário salvo!");
	}

	@Test
	@Ignore
	public void listarUsuarios() {
		List<Usuario> users = new ArrayList<>();
		UsuarioDAO udao = new UsuarioDAO();
		users = udao.listar();
		if (users != null) {
			for (Usuario usuario : users) {
				System.out.println("Nome: " + usuario.getNome());
				System.out.println("Email: " + usuario.getEmail());
			}

		} else {
			System.out.println("Lista vazia!");
		}
	}
	
	@Test
	@Ignore
	public void buscarPorCodigo(){
		Usuario user = new Usuario();
		UsuarioDAO udao = new UsuarioDAO();
		
		user = udao.buscarPorId(2L);
		
		if (user != null){
			System.out.println("Nome: " + user.getNome());
			System.out.println("Email: " + user.getEmail());
		}
		else{
			System.out.println("Usuario nao encontrado!");
		}
	}
	
	@Test
	@Ignore
	public void editarUsuario(){
		Usuario user = new Usuario();
		UsuarioDAO udao = new UsuarioDAO();
		
		user = udao.buscarPorId(2L);
		user.setEmail("walter@yahoo.com.br");
		udao.editar(user);
		
		System.out.println("Usuario editado com sucesso!");
	}
}
