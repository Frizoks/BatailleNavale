/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import serveur.Serveur;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * La classe PanelHeberger représente un panneau d'attente pour héberger une partie multijoueur.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class PanelHeberger extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * La fenêtre parente FrameHeberger à laquelle ce panneau est associé.
	 */
	private FrameHeberger frmHeberger;

	/**
	 * Label pour afficher le port attribué pour la connexion.
	 */
	private String lblPort;

	/**
	 * Label pour afficher l'adresse IP de la machine qui héberge.
	 */
	private String lblAdrIP;

	/**
	 * Libellé pour afficher un message d'attente de l'autre joueur.
	 */
	private String lblTxtConn;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe PanelHeberger.
	 *
	 * @param frmHeberger La fenêtre parente FrameHeberger à laquelle ce panneau est associé.
	 */
	public PanelHeberger(FrameHeberger frmHeberger)
	{
		this.frmHeberger = frmHeberger;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);

		ImageIcon img = new ImageIcon(this.frmHeberger.getIcon().getScaledInstance(350, 225, Image.SCALE_DEFAULT));

		JLabel lblIcon = new JLabel(img);
		this.lblPort    = String.format("%-15s","Port attribué") + ": " + Serveur.PORT;
		this.lblAdrIP   = String.format("%-15s","Adresse IP") + ": " + Serveur.getAdresse();
		this.lblTxtConn = "En attente de l'autre joueur ...";

		this.add(lblIcon, BorderLayout.CENTER);
	}

	/**
	 * Cette méthode est chargée de dessiner les composants graphiques personnalisés
	 * de cette classe. Elle est appelée automatiquement lorsque le composant doit être
	 * redessiné, par exemple en cas de rafraîchissement de l'interface graphique.
	 *
	 * @param g L'objet Graphics utilisé pour le dessin.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.drawString(this.lblPort, 275, 40);
		g2.drawString(this.lblAdrIP,275,55);
		g2.drawString(this.lblTxtConn, 240, 375);
	}
}
