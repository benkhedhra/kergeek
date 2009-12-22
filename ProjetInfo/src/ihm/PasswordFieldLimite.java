package ihm;

import javax.swing.JPasswordField;
import javax.swing.text.AbstractDocument;

public class PasswordFieldLimite extends JPasswordField{
	
	private static final long serialVersionUID = 1L;

	public PasswordFieldLimite(int longueurMax, String motDePasse){
		super(motDePasse);
		AbstractDocument doc = (AbstractDocument) this.getDocument();
		doc.setDocumentFilter(new TextLimiter(longueurMax));
	}
	
}
