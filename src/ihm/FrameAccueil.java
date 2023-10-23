/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import controleur.Controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
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

		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelTest = new JPanel(new GridLayout(1, 3));
		JPanel panelButton = new JPanel(new GridLayout(6, 1, 15, 15));
		JPanel panelInutile = new JPanel();
		JPanel panelInutile2 = new JPanel();

		JLabel j = new JLabel(new ImageIcon(scaledImg));
		j.setLayout(new BorderLayout());

		panel.setOpaque(false);
		panelTest.setOpaque(false);
		panelButton.setOpaque(false);
		panelInutile.setOpaque(false);
		panelInutile2.setOpaque(false);

		this.btnSolo = new JButton("Solo");
		this.btnHeberger = new JButton("Héberger");
		this.btnRejoindre = new JButton("Rejoindre");
		this.btnQuitter = new JButton("Quitter");

		panelButton.add(this.btnSolo);
		panelButton.add(this.btnHeberger);
		panelButton.add(this.btnRejoindre);
		panelButton.add(this.btnQuitter);

		panelTest.add(panelInutile);
		panelTest.add(panelButton);
		panelTest.add(panelInutile2);

		panel.add(panelTest, BorderLayout.SOUTH);

		j.add(panel, BorderLayout.CENTER);

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
