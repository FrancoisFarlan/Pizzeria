package fr.eni.pizzeria.bo;

import java.util.ArrayList;
import java.util.List;

public class Pizza {

	private Integer idPizza;
	private String nom; 
	private List<Ingredient> listeIngredients = new ArrayList<>();
	/**
	 * @param nom
	 * @param listeIngredients
	 */
	public Pizza(String nom) {
		this.nom = nom;
	}
	
	public Pizza(Integer idPizza, String nom) {
		this.idPizza = idPizza;
		this.nom = nom;
	}
	public Integer getIdPizza() {
		return this.idPizza;
	}
	public void setIdPizza(Integer idPizza) {
		this.idPizza = idPizza;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<Ingredient> getIngredients () {
		return this.listeIngredients;
	}
	public void addIngredient (Ingredient in) {
		this.listeIngredients.add(in);
		in.addPizza(this);
	} 
	
	public void removeIngredient (Ingredient in) {
		this.listeIngredients.remove(this.listeIngredients.indexOf(in)); 
		in.removePizza(this);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Pizza : "); 
		str.append(this.getNom());
		str.append(" -> Ingredients : [");
		for(Ingredient in : listeIngredients) {
			str.append(" " + in.getNom() + " ");
		}
		str.append("]");
		return str.toString();
	}
	
	
}
