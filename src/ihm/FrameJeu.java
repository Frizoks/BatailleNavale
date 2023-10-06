package ihm;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controleur.*;
import serveur.Client;
import serveur.Serveur;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

	private char estServeur;

	private static Thread proceServ;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameJeu ( Controleur ctrl, char estServeur )
	{
		this.ctrl = ctrl;
		this.estServeur = estServeur;
		FrameJeu.proceServ  = null;
		
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

		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
				try {
					FrameJeu.proceServ.interrupt();
				}
                catch ( Exception ex ) {}
            }
        });
	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher ( ) { this.dispose ( ); }

	public static boolean connection (String port, String adresse, Controleur ctrl )
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

	public static void connection (FrameHeberger frmHeberger, Controleur ctrl )
	{
		FrameJeu frmJeu = new FrameJeu(ctrl, 'S');

		FrameJeu.proceServ = new Thread(() -> {
				Serveur.initServeur(ctrl, frmHeberger, frmJeu);
		});
		FrameJeu.proceServ.start();
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

	public void navireJoueurPlace()
	{
		this.remove(this.pnlBas);
		
		if (!this.ctrl.estMulti())
		{
			this.pnlBas = new PanelMessage(this);
			this.pnlJeu.grilleAdvActive();
		}
		else
		{
			this.pnlBas = new JPanel();
			this.pnlBas.setBackground(Color.LIGHT_GRAY);
			this.pnlBas.add(new JLabel("En attente de votre adversaire"));
			if (this.estServeur == 'S') { Serveur.envoiePlateau(); }
			if (this.estServeur == 'C') { Client.envoiePlateau(); }
		}
		this.add(this.pnlBas);
		this.repaint();
		this.revalidate();
	}

	public void navireTousPlacer()
	{
		this.remove(this.pnlBas);
		this.pnlBas = new PanelMessage(this);
		this.add(this.pnlBas);
		this.repaint();
		this.revalidate();
		this.pnlJeu.grilleAdvActive();
	}

	public char estServeur() { return this.estServeur; }
	
	public void setCoup(int[] tab)
	{
		if (this.estServeur =='S')
			Serveur.setCoup(tab);
		if (this.estServeur =='C')
			Client.setCoup(tab);
	}

	public void grilleAdvActive() {this.pnlJeu.grilleAdvActive();}

	public void repeindre() {
		this.pnlJeu.repaint();
		this.pnlBas.repaint();
	}

	public void envoyerLogScoreA(char coup) {
		this.pnlJeu.envoyerLogScoreA(coup);
	}

}