package cucumber;

// Coins
import servergame.coins.*;

// Cucumber
import io.cucumber.java8.En;

// JUnit
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;


public class CucumberCoinsTest implements En
{
    private Coins coins;
    private int oldCoins;

    public CucumberCoinsTest ()
    {
        AtomicInteger negatif = new AtomicInteger();

        Given("j'ai {int} pieces", (Integer nbPieces) ->
        {
            this.coins = new Coins(nbPieces);
            this.oldCoins = this.coins.getCoins();
        });

        When("je defausse une carte", () ->
        { this.coins.obtain3coins(); });
        Then("j'obtiens 3 pieces en plus, soit {int} pieces au total", (Integer total) ->
        { assertEquals(total, this.oldCoins + 3); });

        When("je redefini les pieces du joueur par un nombre negatif, par exemple {int}", (Integer neg) ->
        { negatif.set(neg); });
        Then("il est renvoye une erreur d'argument", () ->
        {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> { this.coins.setCoins(negatif.get()); });

            String expectedMessage = "Nombre negatif interdit";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));});
    }
}