package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class PanelJeu extends JPanel implements MouseListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private FrameJeu  frmJeu;

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
	}

	public void dessinerGrilleJ2( Graphics2D g2 )
	{
		for (int cpt = 0; cpt < 11; cpt++)
		{
			g2.drawLine(25*cpt+400,75,25*cpt+400,325);
			g2.drawLine(400,25*cpt+75,650,25*cpt+75);
		}
	}

	public void placerDecor(Graphics2D g2)
	{
		g2.drawString("Votre Plateau",125,25);
		g2.drawString("Plateau de l'adversaire",425,25);

		for (int cpt = 0; cpt < 10; cpt++)
		{
			g2.drawString(""+(char)('A'+cpt),25*cpt+60,70);
			g2.drawString(""+(cpt+1),30,25*cpt+95);

			g2.drawString(""+(char)('A'+cpt),25*cpt+410,70);
			g2.drawString(""+(cpt+1),660,25*cpt+95);
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		if (new Recta)


		this.repaint();
	}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}