package DessinArbre;

import javax.swing.JFrame;

/**
 * Dessin d'arbre.
 * 
 * @author Olivier
 */
public class DessinArbre extends JFrame {

	/**
	 * Num�ro de version de la classe (s�rialisable) ; non utilis�.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Caract�re du mot de parenth�ses correspondant � la parenth�se ouvrante.
	 */
	public static final char CARACTERE_PARENTHESE_OUVRANTE = 'x';
	/**
	 * Caract�re du mot de parenth�ses correspondant � la parenth�se fermante.
	 */
	public static final char CARACTERE_PARENTHESE_FERMANTE = 'y';

	/**
	 * Hauteur de la fen�tre.
	 */
	public static final int HAUTEUR_FENETRE = 400;
	/**
	 * Largeur de la fen�tre.
	 */
	public static final int LARGEUR_FENETRE = 800;

	/**
	 * Cr�ation du dessin d'un arbre.
	 * 
	 * @param motParenth
	 *            Mot de parenth�ses codant un arbre (quelconque).
	 * @param methCalcAbs
	 *            M�thode de calcul des abscisses de l'arbre.
	 */
	public DessinArbre(final String motParenth, final MethCalcAbs methCalcAbs) {
		super("Dessin d'arbre du mot de parenth�ses '" + motParenth + "' selon la m�thode de calcul des abscisses '" + methCalcAbs + "'.");
		if (estMotParentheses(motParenth)) {
			setBounds(0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE);
			Arbre arbre = new Arbre();
			arbre.creerArbre(motParenth);
			arbre.calculerCoordonnees(methCalcAbs);
			add(arbre);
			setVisible(true);
			new ExportPostScript(motParenth, methCalcAbs, arbre);
		}
	}

	/**
	 * Indique si le mot � tester est un mot de parenth�ses.
	 * 
	 * @param motATester
	 *            Mot � tester.
	 * @return Indique si le mot � tester est un mot de parenth�ses.
	 */
	private static boolean estMotParentheses(final String motATester) {
		boolean estMotParentheses;
		if (motATester == null) {
			estMotParentheses = false; // Interdit (contrairement � "").
		} else if (motATester.length() % 2 == 1) {
			estMotParentheses = false; // Taille impaire ==> impossible.
		} else {
			int nbOuvranteMoinsFermante = 0;
			final char[] motATesterEnTableauCaracteres = motATester.toCharArray();
			estMotParentheses = true;
			for (char caractMotATester : motATesterEnTableauCaracteres) {
				// && estMotParentheses
				switch (caractMotATester) {
				case CARACTERE_PARENTHESE_OUVRANTE:
					nbOuvranteMoinsFermante++;
					break;
				case CARACTERE_PARENTHESE_FERMANTE:
					if (nbOuvranteMoinsFermante == 0) {
						// Le pr�fixe du mot � tester contient trop de
						// parenth�ses fermantes par rapport au nombre de
						// parenth�ses ouvrantes.
						estMotParentheses = false;
					} else {
						nbOuvranteMoinsFermante--;
					}
					break;
				default:
					estMotParentheses = false;
					break;
				}
			}
			// Le mot � tester doit avoir autant de chacune des deux lettres.
			estMotParentheses = nbOuvranteMoinsFermante == 0;
		}
		return estMotParentheses;
	}

	/**
	 * Programme principal de l'application pour essayer l'application.
	 * 
	 * @param args
	 *            Arguments.
	 */
	public static void main(String[] args) {
		new DessinArbre("xxxyxyyyxyxxyxyyxxxyyxyxyy", MethCalcAbs.PREMIER);
		new DessinArbre("xxxyxyyyxyxxyxyyxxxyyxyxyy", MethCalcAbs.GAUCHE);
		new DessinArbre("xxyyxyxyxxxyyy", MethCalcAbs.MILIEU);
		new DessinArbre("yx", MethCalcAbs.GAUCHE);
		// TODO : pour essayer l'application !
	}

}
