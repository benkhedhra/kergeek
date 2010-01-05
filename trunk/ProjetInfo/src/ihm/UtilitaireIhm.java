package ihm;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPasswordField;

import metier.Compte;
import metier.Lieu;
import metier.Station;
import metier.Technicien;
import metier.Utilisateur;
import metier.Velo;

/** 
 * UtilitaireIhm est la classe regroupant les méthodes servant à vérifier la cohérence des champs entrés par rapport à un contexte donné par la fenêtre ihm en cours
 * @author KerGeek
 */

public class UtilitaireIhm {
	
	/* Définition des objets servant à l'ensemble de l'ihm
	 * polices, transparence, dimensions
	 */
	
	// définition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 24);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,16);

	// définition de la couleur transparence pour rendre les panels transparents et continuer à voir l'image de fond
	public static final Color TRANSPARENCE = new Color(0,0,0,0);
	public static final Color FONDBOUTON = new Color(170,200,220,50);
	
	/**
	 * Vérifie si un entier correspond bien à un type de compte répertorié
	 * @param type : un entier correspondant au type du {@link Compte} entré au moment de la création
	 * 
	 * @return un booléen
	 * qui vaut vrai si type est bien un type de compte existant
	 * faux sinon
	 */
	public static boolean verifieTypeCreationCompte(int type){
		return((type==Compte.TYPE_ADMINISTRATEUR || type==Compte.TYPE_TECHNICIEN ||type==Compte.TYPE_UTILISATEUR));
	}


	/**
	 * Vérifie si un entier et une String peuvent correspondre à des paramètres adéquats pour construire un administrateur
	 * @param type : int
	 * @param adresseEMail : String
	 * @return un booléen
	 * qui vaut vrai si type correspond à un type de compte existant et que adresseEMail vérifie les contraintes du MLD
	 * faux sinon
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
	 */
	public static boolean verifieChampsCreationAdmin(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * Vérifie si un entier et une String peuvent correspondre à des paramètres adéquats pour construire un {@link Technicien}
	 * @param type : int
	 * @param adresseEMail : String
	 * @return un booléen
	 * qui vaut vrai si type correspond à un type de compte existant et que adresseEMail vérifie les contraintes du MLD
	 * faux sinon
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
	 */
	public static boolean verifieChampsCreationTech(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * Vérifie si un entier et quatre String peuvent correspondre à des paramètres adéquats pour construire un {@link Utilisateur}
	 * @param type : int
	 * @param adresseEMail : String
	 * @param nom : String
	 * @param prenom : String
	 * @param adressePostale : String
	 * @return un booléen
	 * qui vaut vrai si type correspond à un type de compte existant et que adresseEMail, nom, prenom et adressePostale vérifie les contraintes du MLD
	 * faux sinon
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
	 */
	public static boolean verifieChampsCreationUtil(int type,String adresseEMail,String nom,String prenom,String adressePostale){
		return (verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51 && prenom.length()>0 && prenom.length()<21 && nom.length()>0 && nom.length()<21 && adressePostale.length()>0 && adressePostale.length()<251);
	}

	/**
	 * Vérifie si la modification de mot de passe est possible
	 * @param c : Compte
	 * @param ancienMdp : String
	 * @param nouveauMdp1 : String
	 * @param nouveauMdp2 : String
	 * @return un booléen
	 * qui vaut vrai si ancienMdp est bien le mot de passe de c, et si nouveauMdp1 et nouveauMdp2 sont bien la même chaîne
	 * faux si l'une de ces conditions n'est pas vérifiée
	 * @see Compte
	 */
	public static boolean verifieChampsModifMdp(Compte c,String ancienMdp,String nouveauMdp1,String nouveauMdp2){
		return(c.getMotDePasse().equals(ancienMdp) && nouveauMdp1.length()>5 && nouveauMdp1.length()<21 && nouveauMdp1.equals(nouveauMdp2));
	}

	/**
	 * Vérifie s'il n'existe pas de demande d'intervention sur un vélo
	 * @param v : Velo
	 * @return un booléen
	 * qui vaut vrai si une demande d'intevention existe sur le vélo, ie si celui-ci est déclaré en panne (attribut enPanne)
	 * @see Velo
	 */
	public static boolean verifieSiPasDemandeInterventionSurVelo (Velo v){
		return (v.isEnPanne());
	}

	/**
	 * Vérifie s'il y a encore au moins une place disponible dans une station
	 * @param station
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @return un booléen
	 * qui vaut vrai si le nombre de vélos dans station est inférieure strictement à sa capacité
	 * @see DAOVelo
	 */
	public static boolean verifieSiPlaceDisponibleDansStation(Station station) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		return(DAOVelo.getVelosByLieu(station).size() < station.getCapacite());

	}


	/**
	 * Vérifie si des vélos peuvent être assignés à une station
	 * @param ancienneliste
	 * la liste des identifiants des vélos que l'on veut assigner
	 * @param lieu 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @return une ArrayList<String>
	 * de la même taille que ancienneliste
	 * si le vélo correspondant au i-ème identifiant de ancienneliste est assignable, la nouvelle liste a pour i-ème élément ce même identifiant
	 * sinon le i-ème élément de la nouvelle liste est une chaîne vide
	 * @see DAOVelo
	 */
	public static ArrayList<String> verifieSiVelosPeuventEtreAssignes(ArrayList<String> ancienneliste, Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		ArrayList<String> nouvelleListe = new ArrayList<String>();
		List<Velo> listeVelosDansLieu = DAOVelo.getVelosByLieu(lieu);
		List<String> listeIdVelosDansLieu = new ArrayList<String>();
		for (Velo velo : listeVelosDansLieu){
			listeIdVelosDansLieu.add(velo.getId());
		}
		//System.out.println("vélos pouvant être assignés : " + listeIdVelosDansLieu.toString());
		for (String idVelo : ancienneliste){
			if (DAOVelo.existe(idVelo)){
				if(listeIdVelosDansLieu.contains(idVelo)){
					nouvelleListe.add(idVelo);
					System.out.println(idVelo + " : OK");
				}
				else{
					nouvelleListe.add("");
					System.out.println( idVelo + " : incompatible");
				}
			}
			else{
				nouvelleListe.add("");
			}
		}
		return nouvelleListe;
	}

	
	//TODO javadoc
	public static boolean verifieParametresAssignation(int nbVelos,Lieu l){
		return (nbVelos>0 && nbVelos<=l.getCapacite());
	}

	
	
	//TODO javadoc
	public static String obtenirMotDePasse(JPasswordField motDePasseARemplir){
		String mdp = "";
			char[] mdpChar = motDePasseARemplir.getPassword();
			for(char c : mdpChar){
				mdp += c;
			}
			mdpChar = null;//pour augmenter la sécurité de l'application
		return mdp;
	}



}