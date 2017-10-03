package DessinArbre;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Export du dessin d'un arbre au format PostScript.
 * 
 * @author Olivier
 */
public class ExportPostScript {

	/**
	 * Arbre.
	 */
	Arbre arbre = null;

	/**
	 * Fichier d'export du dessin d'un arbre au format PostScript.
	 */
	PrintWriter fichierExportPS = null;

	/**
	 * Création du fichier d'export du dessin d'un arbre au format PostScript.
	 * 
	 * @param motParenth
	 *            Mot de parenthèses codant un arbre (quelconque).
	 * @param methCalcAbs
	 *            Méthode de calcul des abscisses de l'arbre.
	 * @param arbre
	 *            Arbre.
	 */
	public ExportPostScript(final String motParenth, final MethCalcAbs methCalcAbs, final Arbre arbre) {
		this.arbre = arbre;
		try {
			fichierExportPS = new PrintWriter(
					new BufferedWriter(new FileWriter(motParenth + "_" + methCalcAbs + ".ps")));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"Erreur lors de la création du fichier d'export du dessin d'un arbre au format PostScript.");
			System.exit(1);
		}
		entetePostScript();
		dessinerSommetsPostScript(arbre.getRacine()); // Dessine tout l'arbre.
		piedPostScript();
		fichierExportPS.close();
	}

	/**
	 * Entête du fichier d'export du dessin d'un arbre au format PostScript.
	 */
	private void entetePostScript() {
		fichierExportPS.println("%!PS-Adobe-3.0");
		fichierExportPS.println("%%DocumentMedia: DimPage " + DessinArbre.LARGEUR_FENETRE + " "
				+ DessinArbre.HAUTEUR_FENETRE + " 0 white Plain");
		fichierExportPS.println("2 setlinewidth");
		fichierExportPS.println("/cote " + Sommet.TAILLE_SOMMET + " def");
		fichierExportPS.println("/demi-cote cote 2 div def");
		fichierExportPS.println("/carre {");
		fichierExportPS.println("\t0 0 1 setrgbcolor");
		fichierExportPS.println("\t/y exch def");
		fichierExportPS.println("\t/x exch def");
		fichierExportPS.println("\tx demi-cote sub y demi-cote sub moveto");
		fichierExportPS.println("\tcote 0 rlineto");
		fichierExportPS.println("\t0 cote rlineto");
		fichierExportPS.println("\tcote neg 0 rlineto");
		fichierExportPS.println("\t0 cote neg rlineto");
		fichierExportPS.println("\tgsave");
		fichierExportPS.println("\tfill");
		fichierExportPS.println("\tgrestore");
		fichierExportPS.println("\tstroke } def");
		fichierExportPS.println("/disque {");
		fichierExportPS.println("\tnewpath");
		fichierExportPS.println("\t0 1 0 setrgbcolor");
		fichierExportPS.println("\t/y exch def");
		fichierExportPS.println("\t/x exch def");
		fichierExportPS.println("\tx y demi-cote 0 360 arc");
		fichierExportPS.println("\tgsave");
		fichierExportPS.println("\tfill");
		fichierExportPS.println("\tgrestore");
		fichierExportPS.println("\tstroke } def");
		fichierExportPS.println("/trait {");
		fichierExportPS.println("\t1 0 0 setrgbcolor");
		fichierExportPS.println("\t/y2 exch def");
		fichierExportPS.println("\t/x2 exch def");
		fichierExportPS.println("\tx2 y2 moveto");
		fichierExportPS.println("\t/y1 exch def");
		fichierExportPS.println("\t/x1 exch def");
		fichierExportPS.println("\tx1 y1 lineto");
		fichierExportPS.println("\tstroke } def");
	}

	/**
	 * Dessin (récursif) au format PostScript d'un sommet et des arêtes vers ses
	 * enfants dans l'arbre.
	 * 
	 * @param sommet
	 *            Sommet
	 */
	private void dessinerSommetsPostScript(final Sommet sommet) {
		// TODO : à vous de créer tout le code nécessaire.
	}

	/**
	 * Position X au format PostScript de l'abscisse d'un sommet.
	 * 
	 * @param sommet
	 *            Sommet.
	 * @return Position X au format PostScript de l'abscisse d'un sommet.
	 */
	private int getPosXPostScript(final Sommet sommet) {
		return sommet.getPosX();
	}

	/**
	 * Position Y au format PostScript de l'ordonnée d'un sommet.
	 * 
	 * @param sommet
	 *            Sommet.
	 * @return Position Y au format PostScript de l'ordonnée d'un sommet.
	 */
	private int getPosYPostScript(final Sommet sommet) {
		return DessinArbre.HAUTEUR_FENETRE - Sommet.MARGE_FENETRE + arbre.getRacine().getPosY() - sommet.getPosY();
	}

	/**
	 * Pied du fichier d'export du dessin d'un arbre au format PostScript.
	 */
	private void piedPostScript() {
		fichierExportPS.println("showpage");
		fichierExportPS.println("%EOF");
	}

}
