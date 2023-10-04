package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelSelectBateau extends JPanel implements MouseListener, ActionListener
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
	private JButton  btnValider;
	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private JButton  btnAutoBateau;
	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private JLabel  lblMessage;
	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private JLabel  lblMessageErreur;

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private char[]  etat = {'N'/*2*/,'N'/*5*/,'N'/*3*/,'N'/*3*/,'N'/*4*/};

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private Integer idBSelec;

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
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		
		this.idBSelec = null;
		this.btnValider = new JButton("Valider");
		this.btnAutoBateau = new JButton("Auto");
		this.lblMessage = new JLabel("Veuillez placer tout vos navires");
		this.lblMessageErreur = new JLabel();

		this.btnValider.setBorder(new RoundBtn(15));
		this.btnAutoBateau.setBorder(new RoundBtn(15));

		this.btnValider.setBounds(400, 50, 100, 50);
		this.btnAutoBateau.setBounds(550, 50, 100, 50);
		this.lblMessage.setBounds(410, 120, 250, 50);
		this.lblMessageErreur.setBounds(170, 240, 500, 50);

		this.add(this.btnValider);
		this.add(this.btnAutoBateau);
		this.add(this.lblMessage);
		this.add(this.lblMessageErreur);

		this.btnValider.addActionListener(this);
		this.btnAutoBateau.addActionListener(this);
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

	public void setEtatP()
	{
		for (int i = 0; i < etat.length; i++) {
			this.etat[i] = 'P';
		}
	}

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
				this.idBSelec = null;
		}

		if(x > 50 && x < 110 && y > 25 && y < 50 && etat[0] != 'P'  ) {etat[0] = 'S'; this.idBSelec = 0;}
		if(x > 150 && x < 300 && y > 25 && y < 50 && etat[1] != 'P' ) {etat[1] = 'S'; this.idBSelec = 1;}
		if(x > 50 && x < 140 && y > 95 && y < 120 && etat[2] != 'P' ) {etat[2] = 'S'; this.idBSelec = 2;}
		if(x > 180 && x < 270 && y > 95 && y < 120 && etat[3] != 'P') {etat[3] = 'S'; this.idBSelec = 3;}
		if(x > 50 && x < 180 && y > 165 && y < 190 && etat[4] != 'P') {etat[4] = 'S'; this.idBSelec = 4;}
		this.repaint();
	}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnValider)
		{
			int[][] tab = this.frmJeu.pnlJeu.recupPos();
			int taille = 0;
			if(this.idBSelec != null)
			{
				switch(this.idBSelec)
				{
					case 0 : taille = 2;break;
					case 1 : taille = 5;break;
					case 2 : taille = 3;break;
					case 3 : taille = 3;break;
					case 4 : taille = 4;break;
				}
			}
			else {this.lblMessageErreur.setText("ERREUR : Vous devez selectionner un bateau !!!");}

			if(taille == tab[0].length && this.idBSelec != null && frmJeu.getCtrl().ajouterNavire(taille, tab)) {etat[this.idBSelec] = 'P';}
			else if (this.idBSelec != null) 
			{
				etat[this.idBSelec] = 'N';
				this.idBSelec = null;
				this.lblMessageErreur.setText("ERREUR : Votre bateau ne peut pas être placé !!!");
			}
		}
		if ( e.getSource() == this.btnAutoBateau )
		{
			this.frmJeu.getCtrl().ajouterNaviresJoueur(this.etat);
			this.setEtatP();
		}

		if (this.frmJeu.getCtrl().navireJoueurPlace())
			this.frmJeu.navireJoueurPlace();

		this.repaint();
		this.frmJeu.pnlJeu.repaint();
	}
}

class RoundBtn implements Border
{
	private int r;

	RoundBtn(int r) 
	{
		this.r = r;
	}

	public Insets getBorderInsets(Component c) 
	{
		return new Insets(this.r+1, this.r+1, this.r+2, this.r);
	}

	public boolean isBorderOpaque() {return true;}

	public void paintBorder(Component c, Graphics g, int x, int y, 
	int width, int height) 
	{
		g.drawRoundRect(x, y, width-1, height-1, r, r);
	}
}