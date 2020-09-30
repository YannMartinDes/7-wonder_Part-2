package cucumber;

// Coins
import commun.wonderboard.WonderBoard;

// Cucumber
import io.cucumber.java8.En;
import servergame.player.Player;

// JUnit
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;


public class CucumberCoinsTest implements En
{
    private WonderBoard wonderBoard;
    private int oldCoins;
    private Player player;

    public CucumberCoinsTest ()
    {
        AtomicInteger negatif = new AtomicInteger();

        Given("j'ai {int} pieces", (Integer nbPieces) ->
        {
            this.wonderBoard = new WonderBoard("test",null);
            wonderBoard.removeCoin(3);//On enleve l'argent donné au départ
            wonderBoard.addCoin(nbPieces);
            player = new Player("joueur",wonderBoard);
        });

        When("je defausse une carte", () ->
        {
            //TODO PASSER PAR LE PLAYER ET PLAY ACTION
            wonderBoard.addCoin(3);
        });

        When("je dépense {int} pièces", (Integer coin) ->
        {
            wonderBoard.removeCoin(coin);
        });
        Then("j'ai {int} pieces au total", (Integer total) ->
        { assertEquals(total, wonderBoard.getCoin()); });

    }
}