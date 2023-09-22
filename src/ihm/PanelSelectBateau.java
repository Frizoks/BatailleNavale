package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class PanelSelectBateau extends JPanel implements MouseListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private FrameJeu  frmJeu;

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private char[]  etat = {'N','N','N','N','N'};

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public PanelSelectBateau ( FrameJeu frmJeu )
	{
		this.frmJeu = frmJeu;
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseListener(this);
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		g2.setColor(new Color(189, 67, 67)); //R : 189, V : 67, B : 67
		this.dessinerBateau(g2);
	}

	public void dessinerBateau(Graphics2D g2)
	{
		// Affiche le petit Bateau de 2
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG" + getNomImg(0) + ".png" ),50,25,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD" + getNomImg(0) + ".png" ),80,25,25,25,this);

		// Affiche le grand bateau de 5 a coté du 2
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG" + getNomImg(1) + ".png" ),150,25,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(1) + ".png" )    ,180,25,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(1) + ".png" )    ,210,25,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(1) + ".png" )    ,240,25,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD" + getNomImg(1) + ".png" ),270,25,25,25,this);

		// Affiche les 2 bateau de 3 cote cote
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG" + getNomImg(2) + ".png" ),50,95,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(2) + ".png"     ),80,95,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD" + getNomImg(2) + ".png" ),110,95,25,25,this);

		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG" + getNomImg(3) + ".png" ),180,95,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(3) + ".png"     ),210,95,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD" + getNomImg(3) + ".png" ),240,95,25,25,this);

		// Affiche le bateau de 4
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteG" + getNomImg(4) + ".png" ),50,165,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(4) + ".png"     ),80,165,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireMilieu" + getNomImg(4) + ".png"     ),110,165,25,25,this);
		g2.drawImage(Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/navireExtremiteD" + getNomImg(4) + ".png" ),140,165,25,25,this);
	}

	/*public void milieuNavire(Graphics2D g2, int x, int y)
	{
		g2.fillRoundRect(x,y,25,25,10,10);
	}

	public void extremiteGauche(Graphics2D g2, int x, int y)
	{
		g2.fillOval(x - 7,y,25,25);
		g2.fillRoundRect(x,y,25,25,10,10);
	}

	public void extremiteDroite(Graphics2D g2, int x, int y)
	{
		g2.fillOval(x - 7,y,25,25);
		g2.fillRoundRect(x,y,25,25,10,10);
	}

	public void extremiteHaute(Graphics2D g2, int x, int y)
	{
		g2.fillOval(x - 7,y,25,25);
		g2.fillRoundRect(x,y,25,25,10,10);
	}

	public void extremiteBasse(Graphics2D g2, int x, int y)
	{
		g2.fillOval(x - 7,y,25,25);
		g2.fillRoundRect(x,y,25,25,10,10);
	}*/

	public String getNomImg(int i)
	{
		switch (etat[i])
		{
			case 'S' : return "selec";
			case 'P' : return "place";
			default : return "";
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		for (int index = 0; index < etat.length; index++) {
				if ( etat[index] != 'P' ){ etat[index] = 'N'; }
		}

		if(x > 50 && x < 110 && y > 25 && y < 50 && etat[0] != 'P'  ) {etat[0] = 'S';}
		if(x > 150 && x < 300 && y > 25 && y < 50 && etat[1] != 'P' ) {etat[1] = 'S';}
		if(x > 50 && x < 140 && y > 95 && y < 120 && etat[2] != 'P' ) {etat[2] = 'S';}
		if(x > 180 && x < 270 && y > 95 && y < 120 && etat[3] != 'P') {etat[3] = 'S';}
		if(x > 50 && x < 180 && y > 165 && y < 190 && etat[4] != 'P') {etat[4] = 'S';}
		this.repaint();
	}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}