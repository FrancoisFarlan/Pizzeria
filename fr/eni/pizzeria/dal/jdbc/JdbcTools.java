package fr.eni.pizzeria.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.eni.pizzeria.dal.DALException;
import fr.eni.pizzeria.dal.Settings;

public class JdbcTools {

	private static Connection con;

	public static Connection getConnection() throws SQLException {
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(Settings.getProperties("urldb"), Settings.getProperties("userdb"),
					Settings.getProperties("passworddb"));
		}
		return con;
	}

	public static void close(AutoCloseable resource) throws DALException{
		if (resource != null) {
			try {
				resource.close();
			} catch (Exception ex) {
				throw new DALException("erreur de fermeture de ressources", ex);
			}
		}
	}
}
