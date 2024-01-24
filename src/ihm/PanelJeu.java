/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import metier.*;

import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * La classe PanelJeu représente le panneau de jeu principal de la bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class PanelJeu extends JPanel implements MouseListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * La fenêtre parente FrameJeu à laquelle ce panneau est associé.
	 */
	private FrameJeu frmJeu;

	/**
	 * Indique si la grille de l'adversaire est active ou non.
	 */
	private boolean grilleAdvEstActive;

	/**
	 * Liste des positions des cellules sélectionnées.
	 */
	private ArrayList<int[]> positions;

	/**
	 * Nombre de coup que le joueur a effectuer.
	 */
	private int nbCoupJoueur;

	/**
	 * Nombre de coup du joueur où il a touché ou coulé un bateau
	 */
	private int nbNaviresToucheJoueur;

	/**
	 * Nombre de coup que l'adversaire a effectuer.
	 */
	private int nbCoupAdv;

	/**
	 * Nombre de coup de l'adversaire où il a touché ou coulé un bateau
	 */
	private int nbNaviresToucheAdv;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/**
	 * Constructeur de la classe PanelJeu.
	 *
	 * @param frmJeu La fenêtre parente FrameJeu à laquelle ce panneau est associé.
	 */
	public PanelJeu(FrameJeu frmJeu)
	{
		this.frmJeu = frmJeu;
		this.setBackground(Color.LIGHT_GRAY);

		this.positions = new ArrayList<int[]>();
		this.grilleAdvEstActive = false;

		this.nbCoupJoueur = this.nbNaviresToucheJoueur = 0;
		this.nbCoupAdv = this.nbNaviresToucheAdv = 0;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */
		this.addMouseListener(this);
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

		g2.setColor(Color.BLUE);
		this.dessinerGrilleJoueur(g2);
		this.placerDecor(g2);

		if(this.frmJeu.getCtrl().navireJoueurPlace() && this.frmJeu.getCtrl().quelqunGagne() == 'A') {this.afficherAdv(g2);}
		else {this.dessinerGrilleAdv(g2);}

		g2.setColor(Color.RED);
		g2.drawLine(350, 50, 350, 350);
	}

	/**
	 * Cette méthode dessine la grille du joueur en utilisant l'objet
	 * Graphics2D fourni en argument. Elle affiche les navires, les tirs, et les cases sélectionnées
	 * sur la grille du joueur.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	public void dessinerGrilleJoueur(Graphics2D g2)
	{
		for(Cellule[] tab : this.frmJeu.getCtrl().getPlateauJoueur().getGrille())
		{
			for(Cellule cell : tab)
			{
				if(this.frmJeu.getCtrl().getPlateauJoueur().getCellule(cell.getX(), cell.getY()).getNavire())
				{
					char c = this.frmJeu.getCtrl().getPlateauJoueur().extremite(cell.getX(), cell.getY());
					if(c != 'R')
					{
						String nomImg = "image/navireExtremite" + c + "place.png";
						g2.drawImage(new ImageIcon(this.getClass().getResource(nomImg)).getImage(), 52 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
					else
					{
						g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieuplace.png")).getImage(), 52 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
				}
				if(this.frmJeu.getCtrl().getPlateauJoueur().getCellule(cell.getX(), cell.getY()).getTirer())
				{
					g2.drawImage(new ImageIcon(this.getClass().getResource("image/croix.png")).getImage(), 52 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
				}
			}
		}

		for(int[] tab : this.positions)
		{
			int x = tab[0];
			int y = tab[1];
			g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireSelec.png")).getImage(), 52 + x * 25, 77 + y * 25, 21, 21, this);
		}
	}

	/**
	 * Cette méthode dessine la grille de l'adversaire en utilisant l'objet
	 * Graphics2D fourni en argument. Elle affiche les navires, les tirs et les résultats des tirs
	 * sur la grille de l'adversaire.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	public void dessinerGrilleAdv(Graphics2D g2)
	{
		for(Cellule[] tab : this.frmJeu.getCtrl().getPlateauAdv().getGrille())
		{
			for(Cellule cell : tab)
			{
				if(this.frmJeu.getCtrl().getPlateauAdv().navireEntier(cell.getX(), cell.getY()))
				{
					char c = this.frmJeu.getCtrl().getPlateauAdv().extremite(cell.getX(), cell.getY());
					if(c != 'R')
					{
						String nomImg = "image/navireExtremite" + c + ".png";
						g2.drawImage(new ImageIcon(this.getClass().getResource(nomImg)).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
					else
					{
						g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu.png")).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
				}
				else if(cell.getTirer())
				{
					if(cell.getNavire())
					{
						g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireTouche.png")).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
					else
					{
						g2.drawImage(new ImageIcon(this.getClass().getResource("image/tirEau.png")).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
				}
			}
		}
	}

	/**
	 * Cette méthode place des éléments de décoration sur le composant, tels que des étiquettes
	 * pour indiquer les deux grilles (joueur et adversaire) et des repères de coordonnées.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	public void placerDecor(Graphics2D g2)
	{
		g2.drawString("Votre Plateau", 125, 35);
		g2.drawString("Plateau de l'adversaire", 450, 35);

		for(int cpt = 0; cpt < 10; cpt++)
		{
			g2.drawString("" + (char) ('A' + cpt), 25 * cpt + 60, 70);
			g2.drawString("" + (cpt + 1), 30, 25 * cpt + 95);

			g2.drawString("" + (char) ('A' + cpt), 25 * cpt + 410, 70);
			g2.drawString("" + (cpt + 1), 660, 25 * cpt + 95);
		}
		for(int cpt = 0; cpt < 11; cpt++)
		{
			g2.drawLine(25 * cpt + 400, 75, 25 * cpt + 400, 325);
			g2.drawLine(400, 25 * cpt + 75, 650, 25 * cpt + 75);

			g2.drawLine(25 * cpt + 50, 75, 25 * cpt + 50, 325);
			g2.drawLine(50, 25 * cpt + 75, 300, 25 * cpt + 75);
		}
	}

	/**
	 * Cette méthode récupère les positions sélectionnées par le joueur pour placer ses navires.
	 *
	 * @return Un tableau d'entiers contenant les positions sélectionnées.
	 */
	public int[][] recupPos()
	{
		int[][] temp = new int[2][this.positions.size()];

		for(int index = 0; index < this.positions.size(); index++)
		{
			temp[0][index] = this.positions.get(index)[0];
			temp[1][index] = this.positions.get(index)[1];
		}

		this.positions.clear();
		return temp;
	}

	/**
	 * Cette méthode vide la liste des positions sélectionnées par le joueur pour placer ses navires.
	 */
	public void viderPos() {this.positions.clear();}

	/**
	 * Cette méthode vérifie si un tableau d'entiers existe déjà dans une liste de positions.
	 *
	 * @param positions La liste de positions à vérifier.
	 * @param tab       Le tableau d'entiers à rechercher.
	 * @return L'indice de l'élément si trouvé, sinon -1.
	 */
	public int contains(ArrayList<int[]> positions, int[] tab)
	{
		for(int index = 0; index < positions.size(); index++)
		{
			if(positions.get(index)[0] == tab[0] && positions.get(index)[1] == tab[1]) {return index;}
		}
		return -1;
	}

	/**
	 * Cette méthode active ou désactive la grille de l'adversaire en fonction de
	 * l'état du jeu (à votre tour de jouer ou en attente de l'adversaire).
	 */
	public void grilleAdvActive()
	{
		this.grilleAdvEstActive = !this.grilleAdvEstActive;

		if(this.grilleAdvEstActive) {((PanelMessage) this.frmJeu.pnlBas).setAQuiLeTour("C'est à votre tour de jouer !!!!");}
		if(!this.grilleAdvEstActive) {((PanelMessage) this.frmJeu.pnlBas).setAQuiLeTour("En attente de votre adversaire");}
	}

	/**
	 * Cette méthode est appelée lorsque l'utilisateur effectue un clic de souris sur le composant.
	 * Elle gère les interactions liées aux clics de l'utilisateur, comme le placement des navires
	 * ou la sélection d'une case de tir sur la grille de l'adversaire.
	 *
	 * @param e L'événement MouseEvent généré par le clic de souris.
	 */
	public void mouseClicked(MouseEvent e)
	{
		Rectangle2D zoneAdv = new Rectangle2D.Double();
		zoneAdv.setRect(400, 75, 250, 250);

		if(zoneAdv.contains(e.getPoint()) && this.grilleAdvEstActive)
		{
			char cj = this.frmJeu.getCtrl().joueurJoue((e.getX() - 400) / 25, (e.getY() - 75) / 25);
			if(cj != 'R')
			{
				if(!this.frmJeu.getCtrl().estMulti()) {this.grilleAdvEstActive = false;}
				if(this.frmJeu.getCtrl().estMulti()) {this.frmJeu.setCoup(new int[]{(e.getX() - 400) / 25, (e.getY() - 75) / 25});}
				this.envoyerLogScoreJ(cj, (e.getX() - 400) / 25, (e.getY() - 75) / 25);
				if(this.frmJeu.getCtrl().quelqunGagne() == 'J' && !this.frmJeu.getCtrl().estMulti())
				{
					this.frmJeu.partieFini('J');
					return;
				}

				if(!this.frmJeu.getCtrl().estMulti()) {this.envoyerLogScoreA(this.frmJeu.getCtrl().advJoue(""));}
				if(this.frmJeu.getCtrl().quelqunGagne() == 'A' && !this.frmJeu.getCtrl().estMulti()) {this.frmJeu.partieFini('A');}
				if(!this.frmJeu.getCtrl().estMulti()) {this.grilleAdvEstActive = true;}
			}
		}

		Rectangle2D zoneJoueur = new Rectangle2D.Double();
		zoneJoueur.setRect(50, 75, 250, 250);

		if(zoneJoueur.contains(e.getPoint()) && !this.frmJeu.getCtrl().navireJoueurPlace())
		{
			try{
				this.positions.remove(contains(this.positions, new int[]{(e.getX() - 50) / 25, (e.getY() - 75) / 25}));
			}catch(Exception ex) {
				if(!this.frmJeu.getCtrl().getPlateauJoueur().getCellule((e.getX() - 50) / 25, (e.getY() - 75) / 25).getNavire())
				{
					this.positions.add(new int[]{(e.getX() - 50) / 25, (e.getY() - 75) / 25});
				}
			}
		}

		this.repaint();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Cette méthode envoie les log du joueur en fonction du coup joué et joue un son (si activé).
	 *
	 * @param coup Le résultat du coup joué ('E' pour eau, 'T' pour touché, 'C' pour coulé, 'R' pour raté).
	 * @param x    La coordonnée en X du coup.
	 * @param y    La coordonnée en Y du coup.
	 */
	public void envoyerLogScoreJ(char coup, int x, int y)
	{
		if(coup == 'R') {return;}
		switch(coup)
		{
			case 'E':
				((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez tiré dans l'eau en " + (char) ('A' + x) + (y + 1));
				Sound.clipEauOn();break;
			case 'T':
				((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez touché un navire en " + (char) ('A' + x) + (y + 1));
				Sound.clipTirOn();break;
			case 'C':
				((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez coulé un navire en " + (char) ('A' + x) + (y + 1));
				Sound.clipBateauOn();break;
		}

		this.nbCoupJoueur++;
		if(coup != 'E') {this.nbNaviresToucheJoueur++;}

		((PanelMessage) this.frmJeu.pnlBas).ajouterScoreJ("Vous avez coulé " + this.frmJeu.getCtrl().getNbNaviresCouleJoueur() + " navires",
				"Avec une précision de " + Math.round(this.nbNaviresToucheJoueur / (float) this.nbCoupJoueur * 100) + " %");
	}

	/**
	 * Cette méthode envoie les log de l'adversaire en fonction du coup joué.
	 *
	 * @param coup Le résultat du coup joué ('E' pour eau, 'T' pour touché, 'C' pour coulé, 'R' pour raté).
	 */
	public void envoyerLogScoreA(char coup)
	{
		if(coup == 'R') {return;}
		switch(coup)
		{
			case 'E': ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a tiré dans l'eau");break;
			case 'T': ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a touché un navires");break;
			case 'C': ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a coulé un navires");break;
		}

		this.nbCoupAdv++;
		if(coup != 'E') {this.nbNaviresToucheAdv++;}

		((PanelMessage) this.frmJeu.pnlBas).ajouterScoreA("Votre adversaire a coulé " + this.frmJeu.getCtrl().getNbNaviresCouleAdv() + " navires",
				"Avec une précision de " + Math.round(this.nbNaviresToucheAdv / (float) this.nbCoupAdv * 100) + " %");
	}

	/**
	 * Cette méthode affiche le plateau entier de l'adversaire, si le joueur a perdu, afin de lui montrer les navire qu'il n'a pas trouvé.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	public void afficherAdv(Graphics2D g2)
	{
		for(Cellule[] tab : this.frmJeu.getCtrl().getPlateauAdv().getGrille())
		{
			for(Cellule cell : tab)
			{
				String suff = "";
				if(!cell.getTirer() && cell.getNavire()) {suff = "selec";}
				if(cell.getNavire())
				{
					char c = this.frmJeu.getCtrl().getPlateauAdv().extremite(cell.getX(), cell.getY());
					if(c != 'R')
					{
						String nomImg = "image/navireExtremite" + c + suff + ".png";
						g2.drawImage(new ImageIcon(this.getClass().getResource(nomImg)).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
					else
					{
						g2.drawImage(new ImageIcon(this.getClass().getResource("image/navireMilieu" + suff + ".png")).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
					}
				}
				else if(cell.getTirer() && !cell.getNavire())
				{
					g2.drawImage(new ImageIcon(this.getClass().getResource("image/tirEau.png")).getImage(), 402 + cell.getX() * 25, 77 + cell.getY() * 25, 21, 21, this);
				}
			}
		}
	}
}
