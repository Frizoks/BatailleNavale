package serveur;

import ihm.*;
import java.io.*;
import java.net.*;

public class Client {

	private static Socket socket;

	public static void initClient( String port, String adresse, FrameJeu frmJeu) throws IOException {

		// Créez des flux de lecture et d'écriture pour communiquer avec le serveur
		PrintWriter out = new PrintWriter(Client.socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(Client.socket.getInputStream()));

		frmJeu.afficher();

		Thread receiveThread = new Thread(() -> {
			try {
				while ((in.readLine()).equals("TuEstLa?")) { out.print("JeSuisLa"); }
			} catch (Exception e) {
				frmJeu.deconection(1);
			}
		});
		receiveThread.start();

		try {
			receiveThread.join();
		} catch (InterruptedException e) { System.out.println("Prout"); }

		// Fermez les flux et la connexion
		out.close();
		in.close();
		Client.socket.close();	
	}

	public static boolean estlanceable(String port, String adresse, FrameJeu frmJeu) {
		try
		{
			Client.socket = new Socket(adresse, Integer.parseInt(port));
			Client.socket.setSoTimeout(10000);
			return true;
		}catch (Exception e ){return false;}
	}
}