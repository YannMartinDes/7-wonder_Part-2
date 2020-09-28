package commun.wonderboard;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.AddingMaterialEffet;
import commun.effect.VictoryPointEffect;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WonderBoardTest {

    WonderBoard wonderBoard = new WonderBoard("Les jardins suspendus de Babylone",new AddingMaterialEffet(new Material(MaterialType.CLAY,10)));

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
}
