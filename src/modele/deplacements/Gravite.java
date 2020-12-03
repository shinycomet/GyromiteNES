package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

public class Gravite extends RealisateurDeDeplacement {
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || checkCorde(eBas)) {
                if (e.avancerDirectionChoisie(Direction.bas))
                    ret = true;
            }
        }
        return ret;
    }
    public boolean checkCorde(Entite e){
        if(e != null && e.peutEtreEcrase() && !e.peutServirDeSupport() && e.peutPermettreDeMonterDescendre())
            return false;
        return true;
    }
}

/*


    public boolean checkRamassable(Entite e){
        if(e != null && e.peutEtreEcrase() && !e.peutServirDeSupport() && !e.peutPermettreDeMonterDescendre())
            return true;
        return false;
    }
    */
