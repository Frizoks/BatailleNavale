/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import controleur.Controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

/**
 * La classe FrameAccueil représente la fenêtre d'accueil du jeu.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class FrameAccueil extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le contrôleur utilisé pour accéder aux fonctionnalités du jeu.
	 */
	private Controleur ctrl;

	/**
	 * Bouton pour accéder au mode solo.
	 */
	private JButton btnSolo;

	/**
	 * Bouton pour héberger une partie en mode multijoueur.
	 */
	private JButton btnHeberger;

	/**
	 * Bouton pour rejoindre une partie en mode multijoueur.
	 */
	private JButton btnRejoindre;

	/**
	 * Bouton pour quitter le jeu.
	 */
	private JButton btnQuitter;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe FrameAccueil.
	 *
	 * @param ctrl Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	public FrameAccueil(Controleur ctrl)
	{
		this.ctrl = ctrl;

		Image img = new ImageIcon(this.getClass().getResource("image/FondAccueil.png")).getImage();
		Image scaledImg = img.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);

		Image icon = new ImageIcon(this.getClass().getResource("image/BateauIcon.png")).getImage();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int l = (tailleEcran.width - 700) / 2;
		int h = (tailleEcran.height - 450) / 2;

		this.setSize(700, 450);
		this.setLocation(l, h);
		this.setTitle("Bataille Navale");

		JLabel j = new JLabel(new ImageIcon(scaledImg));
		j.setLayout(new BorderLayout());
		
		this.btnSolo = new JButton("Solo");
		this.btnHeberger = new JButton("Créer une partie");
		this.btnRejoindre = new JButton("Rejoindre");
		this.btnQuitter = new JButton("Quitter");

		this.btnSolo.setBounds(233, 200, 233, 25);
		this.btnHeberger.setBounds(233, 235, 233, 25);
		this.btnRejoindre.setBounds(233, 270, 233, 25);
		this.btnQuitter.setBounds(233, 305, 233, 25);

		this.add(this.btnSolo);
		this.add(this.btnHeberger);
		this.add(this.btnRejoindre);
		this.add(this.btnQuitter);

		this.getContentPane().add(j);

		this.btnSolo.addActionListener(this);
		this.btnHeberger.addActionListener(this);
		this.btnRejoindre.addActionListener(this);
		this.btnQuitter.addActionListener(this);

		this.setIconImage(icon);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Gère les actions lorsqu'un bouton est cliqué.
	 *
	 * @param e L'événement d'action.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnHeberger)
		{
			this.ctrl.partieMulti();
			new FrameHeberger(ctrl);
		}
		if (e.getSource() == this.btnRejoindre)
		{
			this.ctrl.partieMulti();
			new FrameRejoindre(ctrl);
		}
		if (e.getSource() == this.btnSolo)
		{
			new FrameDifficulte(this.ctrl);
		}
		this.cacher();
	}

	/**
	 * Ferme la fenêtre d'accueil.
	 */
	public void cacher() {this.dispose();}
}
