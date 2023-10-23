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
 * La classe FrameRejoindre représente la fenêtre de la phase de rejoindre une partie multijoueur.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class FrameRejoindre extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	protected Controleur ctrl;

	/**
	 * Panel pour afficher la phase de rejoindre une partie multijoueur.
	 */
	private JPanel pnlRejoindre;

	/**
	 * Image de l'icône de la fenêtre.
	 */
	private Image icon;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe FrameRejoindre.
	 *
	 * @param ctrl Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	public FrameRejoindre(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.icon = new ImageIcon(this.getClass().getResource("image/BateauIcon.png")).getImage();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int l = (tailleEcran.width - 700) / 2;
		int h = (tailleEcran.height - 450) / 2;

		this.setSize(700, 450);
		this.setLocation(l, h);
		this.setTitle("Bataille Navale");

		this.pnlRejoindre = new PanelRejoindre(this);

		this.add(this.pnlRejoindre);

		this.setIconImage(icon);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Ferme la fenêtre de la phase de rejoindre une partie multijoueur.
	 */
	public void cacher() {this.dispose();}

	/**
	 * Récupère l'icône de la fenêtre.
	 *
	 * @return L'icône de la fenêtre.
	 */
	public Image getIcon() {return this.icon;}
}
