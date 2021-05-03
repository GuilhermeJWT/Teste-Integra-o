package br.com.systemsgs.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.Test;

import br.com.systemsgs.dominio.Usuario;

public class UsuarioDaoTest {
	
	@Test
	public void deveEncontrarPeloNomeEmailMockado() {
		
		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);
		
		Usuario novoUsuario = new Usuario("Guilherme Santos", "guiteste@gmail.com");
		usuarioDao.salvar(novoUsuario);
		
		Usuario usuario = usuarioDao.porNomeEEmail("Guilherme Santos", "guiteste@gmail.com");
		
		assertEquals("Guilherme Santos", usuario.getNome());
		assertEquals("guiteste@gmail.com", usuario.getEmail());
		
		session.close();
		
	}

}
