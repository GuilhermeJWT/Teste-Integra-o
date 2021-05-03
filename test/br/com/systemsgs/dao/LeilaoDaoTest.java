package br.com.systemsgs.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.systemsgs.dominio.Leilao;
import br.com.systemsgs.dominio.Usuario;

public class LeilaoDaoTest {
	
	private Session session;
	private UsuarioDao usuarioDao;
	private LeilaoDao leilaoDao;
	
	@Before
	public void init() {
		session = new CriadorDeSessao().getSession();
		usuarioDao = new UsuarioDao(session);
		leilaoDao = new LeilaoDao(session);
		
		session.beginTransaction();
		
	}
	
	@After
	public void termina() {
		session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void deveContarLeiloesNaoEncerrados() {
		
		Usuario guilherme = new Usuario("Guilherme Santos", "guitestt@gmail.com");
		
		Leilao ativo = new Leilao("Notebook", 1500.0, guilherme, false);
		Leilao encerrado = new Leilao("Celular", 700.0, guilherme, false);
		encerrado.encerra();
		
		usuarioDao.salvar(guilherme);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);
		
		long total = leilaoDao.total();
		
		assertEquals(1L, total);
		
	}

}
