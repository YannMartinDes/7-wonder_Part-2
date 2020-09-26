package severgame.card;

import static org.junit.jupiter.api.Assertions.*;

import commun.card.CardType;
import commun.card.Deck;
import commun.effect.IEffect;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.*;
import servergame.card.CardFactory;

public class CardFactoryTest
{
    private CardFactory cardFactory;

    @BeforeEach
    public void init ()
    {
        this.cardFactory = new CardFactory();
    }

    @Test
    public void testAgeOneCardsName ()
    {
        String[] expected = new String [] {
                "PRÊTEUR SUR GAGES",
                "PRÊTEUR SUR GAGES",
                "BAINS",
                "BAINS",
                "AUTEL",
                "AUTEL",
                "THÉÂTRE",
                "THÉÂTRE"};

        Deck deckGot = this.cardFactory.AgeOneCards();

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getName(), expected[i]);
        }
    }

    @Test
    public void testAgeOneCardsEffect ()
    {
        IEffect[] expected = new IEffect[] {
                new VictoryPointEffect(3),
                new VictoryPointEffect(3),
                new VictoryPointEffect(3),
                new VictoryPointEffect(3),
                new VictoryPointEffect(2),
                new VictoryPointEffect(2),
                new VictoryPointEffect(2),
                new VictoryPointEffect(2)};

        Deck deckGot = this.cardFactory.AgeOneCards();

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getCardEffect().getScore(), expected[i].getScore());
        }
    }

    @Test
    public void testAgeOneCardsType ()
    {
        CardType[] expected = new CardType [] {
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING,
                CardType.CIVIL_BUILDING};

        Deck deckGot = this.cardFactory.AgeOneCards();

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getType(), expected[i]);
        }
    }

    @Test
    public void testAgeOneCardsAge ()
    {
        Integer[] expected = new Integer [] {
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1};

        Deck deckGot = this.cardFactory.AgeOneCards();

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getAge(), expected[i]);
        }
    }
}