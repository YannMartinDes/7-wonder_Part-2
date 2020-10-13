package servergame.player;

import commun.card.Deck;
import servergame.card.CardManager;
import servergame.player.Player;
import servergame.player.PlayerController;

import java.util.List;

public interface PlayerManager {

    public List<Player> getAllPlayers();

    public int getNbPlayer();

    public void playAction();

    public void chooseAction();

    public void finishAction(Deck discard);

    public void afterAction(Deck discard);

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
