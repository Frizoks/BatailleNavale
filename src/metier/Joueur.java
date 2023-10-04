package metier;

public class Joueur 
{
	private Plateau plateau;
	private boolean[] naviresPlace;
	private int nbNaviresCoule;

	public Joueur()
	{
		this.plateau = new Plateau();
		this.naviresPlace = new boolean[Robot.NAVIRES.length];
		this.nbNaviresCoule = 0;
		for(int i = 0; i < Robot.NAVIRES.length; i++) 
		{
			this.naviresPlace[i] = false;
		}
	}

	public Plateau getPlateau() {return this.plateau.getPlateau();}
	public int getNbBateauCoule() {return this.nbNaviresCoule;}

	public void ajtNavireCoule() {this.nbNaviresCoule++;}

	public boolean partiePerdu() {return plateau.partiePerdu();}

	public boolean naviresTousAjoute()
	{
		for (boolean b : naviresPlace) 
		{
			if(!b) {return false;}
		}
		return true;
	}

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
					if(x <  (Plateau.LARG/2) && y <  (Plateau.LARG/2)) {directions[0] = 'D'; directions[1] = 'B';}
					if(x >= (Plateau.LARG/2) && y <  (Plateau.LARG/2)) {directions[0] = 'G'; directions[1] = 'B';}
					if(x <  (Plateau.LARG/2) && y >= (Plateau.LARG/2)) {directions[0] = 'D'; directions[1] = 'H';}
					if(x >= (Plateau.LARG/2) && y >= (Plateau.LARG/2)) {directions[0] = 'G'; directions[1] = 'H';}

					char direction = directions[(int)(Math.random()*2)];

					for(int cpt = 1; cpt < i; cpt++)
					{
						switch(direction)
						{
							case 'H' : y = y - 1;break;
							case 'D' : x = x + 1;break;
							case 'B' : y = y + 1;break;
							case 'G' : x = x - 1;break;
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
