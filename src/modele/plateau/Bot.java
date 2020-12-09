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
    private boolean vole; //savoir si le bot a sauter d'une corde pour eviter qu'il remonte sur la corde lorsqu'il tombe
    private boolean surCorde; //true : sur corde sinon false
    private boolean solDirection; //changer de direction; true droite et false gauche
    private boolean cordeDirection; //true : monter, false : descendre
    private int pauseSmick;// en pause elorsque un smick touche un radis
    private int pauseTime = 20; // temps de pause pour le smick lorsqu'il touche un radis

    public Bot(Jeu _jeu) {
        super(_jeu);
    }

    //joueur vole (tombe) ou pas
    public void setVole(boolean dir){ vole = dir; }
    public boolean getVole(){ return vole; }

    //joueur sur corde ou pas
    public void setCorde(boolean dir){ surCorde = dir; }
    public boolean getCorde(){ return surCorde; }

    //sol direction
    public void setSolDirection(boolean dir){ solDirection = dir; }
    public boolean getSolDirection(){ return solDirection; }

    //corde direction
    public void setCordeDirection(boolean dir){ cordeDirection = dir; }
    public boolean getCordeDirection(){ return cordeDirection; }



    public void setPauseNotNull(){
        if(pauseSmick == 0)
            pauseSmick++;
    }

    public void checkPause(){
        if(pauseSmick == pauseTime)
            pauseSmick = 0;
    }

    public void setPauseIncremente(){
        if(pauseSmick != 0)
            pauseSmick++;
    }

    public int getPause(){
        return pauseSmick;
    }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
