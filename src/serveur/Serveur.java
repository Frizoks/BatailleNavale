package serveur;

import java.io.*;
import java.net.*;

import controleur.Controleur;
import ihm.FrameHeberger;
import ihm.FrameJeu;

public class Serveur {

	public static final int PORT = 9696;
	private static PrintWriter sortie;
	private static BufferedReader entree;

	private static Controleur ctrl;
	private static FrameJeu frmJeu;

	private static int[] coup;
	
	public static void initServeur(Controleur controleur, FrameHeberger frameHeberger, FrameJeu frameJeu) {
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
					while (true) {
						message = Serveur.entree.readLine();
						if (message == null) {
							// Le serveur s'est déconnecté
							frmJeu.deconection(1);
							// Vous pouvez prendre des mesures en conséquence ici
							break;
						}
						else {
							receptionPlateau(message);
							return;
						}
					}
				}catch(IOException e) {}
			});

			Thread attendToutPlacer = new Thread(() -> {
				while (true) 
				{
					if (ctrl.naviresTousAjoute()) {return;}
					try{
						Thread.sleep(100);
					}catch(InterruptedException e) {}
				}
			});

			attendLePlt.start();
			attendToutPlacer.start();

			try {
				attendLePlt.join();
				attendToutPlacer.join();
			} catch (InterruptedException e) { }

			frmJeu.navireTousPlacer();

			Thread jeuThrd = new Thread(() -> {
				try{
					String message;
					while (Serveur.ctrl.quelqunGagne() == 'N') {
						Serveur.frmJeu.grilleAdvActive();
						message = Serveur.entree.readLine();
						if (message == null) {
							// Le serveur s'est déconnecté
							frmJeu.deconection(0);
							// Vous pouvez prendre des mesures en conséquence ici
						}
						else { jouerCoup(message); }
						Serveur.frmJeu.repeindre();
						if (Serveur.ctrl.quelqunGagne() != 'N') {break;}
						Serveur.frmJeu.grilleAdvActive();
						attendCoup();
						Serveur.envoieCoup(Serveur.coup[0],Serveur.coup[1]);
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

	private static void attendCoup() {
		while (coup == null)
		{
			try{
				Thread.sleep(100);
			}catch(InterruptedException e) {}
		}
	}

	public static void envoiePlateau() {sortie.println(ctrl.getPlateauJoueur().toString());}
	public static void envoieCoup(int x, int y) {sortie.println(x + ":" + y);}
	private static void receptionPlateau(String s) {ctrl.ajouterPlateauAdv(s);}
	private static void jouerCoup(String s) {frmJeu.envoyerLogScoreA(ctrl.advJoue(s));}
	public static void setCoup(int[] tab) {coup = tab;}
}
