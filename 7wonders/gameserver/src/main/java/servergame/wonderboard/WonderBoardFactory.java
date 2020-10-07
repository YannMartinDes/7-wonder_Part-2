package servergame.wonderboard;

import commun.cost.MaterialCost;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderStep;
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

        WonderBoard rhodes = new WonderBoard("Le Colosse de Rhodes",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,1)))); //
        WonderBoard alexandrie = new WonderBoard("Le Phare d'Alexandrie",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))));
        WonderBoard artemis = new WonderBoard("Le temple d'Artémis à Ephèse",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))));
        WonderBoard babylone = new WonderBoard("Les jardins suspendus de Babylone",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,1))));
        WonderBoard olympie = new WonderBoard("La statue de Zeus à Olympie",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
        WonderBoard halicarnasse = new WonderBoard("Le mausolée d’Halicarnasse",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,1))));
        WonderBoard gizeh = new WonderBoard("La grande pyramide de Gizeh",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));

        //Ajout des étapes des merveilles pour la face A et B
        switch (rhodes.getFace())
        {
            case "A":
                rhodes.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                rhodes.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 2, new MilitaryEffect(2)));
                rhodes.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 4)), 3, new VictoryPointEffect(7)));

            case "B":
                rhodes.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 1, new VictoryPointEffect(3), new CoinEffect(1), new MilitaryEffect(1)));
                rhodes.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 4)), 2, new VictoryPointEffect(4), new CoinEffect(1), new MilitaryEffect(4)));

        }
        switch (alexandrie.getFace())
        {
            case "A":
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 2, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.CLAY, 1), new Material(MaterialType.STONE, 1)))));
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.GLASS, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.CLAY, 1), new Material(MaterialType.STONE, 1)))));
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.GLASS, 1), new Material(MaterialType.FABRIC, 1)))));
                alexandrie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 3, new VictoryPointEffect(7)));
        }

        switch (artemis.getFace())
        {
            case "A":

                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new CoinEffect(9)));
                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.PAPYRUS, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new CoinEffect(4), new VictoryPointEffect(2)));
                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new CoinEffect(4), new CoinEffect(3)));
                artemis.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1)), 3, new CoinEffect(4), new VictoryPointEffect(5)));
        }

        switch (babylone.getFace())
        {
            case "A":
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new VictoryPointEffect(3)));
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 3)), 2, new ChoiceScientificEffect(new ScientificEffect(ScientificType.GEOGRAPHY), new ScientificEffect(ScientificType.LITERATURE), new ScientificEffect(ScientificType.GEOMETRY))));
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 4)), 3, new VictoryPointEffect(7)));

            case "B":
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 1), new Material(MaterialType.FABRIC, 1)), 1, new VictoryPointEffect(3)));
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2), new Material(MaterialType.GLASS, 1)), 2, null));//Todo effet
                babylone.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3), new Material(MaterialType.PAPYRUS, 1)), 3, new ChoiceScientificEffect(new ScientificEffect(ScientificType.GEOGRAPHY), new ScientificEffect(ScientificType.LITERATURE), new ScientificEffect(ScientificType.GEOMETRY))));
        }

        switch (olympie.getFace())
        {
            case "A":
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, null)); //Todo effet
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR, new Material(MaterialType.CLAY, 1), new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 1))));
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 2, new VictoryPointEffect(5)));
                olympie.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2), new Material(MaterialType.FABRIC, 1)), 3, null));//Todo effet
        }

        switch (halicarnasse.getFace())
        {
            case "A":

                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new VictoryPointEffect(3)));
                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 3)), 2, null)); //Todo effet
                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.FABRIC, 2)), 3, new VictoryPointEffect(7)));

            case "B":

                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 1, new VictoryPointEffect(2))); //Todo effet
                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 2, new VictoryPointEffect(1))); //Todo effet
                halicarnasse.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.FABRIC, 1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.GLASS,1)), 3, null)); //Todo effet

        }

        switch (gizeh.getFace())
        {
            case "A":
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 3)), 2, new VictoryPointEffect(5)));
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 4)),3, new VictoryPointEffect(7)));

            case "B":

                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 2, new VictoryPointEffect(5)));
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 3, new VictoryPointEffect(5)));
                gizeh.getWonders().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 4), new Material(MaterialType.PAPYRUS,1)), 3, new VictoryPointEffect(7)));


        }


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
