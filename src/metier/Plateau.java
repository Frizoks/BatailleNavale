package metier;

public class Plateau
{
	public final static int LARG = 10;

	private Cellule[][] plateau;

	public Plateau()
	{
		plateau = new Cellule[Plateau.LARG][Plateau.LARG];
		initialisation();
	}

	public Cellule getCellule(int x, int y) {return this.plateau[x][y];}
	public Plateau getPlateau() {return this;}
	public Cellule[][] getGrille() { return this.plateau; }

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

	public boolean partiePerdu()
	{
		for(int x = 0; x < Plateau.LARG; x ++)
		{
			for(int y = 0; y < Plateau.LARG; y ++)
			{
				if(this.plateau[x][y].getNavire() && !this.plateau[x][y].getTirer()) {return false;}
			}
		}
		return true;
	}

	public boolean ajouterNavire(int[][] positionXY)
	{
		Cellule[] position = new Cellule[positionXY[0].length];
		for(int i = 0; i < positionXY[0].length; i++)
		{
			position[i] = plateau[positionXY[0][i]][positionXY[1][i]];
		}

		if(!ajoutPossible(position)) {return false;}

		for(Cellule c : position)
		{
			c.ajouterNavire();
		}
		return true;
	}

	public boolean ajoutPossible(Cellule[] position)
	{

		boolean estAligneX = true;
		boolean estAligneY = true;
		Cellule ref = position[0];

		for (int index = 1; index < position.length; index++)
		{
			if (ref.getX() != position[index].getX()) { estAligneX = false;}
			if (ref.getY() != position[index].getY()) { estAligneY = false;}
		}

		if ( !estAligneX && !estAligneY ) { return false; }

		if ( estAligneX )
		{
			int dep = position[0].getY();
			for (Cellule c : position) {if ( c.getY() < dep 				    ) { dep = c.getY(); }}
			for (Cellule c : position) {if ( c.getY() > (dep + position.length - 1) ) { return false;   }}
		}
		if ( estAligneY )
		{
			int dep = position[0].getX();
			for (Cellule c : position) {if ( c.getX() < dep 				    ) { dep = c.getX(); }}
			for (Cellule c : position) {if ( c.getX() > (dep + position.length - 1) ) { return false;   }}
		}

		for(Cellule c : position)
		{
			char[] direction = {'G', 'D', 'H', 'B'};
			if(c.getX() == 0) {direction[0] = 'R';}
			if(c.getX() == (Plateau.LARG - 1)) {direction[1] = 'R';}
			if(c.getY() == 0) {direction[2] = 'R';}
			if(c.getY() == (Plateau.LARG - 1)) {direction[3] = 'R';}
			if(navireAutour(c.getX(), c.getY(), direction)) {return false;}
		}
		return true;
	}

	public boolean navireAutour(int x, int y, char[] direction)
	{
		for(char c : direction)
		{
			if(plateau[x][y].getNavire()) {return true;}
			switch(c)
			{
				case 'H' : if(plateau[x][y - 1] != null && plateau[x][y - 1].getNavire()) {return true;};break;
				case 'D' : if(plateau[x + 1][y] != null && plateau[x + 1][y].getNavire()) {return true;};break;
				case 'B' : if(plateau[x][y + 1] != null && plateau[x][y + 1].getNavire()) {return true;};break;
				case 'G' : if(plateau[x - 1][y] != null && plateau[x - 1][y].getNavire()) {return true;};break;
			}
		}
		return false;
	}

	public char extremite(int x, int y)
	{
		if((x > 0 && plateau[x - 1][y].getNavire()) && (x < (Plateau.LARG - 1) && !plateau[x + 1][y].getNavire()) || x == Plateau.LARG - 1 && plateau[x - 1][y].getNavire()) {return 'D';}
		if((x < (Plateau.LARG - 1) && plateau[x + 1][y].getNavire()) && (x > 0 && !plateau[x - 1][y].getNavire()) || x == 0 && plateau[x + 1][y].getNavire()) {return 'G';}
		if((y > 0 && plateau[x][y - 1].getNavire()) && (y < (Plateau.LARG - 1) && !plateau[x][y + 1].getNavire()) || y == Plateau.LARG - 1 && plateau[x][y - 1].getNavire()) {return 'B';}
		if((y < (Plateau.LARG - 1) && plateau[x][y + 1].getNavire()) && (y > 0 && !plateau[x][y - 1].getNavire()) || y == 0 && plateau[x][y + 1].getNavire()) {return 'H';}
		return 'R';
	}

	public char jouerCoup(int x, int y)
	{
		if(!(x <= 0 && x >= Plateau.LARG) && !(y <= 0 && y >= Plateau.LARG) && !plateau[x][y].getTirer())
		{
			plateau[x][y].tirerSur();
			if(navireEntier(x, y)) {return 'C';} //un bateau à été coulé entierement
			if(plateau[x][y].getNavire()) {return 'T';} //un bateau à été touché
			else {return 'E';} //le coup n'a pas touché de bateau (dans l'eau)
		}
		return 'R'; //coup impossible
	}

	public boolean navireEntier(int x, int y)
	{
		if(!plateau[x][y].getNavire()) {return false;}

		boolean deltaX = false, deltaY = false;
		boolean startX = false, startY = false;

		if(x > 0 && plateau[x - 1][y].getNavire()) {deltaX = true;}
		if(x < (Plateau.LARG - 1) && plateau[x + 1][y].getNavire()) {deltaX = true;}
		if(y > 0 && plateau[x][y - 1].getNavire()) {deltaY = true;}
		if(y < (Plateau.LARG - 1) && plateau[x][y + 1].getNavire()) {deltaY = true;}
		if(!deltaX && !deltaY) {return false;}

		if(deltaX)
		{
			while(!startX)
			{
				if(x > 0 && plateau[x - 1][y].getNavire()) {x = x - 1;}
				else {startX = true;}
			}
		}
		if(deltaY)
		{
			while(!startY)
			{
				if(y > 0 && plateau[x][y - 1].getNavire()) {y = y - 1;}
				else {startY = true;}
			}
		}

		boolean estEntier = false;
		while(!estEntier)
		{
			if ((x < Plateau.LARG && y < Plateau.LARG ))
			{
				Cellule svt = plateau[x][y];

				if(!svt.getNavire()) {estEntier = true;}
				else 
				{
					if(!(svt.getNavire() && svt.getTirer())) {return false;}
				}
			}
			else {return true;}
			
			if(deltaX) {x += 1;}
			if(deltaY) {y += 1;}
		}
		return true;
	}
}
