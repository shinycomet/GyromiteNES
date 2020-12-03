package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Heros;
import modele.plateau.*;
import modele.deplacements.Controle4Directions;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class Colonne extends RealisateurDeDeplacement {
    private static Colonne instance;
    private boolean switchBleu = false;
    private boolean switchRouge = false;

    public static Colonne getInstance(){
        if(instance==null){
            instance = new Colonne();
        }
        return instance;
    }

    public void changerDirectionBleu(){
        switchBleu = !switchBleu;
    }

    public void changerDirectionRouge(){
        switchRouge = !switchBleu;
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {


            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            Entite eHaut = e.regarderDansLaDirection(Direction.haut);
            Entite e2Bas = e.regarderDansLaDirection(Direction.deuxBas);
            Entite e2Haut = e.regarderDansLaDirection(Direction.deuxHaut);

            Couleur c = ((Colonnes)e).getCouleur();

            switch(c){
                case bleu:
                    if(switchBleu)
                        ret = checkVertical(e,eBas,false);
                    else
                        ret = checkVertical(e,eHaut, true);
                    break;
                case rouge:
                    if(switchRouge)
                        ret = checkVertical(e,eBas,false);
                    else
                        ret = checkVertical(e,eHaut, true);
                    break;
            }
            ecrasement(e,eHaut,eBas,e2Haut,e2Bas);
        }
        return false;
    }

    public void ecrasement(EntiteDynamique e, Entite eHaut, Entite eBas, Entite e2Haut, Entite e2Bas){
        if(eHaut != null && e2Haut != null)
            eHaut.getJeu().supprimerEntite((EntiteDynamique) eHaut);
        if(eBas != null && e2Bas != null)
            eBas.getJeu().supprimerEntite((EntiteDynamique) eBas);
    }

    public void monterSurColonne(Entite eDir, boolean dir){
        if(eDir != null && eDir.peutEtreEcrase())
            if(dir)
                Controle4Directions.getInstance().realisationDeplacementSurColonne(true);
            else
                Controle4Directions.getInstance().realisationDeplacementSurColonne(false);
    }

    public boolean checkVertical(EntiteDynamique e, Entite eDir, boolean dir){
        if(eDir == null)
            return move(e, dir);
        monterSurColonne(eDir,dir);
        return false;
    }

    public boolean move(EntiteDynamique e, boolean dir){
        if(dir) {
            e.avancerDirectionChoisie(Direction.haut);
            return true;
        }else {
            e.avancerDirectionChoisie(Direction.bas);
            return true;
        }
    }
}


