package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.AddingMaterialEffet;
import commun.effect.CoinEffect;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CucumberWonderBoardTest implements En {
    WonderBoard wonderBoard;
    Card card;

    public CucumberWonderBoardTest() {
        Given("Je crée un plateau de la merveille de nom {string} qui rapporte une ressource bois", (String name) ->
        {
            wonderBoard = new WonderBoard(name, new AddingMaterialEffet(new Material(MaterialType.WOOD,1)));
        });
        When("J'ajoute une carte de nom {string} de l'age {int}" , (String name, Integer age) ->
        {
            card = new Card(name,CardType.COMMERCIAL_BUILDINGS, new CoinEffect(5), age, null);
            wonderBoard.addCardToBuilding(card);
            wonderBoard.addCoin(card.getCardEffect().getNumberOfCoin());
        });
        Then("La carte peut être ajoutée au plateau de la merveille", () ->
        {
            assertEquals(card, wonderBoard.getBuilding().getCard(0));
        });
        Then("Le plateau de merveille possède {int} pièces", (Integer coins) ->
        {
           assertEquals(8, wonderBoard.getCoin());
        });
    }
}
