package commun.request;


import commun.player.PlayerVisible;

import java.util.List;

/**
 * Les action possible par le joueur pour demander des information sur l'etat de la partie
 */
public interface PlayerRequestGame{
    /**
     * permet de recuperer son propre joueur
     * @return le joueur
     */
    public PlayerVisible getMe();

    /**
     * Permet de voir le voisin de gauche
     * @return le voisin de gauche
     */
    public PlayerVisible getLeftNeighbours();

    /**
     * Permet de voir le voisin de droite
     * @return le voisin de droite
     */
    public PlayerVisible getRightNeighbours();

    /**
     * Permet de voir tout les joueur
     * @return la list de tout les joueur
     */
    public List<PlayerVisible> getAllPlayer();


}
