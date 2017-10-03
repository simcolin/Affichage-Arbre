package DessinArbre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests d'un arbre.
 * 
 * @author Olivier
 */
public class ArbreTest {

	/**
	 * Tests de la création d'un arbre avec des mots de parenthèses corrects.
	 */
	@Test
	public void testArbreMotsParentheses() {
		final String[] MOTS_PARENTHESES = { "xxxyxyyyxyxxyxyyxxxyyxyxyy", "xxyyxyxyxxxyyy", "xxxxyyyy", "xyxyxyxy",
				"" };
		for (String motParenth : MOTS_PARENTHESES) {
			tester1MotParentheses(motParenth);
		}
	}

	/**
	 * Tests de la création d'un arbre avec les mots de parenthèses particuliers
	 * (x^n)(y^n) et (xy)^n, pour les premières valeurs de n.
	 */
	@Test
	public void testArbreMotsParenthesesParticuliers() {
		final int N_MAX = 8;
		for (int n = 0; n <= N_MAX; ++n) {
			tester1MotParentheses(motParenthesesParticulierXnYn(n));
			tester1MotParentheses(motParenthesesParticulierXYn(n));
		}
	}

	/**
	 * Tests des coordonnées, pour chaque méthode de calcul des abscisses, du
	 * mot de parenthèses particulier (x^n)(y^n), pour les premières valeurs de
	 * n.
	 */
	@Test
	public void testArbreCoordMotsParenthesesXnYn() {
		final int N_MAX = 8;
		final float ECART_TOLERE = (float) 0.001;
		for (int n = 0; n <= N_MAX; ++n) {
			String motParenthParticulier = motParenthesesParticulierXnYn(n);
			Arbre arbre = new Arbre();
			arbre.creerArbre(motParenthParticulier);
			for (MethCalcAbs methCalcAbs : MethCalcAbs.values()) {
				arbre.calculerCoordonnees(methCalcAbs);
				Sommet sommet = arbre.getRacine();
				for (int i = 0; i <= n; ++i) {
					assertEquals((float) 0, sommet.getAbscisse(), ECART_TOLERE);
					assertEquals((float) i, sommet.getOrdonnee(), ECART_TOLERE);
					sommet = sommet.premierEnfant();
				}
			}
		}
	}

	/**
	 * Tests des coordonnées, pour chaque méthode de calcul des abscisses, du
	 * mot de parenthèses particulier (xy)^n, pour les premières valeurs de n.
	 */
	@Test
	public void testArbreCoordMotsParenthesesXYn() {
		final int N_MAX = 8;
		final float ECART_TOLERE = (float) 0.001;
		for (int n = 1; n <= N_MAX; ++n) {
			String motParenthParticulier = motParenthesesParticulierXYn(n);
			Arbre arbre = new Arbre();
			arbre.creerArbre(motParenthParticulier);
			Sommet racine = arbre.getRacine();
			// Coordonnées de la racine, pour toutes les méthodes.
			arbre.calculerCoordonnees(MethCalcAbs.PREMIER);
			assertEquals((float) 0, racine.getAbscisse(), ECART_TOLERE);
			assertEquals((float) 0, racine.getOrdonnee(), ECART_TOLERE);
			arbre.calculerCoordonnees(MethCalcAbs.MILIEU);
			assertEquals((float) (n - 1) / (float) 2, racine.getAbscisse(), ECART_TOLERE);
			assertEquals((float) 0, racine.getOrdonnee(), ECART_TOLERE);
			arbre.calculerCoordonnees(MethCalcAbs.GAUCHE);
			assertEquals((float) 0, racine.getAbscisse(), ECART_TOLERE);
			assertEquals((float) 0, racine.getOrdonnee(), ECART_TOLERE);
			// Coordonnées des enfants, pour toutes les méthodes.
			for (MethCalcAbs methCalcAbs : MethCalcAbs.values()) {
				arbre.calculerCoordonnees(methCalcAbs);
				int abscisse = 0;
				for (Sommet enfant : racine.getEnfants()) {
					assertEquals((float) abscisse, enfant.getAbscisse(), ECART_TOLERE);
					assertEquals((float) 1, enfant.getOrdonnee(), ECART_TOLERE);
					abscisse++;
				}
			}
		}
	}

