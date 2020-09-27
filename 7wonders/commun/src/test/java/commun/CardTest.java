package commun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.IEffect;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;


public class CardTest {


    /**
     * Cette methode nous permet de verifier si la carte se créer correctement.
     */
    @Test
    public void creationCardTest()
    {

        IEffect effect = new VictoryPointEffect(3);
        Card card = new Card("CivilBuilding", CardType.CIVIL_BUILDING, effect,1,null);

        assertNotNull(effect);
        assertNotNull(card);

        assertEquals(card.getName(), "CivilBuilding");
        assertEquals(card.getType(), CardType.CIVIL_BUILDING);
        assertEquals(card.getCardEffect(), effect);
        assertEquals(card.getAge(), 1);

    }





}
