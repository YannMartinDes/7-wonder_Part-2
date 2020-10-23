package severgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import client.AI.AI;
import commun.card.Card;
import commun.card.CardType;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.effect.ScientificType;
import commun.effect.guild.ScientistsGuildEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import org.junit.jupiter.api.*;
import org.mockito.*;
import servergame.engine.ScientistsGuildAction;
import servergame.player.PlayerController;

import java.util.ArrayList;

public class ScientistsGuildActionTest
{
    private ScientistsGuildAction scientistsGuildAction;
    private PlayerController playerController;
    private ArrayList<PlayerController> playerControllerArrayList;

    private WonderBoard wonderBoard;

    @Mock
    private Player player;

    @Mock
    private AI ai;

    @BeforeEach
    public void init ()
    {
        /* Dependencies */
        this.wonderBoard = new WonderBoard("W", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1))));
        this.wonderBoard.addCardToBuilding(new Card("GUILDE DES SCIENTIFIQUES", CardType.GUILD_BUILDINGS,new ScientistsGuildEffect(),3,new MaterialCost(new Material(MaterialType.WOOD,2),new Material(MaterialType.ORES,1),new Material(MaterialType.PAPYRUS,1))));

        /* Mockitos */
        this.ai = Mockito.mock(AI.class);
        Mockito.when(this.ai.useScientificsGuildEffect(this.wonderBoard)).thenReturn(ScientificType.GEOGRAPHY);

        this.player = Mockito.mock(Player.class);
        Mockito.when(this.player.getWonderBoard()).thenReturn(this.wonderBoard);

        /* Parameters dependents */
        this.playerController = new PlayerController(this.player, this.ai);
        this.playerControllerArrayList = new ArrayList<PlayerController>();
        this.playerControllerArrayList.add(this.playerController);
        this.scientistsGuildAction = new ScientistsGuildAction(this.playerControllerArrayList);

        GameLogger.verbose = false;
        GameLogger.verbose_socket = false;
    }

    private EffectList getSciEffects ()
    {
        EffectList ret = new EffectList();
        for (IEffect effect : this.wonderBoard.getAllEffects())
        {
            if (effect == null) continue;
            else if (effect.getScientistsGuild() != null) ret.add(effect);
        }
        return ret;
    }

    @Test
    public void testUseScientistsGuildEffect ()
    {
        EffectList effects;

        // Before
        effects = this.getSciEffects();
        for (int i = 0; i < effects.size(); i++)
        {
            assertEquals(effects.get(i).getScientistsGuild().getScientificType(), null);
        }

        // Launch
        this.scientistsGuildAction.useScientistsGuildEffect();

        // After
        effects = this.getSciEffects();
        for (int i = 0; i < effects.size(); i++)
        {
            assertEquals(effects.get(i).getScientistsGuild().getScientificType(), ScientificType.GEOGRAPHY);
        }
    }
}
