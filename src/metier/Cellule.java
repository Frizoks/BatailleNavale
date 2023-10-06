package metier;

public class Cellule 
{
	private int x;
	private int y;
	private boolean tirer;
	private boolean navire;

	public Cellule(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.tirer = false;
		this.navire = false;
	}

	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public boolean getTirer() {return this.tirer;}
	public boolean getNavire() {return this.navire;}

	public void tirerSur()
	{
		this.tirer = true;
	}

	public void ajouterNavire()
	{
		this.navire = true;
	}
}
