package servergame.player;

import client.AI.AI;
import commun.action.AbstractAction;
import commun.action.ActionType;
import commun.card.Deck;
import commun.communication.StatObject;
import commun.effect.ScientificType;
import commun.player.Player;
import commun.request.PlayerRequestGame;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
import log.GameLogger;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements PlayerRequestGame {

    Player player;
    RequestToPlayer ai;
    AbstractAction action;
    StatObject statObject;


    public PlayerController(Player player, RequestToPlayer ai, StatObject statObject) {
        this.player = player;
        this.ai = ai;
        this.statObject = statObject;
    }

    /**
     * Demande a l'ia l'action quelle veut jouer
     */
    public void chooseAction ()
    {
        action = null;
        while(action == null)
            this.action = ai.chooseAction(player.getCurrentDeck(), player.getWonderBoard().getCoin(), player.getWonderBoard().getAllEffects());
    }


    /**
     * Joue l'action
     * @param discardingDeck la défausse
     */
    public void playAction(Deck discardingDeck){
        action.playAction(player.getName(),player.getCurrentDeck(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour());

        if(action.getTradePossibility().size() != 0){//Si il y a un échange à choisir.
            Integer[] AIChoice = ai.choosePurchasePossibility(action.getTradePossibility());//On demande à l'IA de faire son choix;
            //On joue l'action du trade.
            action.nextAction(player.getName(),player.getCurrentDeck(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour(),AIChoice);
        }

        //Log de l'action.
        GameLogger.getInstance().logSpaceBefore("Le joueur : ["+player.getName()+"] :", ConsoleColors.ANSI_CYAN_BOLD);
        action.logAction(player.getName(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour());

        endActionStatistics(statObject,player.getName());
    }

    /**
     * Finis l'action
     * @param discardingDeck la defausse
     */
    public void finishAction(Deck discardingDeck){
        action.finishAction(player.getName(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour(),action.getPlayedCard(),ai);
    }

    public AbstractAction getAction() {
        return action;
    }


    /**
     * Permet de choisir le symbole scientifique que l'ia veut
     * @param wonderBoard
     * @return
     */
    public ScientificType useScientificsGuildEffect(WonderBoard wonderBoard) {
        ScientificType choice = null;
        while (choice == null) { //verif du choix de l'ia
            choice = ai.useScientificsGuildEffect(wonderBoard);
        }
        return choice;
    }

    /**
     * Stats.
     * @param statObject l'objet stats.
     * @param playerName le nom du joueur.
     */
    private void endActionStatistics (StatObject statObject, String playerName)
    {
        if (statObject != null)
        {
            int indexInStatObject = statObject.getUsernames().indexOf(playerName) - 1;
            if (action.getType() == ActionType.BUILD)
            {
                ArrayList<Integer> array = new ArrayList<Integer>();
                this.fillStatisticsArray(indexInStatObject, statObject, array);
                statObject.getStatByAge(statObject.getCurrentAge()).getStatCards(action.getPlayedCard().getType().getIndex()).add(array);
            }
            else if (action.getType() == ActionType.BUILD_STAGE_WONDER)
            {
                ArrayList<Integer> array = new ArrayList<Integer>();
                this.fillStatisticsArray(indexInStatObject, statObject, array);
                statObject.getStatByAge(statObject.getCurrentAge()).getStatWonderProgression().add(array);
            }
            else if (action.getType() == ActionType.DISCARD)
            {
                ArrayList<Integer> array = new ArrayList<Integer>();
                this.fillStatisticsArray(indexInStatObject, statObject, array);
                statObject.getStatByAge(statObject.getCurrentAge()).getStatSoldCards().add(array);
            }
        }
    }

    public RequestToPlayer getAI ()
    { return this.ai; }

    /**
     * Stats
     * @param index index
     * @param statObject l'objet de stats
     * @param array liste
     */
    private void fillStatisticsArray (int index, StatObject statObject, ArrayList<Integer> array)
    {
        // - 1 a cause du username '/'
        for (int i = 0; i < statObject.getUsernames().size() - 1; i++)
        {
            if (i == index) { array.add(1); }
            else { array.add(0); }
        }
    }

    /**
     * Joue la 7eme carte
     * @param discardingDeck la defausse.
     */
    public void playLastCard(Deck discardingDeck) {
        GameLogger.getInstance().logSpaceBefore("Le joueur : [" + player.getName() + "] va jouer sa dernière carte grâce à l'étape de sa merveille.");

        this.chooseAction();
        this.playAction(discardingDeck);
        this.finishAction(discardingDeck);
    }
    public Player getPlayer() {
        return player;
    }

    /*===========================Les donner que le joueur peut demander */
    /**
     * permet de recuperer son propre joueur
     *
     * @return le joueur
     */
    @Override
    public Player getMe() {
        //TODO faire une copie
        return player;
    }

    /**
     * Permet de voir le voisin de gauche
     *
     * @return le voisin de gauche
     */
    @Override
    public Player getLeftNeighbours() {
        //TODO implementer
        return null;
    }

    /**
     * Permet de voir le voisin de droite
     *
     * @return le voisin de droite
     */
    @Override
    public Player getRightNeighbours() {
        //TODO implementer
        return null;
    }

    /**
     * Permet de voir tout les joueur
     * @return la list de tout les joueur
     */
    @Override
    public List<Player> getAllPlayers() {
        //TODO implementer
        return null;
    }
}
