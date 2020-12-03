package modele.plateau;

public class Colonnes extends EntiteDynamique {

    public Couleur col;
    public Colonnes(Jeu _jeu) { super(_jeu);}

    public void setCouleur(Couleur _couleur){col = _couleur;}
    public Couleur getCouleur(){return col;}

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
