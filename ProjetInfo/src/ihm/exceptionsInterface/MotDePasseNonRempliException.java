package ihm.exceptionsInterface;

public class MotDePasseNonRempliException extends Exception{

	private static final long serialVersionUID = 1L;

	public MotDePasseNonRempliException() {
		super();	}

	public MotDePasseNonRempliException(String message) {
		super(message);
	}
	
}
