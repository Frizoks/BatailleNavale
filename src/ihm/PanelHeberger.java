package ihm;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import serveur.*;

public class PanelHeberger extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private FrameHeberger  frmHeberger;

	/**
	 * Un Panel pour afficher des composants
	 */
	private String  lblPort;

	/**
	 * Un Panel pour afficher des composants
	 */
	private String  lblTxtConn;



	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public PanelHeberger ( FrameHeberger frmHeberger )
	{
		this.frmHeberger = frmHeberger;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout( new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);

		ImageIcon img = new ImageIcon(this.frmHeberger.getIcon().getScaledInstance(350, 225, Image.SCALE_DEFAULT));

		JLabel lblIcon = new JLabel( img);
		this.lblPort = "Port attribué : " + Serveur.PORT;
		this.lblTxtConn = "En attente de l'autre joueur ...";

		this.add(lblIcon,BorderLayout.CENTER);
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		g2.drawString(this.lblPort,275,40);
		g2.drawString(this.lblTxtConn,240,375);
	}
}