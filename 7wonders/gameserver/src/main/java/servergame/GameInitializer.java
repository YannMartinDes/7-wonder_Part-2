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

    @Autowired
    PlayerManagerImpl playerManager;

    @Autowired
    InscriptionPlayer inscriptionPlayer;


    public void initGame(){
        inscriptionPlayer.setInscriptionOpen(true);
        List<ID> ids = inscriptionPlayer.waitInscriptionFinish();
        this.initGame(playerBuilder(ids));
    }

    public void initGame(List<RequestToPlayerRestTemplate> listAi){
        List<PlayerController> controllers = initControllers(listAi);
        playerManager.init(controllers);
    }

    /**
     * Permet d'initialiser la list des controller
     * @param listRequestRestTemplate les RequestToPlayerRestTemplate genrer avec les ids
     * @return la list des controller
     */
    protected List<PlayerController> initControllers(List<RequestToPlayerRestTemplate> listRequestRestTemplate)
    {
        ArrayList<PlayerController> allPlayers = new ArrayList<PlayerController>();
        for(int i = 0; i<listRequestRestTemplate.size(); i++)
        {
            PlayerController controller = new PlayerController(new Player(listRequestRestTemplate.get(i).getName()),listRequestRestTemplate.get(i));
            allPlayers.add(controller);
        }
        return allPlayers;
    }

    protected List<RequestToPlayerRestTemplate> playerBuilder(List<ID> ids)
    {
        List<RequestToPlayerRestTemplate> requestToPlayerRestTemplates = new ArrayList<RequestToPlayerRestTemplate>();

        for (ID id : ids)
        {
            RequestToPlayerRestTemplate requestToPlayerRestTemplate = new RequestToPlayerRestTemplate(id);
            requestToPlayerRestTemplates.add(requestToPlayerRestTemplate);
        }
        return requestToPlayerRestTemplates;
    }
}
