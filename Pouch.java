public class Pouch
{
    /* instance fields */
    private int coins;

    // Constructs money with the default number of coins.
    public Pouch()
    {
        coins = 0;
    }

    // Returns the coins in this pouch.
    public int getCoins()
    {
        return coins;
    }

    // Adds coins to this pouch.
    public void addCoins(int coins)
    {
        this.coins += coins;
    }

    // Removes coins from this pouch.
    public void removeCoins(int coins)
    {
        this.coins -= coins;
    }

    // Sets the coins of this pouch.
    public void setCoins(int coins)
    {
        this.coins = coins;
    }
}
