/**
 * Package metier : ce package contient toutes les classes permettant de stocker les données, vérifier les placements et les tirs, et enfin de jouer à notre Bataille Navale.
 */
package metier;

/**
 * Cette classe représente le plateau de jeu pour le jeu de bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Plateau
{
	/**
	 * La largeur du plateau de jeu. Par convention, la valeur est définie à 10.
	 */
	public final static int LARG = 10;

	/**
	 * Un tableau de cellules représentant le plateau de jeu. Chaque cellule du plateau est accessible par ses coordonnées (x, y).
	 */
	private Cellule[][] plateau;

	/**
	 * Constructeur de la classe Plateau qui initialise le plateau de jeu.
	 */
	public Plateau()
	{
		plateau = new Cellule[Plateau.LARG][Plateau.LARG];
		initialisation();
	}

	/**
	 * Récupère la cellule à la position spécifiée.
	 *
	 * @param x Coordonnée en x de la cellule.
	 * @param y Coordonnée en y de la cellule.
	 * @return La cellule à la position (x, y).
	 */
	public Cellule getCellule(int x, int y) { return this.plateau[x][y]; }

	/**
	 * Récupère le plateau lui-même.
	 *
	 * @return Le plateau de jeu.
	 */
	public Plateau getPlateau() { return this; }

	/**
	 * Modifie le plateau avec un nouveau tableau de cellules.
	 *
	 * @param grille Le nouveau tableau de cellules pour le plateau.
	 */
	public void setPlateau(Cellule[][] grille) { this.plateau = grille; }

	/**
	 * Récupère le tableau de cellules représentant le plateau.
	 *
	 * @return Le tableau de cellules du plateau.
	 */
	public Cellule[][] getGrille() { return this.plateau; }

	/**
	 * Initialise le plateau en créant des cellules vides.
	 */
	public void initialisation()
	{
		for(int y = 0; y < Plateau.LARG; y++)
		{
			for(int x = 0; x < Plateau.LARG; x++)
			{
				this.plateau[x][y] = new Cellule(x, y);
			}
		}
	}

	/**
	 * Vérifie si la partie est perdue en parcourant le plateau et en vérifiant si tous les navires ont été touchés.
	 *
	 * @return Vrai si la partie est perdue, sinon faux.
	 */
	public boolean partiePerdu()
	{
		for(int x = 0; x < Plateau.LARG; x++)
		{
			for(int y = 0; y < Plateau.LARG; y++)
			{
				if(this.plateau[x][y].getNavire() && !this.plateau[x][y].getTirer()) { return false; }
			}
		}
		return true;
	}

	/**
	 * Ajoute un navire sur le plateau aux positions spécifiées.
	 *
	 * @param positionXY Tableau 2D des positions (x, y) du navire.
	 * @return Vrai si l'ajout du navire est possible, sinon faux.
	 */
	public boolean ajouterNavire(int[][] positionXY)
	{
		Cellule[] position = new Cellule[positionXY[0].length];
		for(int i = 0; i < positionXY[0].length; i++)
		{
			position[i] = plateau[positionXY[0][i]][positionXY[1][i]];
		}

		if(!ajoutPossible(position)) { return false; }

		for(Cellule c : position)
		{
			c.ajouterNavire();
		}
		return true;
	}

	/**
	 * Vérifie si l'ajout d'un navire est possible en vérifiant l'alignement et la proximité d'autres navires.
	 *
	 * @param position Tableau de cellules représentant la position du navire.
	 * @return Vrai si l'ajout est possible, sinon faux.
	 */
	public boolean ajoutPossible(Cellule[] position)
	{
		boolean estAligneX = true;
		boolean estAligneY = true;
		Cellule ref = position[0];

		for (int index = 1; index < position.length; index++)
		{
			if (ref.getX() != position[index].getX()) { estAligneX = false; }
			if (ref.getY() != position[index].getY()) { estAligneY = false; }
		}

		if (!estAligneX && !estAligneY) { return false; }

		if (estAligneX)
		{
			int dep = position[0].getY();
			for (Cellule c : position) { if (c.getY() < dep) { dep = c.getY(); } }
			for (Cellule c : position) { if (c.getY() > (dep + position.length - 1)) { return false; } }
		}
		if (estAligneY)
		{
			int dep = position[0].getX();
			for (Cellule c : position) { if (c.getX() < dep) { dep = c.getX(); } }
			for (Cellule c : position) { if (c.getX() > (dep + position.length - 1)) { return false; } }
		}

		for(Cellule c : position)
		{
			char[] direction = {'G', 'D', 'H', 'B'};
			if(c.getX() == 0) { direction[0] = 'R'; }
			if(c.getX() == (Plateau.LARG - 1)) { direction[1] = 'R'; }
			if(c.getY() == 0) { direction[2] = 'R'; }
			if(c.getY() == (Plateau.LARG - 1)) { direction[3] = 'R'; }
			if(navireAutour(c.getX(), c.getY(), direction)) { return false; }
		}
		return true;
	}

	/**
	 * Vérifie la présence de navires autour d'une cellule donnée.
	 *
	 * @param x Coordonnée en x de la cellule.
	 * @param y Coordonnée en y de la cellule.
	 * @param direction Tableau de directions (G, D, H, B) pour vérifier la proximité des navires.
	 * @return Vrai si un navire est présent à proximité, sinon faux.
	 */
	public boolean navireAutour(int x, int y, char[] direction)
	{
		for(char c : direction)
		{
			if(plateau[x][y].getNavire()) { return true; }
			switch(c)
			{
				case 'H' : if(plateau[x][y - 1] != null && plateau[x][y - 1].getNavire()) { return true; }; break;
				case 'D' : if(plateau[x + 1][y] != null && plateau[x + 1][y].getNavire()) { return true; }; break;
				case 'B' : if(plateau[x][y + 1] != null && plateau[x][y + 1].getNavire()) { return true; }; break;
				case 'G' : if(plateau[x - 1][y] != null && plateau[x - 1][y].getNavire()) { return true; }; break;
			}
		}
		return false;
	}

	/**
	 * Détermine l'extrémité d'un navire à une position spécifiée.
	 *
	 * @param x Coordonnée en x de la cellule.
	 * @param y Coordonnée en y de la cellule.
	 * @return La direction de l'extrémité du navire (G, D, H, B ou R si aucune extrémité n'est détectée).
	 */
	public char extremite(int x, int y)
	{
		if((x > 0 && plateau[x - 1][y].getNavire()) && (x < (Plateau.LARG - 1) && !plateau[x + 1][y].getNavire()) || x == Plateau.LARG - 1 && plateau[x - 1][y].getNavire()) { return 'D'; }
		if((x < (Plateau.LARG - 1) && plateau[x + 1][y].getNavire()) && (x > 0 && !plateau[x - 1][y].getNavire()) || x == 0 && plateau[x + 1][y].getNavire()) { return 'G'; }
		if((y > 0 && plateau[x][y - 1].getNavire()) && (y < (Plateau.LARG - 1) && !plateau[x][y + 1].getNavire()) || y == Plateau.LARG - 1 && plateau[x][y - 1].getNavire()) { return 'B'; }
		if((y < (Plateau.LARG - 1) && plateau[x][y + 1].getNavire()) && (y > 0 && !plateau[x][y - 1].getNavire()) || y == 0 && plateau[x][y + 1].getNavire()) { return 'H'; }
		return 'R';
	}

	/**
	 * Joue un coup sur le plateau en spécifiant les coordonnées (x, y).
	 *
	 * @param x Coordonnée en x du coup.
	 * @param y Coordonnée en y du coup.
	 * @return Le résultat du coup : 'C' pour un bateau coulé entièrement, 'T' pour un bateau touché, 'E' pour un coup dans l'eau, 'R' pour un coup impossible.
	 */
	public char jouerCoup(int x, int y)
	{
		if(!(x <= 0 && x >= Plateau.LARG) && !(y <= 0 && y >= Plateau.LARG) && !plateau[x][y].getTirer())
		{
			plateau[x][y].tirerSur();
			if(navireEntier(x, y)) { return 'C'; } // un bateau à été coulé entièrement
			if(plateau[x][y].getNavire()) { return 'T'; } // un bateau à été touché
			else { return 'E'; } // le coup n'a pas touché de bateau (dans l'eau)
		}
		return 'R'; // coup impossible
	}

	/**
	 * Vérifie si un navire est entièrement coulé à la position spécifiée.
	 *
	 * @param x Coordonnée en x de la cellule.
	 * @param y Coordonnée en y de la cellule.
	 * @return Vrai si le navire est entièrement coulé, sinon faux.
	 */
	public boolean navireEntier(int x, int y)
	{
		if(!plateau[x][y].getNavire()) { return false; }

		boolean deltaX = false, deltaY = false;
		boolean startX = false, startY = false;

		if(x > 0 && plateau[x - 1][y].getNavire()) { deltaX = true; }
		if(x < (Plateau.LARG - 1) && plateau[x + 1][y].getNavire()) { deltaX = true; }
		if(y > 0 && plateau[x][y - 1].getNavire()) { deltaY = true; }
		if(y < (Plateau.LARG - 1) && plateau[x][y + 1].getNavire()) { deltaY = true; }
		if(!deltaX && !deltaY) { return false; }

		if(deltaX)
		{
			while(!startX)
			{
				if(x > 0 && plateau[x - 1][y].getNavire()) { x = x - 1; }
				else { startX = true; }
			}
		}
		if(deltaY)
		{
			while(!startY)
			{
				if(y > 0 && plateau[x][y - 1].getNavire()) { y = y - 1; }
				else { startY = true; }
			}
		}

		boolean estEntier = false;
		while(!estEntier)
		{
			if ((x < Plateau.LARG && y < Plateau.LARG ))
			{
				Cellule svt = plateau[x][y];

				if(!svt.getNavire()) { estEntier = true; }
				else
				{
					if(!(svt.getNavire() && svt.getTirer())) { return false; }
				}
			}
			else { return true; }

			if(deltaX) { x += 1; }
			if(deltaY) { y += 1; }
		}
		return true;
	}

	/**
	 * Convertit le plateau en une chaîne de caractères représentant l'état actuel du plateau.
	 *
	 * @return Une chaîne de caractères représentant le plateau.
	 */
	public String toString()
	{
		String s = "";
		for(int y = 0; y < Plateau.LARG; y++)
		{
			for(int x = 0; x < Plateau.LARG; x++)
			{
				if(this.getCellule(x, y).getNavire()) { s += "N"; }
				else { s += 'E'; }

				if(!(y == Plateau.LARG - 1 && x == Plateau.LARG - 1)) { s += ","; }
			}
		}
		return s;
	}

	/**
	 * Reconstruit le plateau à partir d'une chaîne de caractères représentant l'état du plateau.
	 *
	 * @param s La chaîne de caractères représentant l'état du plateau.
	 */
	public void rebuildPlateau(String s)
	{
		String[] sTab = s.split(",");

		int index = 0;
		for(int y = 0; y < Plateau.LARG; y++)
		{
			for(int x = 0; x < Plateau.LARG; x++)
			{
				this.plateau[x][y] = new Cellule(x, y);
				if(sTab[index].equals("N")) { this.getCellule(x, y).ajouterNavire(); }
				index++;
			}
		}
	}
}
