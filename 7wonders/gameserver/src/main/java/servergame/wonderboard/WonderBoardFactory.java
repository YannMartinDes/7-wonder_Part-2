package servergame.wonderboard;

import commun.wonderboard.WonderBoard;
import java.util.Random;
import java.util.ArrayList;


public class WonderBoardFactory {

    /**
     * Créer les merveilles.
     * @return la liste des merveilles.
     */
    public ArrayList<WonderBoard> createWonderBoard(){
        ArrayList<WonderBoard> wonderList = new ArrayList<WonderBoard>();

        WonderBoard rhodes = new WonderBoard("Le Colosse de Rhodes");
        WonderBoard alexandrie = new WonderBoard("Le Phare d'Alexandrie");
        WonderBoard artemis = new WonderBoard("Le temple d'Artémis à Ephèse");
        WonderBoard babylone = new WonderBoard("Les jardins suspendus de Babylone");
        WonderBoard olympie = new WonderBoard("La statue de Zeus à Olympie");
        WonderBoard halicarnasse = new WonderBoard("Le mausolée d’Halicarnasse");
        WonderBoard gizeh = new WonderBoard("La grande pyramide de Gizeh");

        wonderList.add(rhodes);
        wonderList.add(alexandrie);
        wonderList.add(artemis);
        wonderList.add(babylone);
        wonderList.add(olympie);
        wonderList.add(halicarnasse);
        wonderList.add(gizeh);

        return wonderList;
    }


    /**
     * Renvoie la liste des wonderBoard tiré au hasard en fonction du nombre de joueur
     */
    public ArrayList<WonderBoard> chooseWonderBoard(int nbPlayer) {

        Random r = new Random();
        ArrayList<WonderBoard> choosenWonderBoards = new ArrayList<WonderBoard>();
        ArrayList<WonderBoard> wonderList = createWonderBoard();

        for(int i = 0; i < nbPlayer; i++) {
            int n = r.nextInt(nbPlayer - i);
            choosenWonderBoards.add(wonderList.get(n));
            wonderList.remove(n);
        }
        return choosenWonderBoards;
    }



}
