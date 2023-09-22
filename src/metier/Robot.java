package metier;

public class Robot 
{
	public final static int NAVIRES[] = {5, 4, 3, 3, 2};

	private Plateau plateau;

	public Robot()
	{
		this.plateau = new Plateau();
	}

	public Plateau getPlateau() {return this.plateau;}

	public boolean partieGagne()
	{
		if(plateau.partieGagne()) {return true;}
		return false;
	}

	public char jouer()
	{
		boolean coupJoue = false;
		while(!coupJoue)
		{
			int x = (int)(Math.random()* Plateau.LARG), y = (int)(Math.random() * Plateau.LARG);
			if( this.plateau.getCellule(x, y) != null && !this.plateau.getCellule(x, y).getTirer())
			{
				return this.plateau.joueCoup(x, y);
			}
		}
		return 'R';
	}

	public void ajouterNavires()
	{
		for(int i : NAVIRES)
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
				if(this.plateau.ajouterNavire(position)) {estAjoute = true;}
			}
		}
	}
}
