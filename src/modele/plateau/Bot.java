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
    public boolean solDirection; //changer de direction
    public boolean cordeDirection; //true : monter, false : descendre

    public Bot(Jeu _jeu) {
        super(_jeu);
    }


    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
