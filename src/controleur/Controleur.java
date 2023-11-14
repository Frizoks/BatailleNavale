/**
 * Package controleur : ce package est une passerelle entre le metier et l'ihm.
 */
package controleur;

import metier.*;
import ihm.*;

/**
 * La classe Controleur est responsable de la gestion de la logique du jeu.
 * Elle contrôle les interactions entre le joueur, l'adversaire, et l'interface utilisateur.
 *
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */

public class Controleur
{
	/**
	 * Une référence à la classe Robot, utilisée pour simuler un adversaire.
	 */
	private Robot rbt;

	/**
	 * Une instance de la classe Joueur, représentant un joueur dans le jeu.
	 */
	private Joueur joueur;

	/**
	 * Une instance de la classe Adversaire, représentant un adversaire dans le jeu.
	 */
	private Adversaire adv;

	/**
	 * Un indicateur booléen indiquant si le jeu est en mode multijoueur.
	 */
	private boolean multijoueur;

	/**
	 * Constructeur de la classe Controleur.
	 * Il crée une instance de la classe FrameAccueil pour commencer le jeu.
	 */
	public Controleur()
	{
		new FrameAccueil(this);
	}

	/**
	 * Démarre une partie solo avec la difficulté spécifiée.
	 *
	 * @param difficulte La difficulté de la partie (F: Facile, M: Moyen, D: Difficile, I: Impossible)
	 */
	public void partieSolo(char difficulte)
	{
		this.rbt = new Robot(this, difficulte);
		this.joueur = new Joueur();
		this.multijoueur = false;
	}

	/**
	 * Démarre une partie multijoueur.
	 */
	public void partieMulti()
	{
		this.adv = new Adversaire(this);
		this.joueur = new Joueur();
		this.multijoueur = true;
	}

	/**
	 * Gère le coup joué par le joueur.
	 *
	 * @param x Coordonnée X du coup
	 * @param y Coordonnée Y du coup
	 * @return Le résultat du coup (R: Raté, T: Touché, C: Coulé)
	 */
	public char joueurJoue(int x, int y)
	{
		char c = 'R';
		if(multijoueur) {c = adv.getPlateau().jouerCoup(x, y);}
		else {c = rbt.getPlateau().jouerCoup(x, y);}
		if(c == 'C') {joueur.ajtNavireCoule();}
		return c;
	}

	/**
	 * Gère le coup joué par l'adversaire ou par le robot, en fonction du mode de jeu.
	 *
	 * @param s La chaîne représentant le coup de l'adversaire (multijoueur)
	 * @return Le résultat du coup (R: Raté, T: Touché, C: Coulé)
	 */
	public char advJoue(String s)
	{
		char c = 'R';
		if(multijoueur) {c = adv.jouerCoup(s);}
		else
		{
			switch (rbt.getDiffficulte())
			{
				case 'F': c = rbt.jouerCoupFacile();break;
				case 'M': c = rbt.jouerCoupMoyen();break;
				case 'D': c = rbt.jouerCoupDifficile();break;
				case 'I': c = rbt.jouerCoupImpossible();break;
			}
		}
		if(c == 'C')
		{
			if(multijoueur) {adv.ajtNavireCoule();}
			else {rbt.ajtNavireCoule();}
		}
		return c;
	}

	/**
	 * Obtient le nombre de navires coulés par le joueur.
	 *
	 * @return Le nombre de navires coulés par le joueur
	 */
	public int getNbNaviresCouleJoueur() {return this.joueur.getNbBateauCoule();}

	/**
	 * Obtient le nombre de navires coulés par l'adversaire ou le robot, en fonction du mode de jeu.
	 *
	 * @return Le nombre de navires coulés par l'adversaire ou le robot
	 */
	public int getNbNaviresCouleAdv()
	{
		if(multijoueur) {return adv.getNbBateauCoule();}
		else {return rbt.getNbBateauCoule();}
	}

	/**
	 * Indique si le joueur a placé tous ses navires.
	 *
	 * @return true si le joueur a placé tous ses navires, sinon false
	 */
	public boolean navireJoueurPlace() {return joueur.naviresTousAjoute();}

	/**
	 * Indique si tous les navires sont ajoutés, à la fois pour le joueur et l'adversaire.
	 *
	 * @return true si tous les navires sont ajoutés, sinon false
	 */
	public boolean naviresTousAjoute() {return (joueur.naviresTousAjoute() && adv.getNaviresTousAjoute());}

	/**
	 * Ajoute un navire avec une taille et des positions spécifiées pour le joueur.
	 *
	 * @param taille      La taille du navire
	 * @param positions   Les positions du navire sur le plateau
	 * @return true si le navire est ajouté avec succès, sinon false
	 */
	public boolean ajouterNavire(int taille, int[][] positions) {return joueur.ajouterNavire(taille, positions);}

	/**
	 * Ajoute les navires du joueur à partir de l'état spécifié.
	 *
	 * @param etat Les états des navires du joueur
	 */
	public void ajouterNaviresJoueur(char[] etat) {joueur.ajouterNavires(etat);}

	/**
	 * Ajoute un plateau pour l'adversaire à partir de la chaîne spécifiée.
	 *
	 * @param s La chaîne représentant le plateau de l'adversaire
	 */
	public void ajouterPlateauAdv(String s) {this.adv.ajouterPlateau(s);}

	/**
	 * Obtient le plateau de jeu du joueur.
	 *
	 * @return Le plateau de jeu du joueur
	 */
	public Plateau getPlateauJoueur() {return joueur.getPlateau();}

	/**
	 * Obtient le plateau de jeu de l'adversaire ou du robot, en fonction du mode de jeu.
	 *
	 * @return Le plateau de jeu de l'adversaire ou du robot
	 */
	public Plateau getPlateauAdv()
	{
		if(multijoueur) {return adv.getPlateau();}
		else {return rbt.getPlateau();}
	}

	/**
	 * Vérifie si quelqu'un a gagné la partie.
	 *
	 * @return 'J' si le joueur a gagné, 'A' si l'adversaire ou le robot a gagné, 'N' si la partie n'est pas encore terminée
	 */
	public char quelqunGagne()
	{
		if(!multijoueur && this.rbt.partiePerdu()) {return 'J';}
		else if(multijoueur && this.adv.partiePerdu()) {return 'J';}

		if(this.joueur.partiePerdu()) {return 'A';}

		return 'N';
	}

	/**
	 * Indique si le jeu est en mode multijoueur.
	 *
	 * @return true si le jeu est en mode multijoueur, sinon false
	 */
	public boolean estMulti() {return this.multijoueur;}

	/**
	 * Point d'entrée du programme. Crée une instance de Controleur pour démarrer le jeu.
	 *
	 * @param args Les arguments de la ligne de commande (non utilisés)
	 */
	public static void main(String[] args)
	{
		new Controleur();
	}
}