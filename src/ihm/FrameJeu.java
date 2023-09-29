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
	protected PanelJeu pnlJeu;


	/**
	 * Un Panel pour afficher des composants
	 */
	JPanel  pnlBas;

	/**
	 * Un Panel pour afficher des composants
	 */
	private Image  icon;

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

		this.setSize     ( 700 , 700   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "Bataille Navale" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout(2,1) );

		this.pnlJeu = new PanelJeu(this);
		this.pnlBas = new PanelSelectBateau(this);

		this.add(this.pnlJeu);
		this.add(this.pnlBas);

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setResizable(false);
		this.setVisible               ( false         );
	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher ( ) { this.dispose ( ); }

	public static boolean connection (String port, String adresse, Controleur ctrl )
	{
		FrameJeu frmJeu = new FrameJeu(ctrl);

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

	public static boolean connection (FrameHeberger frmHeberger, Controleur ctrl )
	{
		FrameJeu frmJeu = new FrameJeu(ctrl);

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

	public void partieFini(char c)
	{
		this.pnlJeu.repaint();
		this.pnlBas.repaint();
		
		JOptionPane.showMessageDialog ( this,c=='J'?"Vous avez gagné":"L' adversaire a gagné", "Fin de partie", JOptionPane.INFORMATION_MESSAGE );
		System.exit(0);
	}

	public Image getIcon() { return this.icon; }

	public void afficher() { this.setVisible(true);}

	public Controleur getCtrl() { return this.ctrl; }

	public void navireTousPlacer()
	{
		this.remove(this.pnlBas);
		this.pnlBas = new PanelMessage(this);
		this.add(this.pnlBas);
		this.repaint();
		this.revalidate();
		this.pnlJeu.grilleAdvActive();
	}
}