package exceptionsMetier;

public class PasDeVeloEmprunteException extends Exception{

	public PasDeVeloEmprunteException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
