package controleur;

import metier.Plateau;

public class CUI 
{
	public static String afficherPlateau(Plateau plateau)
	{
		String sRet = "", sSep = "-----------------------------------------\n";

		sRet += sSep;

		for(int cpt = 0; cpt < Plateau.LARG; cpt++)
		{
			for(int cptBis = 0; cptBis < Plateau.LARG; cptBis++)
			{
				sRet += "| ";
				if(plateau.getCellule(cptBis, cpt) != null && plateau.getCellule(cptBis, cpt).getNavire() && plateau.getCellule(cptBis, cpt).getTirer()) {sRet += "x ";}
				else if(plateau.getCellule(cptBis, cpt) != null && plateau.getCellule(cptBis, cpt).getTirer()) {sRet += "o ";}
				else {sRet += "  ";}
			}
			sRet += "|\n";

			sRet += sSep;
		}
		
		return sRet;
	}

	public static String afficherTestPlateau(Plateau plateau)
	{
		String sRet = "", sSep = "-----------------------------------------\n";

		sRet += sSep;

		for(int cpt = 0; cpt < Plateau.LARG; cpt++)
		{
			for(int cptBis = 0; cptBis < Plateau.LARG; cptBis++)
			{
				sRet += "| ";
				if(plateau.getCellule(cptBis, cpt) != null && plateau.getCellule(cptBis, cpt).getNavire()) {sRet += "x ";}
				else if(plateau.getCellule(cptBis, cpt) != null && plateau.getCellule(cptBis, cpt).getTirer()) {sRet += "o ";}
				else {sRet += "  ";}
			}
			sRet += "|\n";

			sRet += sSep;
		}
		
		return sRet;
	}
}
