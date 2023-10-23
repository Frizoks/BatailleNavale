/**
 * Package metier : ce package contient toutes les classes permettant de stocker les données, vérifier les placements et les tirs, et enfin de jouer à notre Bataille Navale.
 */
package metier;
/**
 * Cette classe représente une cellule sur le plateau de jeu de la bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Cellule 
{
	/**
	 * La coordonnée x de la cellule sur le plateau de jeu.
	 */
	private int x;

	/**
	 * La coordonnée y de la cellule sur le plateau de jeu.
	 */
	private int y;

	/**
	 * Un indicateur booléen indiquant si la cellule a été touchée (true) ou non (false).
	 */
	private boolean tirer;

	/**
	 * Un indicateur booléen indiquant si la cellule contient un navire (true) ou est vide (false).
	 */
	private boolean navire;

	/**
	 * Constructeur de la classe Cellule qui initialise une cellule avec les coordonnées spécifiées.
	 *
	 * @param x Coordonnée x de la cellule.
	 * @param y Coordonnée y de la cellule.
	 */
	public Cellule(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.tirer = false;
		this.navire = false;
	}

	/**
	 * Récupère la coordonnée x de la cellule.
	 *
	 * @return La coordonnée x de la cellule.
	 */
	public int getX() { return this.x; }

	/**
	 * Récupère la coordonnée y de la cellule.
	 *
	 * @return La coordonnée y de la cellule.
	 */
	public int getY() { return this.y; }

	/**
	 * Vérifie si la cellule a été touchée.
	 *
	 * @return Vrai si la cellule a été touchée, sinon faux.
	 */
	public boolean getTirer() { return this.tirer; }

	/**
	 * Vérifie si la cellule contient un navire.
	 *
	 * @return Vrai si la cellule contient un navire, sinon faux.
	 */
	public boolean getNavire() { return this.navire; }

	/**
	 * Marque la cellule comme touchée.
	 */
	public void tirerSur() { this.tirer = true; }

	/**
	 * Ajoute un navire à la cellule.
	 */
	public void ajouterNavire() { this.navire = true; }
}
