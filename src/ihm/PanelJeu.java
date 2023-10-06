package ihm;

import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import metier.*;

public class PanelJeu extends JPanel implements MouseListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private FrameJeu  frmJeu;

	private boolean grilleAdvEstActive;

	private ArrayList<int[]> positions;

	private int nbCoupJoueur;
	private int nbNaviresToucheJoueur;
	private int nbCoupAdv;
	private int nbNaviresToucheAdv;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public PanelJeu ( FrameJeu frmJeu )
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

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		g2.setColor(Color.BLUE);
		this.dessinerGrilleJ1(g2);
		this.dessinerGrilleJ2(g2);
		this.placerDecor(g2);

		g2.setColor(Color.RED);
		g2.drawLine(350,50,350,350);
	}

	public void dessinerGrilleJ1( Graphics2D g2 )
	{
		for (int cpt = 0; cpt < 11; cpt++)
		{
			g2.drawLine(25*cpt+50,75,25*cpt+50,325);
			g2.drawLine(50,25*cpt+75,300,25*cpt+75);
		}

		for (Cellule[] tab : this.frmJeu.getCtrl().getPlateauJoueur().getGrille()) {
			for (Cellule cell : tab) {
				if (this.frmJeu.getCtrl().getPlateauJoueur().getCellule(cell.getX(),cell.getY()).getNavire())
				{
					switch (this.frmJeu.getCtrl().getPlateauJoueur().extremite(cell.getX(),cell.getY())) {
						case 'G':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteGplace.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'D':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteDplace.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'H':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteHplace.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'B':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteBplace.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
					
						default:
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieuplace.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
					}
					
				}
				if ( this.frmJeu.getCtrl().getPlateauJoueur().getCellule(cell.getX(),cell.getY()).getTirer() )
					g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/croix.png" ),52+cell.getX()*25,77+cell.getY()*25,21,21,this);
			}
		}

		for (int[] tab : this.positions)
		{
			int x = tab[0];
			int y = tab[1];
			g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireSelec.png" ),52+x*25,77+y*25,21,21,this);
		}
	}

	public void dessinerGrilleJ2( Graphics2D g2 )
	{
		for (int cpt = 0; cpt < 11; cpt++)
		{
			g2.drawLine(25*cpt+400,75,25*cpt+400,325);
			g2.drawLine(400,25*cpt+75,650,25*cpt+75);
		}
		for (Cellule[] tab : this.frmJeu.getCtrl().getPlateauAdv().getGrille()) {
			for (Cellule cell : tab) {
				if (this.frmJeu.getCtrl().getPlateauAdv().navireEntier(cell.getX(),cell.getY()))
				{
					switch (this.frmJeu.getCtrl().getPlateauAdv().extremite(cell.getX(),cell.getY())) {
						case 'G':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'D':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'H':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteH.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
						case 'B':
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteB.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
					
						default:
							g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
							break;
					}
				}
				else if (cell.getTirer()) {
					if (cell.getNavire())
						g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireTouche.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
					else
						g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/tirEau.png" ),402+cell.getX()*25,77+cell.getY()*25,21,21,this);
				}
			}
		}
	}

	public void placerDecor(Graphics2D g2)
	{
		g2.drawString("Votre Plateau",125,35);
		g2.drawString("Plateau de l'adversaire",450,35);

		for (int cpt = 0; cpt < 10; cpt++)
		{
			g2.drawString(""+(char)('A'+cpt),25*cpt+60,70);
			g2.drawString(""+(cpt+1),30,25*cpt+95);

			g2.drawString(""+(char)('A'+cpt),25*cpt+410,70);
			g2.drawString(""+(cpt+1),660,25*cpt+95);
		}
	}

	public int[][] recupPos()
	{
		int[][] temp = new int[2][this.positions.size()];

		for (int index = 0; index < this.positions.size(); index++) {
			temp[0][index] = this.positions.get(index)[0];
			temp[1][index] = this.positions.get(index)[1];
		}

		this.positions.clear();
		return temp;
	}

	public void viderPos() {this.positions.clear();}

	public int contains(ArrayList<int[]> positions, int[] tab)
	{
		for (int index = 0; index < positions.size(); index++) {
			if (positions.get(index)[0] == tab[0] && positions.get(index)[1] == tab[1])
				return index;
		}
		return -1;
	}

	public void grilleAdvActive()
	{
		this.grilleAdvEstActive = !this.grilleAdvEstActive;

		if(this.grilleAdvEstActive) {((PanelMessage) this.frmJeu.pnlBas).setAQuiLeTour("C'est à votre tour de jouer !!!!");}
		if(!this.grilleAdvEstActive) {((PanelMessage) this.frmJeu.pnlBas).setAQuiLeTour("En attente de votre adversaire");}
	}

	public void mouseClicked(MouseEvent e)
	{
		Rectangle2D zoneAdv = new Rectangle2D.Double ( );
		zoneAdv.setRect ( 400, 75,250,250);

		if ( zoneAdv.contains ( e.getPoint ( ) ) && this.grilleAdvEstActive)
		{
			char cj = this.frmJeu.getCtrl().joueurJoue((e.getX()-400)/25,(e.getY()-75)/25);
			if(cj != 'R')
			{
				if ( this.frmJeu.getCtrl().estMulti())
					this.frmJeu.setCoup(new int[] {(e.getX()-400)/25,(e.getY()-75)/25});

				this.envoyerLogScoreJ(cj, (e.getX()-400)/25, (e.getY()-75)/25);

				this.grilleAdvEstActive = false;

				if ( this.frmJeu.getCtrl().quelqunGagne() == 'J'&& !this.frmJeu.getCtrl().estMulti() ) {this.frmJeu.partieFini('J');}


				if ( !this.frmJeu.getCtrl().estMulti())
					this.envoyerLogScoreA(this.frmJeu.getCtrl().advJoue(""));
				

				if ( this.frmJeu.getCtrl().quelqunGagne() == 'A' && !this.frmJeu.getCtrl().estMulti() ) {this.frmJeu.partieFini('A');}
				this.grilleAdvEstActive = true;
			}
		}

		Rectangle2D zoneJoueur = new Rectangle2D.Double ( );
		zoneJoueur.setRect ( 50, 75,250,250);

		if ( zoneJoueur.contains ( e.getPoint ( ) ) && !this.frmJeu.getCtrl().navireJoueurPlace())
		{
			try {
				this.positions.remove(contains(this.positions,new int[] {(e.getX()-50)/25,(e.getY()-75)/25}));
			} catch (Exception ex) {
				this.positions.add(new int[] {(e.getX()-50)/25,(e.getY()-75)/25});
			}
		}

		this.repaint();
	}

	public void envoyerLogScoreJ(char coup, int x, int y)
	{
		switch(coup)
		{
			case 'E' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez tiré dans l'eau en " + (char)('A' + x) +(y + 1));break;
			case 'T' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez touché un navire en " + (char)('A' + x) +(y + 1));break;
			case 'C' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsJ("Vous avez coulé un navire en " + (char)('A' + x) +(y + 1));break;
		}

		this.nbCoupJoueur++;
		if(coup != 'E') {this.nbNaviresToucheJoueur++;}

		((PanelMessage) this.frmJeu.pnlBas).ajouterScoreJ("Vous avez coulé " + this.frmJeu.getCtrl().getNbNaviresCouleJoueur() + " navires", 
														  "Avec une précision de " + Math.round(this.nbNaviresToucheJoueur/(float)this.nbCoupJoueur*100) + " %");
	}

	public void envoyerLogScoreA(char coup)
	{
		switch(coup)
		{
			case 'E' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a tiré dans l'eau");break;
			case 'T' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a touché un navires");break;
			case 'C' : ((PanelMessage) this.frmJeu.pnlBas).ajouterLogsA("L'adversaire a coulé un navires");break;
		}

		this.nbCoupAdv++;
		if(coup != 'E') {this.nbNaviresToucheAdv++;}

		((PanelMessage) this.frmJeu.pnlBas).ajouterScoreA("Votre adversaire a coulé " + this.frmJeu.getCtrl().getNbNaviresCouleAdv() + " navires", 
														  "Avec une précision de " + Math.round(this.nbNaviresToucheAdv/(float)this.nbCoupAdv*100) + " %");
	}

	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}