package commun.request;

/**
 * Permet de s'inscrire pour jouer une parti.
 * cela permet aussi au serveur de le contacter par la suite
 */
public class ID {

    private String uri;
    private String name;

    public ID(String uri,String name){
        this.uri = uri;
        this.name = name;

    }

    /**
     * Permet de recuperer l'adresse du joueur
     * @return l'uri
     */
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Permet de recuperer le nom du joueur
     * @return le nom du joueur
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
