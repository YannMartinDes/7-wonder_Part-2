package servergame.card;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.AddindChoiceMaterialEffect;
import commun.effect.AddingMaterialEffet;
import commun.effect.VictoryPointEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;

public class CardFactory {

    /**
     * Créer le deck de l'age 1
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeOneCards(){

        Deck deck1 = new Deck();

        //Cartes Bleues (Batiment civil)
        deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));//4+
        deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));//7+
        deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));//3+
        deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));//7+
        deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//3+
        deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//5+
        deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//5+
        deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//6+

        //Cartes Marrons (Matiéres premières)
        deck1.addCard(new Card("CHANTIER", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.WOOD,1 )),1,null)); //3+
        deck1.addCard(new Card("CHANTIER", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.WOOD,1 )),1,null)); //4+
        deck1.addCard(new Card("CAVITÉ", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.STONE,1 )),1,null)); //3+
        deck1.addCard(new Card("CAVITÉ", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.STONE,1 )),1,null)); //5+
        deck1.addCard(new Card("BASSIN ARGILEUX", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.CLAY,1 )),1,null)); //3+
        deck1.addCard(new Card("BASSIN ARGILEUX", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.CLAY,1 )),1,null)); //5+
        deck1.addCard(new Card("FILON", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.ORES,1 )),1,null)); //3+
        deck1.addCard(new Card("FILON", CardType.RAW_MATERIALS, new AddingMaterialEffet(new Material(MaterialType.ORES,1 )),1,null)); //4+

        deck1.addCard(new Card("FRICHE", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.CLAY,1))), 1,new CoinCost(1))); //6+
        deck1.addCard(new Card("EXCAVATION", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.CLAY,1))),1,new CoinCost(1))); //4+
        deck1.addCard(new Card("FOSSE ARGILEUSE", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.CLAY,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //5+
        deck1.addCard(new Card("EXPLOITATION FORESTIÉRE", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.WOOD,1))),1,new CoinCost(1))); //3+
        deck1.addCard(new Card("GISEMENT", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //5+
        deck1.addCard(new Card("MINE", CardType.RAW_MATERIALS, new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //6+

        //Cartes Grises (Produits manufacturés)
        deck1.addCard(new Card("VERRERIE", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.GLASS, 1)), 1,null));//3+
        deck1.addCard(new Card("VERRERIE", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.GLASS, 1)), 1,null));//6+
        deck1.addCard(new Card("MÉTIER À TISSER", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.FABRIC,1)),1,null));//3+
        deck1.addCard(new Card("MÉTIER À TISSER", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.FABRIC,1)),1,null));//6+
        deck1.addCard(new Card("PRESSE", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.PAPYRUS, 1)), 1,null));//3+
        deck1.addCard(new Card("PRESSE", CardType.MANUFACTURED_PRODUCTS, new AddingMaterialEffet(new Material(MaterialType.PAPYRUS, 1)), 1,null));//6+

        return deck1;
    }
}
