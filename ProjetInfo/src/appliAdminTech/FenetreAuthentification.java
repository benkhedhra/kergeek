package appliAdminTech;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

				public class FenetreAuthentification extends JFrame {

					Container c = getContentPane();

					/* Positionner l'Image de fond */
					JPanel imagePanel = new JPanel() {

						public void paint(Graphics g) {
							try {
								// TODO Attention adresse de l'image
								BufferedImage image = ImageIO.read(new File("C:\\Users\\Maëlle\\workspace\\LievreTortue\\src\\ressources\\lievreTortueihm.JPG"/*"P:\\Bureau\\Workspace\\Lievre_Tortue\\src\\resources\\lievreTortueihm.JPG"*/));
								g.drawImage(image, 0, 0, null);
							}
							catch (Exception e){System.out.println("Erreur lors de l'ouverture de l'image");}
							
							
							
							
						}
						
						
						
						
						
					// attribut utilisé pour la sérialisation (ne pas s en occuper)
					private static final long serialVersionUID = 8412804348801208994L;

					/*
					 * Rappel : JLabel = Simple étiquette pour écrire du texte 
					 * JTextField = champ de saisie de texte
					 * JPasswordField = idem mais avec texte saisi non affiché
					 * JButton = bouton (si si c'est vrai)
					 */
					JLabel labelPseudo;
					JTextField fieldPseudo;
					JLabel labelPassword;
					JPasswordField fieldPassword;
					JButton boutonValider;

					public FenetreAuthentification(String titre) {
						super(titre);

						/* juste pour que le programme s'arrete bien quand vous cliquer sur la
						 * petite croix en haut de la fenetre */
						setDefaultCloseOperation(DISPOSE_ON_CLOSE);

						/*définition d'un moyen de disposer les composants*/
						this.setLayout(new GridLayout(3, 2));
						labelPseudo = new JLabel("login");
						fieldPseudo = new JTextField();
						labelPassword = new JLabel("mot de passe");
						fieldPassword = new JPasswordField();

						JButton boutonValider = new JButton("valider");

						setSize(400, 250);
						//ajout des composants à la fenetre
						add(labelPseudo);
						add(fieldPseudo);
						add(labelPassword);
						add(fieldPassword);
						add(boutonValider);

					}

					//on vérifie que les paramètres de connexion sont bons
					protected void authentifier(String pseudo, String motDePasse)
					throws SQLException, ClassNotFoundException {
						UtilitaireSQL.testerAuthent(pseudo,motDePasse);
					}

					//si testerAuthent = -1 : nouvelle fenêtre avec "d'authentification" mais sinon la même
					//sinon ouvrir la connexion

					//si testerAuthent=1 : on renvoie sur la fenêtre MenuPrincipalAdmin
					//si testerAuthent=2 : on renvoie sur la fenêtre MenuPrincipalTech

				}
