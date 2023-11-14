/**
 * Package serveur : ce package contient toutes les classes permettant la connexion et l'envoie des données entre les deux joueurs.
 */
package serveur;

import java.io.*;
import java.net.*;

import controleur.Controleur;
import ihm.FrameHeberger;
import ihm.FrameJeu;

/**
 * La classe Serveur représente le serveur qui permet à un client de se connecter pour jouer à un jeu.
 *  
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Serveur
{

	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le port utilisé par le serveur.
	 */
	public static final int PORT = 9696;
	
	/**
	 * La sortie des données du serveur, le point d'entré des donnes pour le client.
	 */
	private static PrintWriter sortie;

	/**
	 * L'entré des données du serveur, la sortie des donnes pour le client.
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
	 * Initialise le serveur avec le contrôleur, le cadre de l'hébergement, et le cadre de jeu fournis.
	 *
	 * @param controleur    Le contrôleur du jeu.
	 * @param frameHeberger Le cadre d'hébergement du serveur.
	 * @param frameJeu      Le cadre de jeu associé.
	 */
	public static void initServeur(Controleur controleur, FrameHeberger frameHeberger, FrameJeu frameJeu)
	{
		ctrl = controleur;
		frmJeu = frameJeu;
		coup = null;

		try{
			ServerSocket serverSocket = new ServerSocket(PORT);

			Socket clientSocket = serverSocket.accept();

			sortie = new PrintWriter(clientSocket.getOutputStream(), true);
			entree = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			frmJeu.afficher();
			frameHeberger.cacher();

			Thread attendLePlt = new Thread(() -> {
				try{
					String message;
					while(true)
					{
						message = Serveur.entree.readLine();
						if(message == null)
						{
							// Le serveur s'est déconnecté
							frmJeu.deconection(1);
							// Vous pouvez prendre des mesures en conséquence ici
							break;
						}
						else
						{
							receptionPlateau(message);
							return;
						}
					}
				}catch(IOException e) {}
			});

			Thread attendToutPlacer = new Thread(() -> {
				while(true)
				{
					if(ctrl.naviresTousAjoute()) {return;}
					try {
						Thread.sleep(100);
					}catch(InterruptedException e) {}
				}
			});

			attendLePlt.start();
			attendToutPlacer.start();

			try{
				attendLePlt.join();
				attendToutPlacer.join();
			}catch(InterruptedException e) {}

			frmJeu.navireTousPlacer();

			Thread jeuThrd = new Thread(() -> {
				try{
					String message;
					Serveur.frmJeu.grilleAdvActive();
					while (Serveur.ctrl.quelqunGagne() == 'N')
					{
						message = Serveur.entree.readLine();
						if(message == null)
						{
							// Le serveur s'est déconnecté
							frmJeu.deconection(0);
							// Vous pouvez prendre des mesures en conséquence ici
						}
						else {jouerCoup(message);}
						Serveur.frmJeu.repeindre();
						if(Serveur.ctrl.quelqunGagne() != 'N') {break;}
						Serveur.frmJeu.grilleAdvActive();
						attendCoup();
						Serveur.frmJeu.grilleAdvActive();
						try{
							Thread.sleep(500);
						}catch(InterruptedException e) {}
						Serveur.envoieCoup(Serveur.coup[0], Serveur.coup[1]);
						Serveur.coup = null;
					}
					frmJeu.partieFini(Serveur.ctrl.quelqunGagne());
					return;
				}catch(IOException e) {}
			});

			jeuThrd.start();

			try{
				jeuThrd.join();
			}catch(InterruptedException e) {}

			sortie.close();
			entree.close();
			clientSocket.close();
			serverSocket.close();
		}catch(Exception e) {}
	}

	/**
	 * Attend que l'adversaire envoie un coup.
	 */
	private static void attendCoup()
	{
		while (coup == null)
		{
			try{
				Thread.sleep(100);
			}catch(InterruptedException e) {}
		}
	}

	/**
	 * Envoie le plateau de jeu au client connecté.
	 */
	public static void envoiePlateau() {sortie.println(ctrl.getPlateauJoueur().toString());}

	/**
	 * Envoie un coup au client connecté avec les coordonnées spécifiées.
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
	 * @param s Les coordonnées du coup.
	 */
	private static void jouerCoup(String s) {frmJeu.envoyerLogScoreA(ctrl.advJoue(s));}

	/**
	 * Définit les coordonnées du coup à jouer.
	 *
	 * @param tab Un tableau d'entiers contenant les coordonnées du coup.
	 */
	public static void setCoup(int[] tab) {coup = tab;}

	/**
	 * Récupère l'adresse IP de la machine.
	 *
	 * @return Adresse IP.
	 */
	public static String getAdresse()
	{
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch(UnknownHostException e) {return "Un problème est survenu";}
	}
}
