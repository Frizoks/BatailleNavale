package ihm;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controleur.*;

public class FrameRejoindre extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	protected Controleur  ctrl;

	/**
	 * Un Panel pour afficher des composants
	 */
	private JPanel  pnlRejoindre;

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
	public FrameRejoindre ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.icon        = new ImageIcon ( this.getClass().getResource("image/BateauIcon.png")).getImage();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 700 ) / 2;
		int h = ( tailleEcran.height -  450 ) / 2;

		this.setSize     ( 700, 450   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "Bataille Navale" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.pnlRejoindre = new PanelRejoindre(this);

		this.add(this.pnlRejoindre);

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );

	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher() {this.dispose();}
	public Image getIcon() {return this.icon;}

	public void connection() {this.cacher();}
}