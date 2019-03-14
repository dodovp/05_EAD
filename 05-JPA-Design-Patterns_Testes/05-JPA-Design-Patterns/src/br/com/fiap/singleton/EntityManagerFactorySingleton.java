package br.com.fiap.singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

	//1 - Atributo estático do objeto que será único
	private static EntityManagerFactory emf;
	
	//2 - Construtor privado
	private EntityManagerFactorySingleton() {}
	
	//3 - Método estático que instancia o objeto único
	public static EntityManagerFactory getInstance() {
		if (emf == null) {
			emf = Persistence
				.createEntityManagerFactory("CLIENTE_ORACLE");
		}
		return emf;
	}
	
}

