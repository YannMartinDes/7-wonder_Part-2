package servergame.engine;

import java.util.ArrayList;
import java.util.List;

import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import servergame.card.CardManager;
import servergame.wonderboard.WonderBoardFactory;

/**
 * Moteur de jeu qui a pour role de gerer le deroulement d'une partie
 * elle gere les tour, les age et la fin de partie.
 * @author Yohann
 *
 */
public class GameEngine {
	
	private int nbPlayer;
	private List<Player> allPlayers;
	private CardManager cardManager;
	
	public GameEngine(List<Player> allPlayers) {
		this.setNbPlayer(allPlayers.size());
		this.allPlayers = allPlayers;
		this.cardManager = new CardManager();
	}
	
	
	/**
	 * Permet de lancer une parti
	 */
	public void startGame() {
		cardManager.createHands(1);
		assignPlayersWonderBoard();
		assignPlayersDeck();
		round();
		GameLogger.log("fin de la parti");
	}
	
	
	/**
	 * le deroulement d'un tour de jeu
	 */
	public void round() {
		GameLogger.log("debut du round");
		for(Player player : allPlayers) {
			player.controllerPlay();
		}
		
		for(Player player : allPlayers) {
			player.playAction();
		}
		GameLogger.log("fin du round");
		//TODO score calcule + display result
	}
	
	
	/**
	 * on assigne le deck au joueur pour le tour qui commence
	 */
	private void assignPlayersDeck(){
		for(int i =0; i<nbPlayer; i++) {
			allPlayers.get(i).setCurrentDeck(cardManager.getHand(i));
		}
	}
	
	
	/**
	 * On assigne une merveille au joueur pour la parti
	 */
	private void assignPlayersWonderBoard(){
		ArrayList<WonderBoard> wonders = new WonderBoardFactory().chooseWonderBoard(nbPlayer);
		
		for(int i =0; i<nbPlayer; i++) {
			allPlayers.get(i).setWondersBoard(wonders.get(i));
		}
	}
	
	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public CardManager getCardManager() {
		return cardManager;
	}
	
	
	
	

}
