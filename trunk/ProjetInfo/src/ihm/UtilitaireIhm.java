package ihm;

import metier.Compte;
import exception.ChampIncorrectException;

public class UtilitaireIhm {

	public static boolean verifieTypeCreationCompte(int type){
		return((type==1 || type==2 ||type==3));
	}
	
	public static boolean verifieChampsCreationAdmin(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	public static boolean verifieChampsCreationTech(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	public static boolean verifieChampsCreationUtil(int type,String adresseEMail,String nom,String prenom,String adressePostale){
		return (verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51 && prenom.length()>0 && prenom.length()<21 && nom.length()>0 && nom.length()<21 && adressePostale.length()>0 && adressePostale.length()<251);
	}

}
