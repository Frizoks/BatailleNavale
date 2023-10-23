/**
 * Package serveur : ce package contient toutes les classes permettant la connexion et l'envoie des données entre les deux joueurs.
 */
package serveur;

import controleur.Controleur;
import ihm.FrameJeu;

import java.io.*;
import java.net.*;

/**
 * La classe Client représente un client qui se connecte à un serveur pour jouer à un jeu.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Client
{

	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le socket qui permet de se connecter au serveur
	 */
	private static Socket socket;

	/**
	 * La sortie des données du client, le point d'entré des donnes pour le serveur.
	 */
	private static PrintWriter sortie;

	/**
	 * L'entré des données du client, la sortie des donnes pour le serveur.
	 */
	private static BufferedReader entree;

	/**
	 * Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	private static Controleur ctrl;

	/**
	 * La frame qui gêre tout l'affichage de la partie.
	 */
	private static FrameJeu frmJeu;

	/**
	 * Le coup reçu de l'adversaire.
	 */
	private static int[] coup;

	/**
	 * Initialise le client avec le contrôleur, le port, l'adresse, et le cadre de jeu fournis.
	 *
	 * @param controleur Le contrôleur du jeu.
	 * @param port       Le port pour la connexion au serveur.
	 * @param adresse    L'adresse IP du serveur.
	 * @param frameJeu   Le cadre de jeu associé.
	 */
	public static void initClient(Controleur controleur, String port, String adresse, FrameJeu frameJeu)
	{
		try {
			ctrl = controleur;
			frmJeu = frameJeu;
			coup = null;

			try (PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
				 BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

				Client.sortie = sortie;
				Client.entree = entree;

				frmJeu.afficher();

				Thread attendLePlt = new Thread(() -> {
					try {
						String message;
						while (true) {
							message = Client.entree.readLine();
							if (message == null) {
								// Le serveur s'est déconnecté
								frmJeu.deconection(1);
								// Vous pouvez prendre des mesures en conséquence ici
								break;
							} else {
								receptionPlateau(message);
								return;
							}
						}
					} catch (IOException e) {
						frmJeu.deconection(1);
					}
				});

				Thread attendToutPlacer = new Thread(() -> {
					int tempsAttente = 0;
					while (true) {
						if (ctrl.naviresTousAjoute()) { return; }
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) { }
						if ( ++tempsAttente > 10000 )
							frmJeu.deconection(1);
					}
				});

				attendLePlt.start();
				attendToutPlacer.start();

				try {
					attendLePlt.join();
					attendToutPlacer.join();
				} catch (InterruptedException e) {
				}

				frmJeu.navireTousPlacer();

				Thread jeuThrd = new Thread(() -> {
					try {
						String message;
						while (Client.ctrl.quelqunGagne() == 'N') {
							attendCoup();
							Client.envoieCoup(Client.coup[0], Client.coup[1]);
							Client.coup = null;
							if (Client.ctrl.quelqunGagne() != 'N') {
								break;
							}
							Client.frmJeu.grilleAdvActive();

							message = Client.entree.readLine();
							if (message == null) {
								// Le serveur s'est déconnecté
								frmJeu.deconection(1);
								// Vous pouvez prendre des mesures en conséquence ici
							} else {
								jouerCoup(message);
							}

							Client.frmJeu.repeindre();
							Client.frmJeu.grilleAdvActive();
						}
						frmJeu.partieFini(Client.ctrl.quelqunGagne());
						return;
					} catch (IOException e) {
					}
				});

				jeuThrd.start();

				try {
					jeuThrd.join();
				} catch (InterruptedException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attend que l'adversaire envoie un coup.
	 */
	private static void attendCoup() {
		while (coup == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Vérifie si le client peut se connecter au serveur à l'adresse et au port spécifiés.
	 *
	 * @param adresse L'adresse IP du serveur.
	 * @param port    Le port pour la connexion au serveur.
	 * @return `true` si la connexion est possible, sinon `false`.
	 */
	public static boolean estLanceable(String adresse, String port)
	{
		try {
			Client.socket = new Socket(adresse, Integer.parseInt(port));
			return true;
		}catch(IOException e) {return false;}
	}

	/**
	 * Envoie le plateau de jeu au serveur.
	 */
	public static void envoiePlateau() {sortie.println(ctrl.getPlateauJoueur().toString());}

	/**
	 * Envoie un coup au serveur avec les coordonnées spécifiées.
	 *
	 * @param x La coordonnée en X du coup.
	 * @param y La coordonnée en Y du coup.
	 */
	public static void envoieCoup(int x, int y) {sortie.println(x + ":" + y);}

	/**
	 * Reçoit le plateau du serveur pour l'affecter à l'adversaire.
	 *
	 * @param s Le toString du plateau.
	 */
	private static void receptionPlateau(String s) {ctrl.ajouterPlateauAdv(s);}

	/**
	 * Reçoit un coup du serveur avec les coordonnées spécifiées pour pouvoir le jouer.
	 *
	 * @param s Les coordonnée du coup.
	 */
	private static void jouerCoup(String s) {frmJeu.envoyerLogScoreA(ctrl.advJoue(s));}

	/**
	 * Définit les coordonnées du coup à jouer.
	 *
	 * @param tab Un tableau d'entiers contenant les coordonnées du coup.
	 */
	public static void setCoup(int[] tab) {coup = tab;}
}
