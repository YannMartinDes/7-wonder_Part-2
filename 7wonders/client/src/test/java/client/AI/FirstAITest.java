package client.AI;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.effect.ScientificEffect;
import commun.effect.ScientificType;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstAITest {



    private FirstAI firstAI;
    private Deck currentDeck;
    private int playerCoins;
    private EffectList playerEffects;
    private Random random;
    private WonderStep[] wonderSteps= new WonderStep[3];

    @BeforeEach
    void init ()
    {
        this.random = Mockito.mock(Random.class);
        this.currentDeck = new Deck();
        this.playerCoins = 0;
        this.playerEffects = new EffectList();
        this.firstAI = new FirstAI();

    }

    @Test
    void chooseActionTestAge (){
        Card card1 = new Card("test",CardType.CIVIL_BUILDING,null,1,new CoinCost(1),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,null,1,new CoinCost(5),"null");
        Card card3 = new Card("card3", CardType.MILITARY_BUILDINGS,null,1,new CoinCost(6),"null");
        Card card4 = new Card("test",CardType.RAW_MATERIALS,null,1,new CoinCost(12),"null");
        Card card5 = new Card("test",CardType.COMMERCIAL_BUILDINGS,null,1,new CoinCost(3),"null");
        Card card6 = new Card("test",CardType.MANUFACTURED_PRODUCTS,null,1,new CoinCost(3),"null");

        this.currentDeck.add(card1);
        this.currentDeck.add(card2);
        this.currentDeck.add(card3);
        this.currentDeck.add(card4);
        this.currentDeck.add(card5);
        this.currentDeck.add(card6);

        EffectList effects = new EffectList();

        //1er choix :  Points de victoire: CIVIL_BUILDING
        AbstractAction choice = this.firstAI.chooseAction(this.currentDeck,15,effects);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard());


        //2er choix : Ressources : RAW_MATERIALS
        this.currentDeck.removeCard(0);
        choice = this.firstAI.chooseAction(this.currentDeck,12,effects);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(2, choice.getIndexOfCard());


        //3er choix : Militaire : MILITARY_BUILDINGS
        this.currentDeck.removeCard(2);
        choice = this.firstAI.chooseAction(this.currentDeck,6,effects);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(1, choice.getIndexOfCard());

        //Else si possede moins que 10 coins : discard
        this.currentDeck.removeCard(1);
        choice = this.firstAI.chooseAction(this.currentDeck,9,effects);
        assertEquals(DiscardAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard());

        // si le joueur a des coin alors build step
        choice = this.firstAI.chooseAction(this.currentDeck,10,effects);
        assertEquals(BuildStepAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard()); //carte à l'indice 0 pour construire une étape
    }

    /**
     * Test si elle retourne le chois le moins cher
     */

    @Test
    void choosePurchasePossibilityTest (){
        Integer[] purch1 = {4,1};
        Integer[] purch2 = {10,1};
        Integer[] purch3 = {2,1};

        List<Integer[]> purchaseChoice = new ArrayList<>();
        purchaseChoice.add(purch1);
        purchaseChoice.add(purch2);
        purchaseChoice.add(purch3);

        Integer[] choose = this.firstAI.choosePurchasePossibility(purchaseChoice);
        assertEquals(purch3,choose);

    }

    @Test
    void useScientificsGuildEffectTestGEOMETRY (){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);

        ScientificType scientificType = this.firstAI.useScientificsGuildEffect(wonderBoard);

        //GEOMETRY
        assertEquals(ScientificType.GEOMETRY, scientificType);
    }
    @Test
    void useScientificsGuildEffectTestGEOGRAPHY (){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);

        ScientificType scientificType = this.firstAI.useScientificsGuildEffect(wonderBoard);

        //GEOGRAPHY
        assertEquals(ScientificType.GEOGRAPHY, scientificType);
    }
    @Test
    void useScientificsGuildEffectTestLiterature(){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);

        ScientificType scientificType = this.firstAI.useScientificsGuildEffect(wonderBoard);

        //Literature
        assertEquals(ScientificType.LITERATURE, scientificType);
    }

    @Test
    void chooseCardTest (){
        Card card1 = new Card("test",CardType.CIVIL_BUILDING,null,1,null,"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.MILITARY_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card4 = new Card("test",CardType.RAW_MATERIALS,null,1,null,"null");
        Card card5 = new Card("test",CardType.COMMERCIAL_BUILDINGS,null,1,null,"null");

        this.currentDeck.add(card1);
        this.currentDeck.add(card2);
        this.currentDeck.add(card3);
        this.currentDeck.add(card4);
        this.currentDeck.add(card5);

        //1er choix : raw materials
        int choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(3, choice);

        //2er choix : scientific
        this.currentDeck.removeCard(3);
        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(1, choice);


        //3er choix : military
        this.currentDeck.removeCard(1);
        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //4er choix : Military
        this.currentDeck.removeCard(0);
        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //5er choix : 1er venu
        this.currentDeck.removeCard(0);
        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        assertEquals("FirstAI",this.firstAI.toString());
    }
}
