package modele.deplacements;

import modele.plateau.*;
import modele.plateau.Entite;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le clavier) à un ensemble d'entités dynamiques
 */
public class Controle4Directions extends RealisateurDeDeplacement {
    private Direction directionCourante;
    // Design pattern singleton
    private static Controle4Directions c4d;

    public static Controle4Directions getInstance() {
        if (c4d == null) {
            c4d = new Controle4Directions();
        }
        return c4d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }

    public boolean realisationDeplacementSurColonne(boolean dir){
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if(dir)
                e.avancerDirectionChoisie(Direction.haut);
            else
                e.avancerDirectionChoisie(Direction.bas);
            ret = true;
        }
        return ret;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {

           if(e.getPrec() instanceof Bombe){
                e.setEntitePrec(null);
                System.out.println(e);
                //e.getJeu().getGameplay().incrementerScore();
                //e.getJeu().getGameplay().recupBombe(); ???
            }

            if (directionCourante != null)
                switch (directionCourante) {


                    case gauche :
                        Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
                        if(eGauche == null || (eGauche != null && eGauche.peutEtreEcrase()))
                            if (e.avancerDirectionChoisie(Direction.gauche))
                                ret = true;
                    break;

                     case droite :
                        //Entite eDroit = e.regarderDansLaDirection(Direction.droite);
                        //if(eDroit == null || eDroit.peutEtreEcrase())
                            if (e.avancerDirectionChoisie(Direction.droite))
                                ret = true;
                    break;


                    case haut:
                        // on ne peut pas sauter sans prendre appui
                        // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)
                        Entite eBas = e.regarderDansLaDirection(Direction.bas);
                        Entite eHaut = e.regarderDansLaDirection(Direction.haut);

                        if (eBas != null && (eBas.peutServirDeSupport() || eHaut.peutPermettreDeMonterDescendre())) {
                            if (e.avancerDirectionChoisie(Direction.haut))
                                ret = true;
                        }

                    break;

                    case bas :
                        Entite _eBas = e.regarderDansLaDirection(Direction.bas);

                        if (_eBas != null && _eBas.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(Direction.bas))
                                ret = true;
                        }
                    break;
                }
        }
        return ret;
    }

    public void resetDirection() {
        directionCourante = null;
    }
}
