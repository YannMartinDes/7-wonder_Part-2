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
                rhodes.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                rhodes.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 2, new MilitaryEffect(2)));
                rhodes.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 4)), 3, new VictoryPointEffect(7)));

            case "B":
                rhodes.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 1, new VictoryPointEffect(3), new CoinEffect(3), new MilitaryEffect(1)));
                rhodes.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 4)), 2, new VictoryPointEffect(4), new CoinEffect(4), new MilitaryEffect(1)));

        }
        switch (alexandrie.getFace())
        {
            case "A":
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 2, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.CLAY, 1), new Material(MaterialType.STONE, 1)))));
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.GLASS, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.CLAY, 1), new Material(MaterialType.STONE, 1)))));
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.GLASS, 1), new Material(MaterialType.FABRIC, 1)))));
                alexandrie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 3, new VictoryPointEffect(7)));
        }

        switch (artemis.getFace())
        {
            case "A":

                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new CoinEffect(9)));
                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.PAPYRUS, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new CoinEffect(4), new VictoryPointEffect(2)));
                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new CoinEffect(4), new VictoryPointEffect(3)));
                artemis.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1)), 3, new CoinEffect(4), new VictoryPointEffect(5)));
        }

        switch (babylone.getFace())
        {
            case "A":
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new VictoryPointEffect(3)));
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 3)), 2, new ChoiceScientificEffect(new ScientificEffect(ScientificType.GEOGRAPHY), new ScientificEffect(ScientificType.LITERATURE), new ScientificEffect(ScientificType.GEOMETRY))));
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 4)), 3, new VictoryPointEffect(7)));

            case "B":
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 1), new Material(MaterialType.FABRIC, 1)), 1, new VictoryPointEffect(3)));
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2), new Material(MaterialType.GLASS, 1)), 2, new CoinEffect(0)));
                babylone.getWonderSteps().get(1).setCanPlayLastCard(true);
                babylone.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3), new Material(MaterialType.PAPYRUS, 1)), 3, new ChoiceScientificEffect(new ScientificEffect(ScientificType.GEOGRAPHY), new ScientificEffect(ScientificType.LITERATURE), new ScientificEffect(ScientificType.GEOMETRY))));
        }

        switch (olympie.getFace())
        {
            case "A":
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 2, new CoinEffect(0)));
                olympie.getWonderSteps().get(1).setHaveJoker(true);
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 3, new VictoryPointEffect(7)));

            case "B":
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR, new Material(MaterialType.CLAY, 1), new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 1))));
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 2, new VictoryPointEffect(5)));
                olympie.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2), new Material(MaterialType.FABRIC, 1)), 3, new CoinEffect(0)));
                olympie.getWonderSteps().get(2).setCopyNeighborGuild(true);
        }

        switch (halicarnasse.getFace())
        {
            case "A":

                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 2)), 1, new VictoryPointEffect(3)));
                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 3)), 2, new CoinEffect(0)));
                halicarnasse.getWonderSteps().get(1).setPlayDiscardedCard(true);
                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.FABRIC, 2)), 3, new VictoryPointEffect(7)));

            case "B":

                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.ORES, 2)), 1, new VictoryPointEffect(2)));
                halicarnasse.getWonderSteps().get(0).setPlayDiscardedCard(true);
                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 2, new VictoryPointEffect(1)));
                halicarnasse.getWonderSteps().get(1).setPlayDiscardedCard(true);
                halicarnasse.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.FABRIC, 1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.GLASS,1)), 3, new CoinEffect(0)));

        }

        switch (gizeh.getFace())
        {
            case "A":
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 2)), 1, new VictoryPointEffect(3)));
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 3)), 2, new VictoryPointEffect(5)));
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 4)),3, new VictoryPointEffect(7)));

            case "B":

                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD, 2)), 1, new VictoryPointEffect(3)));
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 3)), 2, new VictoryPointEffect(5)));
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY, 3)), 3, new VictoryPointEffect(5)));
                gizeh.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE, 4), new Material(MaterialType.PAPYRUS,1)), 3, new VictoryPointEffect(7)));


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
