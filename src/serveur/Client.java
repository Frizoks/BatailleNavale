package serveur;

import ihm.*;
import java.io.*;
import java.net.*;

import controleur.Controleur;

public class Client {

	private static Socket socket;
	private static PrintWriter sortie;
	private static BufferedReader entree;

	private static Controleur ctrl;
	private static FrameJeu frmJeu;

	private static int[] coup;

	public static void initClient( Controleur ctrl, String port, String adresse, FrameJeu frmJeu) {
		try {
            Client.ctrl = ctrl;
            Client.frmJeu = frmJeu;

            Client.coup = null;

            Client.socket = new Socket(adresse, Integer.parseInt(port));

            // Créez des flux de lecture et d'écriture pour communiquer avec le Client
            try (PrintWriter sortie = new PrintWriter(Client.socket.getOutputStream(), true);
                 BufferedReader entree = new BufferedReader(new InputStreamReader(Client.socket.getInputStream()))) {

                Client.sortie = sortie;
                Client.entree = entree;

                Client.frmJeu.afficher();

				// Créez un thread pour la réception des messages du Client
				Thread attendLePlt = new Thread(() -> {
					try {
						String message;
						while ((message = Client.entree.readLine()) != null) {
								Client.receptionPlateau(message);
								return;
						}
					} catch (IOException e) { System.exit(-1); }
				});

				Thread attendToutPlacer = new Thread(() -> {
					while(true)
					{
						if ( Client.ctrl.naviresTousAjoute() )
							return;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) { System.exit(-1); }
					}
				});

				attendLePlt.start();
				attendToutPlacer.start();

				// Attendez que les threads se terminent
				try {
					attendLePlt.join();
					attendToutPlacer.join();
				} catch (InterruptedException e) { System.exit(-1); }

				if ( !Client.ctrl.naviresTousAjoute() )
					System.exit(0);
				
				Client.frmJeu.navireTousPlacer();

				Runnable coup = new Runnable() {
					@Override
					public void run() {
						while ( Client.coup == null)
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
							while (Client.ctrl.quelqunGagne() == 'N') {
								Thread coupThrd = new Thread(coup);
								coupThrd.start();
								coupThrd.join();
								Client.envoieCoup(Client.coup[0],Client.coup[1]);
								Client.coup = null;
								if (Client.ctrl.quelqunGagne() != 'N')
									return;
								Client.frmJeu.grilleAdvActive();
								while ((message = Client.entree.readLine()) != null) {
										Client.jouerCoup(message);
										break;
								}
								Client.frmJeu.repeindre();
								Client.frmJeu.grilleAdvActive();
							}
							return;
						} catch (IOException e) { System.exit(-1); } catch (InterruptedException e) { System.exit(-1); }
					}
				};

				Thread jeuThrd = new Thread(jeu);
				jeuThrd.start();

				try {
					jeuThrd.join();
				} catch (InterruptedException e) { }

			} // Les flux seront fermés automatiquement à la fin du bloc try

		} catch (IOException e) { }
	}

	public static boolean estLanceable(String port, String adresse, FrameJeu frmJeu) {
        try (Socket testSocket = new Socket(adresse, Integer.parseInt(port))) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	public	static void envoiePlateau	 (			  ) { Client.sortie.println(Client.ctrl.getPlateauJoueur().toString()); }
	public	static void envoieCoup		 (int x, int y) { Client.sortie.println(x + ":" + y);								}

	private static void receptionPlateau (String s	  ) { Client.ctrl.ajouterPlateauAdv(s);									}
	private static void jouerCoup		 (String s	  ) { Client.frmJeu.envoyerLogScoreA(Client.ctrl.advJoue(s));			}
	public	static void setCoup			 (int[] tab	  ) { Client.coup = tab;												}
}