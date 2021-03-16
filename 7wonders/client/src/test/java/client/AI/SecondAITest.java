package client.AI;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.effect.ScientificEffect;
import commun.effect.ScientificType;
import commun.effect.guild.ScientistsGuildEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.request.PlayerRequestGame;
import commun.wonderboard.WonderBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecondAITest {
    private SecondAI secondAI;
    private Deck currentDeck;
    @Mock
    private PlayerRequestGame requestGame ;

    @BeforeEach
    void init ()
    {
        this.currentDeck = new Deck();
        this.secondAI = new SecondAI();
        this.requestGame = Mockito.mock(PlayerRequestGame.class);

    }

    @Test
    void chooseActionTestAge1 (){
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

        // si le joueur n'a pas de coin alors build step

        WonderBoard w = new WonderBoard("", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1 ))));

        Player p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        AbstractAction choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(4, choice.getIndexOfCard()); //carte à l'indice 0 pour construire une étape

        //1er choix :  Matières premières
        w.addCoin(15);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(3, choice.getIndexOfCard());


        //2er choix : Bâtiments commerciaux
        this.currentDeck.removeCard(3);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(3, choice.getIndexOfCard());


        //3er choix : Produits manufacturés //achat avec materiel
        card6 = new Card("test",CardType.MANUFACTURED_PRODUCTS,null,1,new MaterialCost(new Material(MaterialType.STONE,1)),"null");
        this.currentDeck.set(3,card6);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(3, choice.getIndexOfCard());

    }

    @Test
    void chooseActionTestAge2 (){
        Card card1 = new Card("test",CardType.CIVIL_BUILDING,null,2,new CoinCost(1),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,null,2,new CoinCost(5),"null");
        Card card3 = new Card("card3", CardType.MILITARY_BUILDINGS,null,2,new CoinCost(6),"null");
        Card card4 = new Card("test",CardType.RAW_MATERIALS,null,2,new CoinCost(12),"null");
        Card card5 = new Card("test",CardType.COMMERCIAL_BUILDINGS,null,2,new CoinCost(3),"null");
        Card card6 = new Card("test",CardType.MANUFACTURED_PRODUCTS,null,2,new CoinCost(3),"null");

        this.currentDeck.add(card1);
        this.currentDeck.add(card2);
        this.currentDeck.add(card3);
        this.currentDeck.add(card4);
        this.currentDeck.add(card5);
        this.currentDeck.add(card6);

        // si le joueur n'a pas de coin alors build step
        WonderBoard w = new WonderBoard("", null);

        Player p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        AbstractAction choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildStepAction.class, choice.getClass());
        assertEquals(5, choice.getIndexOfCard()); //carte à l'indice 0 pour construire une étape

        //1er choix :  Militaire
        w.addCoin(15);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(2, choice.getIndexOfCard());

        //2er choix : Construire une etape
        w.addCoin(5);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);
        this.currentDeck.removeCard(2);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildStepAction.class, choice.getClass());
        assertEquals(2, choice.getIndexOfCard()); //construit avec une bonne carte (raw) pour gener les voisins

        //3er choix : Points de victoire
        this.currentDeck.removeCard(2);
        this.currentDeck.removeCard(3);

        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(0, choice.getIndexOfCard());
        assertEquals(BuildAction.class, choice.getClass());

    }

    @Test
    void chooseActionTestAge3 (){

        Card card1 = new Card("test",CardType.CIVIL_BUILDING,null,3,new CoinCost(1),"null");
        Card card2 = new Card("test", CardType.SCIENTIFIC_BUILDINGS,null,3,new CoinCost(5),"null");
        Card card3 = new Card("test", CardType.MILITARY_BUILDINGS,null,3,new CoinCost(6),"null");
        Card card4 = new Card("test",CardType.RAW_MATERIALS,null,3,new CoinCost(12),"null");
        Card card5 = new Card("test",CardType.COMMERCIAL_BUILDINGS,null,3,new CoinCost(3),"null");
        Card card6 = new Card("test",CardType.GUILD_BUILDINGS,null,3,new CoinCost(3),"null");

        this.currentDeck.add(card1);
        this.currentDeck.add(card2);
        this.currentDeck.add(card3);
        this.currentDeck.add(card4);
        this.currentDeck.add(card5);
        this.currentDeck.add(card6);

        EffectList effects = new EffectList();
        // si le joueur n'a pas de coin alors discard card
        WonderBoard w = new WonderBoard("", null);
        Player p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        AbstractAction choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard()); //carte à l'indice 0 pour construire une étape

        //1er choix :  Points de victoire
        w.addCoin(3);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(0, choice.getIndexOfCard());


        //2er choix : Guild
        w.addCoin(1);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        this.currentDeck.removeCard(0);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(BuildAction.class, choice.getClass());
        assertEquals(4, choice.getIndexOfCard()); //construit avec une bonne carte (raw) pour gener les voisins


        //3er choix : Scientifique
        w.addCoin(1);
        p = new Player("",w);
        this.secondAI.setRequestGame(requestGame);
        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        this.currentDeck.removeCard(4);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(0, choice.getIndexOfCard());
        assertEquals(BuildAction.class, choice.getClass());

        //else discard card
        this.currentDeck.removeCard(0);
        choice = this.secondAI.chooseAction(this.currentDeck);
        assertEquals(DiscardAction.class, choice.getClass());

        //test ScientificguildEffect

        ScientistsGuildEffect scientistsGuildEffect= new ScientistsGuildEffect();
        scientistsGuildEffect.setSelectedScientificType(ScientificType.GEOMETRY);
        card6 = new Card("test",CardType.GUILD_BUILDINGS,scientistsGuildEffect,3,new CoinCost(3),"null");
        assertEquals(card6.getCardEffect().getScientistsGuild().getClass(), ScientistsGuildEffect.class);
        assertEquals(card6.getCardEffect().getScientistsGuild().getScientificType(), ScientificType.GEOMETRY);

    }

    @Test
    void choosePurchasePossibilityTest (){
        Integer[] purch1 = {4,1};
        Integer[] purch2 = {2,1};

        List<Integer[]> purchaseChoice = new ArrayList<>();
        purchaseChoice.add(purch1);
        purchaseChoice.add(purch2);
        Integer[] choose = this.secondAI.choosePurchasePossibility(purchaseChoice);
        assertEquals(purch2,choose);

    }

    @Test
    void useScientificsGuildEffectTestLiterature (){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);
        Player p = new Player("",wonderBoard);
        this.secondAI.setRequestGame(requestGame);

        Mockito.when(this.requestGame.getMe()).thenReturn(p);

        //Literature
        ScientificType scientificType = this.secondAI.useScientificsGuildEffect();
        assertEquals(ScientificType.LITERATURE, scientificType);
    }

    @Test
    void useScientificsGuildEffectTestGeography (){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);

        //Geography
        Player p = new Player("",wonderBoard);
        this.secondAI.setRequestGame(requestGame);

        Mockito.when(this.requestGame.getMe()).thenReturn(p);
        ScientificType scientificType = this.secondAI.useScientificsGuildEffect();
        assertEquals(ScientificType.GEOGRAPHY, scientificType);
    }

    @Test
    void useScientificsGuildEffectTestGeometry (){

        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Card card1 = new Card("card1", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),1,new CoinCost(0),"null");
        Card card2 = new Card("card2", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");
        Card card3 = new Card("card3", CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),1,new CoinCost(0),"null");

        wonderBoard.getBuilding().add(card1);
        wonderBoard.getBuilding().add(card2);
        wonderBoard.getBuilding().add(card3);

        //Geometry
        Player p = new Player("",wonderBoard);
        this.secondAI.setRequestGame(requestGame);

        Mockito.when(this.requestGame.getMe()).thenReturn(p);
        ScientificType scientificType = this.secondAI.useScientificsGuildEffect();
        assertEquals(ScientificType.GEOMETRY, scientificType);
        wonderBoard.getBuilding().removeCard(1);

        //Geometry dans le else
        scientificType = this.secondAI.useScientificsGuildEffect();
        assertEquals(ScientificType.GEOMETRY, scientificType);
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

        //1er choix : civil
        int choice = this.secondAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //2er choix : military
        this.currentDeck.removeCard(0);
        choice = this.secondAI.chooseCard(this.currentDeck);
        assertEquals(1, choice);


        //3er choix : scientific
        this.currentDeck.removeCard(1);
        choice = this.secondAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        //else : 1er venu
        this.currentDeck.removeCard(0);
        choice = this.secondAI.chooseCard(this.currentDeck);
        assertEquals(0, choice);

        assertEquals("SecondAI",this.secondAI.toString());

    }

}
