package metier;

import java.util.ArrayList;

import controleur.Controleur;

public class Robot extends Adversaire
{
	public final static int NAVIRES[] = {2, 5, 3, 3, 4};
	
	private Controleur ctrl;
	private ArrayList<int[]> coupPossible;

	public Robot(Controleur ctrl)
	{
		super(ctrl);
		this.ctrl = ctrl;
		this.coupPossible = remplirAL(new ArrayList<int[]>());
		this.ajouterNavires();
	}

	public ArrayList<int[]> remplirAL( ArrayList<int[]> list )
	{
		for(int x = 0; x < Plateau.LARG; x ++)
		{
			for(int y = 0; y < Plateau.LARG; y ++)
			{
				list.add(new int[] {x,y});
			}
		}
		return list;
	}

	public char jouerCoup()
	{
		char c = 'R';
		
		if ( this.coupPossible.size() > 0 )
		{
			int[] tabTemp = this.coupPossible.remove((int)(Math.random()*this.coupPossible.size()));
			c = this.ctrl.getPlateauJoueur().jouerCoup(tabTemp[0],tabTemp[1]);
		}

		return c;
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
				if(super.plateau.ajouterNavire(position)) {estAjoute = true;}
			}
		}
	}
}
