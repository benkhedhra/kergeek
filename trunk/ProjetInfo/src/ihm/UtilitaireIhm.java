package ihm;

import java.sql.SQLException;

import gestionBaseDeDonnees.DAOVelo;
import metier.Compte;
import metier.Station;
import metier.Velo;

public class UtilitaireIhm {

	/**
	 * V�rifie si un entier correspond bien � un type de compte r�pertori�
	 * @param un entier : type
	 * @return un bool�en
	 * qui vaut vrai si type est bien un type de compte existant
	 * faux sinon
	 */
	public static boolean verifieTypeCreationCompte(int type){
		return((type==Compte.TYPE_ADMINISTRATEUR || type==Compte.TYPE_TECHNICIEN ||type==Compte.TYPE_UTILISATEUR));
	}
	
	
	/**
	 * V�rifie si un entier et une String peuvent correspondre � des param�tres ad�quats pour construire un administrateur
	 * @param type : int
	 * @param adresseEMail : String
	 * @return un bool�en
	 * qui vaut vrai si type correspond � un type de compte existant et que adresseEMail v�rifie les contraintes du MLD
	 * faux sinon
	 * @see verifieTypeCreationCompte(type)
	 */
	public static boolean verifieChampsCreationAdmin(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * V�rifie si un entier et une String peuvent correspondre � des param�tres ad�quats pour construire un technicien
	 * @param type : int
	 * @param adresseEMail : String
	 * @return un bool�en
	 * qui vaut vrai si type correspond � un type de compte existant et que adresseEMail v�rifie les contraintes du MLD
	 * faux sinon
	 * @see verifieTypeCreationCompte(type)
	 */
	public static boolean verifieChampsCreationTech(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * V�rifie si un entier et quatre String peuvent correspondre � des param�tres ad�quats pour construire un utilisateur
	 * @param type : int
	 * @param adresseEMail : String
	 * @param nom : String
	 * @param prenom : String
	 * @param adressePostale : String
	 * @return un bool�en
	 * qui vaut vrai si type correspond � un type de compte existant et que adresseEMail, nom, prenom et adressePostale v�rifie les contraintes du MLD
	 * faux sinon
	 * @see verifieTypeCreationCompte(type)
	 */
	public static boolean verifieChampsCreationUtil(int type,String adresseEMail,String nom,String prenom,String adressePostale){
		return (verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51 && prenom.length()>0 && prenom.length()<21 && nom.length()>0 && nom.length()<21 && adressePostale.length()>0 && adressePostale.length()<251);
	}
	
	/**
	 * V�rifie si la modification de mot de passe est possible
	 * @param c : Compte
	 * @param ancienMdp : String
	 * @param nouveauMdp1 : String
	 * @param nouveauMdp2 : String
	 * @return un bool�en
	 * qui vaut vrai si ancienMdp est bien le mot de passe de c, et si nouveauMdp1 et nouveauMdp2 sont bien la m�me cha�ne
	 * faux si l'une de ces conditions n'est pas v�rifi�e
	 * @see Compte
	 */
	public static boolean verifieChampsModifMdp(Compte c,String ancienMdp,String nouveauMdp1,String nouveauMdp2){
		return(c.getMotDePasse().equals(ancienMdp) && nouveauMdp1.length()>0 && nouveauMdp1.length()<21 && nouveauMdp1.equals(nouveauMdp2));
	}
	
	
	
	public static boolean verifieSiPasDemandeInterventionSurVelo (Velo v){
		return (v.isEnPanne());
	}
	
	public static boolean verifieSiPlaceDisponibleDansStation(Station station) throws SQLException, ClassNotFoundException{
		return(DAOVelo.getVelosByLieu(station).size() < station.getCapacite());
		
	}
	
}
