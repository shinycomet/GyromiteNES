package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.deplacements.Colonne;
import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.RealisateurDeDeplacement;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoBot;
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoBombeDEBUT,icoBombeMILIEU,icoBombeFIN;
    private ImageIcon icoColonne;
    private ImageIcon icoCorde;
    private ImageIcon icoRamassable;
    private ImageIcon icoDroiteColonne;
    private ImageIcon icoGaucheColonne;

    int debut = 10, milieu = 20 , fin = 30;
    long divider = 1000000000;
    long startTime, endTime, duration;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        startTime = System.nanoTime();

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT  : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN  : Controle4Directions.getInstance().setDirectionCourante(Direction.bas);    break;
                    case KeyEvent.VK_UP    : Controle4Directions.getInstance().setDirectionCourante(Direction.haut);   break;
                    case KeyEvent.VK_A     : Colonne.getInstance().changerDirectionBleu(); break;
                    case KeyEvent.VK_B     : Colonne.getInstance().changerDirectionRouge(); break;
                    case KeyEvent.VK_E     : Controle4Directions.getInstance().setRamassable(); break;
                }
            }
        });
    }

    private void chargerLesIcones() {
        icoBot = chargerIcone("Images/Smick01.png");
        icoHero = chargerIcone("Images/Hector01.png");
        icoVide = chargerIcone("Images/Space.png");
        icoColonne = chargerIcone("Images/Bluepipe03.png");
        icoCorde = chargerIcone("Images/Rope.png");
        icoMur = chargerIcone("Images/Wall01.png");
        icoRamassable = chargerIcone("Images/Radish.png");
        icoGaucheColonne = chargerIcone("Images/Platform02.png");
        icoDroiteColonne = chargerIcone("Images/Platform03.png");
        icoBombeDEBUT = chargerIcone("Images/Bomb03.png");
        icoBombeMILIEU = chargerIcone("Images/Bomb02.png");
        icoBombeFIN = chargerIcone("Images/Bomb01.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(330, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        endTime = System.nanoTime();
        duration = (endTime - startTime)/divider;


        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { 
                    tabJLabel[x][y].setIcon(icoHero);
                } else if (jeu.getGrille()[x][y] instanceof Bot) {
                    tabJLabel[x][y].setIcon(icoBot);
                } else if (jeu.getGrille()[x][y] instanceof Corde) {
                    tabJLabel[x][y].setIcon(icoCorde);

                    //les bombes
                } else if (jeu.getGrille()[x][y] instanceof Bombe && duration < debut) { //debut
                    tabJLabel[x][y].setIcon(icoBombeDEBUT);
                } else if (jeu.getGrille()[x][y] instanceof Bombe && (duration > debut && duration < milieu)) { //milieu
                    tabJLabel[x][y].setIcon(icoBombeMILIEU);
                } else if (jeu.getGrille()[x][y] instanceof Bombe && (duration > milieu)) { //fin
                    tabJLabel[x][y].setIcon(icoBombeFIN);

                } else if (jeu.getGrille()[x][y] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (jeu.getGrille()[x][y] instanceof Ramassable) {
                    tabJLabel[x][y].setIcon(icoRamassable);
                } else if (jeu.getGrille()[x][y] instanceof Colonnes) {
                    tabJLabel[x][y].setIcon(icoColonne);
                } else if (jeu.getGrille()[x][y] instanceof droiteColonne) {
                    tabJLabel[x][y].setIcon(icoDroiteColonne);
                } else if (jeu.getGrille()[x][y] instanceof gaucheColonne) {
                    tabJLabel[x][y].setIcon(icoGaucheColonne);
                } else if (jeu.getGrille()[x][y] instanceof Vide) {
                    tabJLabel[x][y].setIcon(icoVide);
                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */
    }
}
