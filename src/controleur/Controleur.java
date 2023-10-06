package controleur;

import metier.*;

import ihm.FrameAccueil;

public class Controleur 
{
	private FrameAccueil ihm;
	private Robot rbt;
	private Joueur joueur;
	private Adversaire adv;
	private boolean multijoueur;

	public Controleur() {
		this.ihm = new FrameAccueil(this);
	}

	public void partieSolo() {
		this.rbt = new Robot(this);
		this.joueur = new Joueur();
		this.multijoueur = false;
	}

	public void partieMulti() {
		this.adv = new Adversaire(this);
		this.joueur = new Joueur();
		this.multijoueur = true;
	}

	public char joueurJoue(int x, int y) {
		char c = 'R';
		if (multijoueur) {c = adv.getPlateau().jouerCoup(x, y);}
		else {c = rbt.getPlateau().jouerCoup(x, y);}
		if (c == 'C') {
			joueur.ajtNavireCoule();
		}
		return c;
	}

	public char advJoue(String s) {
		char c = 'R';
		if (multijoueur) {
			c = adv.jouerCoup(s);
		} else {
			c = rbt.jouerCoup();
		}
		if (c == 'C') {
			if (multijoueur) {
				adv.ajtNavireCoule();
			} else {
				rbt.ajtNavireCoule();
			}
		}
		return c;
	}

	public int getNbNaviresCouleJoueur() {
		return this.joueur.getNbBateauCoule();
	}

	public int getNbNaviresCouleAdv() {
		if (multijoueur) {
			return adv.getNbBateauCoule();
		} else {
			return rbt.getNbBateauCoule();
		}
	}

	public boolean navireJoueurPlace() {
		return joueur.naviresTousAjoute();
	}

	public boolean naviresTousAjoute() {
		return (joueur.naviresTousAjoute() && adv.getNaviresTousAjoute());
	}

	public boolean ajouterNavire(int taille, int[][] positions) {
		return joueur.ajouterNavire(taille, positions);
	}

	public void ajouterNaviresJoueur(char[] etat) {
		joueur.ajouterNavires(etat);
	}

	public void ajouterPlateauAdv(String s) {
		this.adv.ajouterPlateau(s);
	}

	public Plateau getPlateauJoueur() {
		return joueur.getPlateau();
	}

	public Plateau getPlateauAdv() {
		if (multijoueur) {
			return adv.getPlateau();
		} else {
			return rbt.getPlateau();
		}
	}

	public char quelqunGagne() {
		if (!multijoueur && this.rbt.partiePerdu()) {
			return 'J';
		} else if (multijoueur && this.adv.partiePerdu()) {
			return 'J';
		}

		if (this.joueur.partiePerdu())
			return 'A';

		return 'N';
	}

	public boolean estMulti() {
		return this.multijoueur;
	}

	public static void main(String[] args) {
		new Controleur();
	}
}
