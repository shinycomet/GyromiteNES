/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;
import modele.plateau.Jeu;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {
    public Heros(Jeu _jeu) {
        super(_jeu);
    }
    public boolean detientRamassable;
    public boolean solDirectionHeros; //changer de direction

    private int score;
    public void incrementerScore(int val){ score += val; }
    public int getScore(){return score;}

    public void setDirection(boolean dir) { solDirectionHeros = (dir) ?  true : false; }

    public boolean getDirection(){return solDirectionHeros; }

    public void setRamassable(boolean ram){detientRamassable = ram;}
    public boolean getRamassable() {return detientRamassable;}

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
