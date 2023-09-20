package ihm;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controleur.*;
import serveur.Client;
import serveur.Serveur;

public class FrameJeu extends JFrame
{
	
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private Controleur  ctrl;

	/**
	 * Un Panel pour afficher des composants
	 */
	private JPanel  pnlJeu;

	/**
	 * Un Panel pour afficher des composants
	 */
	private Image  icon;

	/**
	 * Un Panel pour afficher des composants
	 */
	private static boolean  test;

	/**
	 * Bouton pour accéder au jeu en mode 1 joueur
	 */
	private static Thread    frmAttThread ;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameJeu ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.icon        = Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/BateauIcon.png" );
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 700 ) / 2;
		int h = ( tailleEcran.height -  550 ) / 2;

		this.setSize     ( 700 , 550   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "Bataille Navale" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.pnlJeu = new PanelJeu(this);

		this.add(this.pnlJeu);

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( false         );
	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher ( ) { this.dispose ( ); }

	public static boolean connection (String port, String adresse )
	{
		FrameJeu frmJeu = new FrameJeu(null);

		Thread lancerFrame = new Thread(() -> {
			try {
				Client.initClient(port, adresse, frmJeu);
			} catch (Exception e) {}
		});

		if (Client.estlanceable(port, adresse, frmJeu))
		{
			lancerFrame.start();
			return true;
		}
		return false;
			
	}

	public static boolean connection (FrameHeberger frmHeberger )
	{
		FrameJeu frmJeu = new FrameJeu(null);

		try {
			Thread lancerFrame = new Thread(() -> {
                try {
                    Serveur.initServeur(frmHeberger, frmJeu);
                } catch (Exception e) {}
            });
            lancerFrame.start();

			return true;
		} catch (Exception err) { return false; }
	}

	public void deconection(int source)
	{
		JOptionPane.showMessageDialog ( this,source==0?"Le client s'est déconnecté":"Le Serveur est fermé", "Erreur", JOptionPane.ERROR_MESSAGE );
		System.exit(0);
	}

	public Image getIcon() { return this.icon; }

	public void afficher() { this.setVisible(true); }
}