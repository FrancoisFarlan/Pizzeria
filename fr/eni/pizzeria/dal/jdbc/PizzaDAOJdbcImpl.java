package fr.eni.pizzeria.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.pizzeria.bo.Ingredient;
import fr.eni.pizzeria.bo.Pizza;
import fr.eni.pizzeria.dal.DALException;
import fr.eni.pizzeria.dal.DAO;

public class PizzaDAOJdbcImpl implements DAO<Pizza> {

	@Override
	public List<Pizza> selectAll() throws DALException {
		List<Pizza> pizzas = new ArrayList<>();
		String sql = "SELECT id_pizza, nom_pizza FROM Pizzas";
		
		String sql2 = "SELECT nom_ingredient FROM Ingredients "
				+ "JOIN Ingredients_Pizza "
				+ "ON Ingredients.id_ingredient = Ingredients_Pizza.id_ingredient "
				+ "WHERE Ingredients_Pizza.id_pizza = ?";
		
		Connection con = null;
		Statement stmt = null;
		PreparedStatement stmt2 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null; 

		try {
			con = JdbcTools.getConnection();
			stmt = con.createStatement();
			stmt2 = con.prepareStatement(sql2); 
			rs = stmt.executeQuery(sql);
			Pizza p = null;
			Ingredient i = null;
			while (rs.next()) {
				p = new Pizza(rs.getInt("id_pizza"), rs.getString("nom_pizza"));
				stmt2.setInt(1, p.getIdPizza());
				rs2 = stmt2.executeQuery();
				while(rs2.next()) {
					i = new Ingredient(rs2.getString("nom_ingredient"));
					p.addIngredient(i);
				}
				pizzas.add(p);
			}

		} catch (SQLException e) {
			throw new DALException("erreur selectAll", e);
		} finally {
			JdbcTools.close(rs2);
			JdbcTools.close(rs);
			JdbcTools.close(stmt2);
			JdbcTools.close(stmt);
			JdbcTools.close(con);
		}
		return pizzas;

	}

	@Override
	public Pizza selectById(int id) throws DALException {
		
		Pizza p = null;
		String sql = "SELECT id_pizza, nom_pizza FROM Pizzas "
				+ "WHERE id_pizza = ?";
		
		String sql2 = "SELECT nom_ingredient FROM Ingredients "
				+ "JOIN Ingredients_Pizza "
				+ "ON Ingredients.id_ingredient = Ingredients_Pizza.id_ingredient "
				+ "WHERE Ingredients_Pizza.id_pizza = ?";
		
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null; 

		try {
			con = JdbcTools.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt2 = con.prepareStatement(sql2); 
			
			rs = stmt.executeQuery();
			Ingredient i = null;
			if (rs.next()) {
				p = new Pizza(rs.getInt("id_pizza"), rs.getString("nom_pizza"));
				stmt2.setInt(1, p.getIdPizza());
				rs2 = stmt2.executeQuery();
				while(rs2.next()) {
					i = new Ingredient(rs2.getString("nom_ingredient"));
					p.addIngredient(i);
				}
			}

		} catch (SQLException e) {
			throw new DALException("erreur selectbyID", e);
		} finally {
			JdbcTools.close(rs2);
			JdbcTools.close(rs);
			JdbcTools.close(stmt2);
			JdbcTools.close(stmt);
			JdbcTools.close(con);
		}
		return p;
		
	}

	@Override
	public void insert(Pizza pizza) throws DALException {

		Connection con = null;
		String sql = "INSERT INTO Pizzas(nom_pizza) " + "VALUES(?);";
		String sql2 = "INSERT INTO Ingredients(nom_ingredient) " + "VALUES (?)";
		String select = "SELECT id_ingredient FROM Ingredients " + "WHERE nom_ingredient = ?";
		String sql3 = "INSERT INTO Ingredients_Pizza(id_pizza, id_ingredient) " + "VALUES (?, ?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement selectstmt = null;
		PreparedStatement stmt3 = null;

		try {
			// INSERT PIZZA
			con = JdbcTools.getConnection();
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, pizza.getNom());

			int nbLignes = stmt.executeUpdate();
			if (nbLignes == 1) {
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					pizza.setIdPizza(rs.getInt(1));
				}
			}

