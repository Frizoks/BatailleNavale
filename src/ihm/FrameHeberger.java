/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import controleur.Controleur;

/**
 * La classe FrameHeberger représente la fenêtre permettant d'héberger une partie
 * en mode multijoueur.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class FrameHeberger extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	protected Controleur ctrl;

	/**
	 * Panel pour afficher les composants liés à l'hébergement d'une partie.
	 */
	private JPanel pnlHeberger;

	/**
	 * Image de l'icône de la fenêtre.
	 */
	private Image icon;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe FrameHeberger.
	 *
	 * @param ctrl Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	public FrameHeberger(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.icon = new ImageIcon(this.getClass().getResource("image/BateauIcon.png")).getImage();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int l = (tailleEcran.width - 700) / 2;
		int h = (tailleEcran.height - 450) / 2;

		this.setSize(700, 450);
		this.setLocation(l, h);
		this.setTitle("Bataille Navale");

		this.pnlHeberger = new PanelHeberger(this);

		this.add(this.pnlHeberger);

		this.setIconImage(icon);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		FrameJeu.connection(this, this.ctrl);
	}

	/**
	 * Ferme la fenêtre de l'hébergement de la partie.
	 */
	public void cacher() {this.dispose();}

	/**
	 * Permet au panel de récupérer l'icône de la fenêtre.
	 *
	 * @return L'icône de la fenêtre.
	 */
	public Image getIcon() {return this.icon;}
}
