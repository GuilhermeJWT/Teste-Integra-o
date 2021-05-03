package br.com.systemsgs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.systemsgs.dominio.Usuario;

public class UsuarioDaoTest {
	
	private Session session;
	private UsuarioDao usuarioDao;
	
	@Before
	public void init() {
		session = new CriadorDeSessao().getSession();
		usuarioDao = new UsuarioDao(session);
		
		session.beginTransaction();
		
	}
	
	@After
	public void termina() {
		session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void deveEncontrarPeloNomeEmailMockado() {
		
		Usuario novoUsuario = new Usuario("Guilherme Santos", "guiteste@gmail.com");
		usuarioDao.salvar(novoUsuario);
		
		Usuario usuario = usuarioDao.porNomeEEmail("Guilherme Santos", "guiteste@gmail.com");
		
		assertEquals("Guilherme Santos", usuario.getNome());
		assertEquals("guiteste@gmail.com", usuario.getEmail());
		
	}
	
	
	@Test
	public void deveRetornarNuloSeNaoEncontrarUsuario() {

        Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Gui", "gui@teste.com.br");
        assertNull(usuarioDoBanco);

	}
	
}
