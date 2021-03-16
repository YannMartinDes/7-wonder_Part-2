package servergame;

import commun.player.Player;
import commun.request.ID;
import commun.request.RequestToPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import servergame.inscription.InscriptionPlayer;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;
import servergame.player.rest.RequestToPlayerRestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class GameInitializer {
    String[] names = new String[]{"Sardoche", "Paf le chien", "AngryNerd", "Alan Turing", "Hamilton", "Chuck Norris", "Furious Kid"};

    @Autowired
    PlayerManagerImpl playerManager;

    @Autowired
    InscriptionPlayer inscriptionPlayer;

    //nombre d'ia implementer
    private final int NUMBER_IA = 3;

    public void initGame(int numberPlayer){
        inscriptionPlayer.setNbPlayerWaited(numberPlayer);
        inscriptionPlayer.setInscriptionOpen(true);
        List<ID> ids = inscriptionPlayer.waitInscriptionFinish();

        this.initGame(playerBuilder(ids));
    }

    public void initGame(List<RequestToPlayer> listAi){
        List<PlayerController> controllers = initControllers(listAi);
        playerManager.init(controllers);
    }

    /**
     * Permet d'initialiser la list des controller
     * @param listRequestRestTemplate les RequestToPlayerRestTemplate genrer avec les ids
     * @return la list des controller
     */
    protected List<PlayerController> initControllers(List<RequestToPlayer> listRequestRestTemplate){
        ArrayList<PlayerController> allPlayers = new ArrayList<PlayerController>();
        for(int i = 0; i<listRequestRestTemplate.size(); i++){
            PlayerController controller = new PlayerController(new Player(names[i]),listRequestRestTemplate.get(i));
            allPlayers.add(controller);
        }
        return allPlayers;
    }

    protected List<RequestToPlayer> playerBuilder(List<ID> ids)
    {
        List<RequestToPlayer> requestToPlayerRestTemplates = new ArrayList<RequestToPlayer>();

        for (ID id : ids)
        {
            RequestToPlayerRestTemplate requestToPlayerRestTemplate = new RequestToPlayerRestTemplate(id);
            requestToPlayerRestTemplates.add(requestToPlayerRestTemplate);
        }
        return requestToPlayerRestTemplates;
    }
}
