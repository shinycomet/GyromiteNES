package modele.deplacements;

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
            if (e.getPrec() instanceof Ramassable) {
                e.setEntiteCourant(null);
                e.setEntitePrec(null);
                ((Bot)e).setPauseNotNull();
            }

            ((Bot)e).checkPause();

            ((Bot)e).setPauseIncremente();

            if (((Bot) e).getPause() == 0) {

                toucheTerreFerme(e, eBas);

                if (!((Bot) e).getCorde() && !((Bot) e).getVole()) {
                    if (checkIfCorde(eHaut) && Math.random() > 0.5)
                        ret = grimperSurCordeVertical(e, true);
                    else if (checkIfCorde(eBas) && Math.random() > 0.5)
                        ret = grimperSurCordeVertical(e, false);
                    else if (!((Bot) e).getSolDirection()) {
                        if (checkHorizontal(eGauche, eBasGauche)) {
                            if (e.avancerDirectionChoisie(Direction.gauche)) {
                                ((Bot) e).setSolDirection(false);
                                ret = true;
                            }
                        } else if (checkHorizontal(eDroite, eBasDroite)) {
                            if (e.avancerDirectionChoisie(Direction.droite)) {
                                ((Bot) e).setSolDirection(true);
                                ret = true;
                            }
                        }
                    } else if (((Bot) e).getSolDirection()) {
                        if (checkHorizontal(eDroite, eBasDroite)) {
                            if (e.avancerDirectionChoisie(Direction.droite)) {
                                ((Bot) e).setSolDirection(true);
                                ret = true;
                            }
                        } else if (checkHorizontal(eGauche, eBasGauche)) {
                            if (e.avancerDirectionChoisie(Direction.gauche)) {
                                ((Bot) e).setSolDirection(false);
                                ret = true;
                            }
                        }
                    }
                } else if (((Bot) e).getCorde()) {
                    if (((Bot) e).getCordeDirection()) {
                        if (checkIfCorde(eHaut))
                            if (e.avancerDirectionChoisie(Direction.haut))
                                ret = true;
                        if (checkHorizontal(eDroite) && Math.random() > 0.5)
                            ret = grimperSurCordeHorizontal(e, true);
                        if (checkHorizontal(eGauche) && Math.random() > 0.5)
                            ret = grimperSurCordeHorizontal(e, false);
                        else {
                            if (e.avancerDirectionChoisie(Direction.bas)) {
                                ((Bot) e).setCordeDirection(false);
                                ret = true;
                            }
                        }
                    }
                    if (!((Bot) e).getCordeDirection()) {
                        if (checkIfCorde(eBas))
                            if (e.avancerDirectionChoisie(Direction.bas))
                                ret = true;
                        if (checkHorizontal(eDroite) && Math.random() > 0.5)
                            ret = grimperSurCordeHorizontal(e, true);
                        if (checkHorizontal(eGauche) && Math.random() > 0.5)
                            ret = grimperSurCordeHorizontal(e, false);
                        else {
                            if (e.avancerDirectionChoisie(Direction.haut)) {
                                ((Bot) e).setCordeDirection(true);
                                ret = true;
                            }
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
                    ((Bot) e).setCorde(true);
                    ((Bot) e).setCordeDirection(true);
                }
            } else {
                if (e.avancerDirectionChoisie(Direction.bas)) {
                    ((Bot) e).setCorde(false);
                    ((Bot) e).setCordeDirection(false);
                }
            }
            return true;
        }

        public boolean grimperSurCordeHorizontal(EntiteDynamique e, boolean direction) {
            if (direction) {
                if (e.avancerDirectionChoisie(Direction.droite)) {
                    ((Bot) e).setCorde(false);
                    ((Bot) e).setVole(true);
                    ((Bot) e).setSolDirection(true);
                }
            } else {
                if (e.avancerDirectionChoisie(Direction.gauche)) {
                    ((Bot) e).setCorde(false);
                    ((Bot) e).setVole(true);
                    ((Bot) e).setSolDirection(false);
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
            ((Bot) e).setCorde(true);
        else
            ((Bot) e).setCorde(false);
    }

    public void setVole(Entite e, boolean vole) {
        if (vole)
            ((Bot) e).setVole(true);
        else
            ((Bot) e).setVole(false);
    }

    public void setCordeDirection(Entite e, boolean dir) {
        if (dir)
            ((Bot) e).setCordeDirection(true);
        else
            ((Bot) e).setCordeDirection(false);
    }

    public void toucheTerreFerme(Entite e, Entite eBas) {
        if (eBas != null && eBas.peutServirDeSupport())
            ((Bot) e).setVole(false); //touche terre ferme donc ne vole plus

    }

}
