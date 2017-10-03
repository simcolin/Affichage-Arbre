package DessinArbre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests du dessin d'arbre.
 * 
 * @author Olivier
 */
public class DessinArbreTest {

	/**
	 * Tests des caractères des mots de parenthèses.
	 */
	@Test
	public void testCaracteresMotsParentheses() {
		assertTrue(DessinArbre.CARACTERE_PARENTHESE_OUVRANTE != DessinArbre.CARACTERE_PARENTHESE_FERMANTE);
	}

	/**
	 * Tests du dessin d'arbre avec des mots de parenthèses corrects.
	 */
	@Test
	public void testDessinArbreMotsParentheses() {
		final String[] MOTS_PARENTHESES = { "xxxyxyyyxyxxyxyyxxxyyxyxyy", "xxyyxyxyxxxyyy", "xxxxyyyy", "xyxyxyxy",
				"" };
		for (String motParenth : MOTS_PARENTHESES) {
			for (MethCalcAbs methCalcAbs : MethCalcAbs.values()) {
				DessinArbre dessinArbre = new DessinArbre(motParenth, methCalcAbs);
				assertEquals(1, dessinArbre.getComponentCount()); // 1=l'arbre.
				assertTrue(dessinArbre.isVisible());
			}
		}
	}

	/**
	 * Tests du dessin d'arbre avec des mots qui ne sont pas des mots de
	 * parenthèses.
	 */
	@Test
	public void testDessinArbreMotsNonParentheses() {
		final String[] MOTS_NON_PARENTHESES = { null, "xyyx", "xxxy" };
		for (String motNonParenth : MOTS_NON_PARENTHESES) {
			for (MethCalcAbs methCalcAbs : MethCalcAbs.values()) {
				assertFalse((new DessinArbre(motNonParenth, methCalcAbs)).isVisible());
			}
		}
	}

}
