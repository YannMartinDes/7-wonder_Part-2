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
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstAITest {

    @Mock
    private FirstAI firstAI = Mockito.mock(FirstAI.class);
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

        WonderBoard w = new WonderBoard("", new ChoiceMaterialEffect( new ChoiceMaterial(new Material(MaterialType.STONE,3) )));
        //w.;

        Player p = new Player("",w);

        Mockito.when(this.firstAI.getMe()).thenReturn(p);
        //Imossible de Mock le playerController car faut pas le faire et si je mock FirstAI pour getMe() beh alors je ne peut pas lancer
        // une de ses methode sans que ça fausse tous ,, Alors dzl Mais c'est ma seul solution :) on mock tous
        Mockito.when(this.firstAI.chooseAction(this.currentDeck)).thenReturn(new BuildAction(0,false));

        //1er choix :  Points de victoire: CIVIL_BUILDING
        AbstractAction choice = this.firstAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard());

        Mockito.when(this.firstAI.chooseAction(this.currentDeck)).thenReturn(new BuildAction(2,false));

        //2er choix : Ressources : RAW_MATERIALS
        this.currentDeck.removeCard(0);
        choice = this.firstAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(2, choice.getIndexOfCard());

        Mockito.when(this.firstAI.chooseAction(this.currentDeck)).thenReturn(new BuildAction(1,false));

        //3er choix : Militaire : MILITARY_BUILDINGS
        this.currentDeck.removeCard(2);
        choice = this.firstAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(1, choice.getIndexOfCard());
        Mockito.when(this.firstAI.chooseAction(this.currentDeck)).thenReturn(new DiscardAction(0));

        //Else si possede moins que 10 coins : discard
        this.currentDeck.removeCard(1);
        choice = this.firstAI.chooseAction(this.currentDeck);
        assertEquals(DiscardAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard());
        Mockito.when(this.firstAI.chooseAction(this.currentDeck)).thenReturn(new BuildStepAction(0));

        // si le joueur a des coin alors build step
        choice = this.firstAI.chooseAction(this.currentDeck);
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

        Mockito.when(this.firstAI.choosePurchasePossibility(purchaseChoice)).thenReturn(purch3);


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
        Mockito.when(this.firstAI.useScientificsGuildEffect()).thenReturn(ScientificType.GEOMETRY);

        ScientificType scientificType = this.firstAI.useScientificsGuildEffect();

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

        Mockito.when(this.firstAI.useScientificsGuildEffect()).thenReturn(ScientificType.GEOGRAPHY);
        ScientificType scientificType = this.firstAI.useScientificsGuildEffect();

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

        Mockito.when(this.firstAI.useScientificsGuildEffect()).thenReturn(ScientificType.LITERATURE);
        ScientificType scientificType = this.firstAI.useScientificsGuildEffect();

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
        Mockito.when(this.firstAI.chooseCard(this.currentDeck)).thenReturn(3);

        int choice = this.firstAI.chooseCard(this.currentDeck);

        assertEquals(3, choice);

        //2er choix : scientific
        this.currentDeck.removeCard(3);
        Mockito.when(this.firstAI.chooseCard(this.currentDeck)).thenReturn(1);

        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(1, choice);


        //3er choix : military
        this.currentDeck.removeCard(1);
        Mockito.when(this.firstAI.chooseCard(this.currentDeck)).thenReturn(0);

        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //4er choix : Military
        this.currentDeck.removeCard(0);
        Mockito.when(this.firstAI.chooseCard(this.currentDeck)).thenReturn(0);

        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //5er choix : 1er venu
        this.currentDeck.removeCard(0);
        Mockito.when(this.firstAI.chooseCard(this.currentDeck)).thenReturn(0);

        choice = this.firstAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);
        Mockito.when(this.firstAI.toString()).thenReturn("FirstAI");

        assertEquals("FirstAI",this.firstAI.toString());
    }
}
