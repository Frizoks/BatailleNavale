package metier;

import java.util.ArrayList;
import java.util.HashMap;

public class Joueur 
{
	private Plateau plateau;
	private boolean[] naviresPlace;

	public Joueur()
	{
		this.plateau = new Plateau();
		this.naviresPlace = new boolean[Robot.NAVIRES.length];
		for(int i = 0; i < Robot.NAVIRES.length; i++) 
		{
			this.naviresPlace[i] = false;
		}
	}

	public Plateau getPlateau() {return this.plateau;}

	public boolean partieGagne() {return plateau.partieGagne();}

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
}
