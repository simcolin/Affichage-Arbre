package DessinArbre;

import sun.java2d.xr.MutableInteger;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Arbre (quelconque).
 * 
 * @author Olivier
 */
public class Arbre extends Canvas {

	/**
	 * Num�ro de version de la classe (s�rialisable) ; non utilis�.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Racine de l'arbre.
	 */
	private Sommet racine;

	/**
	 * Cr�ation d'un arbre (vide).
	 */
	public Arbre() {
	}

	/**
	 * Racine de l'arbre.
	 * 
	 * @return Racine de l'arbre.
	 */
	public Sommet getRacine() {
		return racine;
	}

	/**
	 * Cr�ation de l'arbre � partir d'un mot de parenth�ses.
	 * 
	 * @param motParenth
	 *            Mot de parenth�ses codant un arbre (quelconque).
	 */
	public void creerArbre(final String motParenth) {
		if(motParenth == null)
			throw new IllegalArgumentException();
		else {
			Stack<Sommet> st = new Stack<Sommet>();
			racine = new Sommet();
			st.push(racine);
			for(int i = 0 ; i < motParenth.length() ; i++ ) {
				switch(motParenth.charAt(i)) {
					case 'x':
						Sommet s = new Sommet();
						st.peek().ajouterEnfant(s);
						st.push(s);
						break;
					case 'y':
						st.pop();
						break;
				}
			}
			st.pop();
		}
	}

	/**
	 * Calcul des coordonn�es des sommets selon la m�thode de calcul des
	 * abscisses.
	 *
     * @param methCalcAbs
     *            M�thode de calcul des abscisses de l'arbre.
     * @param x
     */

	public void dessinPremier(Sommet s, AtomicInteger x, int y) {
		s.setAbscisse(x.intValue());
		s.setOrdonnee(y);
		if(s.getEnfants().isEmpty())
			x.addAndGet(1);
		for( Sommet fils : s.getEnfants())
			dessinPremier(fils,x,y+1);
	}

	public float dessinMilieu(Sommet s, float x, int y) {
		s.setOrdonnee(y);
		if(s.getEnfants().isEmpty()) {
			s.setAbscisse(x);
            return x+1;
		}
		else {
			for (Sommet fils : s.getEnfants())
                x = dessinMilieu(fils, x, y + 1);
			s.setAbscisse( ( s.premierEnfant().getAbscisse() + s.dernierEnfant().getAbscisse() ) / 2 );
            return x;
		}
	}

	public int[] dessinGauche(Sommet s, int[] x, int y) {
		s.setAbscisse(x[y]);
		s.setOrdonnee(y);
		if(s.getEnfants().isEmpty())
			x[y]++;
		for( Sommet fils : s.getEnfants())
            x = dessinGauche(fils,x,y+1);
        return x;
	}

	public void calculerCoordonnees(final MethCalcAbs methCalcAbs) {
		switch(methCalcAbs) {
			case PREMIER:
                AtomicInteger tmp1 = new AtomicInteger(0);
				dessinPremier(racine,tmp1,0);
				break;
			case MILIEU:
                dessinMilieu(racine,0.0f,0);
				break;
			case GAUCHE:
			    int[] tmp2 = new int[1000];
                dessinGauche(racine,tmp2,0);
				break;
		}
	}

	/**
	 * Dessin de l'arbre.
	 * 
	 * @param g
	 *            Graphique.
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	public void paint(final Graphics g) {
		super.paint(g);
		dessiner(g);
	}

	/**
	 * Dessin des sommets et ar�tes de l'arbre.
	 * 
	 * @param g
	 *            Graphique.
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	private void dessiner(final Graphics g) {
		if (racine != null) {
			// Dessin du quadrillage.
			dessinerQuadrillage(g);
			// Dessin des ar�tes de l'arbre.
			g.setColor(Color.RED);
			Graphics2D g2D = (Graphics2D) g;
			g2D.setStroke(new BasicStroke(3));
			dessinerAretes(g, racine);
			// Dessin des sommets de l'arbre.
			dessinerSommets(g, racine);
		}
	}

	/**
	 * Dessin du quadrillage.
	 * 
	 * @param g
	 *            Graphique.
	 */
	private void dessinerQuadrillage(final Graphics g) {
		Point coordonneesMax = new Point(0, 0);
		coordonneesMax(racine, coordonneesMax);
		final int LARGEUR_MAX = (int) Math.round(coordonneesMax.getX());
		final int PROFONDEUR_MAX = (int) Math.round(coordonneesMax.getY());
		g.setColor(Color.BLACK);
		for (int profondeur = 0; profondeur <= PROFONDEUR_MAX; ++profondeur) {
			g.drawLine(Sommet.MARGE_FENETRE, Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * profondeur,
					Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * LARGEUR_MAX,
					Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * profondeur);
		}
		for (int largeur = 0; largeur <= LARGEUR_MAX; ++largeur) {
			g.drawLine(Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * largeur, Sommet.MARGE_FENETRE,
					Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * largeur,
					Sommet.MARGE_FENETRE + Sommet.ESPACEMENT_COORDONNEE * PROFONDEUR_MAX);
		}
	}

	/**
	 * Calcul des coordonn�es (abscisse et ordonn�e) maximales.
	 * 
	 * @param sommet
	 *            Sommet.
	 * @param coordonnees
	 *            Coordonn�es (abscisse et ordonn�e) maximales.
	 */
	private void coordonneesMax(final Sommet sommet, Point coordonnees) {
		if (((int) Math.ceil(sommet.getAbscisse())) > coordonnees.getX()) {
			coordonnees.x = (int) Math.ceil(sommet.getAbscisse());
		}
		if (((int) Math.ceil(sommet.getOrdonnee())) > coordonnees.getY()) {
			coordonnees.y = (int) Math.ceil(sommet.getOrdonnee());
		}
		for (Sommet enfant : sommet.getEnfants()) {
			coordonneesMax(enfant, coordonnees);
		}
	}

	/**
	 * Dessin (r�cursif) des sommets de l'arbre.
	 * 
	 * @param g
	 *            Graphique.
	 * @param sommet
	 *            Sommet.
	 */
	private void dessinerSommets(final Graphics g, final Sommet sommet) {
		sommet.paint(g);
		for (Sommet enfant : sommet.getEnfants()) {
			dessinerSommets(g, enfant);
		}
	}

	/**
	 * Dessin (r�cursif) des ar�tes de l'arbre.
	 * 
	 * @param g
	 *            Graphique.
	 * @param sommetParent
	 *            Sommet parent.
	 */
	private void dessinerAretes(final Graphics g, final Sommet sommetParent) {
		if (!sommetParent.estFeuille()) {
			for (Sommet sommetEnfant : sommetParent.getEnfants()) {
				g.drawLine(sommetParent.getPosX(), sommetParent.getPosY(), sommetEnfant.getPosX(),
						sommetEnfant.getPosY());
				dessinerAretes(g, sommetEnfant);
			}
		}
	}

}
