package servergame.player;

import commun.action.AbstractAction;
import commun.card.Deck;
import servergame.card.CardManager;
import commun.player.Player;

import java.util.List;

public interface PlayerManager {

    public List<PlayerController> getPlayerControllers();

    public List<Player> getAllPlayers();

    public int getNbPlayer();

    public void chooseAction();

    public void playAction(Deck discard);

    public void finishAction(Deck discard);

    public void informations();

    /**
     * on assigne le deck au joueur pour le tour qui commence
     */
    public void assignPlayersDeck(CardManager cardManager);

    /**
     * Assigne les voisins (leur wonderboard) aux joueurs.
     */
    public void assignNeightbours();


    /**
     * On assigne une merveille au joueur pour la partie
     */
    public void assignPlayersWonderBoard();

}
