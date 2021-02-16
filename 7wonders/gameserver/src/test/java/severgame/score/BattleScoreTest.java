package severgame.score;

import commun.card.Card;
import commun.card.CardType;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.guild.StrategistsGuild;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servergame.score.BattleScore;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleScoreTest
{
    private BattleScore battleScore;
    private Player player;

    @BeforeEach
    void init ()
    {
        Logger.logger.verbose_socket = false;
        Logger.logger.verbose = false;
        this.player = new Player("P");
        this.battleScore = new BattleScore();
        // Sa merveille lui donne x1 MaterialType.WOOD
        this.player.setWonderBoard(new WonderBoard("W", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1)))));
    }

    @Test
    void testBattleTokenPoints ()
    {
        assertEquals(this.battleScore.computeScore(this.player), 0);
        this.player.getWonderBoard().addConflictPoints(10);
        assertEquals(this.battleScore.computeScore(this.player), 10);
    }

    @Test
    void testGuildesStrategiesPoints ()
    {
        Card test = new Card("GUILDE DES STRATÈGES", CardType.GUILD_BUILDINGS,new StrategistsGuild(),3,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.STONE,1),new Material(MaterialType.FABRIC,1)));

        assertEquals(this.battleScore.computeScore(this.player), 0);
        this.player.getWonderBoard().getBattlePoint().addToken(10);
        assertEquals(this.battleScore.computeScore(this.player), 10);
    }
}
