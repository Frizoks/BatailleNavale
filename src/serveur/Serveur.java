package serveur;

import ihm.*;
import java.io.*;
import java.net.*;

import controleur.Controleur;

public class Serveur {

	public static final int port = 9696;
	private static PrintWriter sortie;
	private static BufferedReader entree;

	private static Controleur ctrl;
	private static FrameJeu frmJeu;

	private static int[] coup;

	public static void initServeur(Controleur ctrl, FrameHeberger frmHeberger, FrameJeu frmJeu) {

		Serveur.ctrl = ctrl;
		Serveur.frmJeu = frmJeu;

		Serveur.coup = null;
		
		try {
			// Créez un serveur socket sur le port 9696
			ServerSocket serverSocket = new ServerSocket(Serveur.port);

			// Attendez qu'un client se connecte
			Socket clientSocket = serverSocket.accept();
			serverSocket.close();
		} catch (Exception e) { System.out.println("LépatieTOT");}

		lancerServ(frmHeberger);
	}

	public static void lancerServ(FrameHeberger frmHeberger) {
		try {
			// Créez un serveur socket sur le port 9696
			ServerSocket serverSocket = new ServerSocket(Serveur.port);

			// Attendez qu'un client se connecte
			Socket clientSocket = serverSocket.accept();

			// Créez des flux de lecture et d'écriture pour communiquer avec le client
			Serveur.sortie = new PrintWriter(clientSocket.getOutputStream(), true);
			Serveur.entree = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Serveur.frmJeu.afficher();
			frmHeberger.cacher();

			Thread jePart = new Thread(() -> {
				while (clientSocket.isConnected()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) { System.exit(-1); }
				}
				Serveur.frmJeu.deconection(0);
			});
			jePart.start();

			// Créez un thread pour la réception des messages du serveur
			Thread attendLePlt = new Thread(() -> {
				try {
					String message;
					while ((message = Serveur.entree.readLine()) != null) {
						Serveur.receptionPlateau(message);
						return;
					}
				} catch (IOException e) { System.exit(-1); }
			});
			attendLePlt.start();

			Thread attendToutPlacer = new Thread(() -> {
				while(true)
				{
					if ( Serveur.ctrl.naviresTousAjoute() )
						return;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) { System.exit(-1); }
				}
			});
			attendToutPlacer.start();

			// Attendez que les threads se terminent
			try {
				attendLePlt.join();
				attendToutPlacer.join();
			} catch (InterruptedException e) { System.exit(-1); }

			if ( !Serveur.ctrl.naviresTousAjoute() )
				System.exit(0);
			
			frmJeu.navireTousPlacer();

			Runnable coup = new Runnable() {
				@Override
				public void run() {
					while ( Serveur.coup == null)
					{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) { System.exit(-1); }
					}
					return;
				}
			};

			Runnable jeu = new Runnable() {
				@Override
				public void run() {
					try {
						String message;
						while (Serveur.ctrl.quelqunGagne() == 'N') {
							Serveur.frmJeu.grilleAdvActive();
							while ((message = Serveur.entree.readLine()) != null) {
								Serveur.jouerCoup(message);
								break;
							}
							Serveur.frmJeu.repeindre();
							if (Serveur.ctrl.quelqunGagne() != 'N')
								return;
							Serveur.frmJeu.grilleAdvActive();
							Thread coupThrd = new Thread(coup);
							coupThrd.start();
							coupThrd.join();
							Serveur.envoieCoup(Serveur.coup[0],Serveur.coup[1]);
							Serveur.coup = null;
						}
						return;
					} catch (IOException e) { System.exit(-1); } catch (InterruptedException e) { System.exit(-1); }
				}
			};

			Thread jeuThrd = new Thread(jeu);
			jeuThrd.start();

			try {
				jeuThrd.join();
			} catch (InterruptedException e) {  }

			// Fermez les flux et la connexion du client
			Serveur.sortie.close();
			Serveur.entree.close();
			clientSocket.close();
			serverSocket.close();
		} catch (Exception e) { System.exit(-1); }
	}

	public	static void envoiePlateau	 (			  ) { Serveur.sortie.println(Serveur.ctrl.getPlateauJoueur().toString());	}
	public	static void envoieCoup		 (int x, int y) { Serveur.sortie.println(x + ":" + y);									}
	
	private static void receptionPlateau (String s	  ) { Serveur.ctrl.ajouterPlateauAdv(s);									}
	private static void jouerCoup		 (String s	  ) { Serveur.frmJeu.envoyerLogScoreA(Serveur.ctrl.advJoue(s));				}
	public	static void setCoup			 (int[] tab	  ) { Serveur.coup = tab;													}
}
