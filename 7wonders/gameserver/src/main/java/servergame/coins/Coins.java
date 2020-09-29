package servergame.coins;

/** Coins est une class qui represente les pieces/la monnaie d'un joueur dans le jeu */
public class Coins
{
    /* Fields */
    private int coins;

    /** Constructeurs */
    public Coins()
    { this(0); }

    public Coins(int coins)
    { this.coins = coins; }

    /** Sert a la revente d'une carte */
    public void obtain3coins ()
    { this.coins += 3; }

    /** Fati la meme chose que obtain3coins */
    public void add3coins ()
    { this.obtain3coins(); }

    /* Getters - Setters */

    public void setCoins (int coins)
    {
        if (coins < 0)
            throw new IllegalArgumentException("Nombre negatif interdit");
        this.coins = coins;
    }

    public int getCoins ()
    { return this.coins; }
}
