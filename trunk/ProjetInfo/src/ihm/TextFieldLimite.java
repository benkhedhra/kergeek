package ihm;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 * TextField that can be limited in size (max number of characters typed in)
 * @author oma
 *
 */
public class TextFieldLimite extends JTextField {

	private static final long serialVersionUID = 1L;

	public TextFieldLimite(int longueurMax, String message){
		super(message);
		AbstractDocument doc = (AbstractDocument) this.getDocument();
		doc.setDocumentFilter(new TextLimiter(longueurMax));
	}


}
