package fr.eni.pizzeria.dal;

public class DALException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Constructeurs

	
	public DALException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * @param cause
	 */
	public DALException(Throwable cause) {
		super(cause);
	}

	//Méthodes
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());
		
		return sb.toString() ;
	}
	
	
}