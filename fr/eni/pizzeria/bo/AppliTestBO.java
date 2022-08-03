package fr.eni.pizzeria.bo;

public class AppliTestBO {

	public static void main(String[] args) {

		//Création ingrédients
		Ingredient tomate = new Ingredient("Tomate");
		Ingredient fromage = new Ingredient("Fromage");
		Ingredient jambon = new Ingredient("Jambon");
		Ingredient creme = new Ingredient("creme");
		Ingredient saumon = new Ingredient("Saumon");
		Ingredient olives = new Ingredient("Olives");
		Ingredient chorizo = new Ingredient("Chorizo");
		Ingredient champignons = new Ingredient("Champignons");
		Ingredient miel = new Ingredient("Miel"); 
		
				
				
		//Création pizzas	
		Pizza regina = new Pizza("Regina"); 
		Pizza pizzaSaumon = new Pizza("Saumon"); 
		Pizza chevreMiel = new Pizza("Chevre Miel"); 
		Pizza peperonni = new Pizza("Peperonni"); 
		
		//régina
		regina.addIngredient(tomate);
		regina.addIngredient(fromage);
		regina.addIngredient(jambon);
		
		// Saumon
		pizzaSaumon.addIngredient(creme);
		pizzaSaumon.addIngredient(saumon);
		pizzaSaumon.addIngredient(fromage);
		
		//chevre Miel
		chevreMiel.addIngredient(miel);
		chevreMiel.addIngredient(creme);
		chevreMiel.addIngredient(olives);
		chevreMiel.addIngredient(fromage);
		
		//peperonni
		peperonni.addIngredient(tomate);
		peperonni.addIngredient(olives);
		peperonni.addIngredient(champignons);
		peperonni.addIngredient(chorizo);
		peperonni.addIngredient(fromage);
		peperonni.removeIngredient(fromage);
		
		System.out.println(peperonni.toString());
		System.out.println(fromage.toString());
	}

}
