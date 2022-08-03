package fr.eni.pizzeria.dal;

import java.util.ArrayList;
import java.util.List;

import fr.eni.pizzeria.bo.Ingredient;
import fr.eni.pizzeria.bo.Pizza;

public class AppliTestDAL {

	public static void main(String[] args) {

		DAO<Pizza> daoPizza = DAOFactory.getPizzaDAO();

		// Création ingrédients
		Ingredient tomate = new Ingredient("Tomate");
		Ingredient fromage = new Ingredient("Fromage");
		Ingredient jambon = new Ingredient("Jambon");
		Ingredient creme = new Ingredient("creme");
		Ingredient saumon = new Ingredient("Saumon");
		Ingredient olives = new Ingredient("Olives");
		Ingredient chorizo = new Ingredient("Chorizo");
		Ingredient champignons = new Ingredient("Champignons");
		Ingredient miel = new Ingredient("Miel");

		// Création pizzas
		Pizza regina = new Pizza("Regina");
		Pizza pizzaSaumon = new Pizza("Saumon");
		Pizza chevreMiel = new Pizza("Chevre Miel");
		Pizza peperonni = new Pizza("Peperonni");

		// régina
		regina.addIngredient(tomate);
		regina.addIngredient(fromage);
		regina.addIngredient(jambon);

		// Saumon
		pizzaSaumon.addIngredient(creme);
		pizzaSaumon.addIngredient(saumon);
		pizzaSaumon.addIngredient(fromage);

		// chevre Miel
		chevreMiel.addIngredient(miel);
		chevreMiel.addIngredient(creme);
		chevreMiel.addIngredient(olives);
		chevreMiel.addIngredient(fromage);

		// peperonni
		peperonni.addIngredient(tomate);
		peperonni.addIngredient(olives);
		peperonni.addIngredient(champignons);
		peperonni.addIngredient(chorizo);
		peperonni.addIngredient(fromage);
		peperonni.removeIngredient(fromage);
		
		try {
			//daoPizza.insert(peperonni);
			//daoPizza.insert(regina);
			List<Pizza> liste = new ArrayList<>();
			liste = daoPizza.selectAll();
			for(Pizza pizza : liste) {
				System.out.println(pizza.toString());
			}
			
			System.out.println(daoPizza.selectById(2).toString());
		} catch (DALException ex) {
			ex.printStackTrace();
		}
	}
}
