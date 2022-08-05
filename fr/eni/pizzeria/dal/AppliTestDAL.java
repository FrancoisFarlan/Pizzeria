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
		
		
		try {
			//Insert pizzas
			daoPizza.insert(peperonni);
			daoPizza.insert(regina);
			daoPizza.insert(chevreMiel);
			daoPizza.insert(pizzaSaumon);
			
			//select all
			List<Pizza> liste = new ArrayList<>();
			liste = daoPizza.selectAll();
			for(Pizza pizza : liste) {
				System.out.println(pizza.toString());
			}
			
			// select par ID
			System.out.println(daoPizza.selectById(regina.getIdPizza()).toString());
			
			
			//Update
			peperonni.addIngredient(fromage);
			daoPizza.update(peperonni);
			
			
			//Pizza après update 
			System.out.println("Pizza Peperonni après Update :");
			System.out.println(daoPizza.selectById(peperonni.getIdPizza()).toString());
			
			//delete pizza
			daoPizza.delete(peperonni);
			liste = daoPizza.selectAll();
			for(Pizza pizza : liste) {
				System.out.println(pizza.toString());
			}
			
			
			
		} catch (DALException ex) {
			ex.printStackTrace();
		}
	}
}
