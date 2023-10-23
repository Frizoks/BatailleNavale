/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import controleur.Controleur;
import serveur.Client;
import serveur.Serveur;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * La classe FrameJeu représente la fenêtre du jeu de Bataille Navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class FrameJeu extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	private Controleur ctrl;

	/**
	 * Panel pour afficher le plateau de jeu.
	 */
	protected PanelJeu pnlJeu;

	/**
	 * Panel inférieur pour afficher des informations ou des actions.
	 */
	JPanel pnlBas;

	/**
	 * Image de l'icône de la fenêtre.
	 */
	private Image icon;

	/**
	 * Indique si la fenêtre du jeu est en mode serveur ('S') ou client ('C').
	 */
	private char estServeur;

	/**
	 * Thread pour gérer la communication serveur/client.
	 */
	private static Thread proceServ;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe FrameJeu.
	 *
	 * @param ctrl       Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 * @param estServeur Indique si la fenêtre du jeu est en mode serveur ('S') ou client ('C').
	 */
	public FrameJeu(Controleur ctrl, char estServeur)
	{
		this.ctrl = ctrl;
		this.estServeur = estServeur;
		FrameJeu.proceServ = null;

		this.icon = new ImageIcon(this.getClass().getResource("image/BateauIcon.png")).getImage();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int l = (tailleEcran.width - 700) / 2;
		int h = (tailleEcran.height - 700) / 2;

		this.setSize(700, 700);
		this.setLocation(l, h);
		this.setTitle("Bataille Navale");

		this.setLayout(new GridLayout(2, 1));

		this.pnlJeu = new PanelJeu(this);
		this.pnlBas = new PanelSelectBateau(this);

		this.add(this.pnlJeu);
		this.add(this.pnlBas);

		this.setIconImage(icon);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(false);

		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				try{
					FrameJeu.proceServ.interrupt();
				}catch(Exception ex) {}
			}
		});
	}

	/**
	 * Ferme la fenêtre de jeu.
	 */
	public void cacher() {this.dispose();}

	/**
	 * Établit une connexion client avec le serveur distant.
	 *
	 * @param port  Le port pour la connexion client.
	 * @param adresse L'adresse du serveur distant.
	 * @param ctrl   Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 * @return true si la connexion a réussi, sinon false.
	 */
	public static boolean connection(String port, String adresse, Controleur ctrl)
	{
		FrameJeu frmJeu = new FrameJeu(ctrl, 'C');

		FrameJeu.proceServ = new Thread(() -> {
			Client.initClient(ctrl, port, adresse, frmJeu);
		});

		if (Client.estLanceable(adresse, port))
		{
			FrameJeu.proceServ.start();
			return true;
		}
		System.out.println(adresse);
		return false;
	}

	/**
	 * Établit une connexion serveur pour héberger une partie.
	 *
	 * @param frmHeberger La fenêtre d'hébergement.
	 * @param ctrl         Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	public static void connection(FrameHeberger frmHeberger, Controleur ctrl)
	{
		FrameJeu frmJeu = new FrameJeu(ctrl, 'S');

		FrameJeu.proceServ = new Thread(() -> {
			Serveur.initServeur(ctrl, frmHeberger, frmJeu);
		});
		FrameJeu.proceServ.start();
	}

	/**
	 * Gère la déconnexion du client ou la fermeture du serveur.
	 *
	 * @param source 0 pour la déconnexion du client, 1 pour la fermeture du serveur.
	 */
	public void deconection(int source)
	{
		JOptionPane.showMessageDialog(this, source == 0 ? "Le client s'est déconnecté" : "Le Serveur est fermé", "Erreur", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	/**
	 * Affiche un message de fin de partie et indique le gagnant.
	 *
	 * @param c 'J' si le joueur a gagné, 'P' si l'adversaire a gagné.
	 */
	public void partieFini(char c)
	{
		this.pnlJeu.repaint();
		this.pnlBas.repaint();

		if(c == 'J') 
		{
			((PanelMessage) this.pnlBas).setAQuiLeTour(" La partie est finie  ( gagnée ) ");
			try {
				// Open an audio input stream.
				File file = new File("./image/win.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				clip.start();
			}catch(Exception e){ e.printStackTrace();}
		}
		else {((PanelMessage) this.pnlBas).setAQuiLeTour(" La partie est finie  ( perdue ) ");}
		JOptionPane.showMessageDialog(this, c == 'J' ? "Vous avez gagné" : "L'adversaire a gagné", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Récupère l'icône de la fenêtre.
	 *
	 * @return L'icône de la fenêtre.
	 */
	public Image getIcon() {return this.icon;}

	/**
	 * Affiche la fenêtre de jeu.
	 */
	public void afficher() {this.setVisible(true);}

	/**
	 * Récupère le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 *
	 * @return Le contrôleur du jeu.
	 */
	public Controleur getCtrl() {return this.ctrl;}

	/**
	 * Récupère le mode serveur/client de la fenêtre.
	 *
	 * @return 'S' pour le mode serveur, 'C' pour le mode client.
	 */
	public char estServeur() {return this.estServeur;}

	/**
	 * Active la grille de l'adversaire pour effectuer une action.
	 */
	public void grilleAdvActive() {this.pnlJeu.grilleAdvActive();}

	/**
	 * Indique que les navires du joueur ont été placés.
	 */
	public void navireJoueurPlace()
	{
		this.remove(this.pnlBas);

		if (!this.ctrl.estMulti()) {
			this.pnlBas = new PanelMessage(this);
			this.pnlJeu.grilleAdvActive();
		} else {
			this.pnlBas = new JPanel();
			this.pnlBas.setBackground(Color.LIGHT_GRAY);
			this.pnlBas.add(new JLabel("En attente de votre adversaire"));
			if (this.estServeur == 'S') {
				Serveur.envoiePlateau();
			}
			if (this.estServeur == 'C') {
				Client.envoiePlateau();
			}
		}
		this.add(this.pnlBas);
		this.repaint();
		this.revalidate();
	}

	/**
	 * Indique que tous les navires ont été placés et la partie peut commencer.
	 */
	public void navireTousPlacer()
	{
		this.remove(this.pnlBas);
		this.pnlBas = new PanelMessage(this);
		this.add(this.pnlBas);
		this.repaint();
		this.revalidate();
		this.pnlJeu.grilleAdvActive();
	}

	/**
	 * Définit la case ciblée par un coup.
	 *
	 * @param tab Tableau contenant les coordonnées de la case ciblée.
	 */
	public void setCoup(int[] tab)
	{
		if (this.estServeur == 'S') {Serveur.setCoup(tab);}
		if (this.estServeur == 'C') {Client.setCoup(tab);}
	}

	/**
	 * Redessine les éléments du jeu.
	 */
	public void repeindre()
	{
		this.pnlJeu.repaint();
		this.pnlBas.repaint();
	}

	/**
	 * Envoie un log de score à l'adversaire.
	 *
	 * @param coup 'A' si le joueur a touché un navire adverse, 'R' s'il a raté.
	 */
	public void envoyerLogScoreA(char coup) {this.pnlJeu.envoyerLogScoreA(coup);}
}
