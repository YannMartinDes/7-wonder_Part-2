package client.AI;

import commun.action.AbstractAction;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.player.Player;
import commun.request.PlayerRequestGame;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;

import java.util.List;

/** Interface qui représente les fonctionnalités d'une intelligence artificielle */
public abstract class AI implements RequestToPlayer {
    private PlayerRequestGame requestGame;

    public void setRequestGame(PlayerRequestGame requestGame) {
        this.requestGame = requestGame;
    }

    /*########### permet au joueur de recuperer des information du jeu ###########*/
    /**
     * permet de recuperer son propre joueur
     * @return le joueur
     */
    public Player getMe(){
        return requestGame.getMe();
    }


    /**
     * Permet de voir le voisin de gauche
     * @return le voisin de gauche
     */
    public Player getLeftNeighbours(){
        return requestGame.getLeftNeighbours();
    }

    /**
     * Permet de voir le voisin de droite
     * @return le voisin de droite
     */
    public Player getRightNeighbours(){
        return requestGame.getRightNeighbours();

    }

    /**
     * Permet de voir tout les joueur
     * @return la list de tout les joueur
     */
    public List<Player> getAllPlayers(){
        return requestGame.getAllPlayers();
    }


    /*########### Le joueur dois pouvoir faire ces action pour jouer son tour de jeu ###########*/

    /**
     * Choisi une carte au hasard dans un deck et l'action qu'elle veut effectuer sur cette carte
     * @param deck
     * @return la carte choisie au hasard.
     */
    public abstract AbstractAction chooseAction(Deck deck, int playerCoins, EffectList playerEffects);

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    public abstract Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice);



    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     * @param wonderBoard la wonderboard du joueur
     * @return le type selectionner
     */
    public abstract ScientificType useScientificsGuildEffect(WonderBoard wonderBoard);

  
    /**
     * Choisi une carte au hasard dans les carte defaussé
     * @param deck
     * @return la carte choisie au hasard.
     */
    public abstract int chooseCard(Deck deck);


}
