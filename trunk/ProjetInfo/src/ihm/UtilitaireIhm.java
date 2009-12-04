package ihm;

import metier.Compte;
import exception.ChampIncorrectException;

public class UtilitaireIhm {

	public static boolean verifieChampsCreationCompte(int type,String adresseEMail) throws ChampIncorrectException{
		boolean champsOk = false;
		if ((type==1 || type==2 ||type==3) && (adresseEMail.length()>0 && (adresseEMail.length()<51))){
			champsOk=true;
		}
		else {
			throw new ChampIncorrectException("Champs entrés incorrects");
		}
		return champsOk;
	}

	public static boolean verifieChampsCreationAdmin(Compte compte) throws ChampIncorrectException{
		return verifieChampsCreationCompte(compte.getType(),compte.getAdresseEmail());
	}

	public static boolean verifieChampsCreationTech(Compte compte) throws ChampIncorrectException{
		return verifieChampsCreationCompte(compte.getType(),compte.getAdresseEmail());
	}

	public static boolean verifieChampsCreationUtil(Compte compte,String nom,String prenom,String adressePostale) throws ChampIncorrectException{
		return (verifieChampsCreationCompte(compte.getType(),compte.getAdresseEmail()) && prenom.length()>0 && prenom.length()<21 && nom.length()>0 && nom.length()<21 && adressePostale.length()>0 && adressePostale.length()<251);
	}

}