	/**
	 * Tests de la création d'un arbre k-aire parfait de hauteur h, pour les
	 * premières valeurs de k et de h.
	 */
	@Test
	public void testArbreParfait() {
		final int K_MAX = 4;
		for (int k = 1; k <= K_MAX; ++k) {
			final int H_MAX = 4;
			for (int h = 0; h <= H_MAX; ++h) {
				tester1MotParentheses(motParenthesesParfait(k, h));
			}
		}
	}

	/**
	 * Tests des tests de création d'arbre particulier comme arbre k-aire
	 * parfait de hauteur h, pour les premières valeurs de k et de h.
	 */
	@Test
	public void testArbreParculierParfait() {
		final int K_MAX = 12;
		for (int k = 0; k <= K_MAX; ++k) {
			assertTrue(motParenthesesParfait(k, 1).equals(motParenthesesParticulierXYn(k)));
		}
		final int H_MAX = 12;
		for (int h = 0; h <= H_MAX; ++h) {
			assertTrue(motParenthesesParfait(1, h).equals(motParenthesesParticulierXnYn(h)));
		}
	}

	/**
	 * Test d'un mot de parenthèses correspondant à l'arbre.
	 * 
	 * @param motParenth
	 *            Mot de parenthèses correspondant à l'arbre.
	 */
	private static void tester1MotParentheses(final String motParenth) {
		Arbre arbre = new Arbre();
		arbre.creerArbre(motParenth);
		assertTrue(motParenth.equals(ArbreTest.motParentheses(arbre.getRacine())));
	}

	/**
	 * Mot de parenthèses correspondant à l'arbre (obtenu par un parcours en
	 * profondeur en ordre infixe).
	 * 
	 * @param sommet
	 *            Sommet
	 * @return Mot de parenthèses correspondant à l'arbre.
	 */
	private static String motParentheses(final Sommet sommet) {
		// On a sommet != null.
		String motParenth = "";
		for (Sommet enfant : sommet.getEnfants()) { // OK si feuille.
			motParenth = motParenth + Character.toString(DessinArbre.CARACTERE_PARENTHESE_OUVRANTE)
					+ ArbreTest.motParentheses(enfant) + Character.toString(DessinArbre.CARACTERE_PARENTHESE_FERMANTE);
		}
		return motParenth;
	}

	/**
	 * Mot de parenthèses particulier (x^n)(y^n).
	 * 
	 * @return Mot de parenthèses particulier (x^n)(y^n).
	 */
	private static String motParenthesesParticulierXnYn(final int n) {
		String motParenthParticulier = "";
		for (int i = 1; i <= n; ++i) {
			motParenthParticulier = Character.toString(DessinArbre.CARACTERE_PARENTHESE_OUVRANTE)
					+ motParenthParticulier + Character.toString(DessinArbre.CARACTERE_PARENTHESE_FERMANTE);
		}
		return motParenthParticulier;
	}

	/**
	 * Mot de parenthèses particulier (xy)^n.
	 * 
	 * @return Mot de parenthèses particulier (xy)^n.
	 */
	private static String motParenthesesParticulierXYn(final int n) {
		String motParenthParticulier = "";
		for (int i = 1; i <= n; ++i) {
			motParenthParticulier += Character.toString(DessinArbre.CARACTERE_PARENTHESE_OUVRANTE)
					+ Character.toString(DessinArbre.CARACTERE_PARENTHESE_FERMANTE);
		}
		return motParenthParticulier;
	}

	/**
	 * Mot de parenthèses d'un arbre k-aire parfait de hauteur h.
	 * 
	 * @return Mot de parenthèses d'un arbre k-aire parfait de hauteur h.
	 */
	private static String motParenthesesParfait(final int k, final int h) {
		String motParenthesesParfait;
		if (h == 0) {
			motParenthesesParfait = "";
		} else {
			final String motParenthesesParfait1Enfant = Character.toString(DessinArbre.CARACTERE_PARENTHESE_OUVRANTE)
					+ motParenthesesParfait(k, h - 1) + Character.toString(DessinArbre.CARACTERE_PARENTHESE_FERMANTE);
			motParenthesesParfait = "";
			for (int i = 1; i <= k; ++i) {
				motParenthesesParfait += motParenthesesParfait1Enfant;
			}
		}
		return motParenthesesParfait;
	}

}
