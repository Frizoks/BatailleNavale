/**
 * Package metier : ce package contient toutes les classes permettant de stocker les données, vérifier les placements et les tirs, et enfin de jouer à notre Bataille Navale.
 */
package metier;

/**
 * Cette classe représente un joueur dans le jeu de la bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Joueur
{
	/**
	 * Le plateau de jeu du joueur.
	 */
	private Plateau plateau;

	/**
	 * Un tableau de booléens indiquant quels navires ont été placés par le joueur.
	 * Chaque indice correspond à un type de navire, et la valeur `true` indique que le navire a été placé, tandis que `false` indique qu'il n'a pas été placé.
	 */
	private boolean[] naviresPlace;

	/**
	 * Le nombre de navires coulés par le joueur au cours de la partie.
	 */
	private int nbNaviresCoule;

	/**
	 * Constructeur de la classe Joueur qui initialise un joueur avec un plateau vide et aucun navire placé.
	 */
	public Joueur()
	{
		this.plateau = new Plateau();
		this.naviresPlace = new boolean[Robot.NAVIRES.length];
		this.nbNaviresCoule = 0;
		for(int i = 0; i < Robot.NAVIRES.length; i++) {this.naviresPlace[i] = false;}
	}

	/**
	 * Récupère le plateau du joueur.
	 *
	 * @return Le plateau du joueur.
	 */
	public Plateau getPlateau() {return this.plateau.getPlateau();}

	/**
	 * Récupère le nombre de navires coulés par le joueur.
	 *
	 * @return Le nombre de navires coulés par le joueur.
	 */
	public int getNbBateauCoule() {return this.nbNaviresCoule;}

	/**
	 * Incrémente le nombre de navires coulés par le joueur.
	 */
	public void ajtNavireCoule() {this.nbNaviresCoule++;}

	/**
	 * Vérifie si le joueur a perdu la partie.
	 *
	 * @return Vrai si le joueur a perdu, sinon faux.
	 */
	public boolean partiePerdu() {return plateau.partiePerdu();}

	/**
	 * Vérifie si tous les navires ont été placés par le joueur.
	 *
	 * @return Vrai si tous les navires ont été placés, sinon faux.
	 */
	public boolean naviresTousAjoute()
	{
		for(boolean b : naviresPlace)
		{
			if(!b) {return false;}
		}
		return true;
	}

	/**
	 * Ajoute un navire sur le plateau du joueur.
	 *
	 * @param taille La taille du navire à ajouter.
	 * @param positions Les coordonnées de placement du navire.
	 * @return Vrai si le navire a été ajouté avec succès, sinon faux.
	 */
	public boolean ajouterNavire(int taille, int[][] positions)
	{
		int ind = 0;
		boolean placementPossible = false;
		for(int i = 0; i < Robot.NAVIRES.length; i++) 
		{
			if(Robot.NAVIRES[i] == taille && !this.naviresPlace[i])
			{
				ind = i;
				placementPossible = true;
			}
		}
		if(!placementPossible) {return false;}

		if(this.plateau.ajouterNavire(positions))
		{
			this.naviresPlace[ind] = true;
			return true;
		}
		return false;
	}

	/**
	 * Ajoute aléatoirement des navires sur le plateau du joueur en fonction de l'état spécifié.
	 *
	 * @param etat Un tableau d'état indiquant quels navires doivent être ajoutés ('P' pour placé, autre pour non placé).
	 */
	public void ajouterNavires(char[] etat)
	{
		int indNavire = 0;
		for(int i : Robot.NAVIRES)
		{
			if(etat[indNavire] != 'P')
			{
				boolean estAjoute = false;
				while(!estAjoute)
				{
					int[][] position = new int[2][i];
					int x = (int)(Math.random() * Plateau.LARG), y = (int)(Math.random() * Plateau.LARG);
					position[0][0] = x;
					position[1][0] = y;
					char[] directions = new char[2];
					if(x <  (Plateau.LARG/2) && y <  (Plateau.LARG/2)) { directions[0] = 'D'; directions[1] = 'B'; }
					if(x >= (Plateau.LARG/2) && y <  (Plateau.LARG/2)) { directions[0] = 'G'; directions[1] = 'B'; }
					if(x <  (Plateau.LARG/2) && y >= (Plateau.LARG/2)) { directions[0] = 'D'; directions[1] = 'H'; }
					if(x >= (Plateau.LARG/2) && y >= (Plateau.LARG/2)) { directions[0] = 'G'; directions[1] = 'H'; }

					char direction = directions[(int)(Math.random()*2)];

					for(int cpt = 1; cpt < i; cpt++)
					{
						switch(direction)
						{
							case 'H' : y = y - 1; break;
							case 'D' : x = x + 1; break;
							case 'B' : y = y + 1; break;
							case 'G' : x = x - 1; break;
						}
						position[0][cpt] = x;
						position[1][cpt] = y;
					}
					if(this.plateau.ajouterNavire(position)) 
					{
						estAjoute = true;
						this.naviresPlace[indNavire++] = true;
					}
				}
			}
			else {indNavire++;}
		}
	}
}
