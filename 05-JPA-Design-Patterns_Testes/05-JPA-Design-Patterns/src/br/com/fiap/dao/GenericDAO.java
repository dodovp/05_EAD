package br.com.fiap.dao;

import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.SearchNotFoundException;

public interface GenericDAO<T,K> {

	void create(T table);
	
	T read(K key) throws SearchNotFoundException;
	
	void update(T table);
	
	void delete(K key) throws SearchNotFoundException;
	
	void commit() throws CommitException;
	
}