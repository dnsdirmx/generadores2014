package Model.EntityCRUD.Exceptions;

public class MaxLength extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaxLength(String attibute) {
		super("El "+attibute+" sobrepasa el n�mero de caracteres permitido en la base de datos");
	}

}
