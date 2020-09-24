package servergame.engine;

import java.util.List;

import commun.wonders.Player;
import servergame.card.CardManager;

public class GameEngine {
	
	private int nbPlayer;
	private List<Player> allPlayers;
	private CardManager cardManager;
	
	public GameEngine(int nbPlayer,List<Player> allPlayers) {
		this.setNbPlayer(nbPlayer);
		this.allPlayers = allPlayers;
		this.cardManager = new CardManager();
	}
	
	public void startGame() {
		cardManager.createHands(1);
		assignPlayersDeck();
		round();
	}
	
	public void round() {
		
		for(Player player : allPlayers) {
			player.iaPlay();
		}
		
		for(Player player : allPlayers) {
			player.playAction();
		}
		
		//TODO score calcule + display result
	}
	

	public void assignPlayersDeck(){
		for(int i =0; i<nbPlayer; i++) {
			allPlayers.get(i).setCurrentDeck(cardManager.getHand(i));
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
