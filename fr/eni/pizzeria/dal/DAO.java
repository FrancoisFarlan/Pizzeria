package fr.eni.pizzeria.dal;

import java.util.List;

public interface DAO<T> {

	public List<T> selectAll() throws DALException;
	public T selectById(int id) throws DALException;
	public void insert(T t) throws DALException;
	public void update(T t) throws DALException;
	public void delete(T t) throws DALException;
}
