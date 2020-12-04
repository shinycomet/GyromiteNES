package modele.plateau;

public class Ramassable extends EntiteDynamique {
    public Ramassable(Jeu _jeu) { super(_jeu); }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return true; };
}

