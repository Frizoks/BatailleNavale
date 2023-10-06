package serveur;

import controleur.Controleur;
import ihm.FrameJeu;

import java.io.*;
import java.net.*;

public class Client
{
	private static Socket socket;
	private static PrintWriter sortie;
	private static BufferedReader entree;

	private static Controleur ctrl;
	private static FrameJeu frmJeu;

	private static int[] coup;

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
							}
							else {
								receptionPlateau(message);
								return;
							}
						}
					} catch (IOException e) { frmJeu.deconection(1); }
				});

				Thread attendToutPlacer = new Thread(() -> {
					while (true) {
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
					try {
						String message;
						while (Client.ctrl.quelqunGagne() == 'N') {
							attendCoup();
							Client.envoieCoup(Client.coup[0],Client.coup[1]);
							Client.coup = null;
							if (Client.ctrl.quelqunGagne() != 'N') {break;}
							Client.frmJeu.grilleAdvActive();

							message = Client.entree.readLine();
							if (message == null) {
								// Le serveur s'est déconnecté
								frmJeu.deconection(1);
								// Vous pouvez prendre des mesures en conséquence ici
							}
							else {jouerCoup(message);}
							
							Client.frmJeu.repeindre();
							Client.frmJeu.grilleAdvActive();
						}
						frmJeu.partieFini(Client.ctrl.quelqunGagne());
						return;
					} catch (IOException e) {  }
				});

				jeuThrd.start();

				try {
					jeuThrd.join();
				} catch (InterruptedException e) { }
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private static void attendCoup() {
		while(coup == null) 
		{
			try{
				Thread.sleep(100);
			}catch(InterruptedException e) {}
		}
	}

	public static boolean estLanceable(String adresse, String port) {
		try{
			Client.socket = new Socket(adresse, Integer.parseInt(port));
			return true;
		}catch(IOException e) {return false;}
	}

	public static void envoiePlateau() {sortie.println(ctrl.getPlateauJoueur().toString());}
	public static void envoieCoup(int x, int y) {sortie.println(x + ":" + y);}
	private static void receptionPlateau(String s) {ctrl.ajouterPlateauAdv(s);}
	private static void jouerCoup(String s) {frmJeu.envoyerLogScoreA(ctrl.advJoue(s));}
	public static void setCoup(int[] tab) {coup = tab;}
}
