package commun.wonderboard;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.ChoiceMaterialEffect;
import commun.cost.MaterialCost;
import commun.effect.EffectList;
import commun.effect.IEffect;

import commun.effect.VictoryPointEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WonderBoardTest {

    WonderBoard wonderBoard = new WonderBoard("Les jardins suspendus de Babylone",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,10))));

    @Test
    public void addCardToBuildingTest(){
        //La liste est vide
        assertEquals(0,wonderBoard.getBuilding().getLength());

        int size = 1;
        Card card = new Card("testBase", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null);
        Card previousCard;

        wonderBoard.addCardToBuilding(card);
        assertEquals(1,wonderBoard.getBuilding().getLength());//La taille a augmentée

        for(int i =0; i<100;i++){
            previousCard = card;
            card = new Card("test"+i, CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null);

            wonderBoard.addCardToBuilding(card);
            size++;
            //La dernière carte ajoutée est bien la bonne.
            assertTrue(wonderBoard.getBuilding().getCard(wonderBoard.getBuilding().getLength()-1).equals(card));
            //La carte précédente est la meme.
            assertTrue(wonderBoard.getBuilding().getCard(wonderBoard.getBuilding().getLength()-2).equals(previousCard));
            assertEquals(wonderBoard.getBuilding().getLength(),size);//La taille a augmentée
        }
    }

    @Test
    public void testGetAllEffects ()
    {
        wonderBoard = new WonderBoard("Les jardins suspendus de Babylone",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,10))));

        wonderBoard.addCardToBuilding(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));
        wonderBoard.addCardToBuilding(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));
        wonderBoard.addCardToBuilding(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));

        EffectList expected = new EffectList();
        expected.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,10))));
        expected.add(new VictoryPointEffect(3));
        expected.add(new VictoryPointEffect(2));
        expected.add(new VictoryPointEffect(2));

        EffectList effects = wonderBoard.getAllEffects();

        assertEquals(expected.size(), effects.size());
        for (int i = 0; i < expected.size(); i++)
        {
            assertEquals(expected.get(i).getNumberOfCoin(), effects.get(i).getNumberOfCoin());
            assertEquals(expected.get(i).getScientificType(), effects.get(i).getScientificType());
            assertEquals(expected.get(i).getScore(), effects.get(i).getScore());
            assertEquals(expected.get(i).getEarnWithCardEffect(), effects.get(i).getEarnWithCardEffect());
            assertEquals(expected.get(i).getMilitaryEffect(), effects.get(i).getMilitaryEffect());
            assertEquals(expected.get(i).getNeighborMaterials(), effects.get(i).getNeighborMaterials());
            assertEquals(expected.get(i).getClass(), effects.get(i).getClass());
        }
    }

    @Test
    public void testIsAlreadyInBuilding ()
    {
        wonderBoard = new WonderBoard("Les jardins suspendus de Babylone",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,10))));

        wonderBoard.addCardToBuilding(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));
        wonderBoard.addCardToBuilding(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));
        wonderBoard.addCardToBuilding(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));

        String [] cardTrue = new String []{"BAINS", "AUTEL", "THÉÂTRE"};
        String [] cardFalse = new String []{"TEST", "BAINSS", "BAINS\n", "B*"};

        EffectList effects = wonderBoard.getAllEffects();

        for (String s : cardTrue)
        { assertTrue(wonderBoard.isAlreadyInBuilding(s)); }
        for (String s : cardFalse)
        { assertFalse(wonderBoard.isAlreadyInBuilding(s)); }
    }

    @Test
    public void testCountCard ()
    {
        Random r = new Random();
        CardType[] cardTypes = new CardType[1] ;

        for (int i = 0; i < 1000; i++)
        {
            wonderBoard = new WonderBoard("Les jardins suspendus de Babylone",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,10))));
            int nbCartes = r.nextInt(1000);
            for (int k = 0; k < nbCartes; k++)
            {
                wonderBoard.addCardToBuilding(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));
            }
            cardTypes[0] = CardType.CIVIL_BUILDING;
            assertEquals(nbCartes, wonderBoard.countCard(cardTypes));
        }
    }
}