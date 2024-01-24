/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Le PanelMessage est une classe représentant un panneau affichant des informations
 * telles que les logs, les scores et l'état du tour de jeu.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class PanelMessage extends JPanel
{
	/**
	 * Tableau de logs du joueur.
	 */
	private String[] logsJ;

	/**
	 * Tableau de logs de l'adversaire.
	 */
	private String[] logsA;

	/**
	 * Score du joueur.
	 */
	private String scoreJ;

	/**
	 * Précision du joueur.
	 */
	private String precisionJ;

	/**
	 * Score de l'adversaire.
	 */
	private String scoreA;

	/**
	 * Précision de l'adversaire.
	 */
	private String precisionA;

	/**
	 * Indique le tour de jeu en cours.
	 */
	private String aQuiLeTour;

	/**
	 * Constructeur de la classe PanelMessage.
	 *
	 * @param frmJeu La FrameJeu associée.
	 */
	public PanelMessage(FrameJeu frmJeu)
	{
		this.logsJ = new String[]{"", "", "", "", ""};
		this.logsA = new String[]{"", "", "", "", ""};
		this.scoreJ = this.precisionJ = "";
		this.scoreA = this.precisionA = "";

		this.aQuiLeTour = "C'est à votre tour de jouer !!!!";
		if(frmJeu.estServeur() == 'S') {this.aQuiLeTour = "En attente de votre adversaire";}

		this.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Cette méthode est responsable de la peinture du composant. Elle dessine les logs, les scores
	 * et autres informations graphiques à l'aide de l'objet Graphics2D fourni en argument.
	 *
	 * @param g L'objet Graphics utilisé pour le dessin.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.blue);
		g2.setFont(new Font("FreeSans", Font.BOLD, 12));
		dessinerLogs(g2);
		dessinerScores(g2);
	}

	/**
	 * Cette méthode dessine les logs du joueur et de l'adversaire sur le composant en utilisant l'objet Graphics2D fourni.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	private void dessinerLogs(Graphics2D g2)
	{
		// Logs du Joueur
		g2.drawString("->", 380, 50);
		for(int index = 0; index < this.logsJ.length; index++)
		{
			if(this.logsJ[index].contains("coulé")) {g2.setColor(new Color(171, 40, 40));}
			else if(this.logsJ[index].contains("touché")) {g2.setColor(new Color(214, 103, 0));}
			else {g2.setColor(Color.blue);}

			g2.drawString(this.logsJ[index], 405, 50 + 25 * index);
			g2.setColor(Color.blue);
		}

		// Logs de l'Adversaire
		g2.drawString("->", 30, 50);
		for(int index = 0; index < this.logsA.length; index++)
		{
			if(this.logsA[index].contains("coulé")) {g2.setColor(new Color(171, 40, 40));}
			else if(this.logsA[index].contains("touché")) {g2.setColor(new Color(214, 103, 0));}
			else {g2.setColor(Color.blue);}

			g2.drawString(this.logsA[index], 55, 50 + 25 * index);
			g2.setColor(Color.blue);
		}
	}

	/**
	 * Cette méthode dessine les scores des joueurs ainsi que l'indication du tour de jeu sur le composant
	 * en utilisant l'objet Graphics2D fourni.
	 *
	 * @param g2 L'objet Graphics2D utilisé pour le dessin.
	 */
	private void dessinerScores(Graphics2D g2) 
	{
		g2.drawString(scoreJ, 400, 220);
		g2.drawString(precisionJ, 400, 250);

		g2.drawString(scoreA, 50, 220);
		g2.drawString(precisionA, 50, 250);

		g2.drawString(aQuiLeTour, 255, 310);

		g2.setColor(Color.red);
		if(!this.scoreJ.equals("")) {g2.drawLine(390, 190, 650, 190);}
		if(!this.scoreA.equals("")) {g2.drawLine(40, 190, 300, 190);}

		if(Sound.getOs().equals("linux")) {g2.drawRect(240, 285, 240, 40);}
		else {g2.drawRect(240, 285, 200, 40);}
		g2.setColor(Color.blue);
	}

	/**
	 * Ajoute un log au tableau des logs du joueur.
	 *
	 * @param log Le log à ajouter.
	 */
	public void ajouterLogsJ(String log)
	{
		for(int index = logsJ.length - 1; index > 0; index--) {logsJ[index] = logsJ[index - 1];}
		logsJ[0] = log;
		this.repaint();
	}

	/**
	 * Ajoute un log au tableau des logs de l'adversaire.
	 *
	 * @param log Le log à ajouter.
	 */
	public void ajouterLogsA(String log)
	{
		for(int index = logsA.length - 1; index > 0; index--) {logsA[index] = logsA[index - 1];}
		logsA[0] = log;
		this.repaint();
	}

	/**
	 * Définit le score et la précision du joueur.
	 *
	 * @param score     Le score du joueur.
	 * @param precision La précision du joueur.
	 */
	public void ajouterScoreJ(String score, String precision)
	{
		this.scoreJ = score;
		this.precisionJ = precision;
	}

	/**
	 * Définit le score et la précision de l'adversaire.
	 *
	 * @param score     Le score de l'adversaire.
	 * @param precision La précision de l'adversaire.
	 */
	public void ajouterScoreA(String score, String precision)
	{
		this.scoreA = score;
		this.precisionA = precision;
	}

	/**
	 * Définit l'état du tour de jeu.
	 *
	 * @param s L'état du tour de jeu à afficher.
	 */
	public void setAQuiLeTour(String s)
	{
		this.aQuiLeTour = s;
		this.repaint();
	}
}
