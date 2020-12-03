package modele.plateau;

public class Vide extends EntiteStatique {
    public Vide(Jeu _jeu) { super(_jeu);}

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
