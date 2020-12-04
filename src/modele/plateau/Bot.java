/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;
import modele.plateau.Jeu;


/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    public boolean vole; //savoir si le bot a sauter d'une corde pour eviter qu'il remonte sur la corde lorsqu'il tombe
    public boolean surCorde; //true : sur corde sinon false
    public boolean solDirection; //changer de direction; true droite et false gauche
    public boolean cordeDirection; //true : monter, false : descendre
    private boolean enVie; //true en vie et false est mort, il n'est plus affiché et Bot ne tourne plus

    public Bot(Jeu _jeu) {
        super(_jeu);
    }

    public boolean getenVie() {return enVie;}
    public void setenVie() { enVie = !enVie;}

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
