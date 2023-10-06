package ihm;

import java.awt.*;
import javax.swing.JPanel;

public class PanelMessage extends JPanel
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
	private String[]  logsJ;
	private String[]  logsA;

	private String scoreJ;
	private String scoreA;
	private String precisionJ;
	private String precisionA;

	private String aQuiLeTour;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public PanelMessage ( FrameJeu frmJeu )
	{
		this.frmJeu = frmJeu;
		this.logsJ = new String[] {"","","","",""};
		this.logsA = new String[] {"","","","",""};
		this.scoreJ = this.precisionJ = "";
		this.scoreA = this.precisionA = "";
		
		this.aQuiLeTour = "C'est à votre tour de jouer !!!!";
		if(frmJeu.estServeur() == 'S') {this.aQuiLeTour = "En attente de votre adversaire";}

		this.setBackground(Color.LIGHT_GRAY);
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		g2.setColor(Color.blue);
		g2.setFont(new Font("FreeSans",Font.BOLD,12));
		dessinerLogs(g2);
		dessinerScores(g2);
	}

	public void dessinerLogs(Graphics2D g2)
	{
		//Logs du J
		g2.drawString("->",380,50);
		for (int index = 0; index < this.logsJ.length; index++)
		{
			if(this.logsJ[index].contains("coulé")) {g2.setColor(new Color(171,40,40));}
			else if(this.logsJ[index].contains("touché")) {g2.setColor(new Color(214, 103, 0));}
			else {g2.setColor(Color.blue);}

			g2.drawString(this.logsJ[index],405,50+25*index);
			g2.setColor(Color.blue);
		}

		//Logs du A
		g2.drawString("->",30,50);
		for (int index = 0; index < this.logsA.length; index++)
		{
			if(this.logsA[index].contains("coulé")) {g2.setColor(new Color(171,40,40));}
			else if(this.logsA[index].contains("touché")) {g2.setColor(new Color(214, 103, 0));}
			else {g2.setColor(Color.blue);}

			
			g2.drawString(this.logsA[index],55,50+25*index);
			g2.setColor(Color.blue);
		}
	}

	public void dessinerScores(Graphics2D g2)
	{
		g2.drawString(scoreJ,400,220);
		g2.drawString(precisionJ,400,250);
		
		g2.drawString(scoreA,50,220);
		g2.drawString(precisionA,50,250);
		
		g2.drawString(aQuiLeTour,255,310);

		g2.setColor(Color.red);
		if(!this.scoreJ.equals("")) {g2.drawLine(390, 190, 650, 190);}
		if(!this.scoreA.equals("")) {g2.drawLine(40, 190, 300, 190);}
		if(System.getProperty("os.name").indexOf("nux") >= 0) {g2.drawRect(240, 285, 240, 40);}
		else {g2.drawRect(240, 285, 200, 40);}
		g2.setColor(Color.blue);
	}

	public void ajouterLogsJ(String log)
	{
		for (int index = logsJ.length - 1; index > 0; index--) {
			logsJ[index] = logsJ[index-1];
		}
		logsJ[0] = log;
		this.repaint();
	}

	public void ajouterLogsA(String log)
	{
		for (int index = logsA.length - 1; index > 0; index--) {
			logsA[index] = logsA[index-1];
		}
		logsA[0] = log;
		this.repaint();
	}

	public void ajouterScoreJ(String score, String precision)
	{
		this.scoreJ = score;
		this.precisionJ = precision;
	}

	public void ajouterScoreA(String score, String precision)
	{
		this.scoreA = score;
		this.precisionA = precision;
	}

	public void setAQuiLeTour(String s) {this.aQuiLeTour = s;this.repaint();}
}