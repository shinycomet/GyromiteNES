package modele.plateau;

public class droiteColonne extends EntiteStatique {

    public droiteColonne(Jeu _jeu) { super(_jeu);}

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
