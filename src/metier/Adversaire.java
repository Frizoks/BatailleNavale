package metier;

import controleur.Controleur;

public class Adversaire 
{
	protected Plateau plateau;
	private Controleur ctrl;
	private int nbNaviresCoule;
	private boolean naviresTousAjoute;

	public Adversaire(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.plateau = new Plateau();
		this.nbNaviresCoule = 0;
		this.naviresTousAjoute = false;
	}

	public Plateau getPlateau() {return this.plateau.getPlateau();}
	public boolean getNaviresTousAjoute() {return this.naviresTousAjoute;}
	public void setNaviresTousAjoute() {this.naviresTousAjoute = true;}
	public int getNbBateauCoule() {return this.nbNaviresCoule;}
	public void ajtNavireCoule() {this.nbNaviresCoule++;}
	public boolean partiePerdu() {return plateau.partiePerdu();}

	public char jouerCoup(String s) 
	{
		String[] coord = s.split(":");
		char c = this.ctrl.getPlateauJoueur().jouerCoup(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
		return c;
	}

	public void ajouterPlateau(String s)
	{
		this.setNaviresTousAjoute();
		this.plateau.rebuildPlateau(s);
	}
}
