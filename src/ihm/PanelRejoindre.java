/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Le PanelRejoindre est une classe représentant un panneau qui permet à un joueur de rejoindre une partie.
 * Il affiche un champ de saisie pour le port et un champ de saisie pour l'adresse IP du serveur à rejoindre.
 * Le joueur peut également valider sa connexion en cliquant sur un bouton.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class PanelRejoindre extends JPanel implements ActionListener
{
	/**
	 * La FrameRejoindre associée.
	 */
	private FrameRejoindre frmRejoindre;

	/**
	 * Champ de saisie du port.
	 */
	private JTextField txtfPort;

	/**
	 * Champ de saisie de l'adresse IP.
	 */
	private JTextField txtfAdresse;

	/**
	 * Bouton de validation de la connexion.
	 */
	private JButton btnValider;

	/**
	 * Étiquette pour afficher des messages d'erreur.
	 */
	private JLabel lblErreur;

	/**
	 * Constructeur de la classe PanelRejoindre.
	 *
	 * @param frmRejoindre La FrameRejoindre associée.
	 */
	public PanelRejoindre(FrameRejoindre frmRejoindre)
	{
		this.frmRejoindre = frmRejoindre;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);

		ImageIcon img = new ImageIcon(this.frmRejoindre.getIcon().getScaledInstance(350, 225, Image.SCALE_DEFAULT));

		JLabel lblIcon = new JLabel(img);

		JPanel pnlTemp = new JPanel(new GridLayout(3, 1));
		JPanel pnlTxtf = new JPanel();
		this.txtfPort = new JTextField("9696", 25);
		this.txtfAdresse = new JTextField("localhost", 25);
		this.btnValider = new JButton("Valider");
		this.lblErreur = new JLabel("");

		this.btnValider.addActionListener(this);

		pnlTxtf.add(this.txtfPort);
		pnlTxtf.add(this.txtfAdresse);
		pnlTxtf.add(this.btnValider);

		pnlTemp.add(new JLabel(String.format("%6s", "") + String.format("%-67s", "Port :") + "Adresse IP :"));
		pnlTemp.add(pnlTxtf);
		pnlTemp.add(this.lblErreur);

		this.add(lblIcon, BorderLayout.CENTER);
		this.add(pnlTemp, BorderLayout.SOUTH);
	}

	/**
	 * Gestionnaire d'événements pour les actions effectuées.
	 *
	 * @param e L'événement déclencheur.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnValider)
		{
			if (!FrameJeu.connection(this.txtfPort.getText(), this.txtfAdresse.getText(), this.frmRejoindre.ctrl))
			{
				this.lblErreur.setText("Port ou adresse introuvable");
			}
			else {this.frmRejoindre.cacher();}
		}
	}
}
