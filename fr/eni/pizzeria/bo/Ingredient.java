package fr.eni.pizzeria.bo;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

	private Integer idIngredient;
	private String nom; 
	private List<Pizza> pizzas = new ArrayList<>();
	/**
	 * @param nom
	 */
	public Ingredient(String nom) {
		this.nom = nom;
	}
	public Integer getIdIngredient() {
		return this.idIngredient;
	}
	public void setIdIngredient(Integer idIngredient) {
		this.idIngredient = idIngredient;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public List<Pizza> getPizzas() {
		return this.pizzas;
	}
	public void addPizza(Pizza pizza) {
		this.pizzas.add(pizza);
	
	}
	
	public void removePizza(Pizza pizza) {
		this.pizzas.remove(this.pizzas.indexOf(pizza)); 
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Cet ingrédient entre dans la composition des pizzas : \n");
		for(Pizza pizza : pizzas) {
			str.append(pizza.getNom());
			str.append("\n"); 
		}
		
		return str.toString();
	}
}
