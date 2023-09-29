package metier;

import java.util.ArrayList;

import controleur.Controleur;

public abstract class Adversaire 
{
	protected Plateau plateau;
	private Controleur ctrl;
	private int nbNaviresCoule;

	public Adversaire(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.plateau = new Plateau();
		//this.ajouterNavires();
		this.nbNaviresCoule = 0;
	}

	public Plateau getPlateau() {return this.plateau.getPlateau();}
	public int getNbBateauCoule() {return this.nbNaviresCoule;}

	public void ajtNavireCoule() {this.nbNaviresCoule++;}

	public boolean partiePerdu() {return plateau.partiePerdu();}

	public abstract char jouerCoup();
	public abstract void ajouterNavires();
}
