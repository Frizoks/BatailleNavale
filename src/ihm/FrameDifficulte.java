/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import controleur.Controleur;

/**
 * La classe FrameDifficulte représente la fenêtre de sélection de la difficulté
 * pour une partie en mode solo.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class FrameDifficulte extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Le contrôleur utilisé pour accéder aux fonctionnalités du jeu.
	 */
	private Controleur ctrl;

	/**
	 * Bouton pour choisir la difficulté "Facile".
	 */
	private JButton btnFacile;

	/**
	 * Bouton pour choisir la difficulté "Moyen".
	 */
	private JButton btnMoyen;

	/**
	 * Bouton pour choisir la difficulté "Difficile".
	 */
	private JButton btnDifficile;

	/**
	 * Bouton pour choisir la difficulté "Impossible".
	 */
	private JButton btnImpossible;

	/**
	 * Bouton pour revenir en arrière.
	 */
	private JButton btnRetour;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe FrameDifficulte.
	 *
	 * @param ctrl Le contrôleur utilisé pour gérer les fonctionnalités du jeu.
	 */
	public FrameDifficulte(Controleur ctrl)
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

		this.btnFacile = new JButton("Facile");
		this.btnMoyen = new JButton("Moyen");
		this.btnDifficile = new JButton("Difficile");
		this.btnImpossible = new JButton("Impossible");
		this.btnRetour = new JButton("Retour");

		this.btnFacile.setBounds(233, 200, 233, 25);
		this.btnMoyen.setBounds(233, 235, 233, 25);
		this.btnDifficile.setBounds(233, 270, 233, 25);
		this.btnImpossible.setBounds(233, 305, 233, 25);
		this.btnRetour.setBounds(233, 355, 233, 20);

		this.add(this.btnFacile);
		this.add(this.btnMoyen);
		this.add(this.btnDifficile);
		this.add(this.btnImpossible);
		this.add(this.btnRetour);

		this.getContentPane().add(j);

		this.btnFacile.addActionListener(this);
		this.btnMoyen.addActionListener(this);
		this.btnDifficile.addActionListener(this);
		this.btnImpossible.addActionListener(this);
		this.btnRetour.addActionListener(this);

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
		if (e.getSource() == this.btnFacile) {this.ctrl.partieSolo('F');}
		if (e.getSource() == this.btnMoyen) {this.ctrl.partieSolo('M');}
		if (e.getSource() == this.btnDifficile) {this.ctrl.partieSolo('D');}
		if (e.getSource() == this.btnImpossible) {this.ctrl.partieSolo('I');}
		if (e.getSource() == this.btnRetour) 
		{
			this.dispose();
			new Controleur();
			return;
		}

		FrameJeu frmTemp = new FrameJeu(ctrl, 'N');
		frmTemp.afficher();

		this.cacher();
	}

	/**
	 * Ferme la fenêtre de sélection de la difficulté.
	 */
	public void cacher() {this.dispose();}
}
