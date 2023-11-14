/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Le PanelSelectBateau est une classe représentant un panneau permettant au joueur de sélectionner et placer ses bateaux.
 * Il affiche des boutons pour valider et placer automatiquement les bateaux, ainsi que des images de bateaux de différentes tailles.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class PanelSelectBateau extends JPanel implements MouseListener, ActionListener, KeyListener
{
	/**
	 * La FrameJeu associée.
	 */
	private FrameJeu frmJeu;

	/**
	 * Bouton pour valider le placement des bateaux.
	 */
	private JButton btnValider;

	/**
	 * Bouton pour placer automatiquement les bateaux.
	 */
	private JButton btnAutoBateau;

	/**
	 * Étiquette pour afficher un message à l'utilisateur.
	 */
	private JLabel lblMessage;

	/**
	 * Étiquette pour afficher un message à l'utilisateur en dessous de l'autre.
	 */
	private JLabel lblMessage2;

	/**
	 * Étiquette pour afficher des messages d'erreur.
	 */
	private JLabel lblMessageErreur;

	/**
	 * État de sélection des bateaux (Sélectionné, Non sélectionné, Placé).
	 */
	private char[] etat = {'N', 'N', 'N', 'N', 'N'};

	/**
	 * Identifiant du bateau sélectionné.
	 */
	private Integer idBSelec;

	/**
	 * Constructeur de la classe PanelSelectBateau.
	 *
	 * @param frmJeu La FrameJeu associée.
	 */
	public PanelSelectBateau(FrameJeu frmJeu)
	{
		this.frmJeu = frmJeu;
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);

		this.idBSelec = null;
		this.btnValider = new JButton("Valider");
		this.btnAutoBateau = new JButton("Auto");
		this.lblMessage = new JLabel("Veuillez placer tous vos navires");
		this.lblMessage2 = new JLabel("(en les validant 1 par 1)");
		this.lblMessageErreur = new JLabel();

		this.btnValider.setBorder(new RoundBtn(15));
		this.btnAutoBateau.setBorder(new RoundBtn(15));

		this.btnValider.setBounds(400, 50, 100, 50);
		this.btnAutoBateau.setBounds(550, 50, 100, 50);
		this.lblMessage.setBounds(410, 120, 250, 50);
		this.lblMessage2.setBounds(435, 145, 250, 50);
		this.lblMessageErreur.setBounds(170, 240, 500, 50);

		this.add(this.btnValider);
		this.add(this.btnAutoBateau);
		this.add(this.lblMessage);
		this.add(this.lblMessage2);
		this.add(this.lblMessageErreur);

		this.btnValider.addActionListener(this);
		this.btnValider.addKeyListener(this);
		this.btnAutoBateau.addActionListener(this);
		this.addMouseListener(this);
	}

	/**
	 * Cette méthode est chargée de peindre le composant. Elle dessine les bateaux sur le composant en utilisant
	 * un objet Graphics2D et spécifie la couleur des bateaux.
	 *
	 * @param g L'objet Graphics utilisé pour le dessin.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(189, 67, 67)); // Couleur : Rouge foncé
		this.dessinerBateau(g2);
	}

	/**
	 * Cette méthode dessine les navires à placer sur le composant en utilisant un objet Graphics2D fourni en argument.
	 * 
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	public void dessinerBateau(Graphics2D g2)
	{
		// Affiche le petit Bateau de 2
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteG" + getNomImg(0) + ".png")).getImage(), 50, 25, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteD" + getNomImg(0) + ".png")).getImage(), 80, 25, 25, 25, this);

		// Affiche le grand bateau de 5 à côté du 2
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteG" + getNomImg(1) + ".png")).getImage(), 150, 25, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(1) + ".png")).getImage(), 180, 25, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(1) + ".png")).getImage(), 210, 25, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(1) + ".png")).getImage(), 240, 25, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteD" + getNomImg(1) + ".png")).getImage(), 270, 25, 25, 25, this);

		// Affiche les 2 bateaux de 3 côte à côte
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteG" + getNomImg(2) + ".png")).getImage(), 50, 95, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(2) + ".png")).getImage(), 80, 95, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteD" + getNomImg(2) + ".png")).getImage(), 110, 95, 25, 25, this);

		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteG" + getNomImg(3) + ".png")).getImage(), 180, 95, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(3) + ".png")).getImage(), 210, 95, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteD" + getNomImg(3) + ".png")).getImage(), 240, 95, 25, 25, this);

		// Affiche le bateau de 4
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteG" + getNomImg(4) + ".png")).getImage(), 50, 165, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(4) + ".png")).getImage(), 80, 165, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + getNomImg(4) + ".png")).getImage(), 110, 165, 25, 25, this);
		g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireExtremiteD" + getNomImg(4) + ".png")).getImage(), 140, 165, 25, 25, this);
	}

	/**
	 * Cette méthode initialise l'état des bateaux en les marquant comme "Place".
	 */
	public void setEtatP()
	{
		for(int i = 0; i < etat.length; i++) {this.etat[i] = 'P';}
	}

	/**
	 * Cette méthode retourne le nom de l'image en fonction de l'état du bateau.
	 *
	 * @param i L'indice du bateau.
	 * @return Le nom de l'image correspondant à l'état du bateau.
	 */
	public String getNomImg(int i)
	{
		switch (etat[i])
		{
			case 'S': return "selec";
			case 'P': return "place";
			default: return "";
		}
	}

	/**
	 * Cette méthode est appelée en réponse à un événement de clic de souris. Elle permet de sélectionner un bateau
	 * en fonction des coordonnées du clic et met à jour l'état des bateaux en conséquence.
	 *
	 * @param e L'objet MouseEvent contenant des informations sur l'événement de clic de souris.
	 */
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		for(int index = 0; index < etat.length; index++)
		{
			if (etat[index] != 'P') {etat[index] = 'N';}
			this.idBSelec = null;
			this.frmJeu.pnlJeu.viderPos();
		}

		this.lblMessageErreur.setText("");

		if(x > 50 && x < 110 && y > 25 && y < 50 && etat[0] != 'P') {etat[this.idBSelec = 0] = 'S';}
		if(x > 150 && x < 300 && y > 25 && y < 50 && etat[1] != 'P') {etat[this.idBSelec = 1] = 'S';}
		if(x > 50 && x < 140 && y > 95 && y < 120 && etat[2] != 'P') {etat[this.idBSelec = 2] = 'S';}
		if(x > 180 && x < 270 && y > 95 && y < 120 && etat[3] != 'P') {etat[this.idBSelec = 3] = 'S';}
		if(x > 50 && x < 180 && y > 165 && y < 190 && etat[4] != 'P') {etat[this.idBSelec = 4] = 'S';}
		this.frmJeu.repeindre();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Cette méthode est appelée en réponse à des événements d'action, comme un clic sur un bouton. Elle gère les actions
	 * à effectuer en fonction de la source de l'événement.
	 *
	 * @param e L'objet ActionEvent contenant des informations sur l'événement d'action.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Sound.clipClicOn();
		if(e.getSource() == this.btnValider)
		{
			this.validerNavire();
		}
		if(e.getSource() == this.btnAutoBateau)
		{
			this.frmJeu.pnlJeu.viderPos();
			this.frmJeu.getCtrl().ajouterNaviresJoueur(this.etat);
			this.setEtatP();
		}

		if(this.frmJeu.getCtrl().navireJoueurPlace()) {this.frmJeu.navireJoueurPlace();}

		this.frmJeu.repeindre();
	}

	/**
	 * Méthode appelée lorsqu'une touche est enfoncée.
	 *
	 * @param e L'événement déclencheur.
	 */
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			this.validerNavire();
			if(this.frmJeu.getCtrl().navireJoueurPlace()) {this.frmJeu.navireJoueurPlace();}
			this.frmJeu.repeindre();
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	/**
	 * Méthode permettant de valider le placement du bateau, et le place
	 * si les positions sont valides.
	 */
	public void validerNavire()
	{
		int[][] tab = this.frmJeu.pnlJeu.recupPos();
		int taille = 0;
		if(this.idBSelec != null)
		{
			switch (this.idBSelec)
			{
				case 0: taille = 2;break;
				case 1: taille = 5;break;
				case 2: taille = 3;break;
				case 3: taille = 3;break;
				case 4: taille = 4;break;
			}
		}
		else {this.lblMessageErreur.setText("ERREUR : Vous devez sélectionner un bateau !!!");}

		if(taille == tab[0].length && this.idBSelec != null && frmJeu.getCtrl().ajouterNavire(taille, tab))
		{
			etat[this.idBSelec] = 'P';
			this.idBSelec = null;
			this.lblMessageErreur.setText("");
		}
		else if(this.idBSelec != null)
		{
			etat[this.idBSelec] = 'N';
			this.idBSelec = null;
			this.lblMessageErreur.setText("ERREUR : Votre bateau ne peut pas être placé !!!");
		}
	}
}

/**
 * Cette classe interne RoundBtn est une classe personnalisée pour créer une bordure arrondie pour les boutons.
 */
class RoundBtn implements Border
{
	/**
	 * Entier coorespondant au degré d'arrondissement.
	 */
	private int r;

	/**
	 * Constructeur de la classe RoundBtn.
	 *
	 * @param r dégré d'arrondissement.
	 */
	RoundBtn(int r)
	{
		this.r = r;
	}

	public Insets getBorderInsets(Component c) {return new Insets(this.r + 1, this.r + 1, this.r + 2, this.r);}
	public boolean isBorderOpaque() {return true;}
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {g.drawRoundRect(x, y, width - 1, height - 1, r, r);}
}
