package servergame.player;


import commun.player.PlayerVisible;

import java.util.List;

public interface PlayerView {

    /**
     * Permet de recuperer la list de tout les joueur (dans l'ordre)
     * @return la liste de tout les joueur
     */
    public List<PlayerVisible> getAllPlayer();

    private int getIndexPlayer(PlayerVisible player){
        int index = 0;
        List<PlayerVisible> allPlayer = getAllPlayer();
        for(int i =0; i<allPlayer.size();i++){
            if(player.equals(allPlayer.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Permet de recuperer le voisin de gauche
     * @param player permet de recuperer le voisin de gauche
     * @return le voisin de gauche
     */
    public default PlayerVisible getLeftNeghbours(PlayerVisible player){
        int index = getIndexPlayer(player);
        List<PlayerVisible> allPlayer = getAllPlayer();
        if(index == 0) return allPlayer.get(allPlayer.size()-1);
        return allPlayer.get(index-1);
    }

    /**
     * Permet de recuperer le voisin de droite
     * @param player permet de recuperer le voisin de droite
     * @return le voisin de droite
     */
    public default PlayerVisible getRightNeghbours(PlayerVisible player){
        int index = getIndexPlayer(player);
        List<PlayerVisible> allPlayer = getAllPlayer();
        if(index == allPlayer.size()-1) return allPlayer.get(0);
        return allPlayer.get(index+1);

    }
}
