package DessinArbre;

/**
 * Méthode de calcul des abscisses (car les ordonnées correspondent à la
 * profondeur) d'un sommet de dessin.
 * 
 * @author Olivier
 */
public enum MethCalcAbs {
	/**
	 * Abscisse du parent = abscisse du premier enfant (ainé).
	 */
	PREMIER,
	/**
	 * Abscisse du parent = moyenne des abscisses du premier (ainé) et du
	 * dernier (benjamin) enfant.
	 */
	MILIEU,
	/**
	 * Abscisses justifiées à gauche (des frères/sœurs ou cousin(e)s "plus
	 * âgé(e)s").
	 */
	GAUCHE;

}
