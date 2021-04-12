package client.playerRestTemplate;


import commun.communication.CommunicationMessages;
import commun.player.Player;
import commun.request.ID;
import commun.request.PlayerRequestGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Proxy qui contient le resTemplate pour demander les informations au GameServer
 */
@Component
@Scope("singleton")
public class PlayerRestTemplate implements PlayerRequestGame {
    private int playerID = 0;//ID du joueur
    private int nbPlayer = 0;//Nombre de joueur dans la partie

    @Value("${gameServer.uri:}")
    private String URI = "";//URI du GameServer


    private RestTemplate restTemplate;

    public PlayerRestTemplate(RestTemplate restTemplate){this.restTemplate = restTemplate;}
    public PlayerRestTemplate(){
        this.restTemplate = new RestTemplate();
    }

    /**
     * permet de recuperer son propre joueur
     * @return le joueur
     */
    public Player getMe(){
        //Récupération de la réponse.
        ResponseEntity<Player> response;
        response = restTemplate.getForEntity(URI + "/" + CommunicationMessages.BOARD + "/" + playerID, Player.class);

        //Récupération du joueur (de son plateau)
        Player board = response.getBody();
        return board;
    }

    /**
     * Permet de voir le voisin de gauche
     * @return le voisin de gauche
     */
    public Player getLeftNeighbours(){
        int targetID;

        if(playerID == 0) targetID = nbPlayer-1;//On récupère le dernier joueur.
        else targetID = playerID -1;//Joueur de gauche

        //Récupération de la réponse.
        ResponseEntity<Player> response;
        response = restTemplate.getForEntity(URI + "/" + CommunicationMessages.BOARD + "/" + targetID, Player.class);

        //Récupération du joueur (de son plateau)
        Player board = response.getBody();
        return board;
    }

    /**
     * Permet de voir le voisin de droite
     * @return le voisin de droite
     */
    public Player getRightNeighbours(){
        int targetID = (playerID + 1)%nbPlayer;//Monde torique.

        //Récupération de la réponse.
        ResponseEntity<Player> response;
        response = restTemplate.getForEntity(URI + "/" + CommunicationMessages.BOARD + "/" + targetID, Player.class);

        //Récupération du joueur (de son plateau)
        Player board = response.getBody();
        return board;
    }

    /**
     * Permet de voir tout les joueur
     * @return la list de tout les joueur
     */
    public List<Player> getAllPlayers(){
        List<Player> allPlayer = new ArrayList<>();

        //on récupère le plateau de chaque joueur.
        for(int i =0; i< nbPlayer; i++){

            //Récupération de la réponse.
            ResponseEntity<Player> response;
            response = restTemplate.getForEntity(URI + "/" + CommunicationMessages.BOARD + "/" + i, Player.class);

            //Récupération du joueur (de son plateau)
            Player board = response.getBody();
            allPlayer.add(board);
        }
        return allPlayer;
    }


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }
}
