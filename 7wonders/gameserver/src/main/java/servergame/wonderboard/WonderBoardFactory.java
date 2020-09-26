package servergame.wonderboard;

import commun.effect.AddingMaterialEffet;
import commun.material.Material;
import commun.material.MaterialType;
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

        WonderBoard rhodes = new WonderBoard("Le Colosse de Rhodes",new AddingMaterialEffet(new Material(MaterialType.ORES,10))); //
        WonderBoard alexandrie = new WonderBoard("Le Phare d'Alexandrie",new AddingMaterialEffet(new Material(MaterialType.GLASS,10)));
        WonderBoard artemis = new WonderBoard("Le temple d'Artémis à Ephèse",new AddingMaterialEffet(new Material(MaterialType.PAPYRUS,10)));
        WonderBoard babylone = new WonderBoard("Les jardins suspendus de Babylone",new AddingMaterialEffet(new Material(MaterialType.CLAY,10)));
        WonderBoard olympie = new WonderBoard("La statue de Zeus à Olympie",new AddingMaterialEffet(new Material(MaterialType.WOOD,10)));
        WonderBoard halicarnasse = new WonderBoard("Le mausolée d’Halicarnasse",new AddingMaterialEffet(new Material(MaterialType.FABRIC,10)));
        WonderBoard gizeh = new WonderBoard("La grande pyramide de Gizeh",new AddingMaterialEffet(new Material(MaterialType.STONE,10)));

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
