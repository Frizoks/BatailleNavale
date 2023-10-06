package metier;

import controleur.Controleur;

public class Adversaire 
{
	protected Plateau plateau;
	private Controleur ctrl;
	private int nbNaviresCoule;
	private boolean naviresTousAjoute;
	//private char resultatDernierCoup;

	public Adversaire(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.plateau = new Plateau();
		this.nbNaviresCoule = 0;
		this.naviresTousAjoute = false;
		//this.resultatDernierCoup = 'R';
	}

	public Plateau getPlateau() {return this.plateau.getPlateau();}
	public int getNbBateauCoule() {return this.nbNaviresCoule;}
	public boolean getNaviresTousAjoute() {return this.naviresTousAjoute;}
	public void setNaviresTousAjoute() {this.naviresTousAjoute = true;}
	public void ajtNavireCoule() {this.nbNaviresCoule++;}
	//public char getDernierCoup() {return this.resultatDernierCoup;}

	public boolean partiePerdu() {return plateau.partiePerdu();}

	public char jouerCoup(String s) 
	{
		String[] coord = s.split(":");
		char c = this.ctrl.getPlateauJoueur().jouerCoup(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
		//this.resultatDernierCoup = c;
		return c;
	}

	public void ajouterPlateau(String s)
	{
		this.setNaviresTousAjoute();
		this.plateau.rebuildPlateau(s);
	}
}
