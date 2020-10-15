package servergame.player;

import commun.player.Player;

import java.util.List;

public interface PlayerView {
    /**
     * Permet de recuperer le voisin de gauche du joueur
     * @param player le joueur que l'on veut recuperer les voisin de gauche
     * @return le voisin de gauche
     */
    public Player getLeftNeighbours(Player player);

    /**
     * Permet de recuperer le voisin de droite du joueur
     * @param player le joueur que l'on veut recuperer les voisin de droite
     * @return le voisin de droite
     */
    public Player getRightNeighbours(Player player);

    /**
     * Recuperer la list de tout les joueur
     * @return la liste de tout les joueur.
     */
    public List<Player> getAllPlayers();
}
