/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
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

		this.btnFacile = new JButton("Facile");
		this.btnMoyen = new JButton("Moyen");
		this.btnDifficile = new JButton("Difficile");
		this.btnImpossible = new JButton("Impossible");

		panelButton.add(this.btnFacile);
		panelButton.add(this.btnMoyen);
		panelButton.add(this.btnDifficile);
		panelButton.add(this.btnImpossible);

		panelTest.add(panelInutile);
		panelTest.add(panelButton);
		panelTest.add(panelInutile2);

		panel.add(panelTest, BorderLayout.SOUTH);

		j.add(panel, BorderLayout.CENTER);

		this.getContentPane().add(j);

		this.btnFacile.addActionListener(this);
		this.btnMoyen.addActionListener(this);
		this.btnDifficile.addActionListener(this);
		this.btnImpossible.addActionListener(this);

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
		if (e.getSource() == this.btnFacile) {
			this.ctrl.partieSolo('F');
		}
		if (e.getSource() == this.btnMoyen) {
			this.ctrl.partieSolo('M');
		}
		if (e.getSource() == this.btnDifficile) {
			this.ctrl.partieSolo('D');
		}
		if (e.getSource() == this.btnImpossible) {
			this.ctrl.partieSolo('I');
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
