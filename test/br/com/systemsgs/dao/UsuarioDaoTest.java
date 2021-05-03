package br.com.systemsgs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
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
	
	@Test
	public void deveDeletarUmUsuario() {
		
		Usuario gui = new Usuario("Guilherme Santos", "gui@teste.com.br");
		
		usuarioDao.salvar(gui);
		usuarioDao.deletar(gui);
		
		session.flush();
		session.clear();
		
		Usuario deletado = usuarioDao.porNomeEEmail("Guilherme Santos", "gui@teste.com.br");
		
		Assert.assertNull(deletado);
		
	}
	
	@Test
    public void deveAlterarUmUsuario() {
        Usuario usuario = new Usuario("Guilherme Santos", "gui@teste.com.br");

        usuarioDao.salvar(usuario);

        usuario.setNome("Guilherme Santos");
        usuario.setEmail("gui@teste.com.br");

        usuarioDao.atualizar(usuario);

        session.flush();

        Usuario novoUsuario = usuarioDao.porNomeEEmail("Guilherme Santos", "gui@teste.com.br");
        assertNotNull(novoUsuario);
        System.out.println(novoUsuario);

        Usuario usuarioInexistente = usuarioDao.porNomeEEmail("Guilherme Santos", "gui@teste.com.br");
        assertNull(usuarioInexistente);

    }
	
}
