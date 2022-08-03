package fr.eni.pizzeria.dal;

import fr.eni.pizzeria.bo.Pizza;
import fr.eni.pizzeria.dal.jdbc.PizzaDAOJdbcImpl;

public class DAOFactory {

	public static DAO<Pizza> getPizzaDAO() {
		return new PizzaDAOJdbcImpl();
	}
}
