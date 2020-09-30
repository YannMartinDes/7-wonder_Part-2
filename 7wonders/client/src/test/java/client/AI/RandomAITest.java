package client.AI;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import commun.action.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RandomAITest
{
    private RandomAI randomAI;

    @BeforeEach
    public void init ()
    { this.randomAI = new RandomAI(); }

}
