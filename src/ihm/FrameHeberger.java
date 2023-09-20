package ihm;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JFrame;

import controleur.*;

public class FrameHeberger extends JFrame
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
	private JPanel  pnlHeberger;

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
	public FrameHeberger ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.icon        = Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/BateauIcon.png" );
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 700 ) / 2;
		int h = ( tailleEcran.height -  450 ) / 2;

		this.setSize     ( 700, 450   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "Bataille Navale" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.pnlHeberger = new PanelHeberger(this);

		this.add(this.pnlHeberger);

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );

		FrameJeu.connection(this);
	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher ( ) { this.dispose ( ); }

	public Image getIcon() { return this.icon; }
}