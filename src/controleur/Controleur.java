package controleur;

import metier.Joueur;
import metier.Plateau;
import metier.Robot;

import ihm.FrameAccueil;

public class Controleur 
{
	private Plateau plateau;
	private FrameAccueil ihm;
	private static Robot rbt;
	private Joueur joueur;

	public Controleur()
	{
		this.plateau = new Plateau();
		this.ihm = new FrameAccueil(this);
	}

	public void partieSolo()
	{
		this.rbt = new Robot();
		this.joueur = new Joueur();
	}

	public boolean naviresTousAjoute()
	{
		return joueur.naviresTousAjoute();
	}

	public Plateau getPlateauJoueur()
	{
		return joueur.getPlateau();
	}

	/*public static void main(String[] args) 
	{
		Controleur ctrl = new Controleur();

		ctrl.partieSolo();
		rbt.ajouterNavires();
		System.out.println(CUI.afficherTestPlateau(rbt.getPlateau()));

		for(int i = 0; i < 100; i++)
		{
			System.out.println(rbt.jouer());
		}

		System.out.println(CUI.afficherPlateau(rbt.getPlateau()));
		if(rbt.partieGagne()) {System.out.println("Vous avez gagné cette partie !!!");}
	}*/

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
