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
 * UtilitaireIhm est la classe regroupant les m�thodes servant � v�rifier la coh�rence des champs entr�s par rapport � un contexte donn� par la fen�tre ihm en cours
 * @author KerGeek
 */

public class UtilitaireIhm {
	
	/* D�finition des objets servant � l'ensemble de l'ihm
	 * polices, transparence, dimensions
	 */
	
	// d�finition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 24);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,16);

	// d�finition de la couleur transparence pour rendre les panels transparents et continuer � voir l'image de fond
	public static final Color TRANSPARENCE = new Color(0,0,0,0);
	public static final Color FONDBOUTON = new Color(170,200,220,50);
	
	/**
	 * V�rifie si un entier correspond bien � un type de compte r�pertori�
	 * @param type : un entier correspondant au type du {@link Compte} entr� au moment de la cr�ation
	 * 
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
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
	 */
	public static boolean verifieChampsCreationAdmin(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * V�rifie si un entier et une String peuvent correspondre � des param�tres ad�quats pour construire un {@link Technicien}
	 * @param type : int
	 * @param adresseEMail : String
	 * @return un bool�en
	 * qui vaut vrai si type correspond � un type de compte existant et que adresseEMail v�rifie les contraintes du MLD
	 * faux sinon
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
	 */
	public static boolean verifieChampsCreationTech(int type,String adresseEMail){
		return verifieTypeCreationCompte(type) && adresseEMail.length()>0 && adresseEMail.length()<51;
	}

	/**
	 * V�rifie si un entier et quatre String peuvent correspondre � des param�tres ad�quats pour construire un {@link Utilisateur}
	 * @param type : int
	 * @param adresseEMail : String
	 * @param nom : String
	 * @param prenom : String
	 * @param adressePostale : String
	 * @return un bool�en
	 * qui vaut vrai si type correspond � un type de compte existant et que adresseEMail, nom, prenom et adressePostale v�rifie les contraintes du MLD
	 * faux sinon
	 * @see UtilitaireIhm#verifieTypeCreationCompte(int)
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
		return(c.getMotDePasse().equals(ancienMdp) && nouveauMdp1.length()>5 && nouveauMdp1.length()<21 && nouveauMdp1.equals(nouveauMdp2));
	}

	/**
	 * V�rifie s'il n'existe pas de demande d'intervention sur un v�lo
	 * @param v : Velo
	 * @return un bool�en
	 * qui vaut vrai si une demande d'intevention existe sur le v�lo, ie si celui-ci est d�clar� en panne (attribut enPanne)
	 * @see Velo
	 */
	public static boolean verifieSiPasDemandeInterventionSurVelo (Velo v){
		return (v.isEnPanne());
	}

	/**
	 * V�rifie s'il y a encore au moins une place disponible dans une station
	 * @param station
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @return un bool�en
	 * qui vaut vrai si le nombre de v�los dans station est inf�rieure strictement � sa capacit�
	 * @see DAOVelo
	 */
	public static boolean verifieSiPlaceDisponibleDansStation(Station station) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		return(DAOVelo.getVelosByLieu(station).size() < station.getCapacite());

	}


	/**
	 * V�rifie si des v�los peuvent �tre assign�s � une station
	 * @param ancienneliste
	 * la liste des identifiants des v�los que l'on veut assigner
	 * @param lieu 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @return une ArrayList<String>
	 * de la m�me taille que ancienneliste
	 * si le v�lo correspondant au i-�me identifiant de ancienneliste est assignable, la nouvelle liste a pour i-�me �l�ment ce m�me identifiant
	 * sinon le i-�me �l�ment de la nouvelle liste est une cha�ne vide
	 * @see DAOVelo
	 */
	public static ArrayList<String> verifieSiVelosPeuventEtreAssignes(ArrayList<String> ancienneliste, Lieu lieu) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		ArrayList<String> nouvelleListe = new ArrayList<String>();
		List<Velo> listeVelosDansLieu = DAOVelo.getVelosByLieu(lieu);
		List<String> listeIdVelosDansLieu = new ArrayList<String>();
		for (Velo velo : listeVelosDansLieu){
			listeIdVelosDansLieu.add(velo.getId());
		}
		//System.out.println("v�los pouvant �tre assign�s : " + listeIdVelosDansLieu.toString());
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
			mdpChar = null;//pour augmenter la s�curit� de l'application
		return mdp;
	}



}