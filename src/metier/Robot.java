/**
 * Package metier : ce package contient toutes les classes permettant de stocker les données, vérifier les placements et les tirs, et enfin de jouer à notre Bataille Navale.
 */
package metier;

import java.util.ArrayList;

import controleur.Controleur;

/**
 * Cette classe représente un adversaire contrôlé par l'ordinateur dans le jeu de la bataille navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Robot extends Adversaire
{
	/**
	 * Tableau des tailles des navires que le robot peut placer sur son plateau de jeu.
	 */
	public final static int NAVIRES[] = {2, 5, 3, 3, 4};

	/**
	 * Le contrôleur associé au robot, permettant au robot d'accéder aux fonctionnalités du jeu.
	 */
	private Controleur ctrl;

	/**
	 * La difficulté du robot, qui peut être "Facile", "Moyen", "Difficile", ou "Impossible".
	 */
	private char difficulte;

	/**
	 * Une liste des coups possibles que le robot peut jouer sur le plateau de jeu.
	 */
	private ArrayList<int[]> coupPossible;

	/**
	 * Une liste des derniers coups joués par le robot.
	 */
	private ArrayList<int[]> dernierCoup;

	/**
	 * L'alignement du bateau touché (X ou Y).
	 */
	private char delta;

	/**
	 * Une liste des voisins de tirs possibles pour le robot.
	 */
	private static ArrayList<int[]> voisinTir;

	/**
	 * Le niveau de coup optimal pour le robot, qui détermine la stratégie de tir à adopter.
	 */
	private int coupOpti;

	/**
	 * Constructeur de la classe Robot.
	 *
	 * @param ctrl Le contrôleur associé au robot.
	 * @param difficulte La difficulté du robot (Facile, Moyen, Difficile, Impossible).
	 */
	public Robot(Controleur ctrl, char difficulte)
	{
		super(ctrl);
		this.ctrl = ctrl;
		this.difficulte = difficulte;
		this.coupPossible = remplirAL(new ArrayList<int[]>());
		this.delta = 'N';
		this.dernierCoup = new ArrayList<int[]>();
		Robot.voisinTir = new ArrayList<int[]>();
		this.coupOpti = 4;
		this.ajouterNavires();
	}

	/**
	 * Récupère la difficulté du robot.
	 *
	 * @return La difficulté du robot (Facile, Moyen, Difficile, Impossible).
	 */
	public char getDiffficulte() { return this.difficulte; }

	/**
	 * Remplit une liste d'entiers avec toutes les combinaisons de coordonnées possibles du plateau.
	 *
	 * @param list La liste à remplir.
	 * @return La liste remplie de toutes les combinaisons de coordonnées possibles du plateau.
	 */
	public ArrayList<int[]> remplirAL(ArrayList<int[]> list)
	{
		for(int x = 0; x < Plateau.LARG; x++)
		{
			for(int y = 0; y < Plateau.LARG; y++)
			{
				list.add(new int[]{x, y});
			}
		}
		return list;
	}

	/**
	 * Joue un coup de niveau Facile.
	 *
	 * @return Le résultat du coup ("C" pour coulé, "T" pour touché, "E" pour dans l'eau, "R" pour coup raté).
	 */
	public char jouerCoupFacile()
	{
		int[] tabTemp = coupPossible.remove((int)(Math.random() * coupPossible.size()));
		return this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);
	}

	/**
	 * Joue un coup de niveau Moyen.
	 *
	 * @return Le résultat du coup ("C" pour coulé, "T" pour touché, "E" pour dans l'eau, "R" pour coup raté).
	 */
	public char jouerCoupMoyen()
	{
		char c = 'R';

		if (this.coupPossible.size() > 0)
		{
			int[] tabTemp;

			if (!Robot.voisinTir.isEmpty() && !ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				tabTemp = Robot.voisinTir.remove((int)(Math.random() * Robot.voisinTir.size()));
				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);
				coupPossible.remove(tabTemp);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					dernierCoup.add(tabTemp);
					ajouterVoisins(tabTemp[0], tabTemp[1]);
				}
			}
			else if (!dernierCoup.isEmpty() && ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				Robot.voisinTir.clear();
				dernierCoup.clear();
				c = this.jouerCoupMoyen();
			}
			else
			{
				tabTemp = coupPossible.remove((int)(Math.random() * coupPossible.size()));
				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					this.dernierCoup.add(tabTemp);
					ajouterVoisins(dernierCoup.get(0)[0], dernierCoup.get(0)[1]);
				}
			}
		}
		return c;
	}

	/**
	 * Joue un coup de niveau Difficile.
	 *
	 * @return Le résultat du coup ("C" pour coulé, "T" pour touché, "E" pour dans l'eau, "R" pour coup raté).
	 */
	public char jouerCoupDifficile()
	{
		char c = 'R';

		if (this.coupPossible.size() > 0)
		{
			int[] tabTemp;

			if (dernierCoup.size() >= 2)
				delta = dernierCoup.get(0)[0] == dernierCoup.get(1)[0] ? 'X' : 'Y';

			if (!Robot.voisinTir.isEmpty() && !ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				if (delta == 'X' || delta == 'Y')
				{
					int ind = delta == 'X' ? 0 : 1;
					for (int index = 0; index < Robot.voisinTir.size(); index++)
					{
						if (Robot.voisinTir.get(index)[ind] != dernierCoup.get(0)[ind])
						{
							coupPossible.remove(Robot.voisinTir.remove(index--));
						}
					}
				}

				tabTemp = Robot.voisinTir.remove((int)(Math.random() * Robot.voisinTir.size()));
				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);
				this.coupPossible.remove(tabTemp);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					dernierCoup.add(tabTemp);
					ajouterVoisins(tabTemp[0], tabTemp[1]);
				}
			}
			else if (!dernierCoup.isEmpty() && ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				for (int[] tab : Robot.voisinTir)
				{
					coupPossible.remove(tab);
				}
				Robot.voisinTir.clear();
				dernierCoup.clear();
				delta = 'N';
				c = this.jouerCoupDifficile();
			}
			else
			{
				ArrayList<int[]> coupTopTier = new ArrayList<int[]>();
				boolean encore = false;

				for (int[] tab : coupPossible)
				{
					if (coupUtile(tab[0], tab[1]) == coupOpti)
						encore = true;
				}
				if (!encore)
					coupOpti--;

				for (int[] coup : coupPossible)
				{
					if (coupUtile(coup[0], coup[1]) == coupOpti)
						coupTopTier.add(coup);
				}

				tabTemp = coupTopTier.get((int)(Math.random() * coupTopTier.size()));
				coupPossible.remove(tabTemp);

				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					this.dernierCoup.add(tabTemp);
					ajouterVoisins(dernierCoup.get(0)[0], dernierCoup.get(0)[1]);
				}
			}
		}
		return c;
	}

	/**
	 * Joue un coup de niveau Impossible.
	 *
	 * @return Le résultat du coup ("C" pour coulé, "T" pour touché, "E" pour dans l'eau, "R" pour coup raté).
	 */
	public char jouerCoupImpossible()
	{
		char c = 'R';

		if (this.coupPossible.size() > 0)
		{
			int[] tabTemp;

			if (dernierCoup.size() >= 2)
				delta = dernierCoup.get(0)[0] == dernierCoup.get(1)[0] ? 'X' : 'Y';

			if (!Robot.voisinTir.isEmpty() && !ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				if (delta == 'X' || delta == 'Y')
				{
					int ind = delta == 'X' ? 0 : 1;
					for (int index = 0; index < Robot.voisinTir.size(); index++)
					{
						if (Robot.voisinTir.get(index)[ind] != dernierCoup.get(0)[ind])
						{
							coupPossible.remove(Robot.voisinTir.remove(index--));
						}
					}
				}

				tabTemp = Robot.voisinTir.remove((int)(Math.random() * Robot.voisinTir.size()));
				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);
				this.coupPossible.remove(tabTemp);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					dernierCoup.add(tabTemp);
					ajouterVoisins(tabTemp[0], tabTemp[1]);
				}
			}
			else if (!dernierCoup.isEmpty() && ctrl.getPlateauJoueur().navireEntier(dernierCoup.get(0)[0], dernierCoup.get(0)[1]))
			{
				for (int[] tab : Robot.voisinTir)
				{
					coupPossible.remove(tab);
				}
				Robot.voisinTir.clear();
				dernierCoup.clear();
				delta = 'N';
				c = this.jouerCoupImpossible();
			}
			else
			{
				ArrayList<int[]> coupTopTier = new ArrayList<int[]>();
				boolean encore = false;

				for (int[] tab : coupPossible)
				{
					if (coupUtile(tab[0], tab[1]) == coupOpti)
						encore = true;
				}
				if (!encore)
					coupOpti--;

				for (int[] coup : coupPossible)
				{
					if (coupUtile(coup[0], coup[1]) == coupOpti)
						coupTopTier.add(coup);
				}

				if ((int)(Math.random() * 3) == 0)
				{
					tabTemp = null;
					for (int[] coup : coupPossible)
					{
						if (ctrl.getPlateauJoueur().getCellule(coup[0], coup[1]).getNavire())
						{
							tabTemp = coup;
							coupPossible.remove(tabTemp);
							break;
						}
					}
				}
				else
				{
					tabTemp = coupTopTier.get((int)(Math.random() * coupTopTier.size()));
					coupPossible.remove(tabTemp);
				}

				c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0], tabTemp[1]);

				if (ctrl.getPlateauJoueur().getCellule(tabTemp[0], tabTemp[1]).getNavire())
				{
					this.dernierCoup.add(tabTemp);
					ajouterVoisins(dernierCoup.get(0)[0], dernierCoup.get(0)[1]);
				}
			}
		}
		return c;
	}

	/**
	 * Ajoute les voisins de tir possibles pour une cellule donnée.
	 *
	 * @param x La coordonnée x de la cellule.
	 * @param y La coordonnée y de la cellule.
	 */
	public void ajouterVoisins(int x, int y)
	{
		for (int[] tab : this.coupPossible)
		{
			if (tab[0] > x - 2 && tab[0] < x + 2 && tab[1] == y ||
				tab[0] == x && tab[1] > y - 2 && tab[1] < y + 2)
			{
				Robot.voisinTir.add(tab);
			}
		}
	}

	/**
	 * Détermine le nombre de cases utiles autour d'une cellule.
	 *
	 * @param x La coordonnée x de la cellule.
	 * @param y La coordonnée y de la cellule.
	 * @return Le nombre de cases utiles autour de la cellule.
	 */
	public int coupUtile(int x, int y)
	{
		int nbCases = 0;

		boolean[] tab = new boolean[]{contiens(new int[]{x, y - 1}), contiens(new int[]{x, y + 1}),
			contiens(new int[]{x + 1, y}), contiens(new int[]{x - 1, y})};

		for (boolean test : tab)
		{
			if (test)
				nbCases++;
		}
		return nbCases;
	}

	/**
	 * Vérifie si la liste de coups possibles contient un certain coup.
	 *
	 * @param coup Le coup à vérifier.
	 * @return true si le coup est dans la liste de coups possibles, sinon false.
	 */
	public boolean contiens(int[] coup)
	{
		if (coup[0] >= Plateau.LARG && coup[0] < 0 || coup[1] >= Plateau.LARG && coup[1] < 0)
			return true;

		for (int[] tab : coupPossible)
		{
			if (coup[0] == tab[0] && coup[1] == tab[1])
				return true;
		}
		return false;
	}

	/**
	 * Ajoute les navires du robot sur le plateau de jeu.
	 */
	public void ajouterNavires()
	{
		for (int i : NAVIRES)
		{
			boolean estAjoute = false;
			while (!estAjoute)
			{
				int[][] position = new int[2][i];
				int x = (int)(Math.random() * Plateau.LARG), y = (int)(Math.random() * Plateau.LARG);
				position[0][0] = x;
				position[1][0] = y;
				char[] directions = new char[2];
				if (x < (Plateau.LARG / 2) && y < (Plateau.LARG / 2))
				{
					directions[0] = 'D';
					directions[1] = 'B';
				}
				if (x >= (Plateau.LARG / 2) && y < (Plateau.LARG / 2))
				{
					directions[0] = 'G';
					directions[1] = 'B';
				}
				if (x < (Plateau.LARG / 2) && y >= (Plateau.LARG / 2))
				{
					directions[0] = 'D';
					directions[1] = 'H';
				}
				if (x >= (Plateau.LARG / 2) && y >= (Plateau.LARG / 2))
				{
					directions[0] = 'G';
					directions[1] = 'H';
				}

				char direction = directions[(int)(Math.random() * 2)];

				for (int cpt = 1; cpt < i; cpt++)
				{
					switch (direction)
					{
						case 'H':
							y = y - 1;
							break;
						case 'D':
							x = x + 1;
							break;
						case 'B':
							y = y + 1;
							break;
						case 'G':
							x = x - 1;
							break;
					}
					position[0][cpt] = x;
					position[1][cpt] = y;
				}
				if (super.plateau.ajouterNavire(position))
				{
					estAjoute = true;
				}
			}
		}
	}
}
