package servergame.card;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;

public class CardFactory {

    /**
     * Créer le deck de l'age 1
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeOneCards(){

        Deck deck1 = new Deck();

        //Cartes Bleues (Batiment civil)
        deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1));//4+
        deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1));//7+
        deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1));//3+
        deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1));//7+
        deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1));//3+
        deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1));//5+
        deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1));//5+
        deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1));//6+

        return deck1;
    }
}
