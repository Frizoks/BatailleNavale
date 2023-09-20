package serveur;

import ihm.*;
import java.io.*;
import java.net.*;

public class Serveur {

	public static final int port = 9696;

	public static void initServeur(FrameHeberger frmHeberger, FrameJeu frmJeu) throws IOException {
		// Créez un serveur socket sur le port 12345
		ServerSocket serverSocket = new ServerSocket(Serveur.port);

		// Attendez qu'un client se connecte
		Socket clientSocket = serverSocket.accept();

		// Créez des flux de lecture et d'écriture pour communiquer avec le client
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		frmJeu.afficher();
		frmHeberger.cacher();
		

		Thread receiveThread = new Thread(() -> {
			try {
				while ((in.readLine()).equals("JeSuisLa")) {
					out.print("TuEstLa?");
				}
			} catch (Exception e) { frmJeu.deconection(0); }
		});
		receiveThread.start();

		// Attendez que le thread de réception se termine (client déconnecté)
		try {
			receiveThread.join();
		} catch (InterruptedException e) {}

		// Fermez les flux et la connexion du client
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}
