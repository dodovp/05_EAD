package br.com.fiap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fiap.dao.CustomerDAO;
import br.com.fiap.dao.impl.CustomerDAOImpl;
import br.com.fiap.entity.Customer;
import br.com.fiap.entity.Gender;
import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.SearchNotFoundException;

class CustomerDAOTest {
	private static CustomerDAO dao;
	private Customer c;
	
	//MÉTODO EXECUTADO ANTES DE TODOS OS TESTES
	@BeforeAll
	public static void init() {
		EntityManagerFactory fabrica = 
				Persistence.createEntityManagerFactory("teste"); 
		
		EntityManager em = fabrica.createEntityManager(); 
		
		dao = new CustomerDAOImpl(em);
	}
	
	@BeforeEach //Método
	void beforeTest() {
			//Arrange: instanciar os objetos
			Customer c = new Customer("Teste", 
				new GregorianCalendar(1999, Calendar.MARCH, 15), 
				Gender.OTHERS, null);
			
			//Act: cadastrar o cliente
			try {
				dao.create(c);
				dao.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				fail("Falha no teste...");
			}
	}
	
	
	//TESTE CREATE
	@Test
	void createTest() {
		
		
		//Arrange: instanciar os objetos
		 c = new Customer("Teste", 
			new GregorianCalendar(1999, Calendar.MARCH, 15), 
			Gender.OTHERS, null);
		
		//Act: cadastrar o cliente
		try {
			dao.create(c);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail("Falha no teste...");
		}
		
		//Assert: validar o resultado
		//valida se a sequence gerou um código para o customer
		assertNotEquals(c.getId(), 0);
		
	}
	
	//TESTE READ
	@Test
	@DisplayName("Teste de cadastro com sucesso")
	void read() {
		//ARRANGE - INSTANCIAR OS OBJETOS E O CADASTRO
		 c = new Customer("Teste", 
			new GregorianCalendar(1999, Calendar.MARCH, 15), 
			Gender.MALE, null);
		try {
			dao.create(c);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			fail("Falha no teste");
		}
		
		//ACT - PESQUISAR O CUSTOMER QUE FOI CADASTRADO
		 
		try {
			Customer search = dao.read(c.getId());
			
			//ASSERT - VALIDA SE PESQUISOU
			assertNotNull(search);
			assertEquals(search.getId(), c.getId());
		} catch (SearchNotFoundException e) {
			e.printStackTrace();
			fail("Falha no teste...");
		}
		
	}
	
	//TESTE ATUALIZAR
	@Test
	@DisplayName("Teste de atualização com sucesso")
	void update() {
		//CADASTRAR UM CUSTOMER
		 c = new Customer("Teste", 
				new GregorianCalendar(1999, Calendar.MARCH, 15), 
				Gender.MALE, null);
			try {
				dao.create(c);
				dao.commit();
			} catch (CommitException e) {
				e.printStackTrace();
				fail("Falha no teste");
			}
		//ATUALIZAR O CUSTOMER CADASTRADO
		c.setName("Teste Update");
		c.setGender(Gender.MALE);
			
		try {
			dao.update(c);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falha no teste");
		}
			
			
		//PESQUISAR O CUSTOMER
		try {
			Customer search = dao.read(c.getId());
			
			//VALIDAR SE AS ALTERACOES FORAM REALIZADAS
			assertNotNull("Teste Update", search.getName());
			assertEquals(Gender.MALE, search.getGender());
			
		} catch (SearchNotFoundException e) {
			e.printStackTrace();
			fail("Falha no teste...");
		}
			
	}
	
	@Test
	@DisplayName("Teste de Deleção feito com sucesso")
	void Delete() {
		//CADASTRAR UM CUSTOMER
				  c = new Customer("Teste", 
						new GregorianCalendar(1999, Calendar.MARCH, 15), 
						Gender.MALE, null);
					try {
						dao.create(c);
						dao.commit();
					} catch (CommitException e) {
						e.printStackTrace();
						fail("Falha no teste");
					}
		
		//DELETAR CONTÉUDO
		
		try {
			dao.delete(c.getId());
			dao.commit();
		} catch (SearchNotFoundException | CommitException e) {
			e.printStackTrace();
			fail("Falha no teste...");
		}
		
		
		//PESQUISAR O CUSTOMER DEPOIS DE DELETADO
		Customer search = null;
		try {
			search = dao.read(c.getId());
			fail("Falha no teste");
			//ASSERT - VALIDA SE PESQUISOU
			
		} catch (SearchNotFoundException e) {
			assertNull(search);
		}
	}
	
	
}








