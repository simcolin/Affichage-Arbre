package DessinArbre;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Sommet.
 * 
 * @author Olivier
 */
public class Sommet extends Canvas {

	/**
	 * Numéro de version de la classe (sérialisable) ; non utilisé.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Marge avec le bord de la fenêtre.
	 */
	public static final int MARGE_FENETRE = 50;

	/**
	 * Espacement entre deux coordonnées successives.
	 */
	public static final int ESPACEMENT_COORDONNEE = MARGE_FENETRE;

	/**
	 * Taille d'un sommet.
	 */
	public static final int TAILLE_SOMMET = 16;

	/**
	 * Sommets enfants du sommet.
	 */
	private List<Sommet> enfants;

	/**
	 * Abscisse du sommet.
	 */
	private float abscisse;
	/**
	 * Ordonnée du sommet.
	 */
	private float ordonnee;

	/**
	 * Création d'un sommet.
	 */
	public Sommet() {
		enfants = new ArrayList<Sommet>();
	}

	/**
	 * Affecte l'abscisse d'un sommet.
	 * 
	 * @param abscisse
	 *            Abscisse du sommet.
	 */
	public void setAbscisse(final float abscisse) {
		this.abscisse = abscisse;
	}

	/**
	 * Abscisse du sommet.
	 * 
	 * @return Abscisse du sommet.
	 */
	public float getAbscisse() {
		return abscisse;
	}

	/**
	 * Position X d'un sommet.
	 * 
	 * @return Position X d'un sommet.
	 */
	public int getPosX() {
		return Math.round((float) MARGE_FENETRE + (float) ESPACEMENT_COORDONNEE * abscisse);
	}

	/**
	 * Affecte l'ordonnée d'un sommet.
	 * 
	 * @param ordonnee
	 *            Ordonnée du sommet.
	 */
	public void setOrdonnee(final float ordonnee) {
		this.ordonnee = ordonnee;
	}

	/**
	 * Ordonnée du sommet.
	 * 
	 * @return Ordonnée du sommet.
	 */
	public float getOrdonnee() {
		return ordonnee;
	}

	/**
	 * Position Y d'un sommet.
	 * 
	 * @return Position Y d'un sommet.
	 */
	public int getPosY() {
		return Math.round((float) MARGE_FENETRE + (float) ESPACEMENT_COORDONNEE * ordonnee);
	}

	/**
	 * Sommets enfants du sommet.
	 * 
	 * @return Sommets enfants du sommet.
	 */
	public List<Sommet> getEnfants() {
		return enfants;
	}

	/**
	 * Ajoute un sommet enfant au sommet.
	 * 
	 * @param enfant
	 *            Sommet enfant.
	 */
	public void ajouterEnfant(final Sommet enfant) {
		enfants.add(enfant);
	}

	/**
	 * Indique si le sommet est une feuille.
	 * 
	 * @return Indique si le sommet est une feuille.
	 */
	public boolean estFeuille() {
		return enfants.isEmpty();
	}

	/**
	 * Premier enfant du sommet.
	 * 
	 * @return Premier enfant du sommet.
	 */
	public Sommet premierEnfant() {
		if (enfants.isEmpty()) {
			return null;
		} else {
			return enfants.get(0);
		}
	}

	/**
	 * Dernier enfant du sommet.
	 * 
	 * @return Dernier enfant du sommet.
	 */
	public Sommet dernierEnfant() {
		if (enfants.isEmpty()) {
			return null;
		} else {
			return enfants.get(enfants.size() - 1);
		}
	}

	/**
	 * Dessin du sommet.
	 * 
	 * @param g
	 *            Graphique.
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	public void paint(final Graphics g) {
		final int DEMI_TAILLE_SOMMET = TAILLE_SOMMET / 2;
		super.paint(g);
		if (enfants.isEmpty()) {
			// Dessin d'une feuille.
			g.setColor(Color.BLUE);
			g.fillRect(getPosX() - DEMI_TAILLE_SOMMET, getPosY() - DEMI_TAILLE_SOMMET, TAILLE_SOMMET, TAILLE_SOMMET);
		} else {
			// Dessin d'un nœud.
			g.setColor(Color.GREEN);
			g.fillOval(getPosX() - DEMI_TAILLE_SOMMET, getPosY() - DEMI_TAILLE_SOMMET, TAILLE_SOMMET, TAILLE_SOMMET);
		}
	}

}
