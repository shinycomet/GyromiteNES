package modele.deplacements;

import java.util.Random;

import modele.plateau.*;

public class IA extends RealisateurDeDeplacement {
    protected boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {

            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            Entite eHaut = e.regarderDansLaDirection(Direction.haut);
            Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
            Entite eDroite = e.regarderDansLaDirection(Direction.droite);
            Entite eBasGauche = e.regarderDansLaDirection(Direction.diagBasGauche);
            Entite eBasDroite = e.regarderDansLaDirection(Direction.diagBasDroite);

            //bot touche un ramassable (radis)
            if(e.getPrec() instanceof Bombe){
                e.setEntitePrec(null); //detruit ramassable
                //e.getJeu().getGameplay().incrementerScore();
            }

            //e.getPrec().getJeu().supprimerEntite(e);

            toucheTerreFerme(e, eBas);


            if (!((Bot) e).surCorde && !((Bot) e).vole) {
                if (checkIfCorde(eHaut) && Math.random() > 0.5)
                    ret = grimperSurCordeVertical(e, true);
                else if (checkIfCorde(eBas) && Math.random() > 0.5)
                    ret = grimperSurCordeVertical(e, false);
                else if (!((Bot)e).solDirection){
                    if (checkHorizontal(eGauche, eBasGauche)) {
                        if (e.avancerDirectionChoisie(Direction.gauche)) {
                            ((Bot) e).solDirection = false;
                            ret = true;
                        }
                    }else if (checkHorizontal(eDroite, eBasDroite)) {
                        if (e.avancerDirectionChoisie(Direction.droite)) {
                            ((Bot) e).solDirection = true;
                            ret = true;
                        }
                    }
                } else if (((Bot)e).solDirection) {
                    if (checkHorizontal(eDroite, eBasDroite)) {
                        if (e.avancerDirectionChoisie(Direction.droite)) {
                            ((Bot) e).solDirection = true;
                            ret = true;
                        }
                    } else if (checkHorizontal(eGauche, eBasGauche)) {
                        if (e.avancerDirectionChoisie(Direction.gauche)) {
                            ((Bot) e).solDirection = false;
                            ret = true;
                        }
                    }
                }
            }
            else if(((Bot) e).surCorde) {
                if (((Bot)e).cordeDirection) {
                    if (checkIfCorde(eHaut))
                        if (e.avancerDirectionChoisie(Direction.haut))
                            ret = true;
                    if (checkHorizontal(eDroite) && Math.random() > 0.5)
                        ret = grimperSurCordeHorizontal(e, true);
                    if (checkHorizontal(eGauche) && Math.random() > 0.5)
                        ret = grimperSurCordeHorizontal(e, false);
                    else {
                        if (e.avancerDirectionChoisie(Direction.bas)) {
                            ((Bot)e).cordeDirection = false;
                            ret = true;
                        }
                    }
                }
                if (!((Bot)e).cordeDirection) {
                    if (checkIfCorde(eBas))
                        if (e.avancerDirectionChoisie(Direction.bas))
                            ret = true;
                    if (checkHorizontal(eDroite) && Math.random() > 0.5)
                        ret = grimperSurCordeHorizontal(e, true);
                    if (checkHorizontal(eGauche) && Math.random() > 0.5)
                        ret = grimperSurCordeHorizontal(e, false);
                    else {
                        if (e.avancerDirectionChoisie(Direction.haut)) {
                            ((Bot)e).cordeDirection = true;
                            ret = true;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public boolean grimperSurCordeVertical(EntiteDynamique e, boolean direction) {
        if (direction) {
            if (e.avancerDirectionChoisie(Direction.haut)) {
                ((Bot) e).surCorde = true;
                ((Bot) e).cordeDirection = true;
            }
        } else {
            if (e.avancerDirectionChoisie(Direction.bas)) {
                ((Bot) e).surCorde = false;
                ((Bot) e).cordeDirection = false;
            }
        }
        return true;
    }

    public boolean grimperSurCordeHorizontal(EntiteDynamique e, boolean direction) {
        if (direction) {
            if (e.avancerDirectionChoisie(Direction.droite)) {
                ((Bot) e).surCorde = false;
                ((Bot) e).vole = true;
                ((Bot) e).solDirection = true;
            }
        } else {
            if (e.avancerDirectionChoisie(Direction.gauche)) {
                ((Bot) e).surCorde = false;
                ((Bot) e).vole = true;
                ((Bot) e).solDirection = false;
            }
        }
        return true;
    }

    public boolean checkHorizontal(Entite e) {
        if (e != null && e.peutServirDeSupport())
            return true;
        else
            return false;
    }

    public boolean regarderGauche(EntiteDynamique e, Entite eGauche, Entite eBasGauche, Entite eDroite, Entite eBasDroite) {
        if (checkHorizontal(eGauche, eBasGauche))
            e.avancerDirectionChoisie(Direction.gauche);
        else if (checkHorizontal(eDroite, eBasDroite))
            e.avancerDirectionChoisie(Direction.droite);
        return true;
    }

    public boolean checkHorizontal(Entite e, Entite eBas) {
        if (e == null && eBas != null || (e != null && e.peutEtreEcrase()))
            return true;
        return false;
    }

    public boolean checkIfCorde(Entite e) {
        if (e != null && e.peutPermettreDeMonterDescendre())
            return true;
        return false;
    }

    public void setCorde(Entite e, boolean on) {
        if (on)
            ((Bot) e).surCorde = true;
        else
            ((Bot) e).surCorde = false;
    }

    public void setVole(Entite e, boolean vole) {
        if (vole)
            ((Bot) e).vole = true;
        else
            ((Bot) e).vole = false;
    }

    public void setCordeDirection(Entite e, boolean dir) {
        if (dir)
            ((Bot) e).cordeDirection = true;
        else
            ((Bot) e).cordeDirection = false;
    }

    public void toucheTerreFerme(Entite e, Entite eBas) {
        if (eBas != null && eBas.peutServirDeSupport())
            ((Bot) e).vole = false; //touche terre ferme donc ne vole plus

    }

}

/*
} else {
                //if (Math.random() > 0.5) {
                    if (((Bot) e).cordeDirection) {
                        if (checkIfCorde(eHaut)) {
                            if (e.avancerDirectionChoisie(Direction.haut))
                                ret = true;
                            else {
                                if (e.avancerDirectionChoisie(Direction.bas)) {
                                    ((Bot) e).cordeDirection = false;
                                    ret = true;
                                }
                            }
                       // }
                    } else if (!((Bot) e).cordeDirection) {
                        //if (Math.random() > 0.5)
                            if (checkIfCorde(eBas))
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    ret = true;
                                else if (checkIfCorde(eHaut))
                                    if (e.avancerDirectionChoisie(Direction.haut)) {
                                        ((Bot) e).cordeDirection = true;
                                        ret = true;
                                    }
                    }
                } else {
                    if (Math.random() > 0.5) {
                        if (checkIfCorde(eBasDroite)) {
                            ret = grimperSurCordeHorizontal(e, true);
                        } else if (checkIfCorde(eBasGauche)) {
                            ret = grimperSurCordeHorizontal(e, false);
                        }
                    }
                }
            }
        }





                    }
            }
        }
        return ret;
 */