			// INSERT INGREDIENTS
			List<Ingredient> listeIngredients = pizza.getIngredients();
			selectstmt = con.prepareStatement(select);
			stmt2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			stmt3 = con.prepareStatement(sql3);
			
			for (Ingredient in : listeIngredients) {

				selectstmt.setString(1, in.getNom());
				rs2 = selectstmt.executeQuery(); 

				if (!rs2.next()) {

					stmt2.setString(1, in.getNom());

					int nbLignesIng = stmt2.executeUpdate();
					if (nbLignesIng == 1) {
						rs3 = stmt2.getGeneratedKeys();
						if (rs3.next()) {
							in.setIdIngredient(rs3.getInt(1));
						}
					}
				} else {
					in.setIdIngredient(rs2.getInt("id_ingredient"));
				}

				// INSERT TABLE Ingredients_Pizza
				
				stmt3.setInt(1, pizza.getIdPizza());
				stmt3.setInt(2, in.getIdIngredient());

				stmt3.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DALException("insertion impossible", e);
		} finally {
			JdbcTools.close(rs3);
			JdbcTools.close(rs2);
			JdbcTools.close(rs);
			JdbcTools.close(stmt3);
			JdbcTools.close(stmt2);
			JdbcTools.close(stmt);
			JdbcTools.close(con);
		}

	}

	@Override
	public void update(Pizza pizza) throws DALException {

		Connection con = null; 
		String sql = "DELETE FROM Ingredients_Pizza WHERE id_pizza = ? ";
		String updateNom = "UPDATE Pizzas SET nom_pizza = ? WHERE id_pizza = ? ";
		String sql2 = "INSERT INTO Ingredients(nom_ingredient) " + "VALUES (?)";
		String select = "SELECT id_ingredient FROM Ingredients " + "WHERE nom_ingredient = ?";
		String sql3 = "INSERT INTO Ingredients_Pizza(id_pizza, id_ingredient) " + "VALUES (?, ?)";
		PreparedStatement stmt = null;
		PreparedStatement stmtupdate = null;
		PreparedStatement stmt2 = null;
		PreparedStatement selectstmt = null;
		PreparedStatement stmt3 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		try {
			con = JdbcTools.getConnection();
			
			//Suppression liaisons
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, pizza.getIdPizza());
			stmt.executeUpdate();
			// Update nom pizza
			stmtupdate = con.prepareStatement(updateNom);
			stmtupdate.setString(1, pizza.getNom());
			stmtupdate.setInt(2, pizza.getIdPizza());
			stmtupdate.executeUpdate();
			
			//INSERT Ingrédients
			List<Ingredient> listeIngredients = pizza.getIngredients();
			selectstmt = con.prepareStatement(select);
			stmt2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			stmt3 = con.prepareStatement(sql3);
			
			for (Ingredient in : listeIngredients) {

				selectstmt.setString(1, in.getNom());
				rs2 = selectstmt.executeQuery(); 

				if (!rs2.next()) {

					stmt2.setString(1, in.getNom());

					int nbLignesIng = stmt2.executeUpdate();
					if (nbLignesIng == 1) {
						rs3 = stmt2.getGeneratedKeys();
						if (rs3.next()) {
							in.setIdIngredient(rs3.getInt(1));
						}
					}
				} else {
					in.setIdIngredient(rs2.getInt("id_ingredient"));
				}

				// INSERT TABLE Ingredients_Pizza
				
				stmt3.setInt(1, pizza.getIdPizza());
				stmt3.setInt(2, in.getIdIngredient());

				stmt3.executeUpdate();
			}
			
			
			
		} catch (SQLException ex) {
			throw new DALException("Erreur Update", ex); 
		}
		
		
		
	}

	@Override
	public void delete(Pizza pizza) throws DALException {

		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM Pizzas WHERE id_pizza = ?";
		
		try {
			con = JdbcTools.getConnection();
			stmt = con.prepareStatement(sql); 
			stmt.setInt(1, pizza.getIdPizza());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DALException("Erreur Delete", ex); 
		} finally {
			JdbcTools.close(stmt);
			JdbcTools.close(con);
		}
	}

}
