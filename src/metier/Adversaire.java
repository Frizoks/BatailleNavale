/**
 * Package metier : ce package contient toutes les classes permettant de stocker les données, vérifier les placements et les tirs, et enfin de jouer à notre Bataille Navale.
 */
package metier;

import controleur.Controleur;

/**
 * Cette classe représente un adversaire dans le jeu de la bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Adversaire 
{
	/**
	 * Le plateau de jeu de l'adversaire.
	 */
	protected Plateau plateau;

	/**
	 * Le contrôleur utilisé pour accéder aux fonctionnalités du jeu.
	 */
	private Controleur ctrl;

	/**
	 * Le nombre de navires coulés par le joueur au cours de la partie.
	 */
	private int nbNaviresCoule;

	/**
	 * Un indicateur booléen indiquant si tout les navires de l'adversaire sont ajoutés.
	 */
	private boolean naviresTousAjoute;

	/**
	 * Constructeur de la classe Adversaire qui initialise un adversaire avec un plateau vide.
	 *
	 * @param ctrl Le contrôleur associé à l'adversaire.
	 */
	public Adversaire(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.plateau = new Plateau();
		this.nbNaviresCoule = 0;
		this.naviresTousAjoute = false;
	}

	/**
	 * Récupère le plateau de l'adversaire.
	 *
	 * @return Le plateau de l'adversaire.
	 */
	public Plateau getPlateau() {return this.plateau.getPlateau();}

	/**
	 * Vérifie si tous les navires de l'adversaire sont placés.
	 *
	 * @return Vrai si tous les navires sont placés, sinon faux.
	 */
	public boolean getNaviresTousAjoute() {return this.naviresTousAjoute;}

	/**
	 * Marque tous les navires de l'adversaire comme étant placés.
	 */
	public void setNaviresTousAjoute() {this.naviresTousAjoute = true;}

	/**
	 * Récupère le nombre de navires coulés par l'adversaire.
	 *
	 * @return Le nombre de navires coulés par l'adversaire.
	 */
	public int getNbBateauCoule() {return this.nbNaviresCoule;}

	/**
	 * Incrémente le nombre de navires coulés par l'adversaire.
	 */
	public void ajtNavireCoule() {this.nbNaviresCoule++;}

	/**
	 * Vérifie si l'adversaire a perdu la partie.
	 *
	 * @return Vrai si l'adversaire a perdu, sinon faux.
	 */
	public boolean partiePerdu() {return plateau.partiePerdu();}

	/**
	 * Joue un coup sur le plateau de l'adversaire en fonction des coordonnées spécifiées.
	 *
	 * @param s Les coordonnées du coup sous la forme "x:y".
	 * @return Le résultat du coup ("C" pour coulé, "T" pour touché, "E" pour dans l'eau, "R" pour coup impossible).
	 */
	public char jouerCoup(String s)
	{
		String[] coord = s.split(":");
		char c = this.ctrl.getPlateauJoueur().jouerCoup(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
		return c;
	}

	/**
	 * Ajoute un plateau au joueur adversaire en fonction de la chaîne spécifiée.
	 *
	 * @param s La chaîne représentant le plateau de jeu de l'adversaire.
	 */
	public void ajouterPlateau(String s)
	{
		this.setNaviresTousAjoute();
		this.plateau.rebuildPlateau(s);
	}
}
