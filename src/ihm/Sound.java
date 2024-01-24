/**
 * Package ihm : ce package contient toutes les classes permettant de construire les frames et de les rendres fonctionnelles.
 */
package ihm;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * La classe Sound permet de gérer la totalité des sons joués dans la Bataille Navale.
 * 
 * @author Luc LECARPENTIER & Mathys PORET - BUT INFO - 2023
 */
public class Sound
{
	/**
	 * Boolean pour savoir si la classe a déja été initialisé.
	 */
	private static boolean init = false;

	/**
	 * Chaine de caractère pour identifier le système d'exploitation de la machine.
	 */
	private static String os;

	/**
	 * Boolean pour activer/désactiver la musique.
	 */
	private static boolean sound = true;

	/**
	 * Un Clip d'une musique d'attente.
	 */
	private static Clip clipAttente;

	/**
	 * Un Clip d'une musique pour le gagnant.
	 */
	private static Clip clipWin;

	/**
	 * Un Clip d'une musique pour le perdant.
	 */
	private static Clip clipLoose;

	/**
	 * Un Clip d'un bruitage "plouf".
	 */
	private static Clip clipEau;

	/**
	 * Un clip d'un bruitage d'explosion.
	 */
	private static Clip clipTir;

	/**
	 * Un clip de bruitage de klaxon de bateau.
	 */
	private static Clip clipBateau;

	/**
	 * Un clip de bruitage de clic de souris.
	 */
	private static Clip clipClic;

	/**
	 * Constructeur de la classe Sound, Il initialise et charge les différents sons de la bataille navale.
	 */
	public Sound()
	{
		if(!init)
		{
			if(System.getProperty("os.name").indexOf("nux") >= 0) {Sound.os = "linux";}
			else {Sound.os = "windows";}

			Sound.soundCharge("attente");
			Sound.soundCharge("win");
			Sound.soundCharge("loose");
			Sound.soundCharge("eau");
			Sound.soundCharge("tir");
			Sound.soundCharge("bateau");
			Sound.soundCharge("clic");

			System.out.println("\nCopyright © 2023 - all rights reserved - Luc LECARPENTIER & Mathys PORET\n");
			if(!Sound.sound) {System.out.println("Un problème est survenu, aucun son ne peut actuellement être jouer.");}

			init = true;
		}
	}

	/**
	 * Méthode permettant de charger un fichier wav afin de pouvoir le jouer.
	 *
	 * @param fichier Le nom du fichier wav à charger.
	 */
	public static void soundCharge(String fichier)
	{
		try{
			AudioInputStream audioIn = null;
			try{
				String nomFichier = os == "linux" ? "image/" + fichier + ".wav" : "image\\" + fichier + ".wav";

				// Ouvrez le fichier en tant que flux d'entrée
				InputStream inputStream = Sound.class.getResourceAsStream(nomFichier);

				// Enveloppez le flux d'entrée dans un BufferedInputStream
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

				// Convertissez le flux d'entrée en flux audio
				audioIn = AudioSystem.getAudioInputStream(bufferedInputStream);
			}catch(Exception ex) {Sound.sound = false;}
			
			// Get a sound clip resource and Open audio clip.
			switch(fichier)
			{
				case "attente":
					Sound.clipAttente = AudioSystem.getClip();
					Sound.clipAttente.open(audioIn);
					break;
				case "win":
					Sound.clipWin = AudioSystem.getClip();
					Sound.clipWin.open(audioIn);
					break;
				case "loose":
					Sound.clipLoose = AudioSystem.getClip();
					Sound.clipLoose.open(audioIn);
					break;
				case "eau":
					Sound.clipEau = AudioSystem.getClip();
					Sound.clipEau.open(audioIn);
					break;
				case "tir":
					Sound.clipTir = AudioSystem.getClip();
					Sound.clipTir.open(audioIn);
					break;
				case "bateau":
					Sound.clipBateau = AudioSystem.getClip();
					Sound.clipBateau.open(audioIn);
					break;
				case "clic":
					Sound.clipClic = AudioSystem.getClip();
					Sound.clipClic.open(audioIn);
					break;
			}
		}catch(Exception e) {}
	}

	/**
	 * Indique si les fichiers sons ont été trouvé, et que la musique peut donc être joué.
	 *
	 * @return true si la musique est prête, false sinon
	 */
	public static boolean musiquePossible() {return Sound.sound;}

	/**
	 * Renvoie le système d'exploitation de la machine.
	 *
	 * @return "linux" si la machine est sous Linux, "windows" si la machine est sous Windows
	 */
	public static String getOs() {return Sound.os;}

	/**
	 * Active ou désactive le son de la Bataille Navale.
	 */
	public static void soundOnOff()
	{
		Sound.sound = !Sound.sound;
		if(Sound.sound) {Sound.clipAttente.loop(Clip.LOOP_CONTINUOUSLY);}
		if(!Sound.sound) {Sound.clipAttente.stop();}
	}

	/**
	 * Joue indefiniment une musique d'ascenseur.
	 */
	public static void clipAttenteOn() {if(clipAttente != null && sound) {Sound.clipAttente.loop(Clip.LOOP_CONTINUOUSLY);}}

	/**
	 * Eteint la musique d'ascenseur.
	 */
	public static void clipAttenteOff() {if(clipAttente != null && sound) {Sound.clipAttente.stop();}}

	/**
	 * Joue une musique quand la partie est gagné.
	 */
	public static void clipWinOn()
	{
		if(clipAttente != null && sound)
		{
			Sound.clipWin.start();
			Sound.soundCharge("win");
		}
	}

	/**
	 * Joue une musique quand la partie est perdu.
	 */
	public static void clipLooseOn()
	{
		if(clipAttente != null && sound)
		{
			Sound.clipLoose.start();
			Sound.soundCharge("loose");
		}
	}

	/**
	 * Joue le bruit d'un plouf dans l'eau.
	 */
	public static void clipEauOn()
	{
		if(clipAttente != null && sound)
		{
			Sound.clipEau.start();
			Sound.soundCharge("eau");
		}
	}

	/**
	 * Joue le bruit d'une explosion.
	 */
	public static void clipTirOn()
	{
		if(clipAttente != null && sound)
		{
			Sound.clipTir.start();
			Sound.soundCharge("tir");
		}
	}

	/**
	 * Joue le bruit d'un klaxon de bateau.
	 */
	public static void clipBateauOn()
	{
		if(clipAttente != null && sound)
		{
			Sound.clipBateau.start();
			Sound.soundCharge("bateau");
		}
	}

	/**
	 * Joue le bruit d'un clique de souris.
	 */
	public static void clipClicOn()
	{
		if(clipAttente != null && sound) 
		{
			Sound.clipClic.start();
			Sound.soundCharge("clic");
		}
	}
}
