package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.CoinEffect;
import commun.effect.MilitaryEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CucumberWonderBoardTest implements En {
    WonderBoard wonderBoard;
    Card card;
    Card card2;

    public CucumberWonderBoardTest() {
        Given("Je crée un plateau de la merveille de nom {string} qui rapporte une ressource bois", (String name) ->
        {
            wonderBoard = new WonderBoard(name, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
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


        Given("Je crée un plateau de merveille de nom {string}", (String name) ->
        {
           wonderBoard = new WonderBoard(name, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
        });
        When("J'ajoute la carte {string} de l'âge {int} et la carte {string} de l'âge {int} à {string}",
                (String cardName1, Integer age1, String cardName2, Integer age2, String wonder)-> {
            card = new Card(cardName1,CardType.MILITARY_BUILDINGS, new MilitaryEffect(1), age1,new MaterialCost(new Material(MaterialType.WOOD,1)));
            card2 = new Card(cardName1,CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), age2,new MaterialCost(new Material(MaterialType.STONE,3)));
            wonderBoard.addCardToBuilding(card);
            wonderBoard.addMilitaryPower(1);
            wonderBoard.addCardToBuilding(card2);
            wonderBoard.addMilitaryPower(2);


        });
        Then("Les cartes {string} et {string} peuvent être ajoutées au plateau de la merveille {string}", (String cardName, String cardName2, String name) ->
        {
            assertEquals(card, wonderBoard.getBuilding().getCard(0));
            assertEquals(card2, wonderBoard.getBuilding().getCard(1));
        });
        Then("La merveille {string} possède {int} de puissance militaire", (String wonder, Integer militaryPower) ->
        {
            assertEquals(militaryPower, wonderBoard.getMilitaryPower());
        });

    }
}
