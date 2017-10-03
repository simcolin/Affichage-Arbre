package DessinArbre;

/**
 * M�thode de calcul des abscisses (car les ordonn�es correspondent � la
 * profondeur) d'un sommet de dessin.
 * 
 * @author Olivier
 */
public enum MethCalcAbs {
	/**
	 * Abscisse du parent = abscisse du premier enfant (ain�).
	 */
	PREMIER,
	/**
	 * Abscisse du parent = moyenne des abscisses du premier (ain�) et du
	 * dernier (benjamin) enfant.
	 */
	MILIEU,
	/**
	 * Abscisses justifi�es � gauche (des fr�res/s�urs ou cousin(e)s "plus
	 * �g�(e)s").
	 */
	GAUCHE;

}
