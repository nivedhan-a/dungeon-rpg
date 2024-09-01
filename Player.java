import java.util.Random;

public class Player {
    /* Class fields */

    // Default number of potions of this player.
    public static final int DEFAULT_NUMBER_OF_POTIONS = 3;

    // The delay used for display messages to allow for readability.
    public static final long DELAY = 1000;

    // Random number generator for this player.
    public static Random RANDOM = new Random();

    // The amount of HP one potion heals.
    public static final int POTION_HEALING = 30;

    // Maximum health of this player.
    public static final int FULL_HEALTH = 100;

    // The maximum attack damage of this player.
    public static final int MAXIMUM_ATTACK_DAMAGE = 25;

    // The default name given to a player.
    public static final String NO_NAME = "";

    /* instance fields */
    private Armour armour;
    private int attackDamage;
    private int enemiesKilled;
    private boolean hasSword;
    private boolean hasArmour;
    private int health;
    private Pouch pouch;
    private String name;
    private int potionsRemaining;
    private Sword sword;

    // Constructs a new Player.
    public Player()
    {
        name = NO_NAME;
        hasSword = false;
        hasArmour = false;
        health = FULL_HEALTH;
        potionsRemaining = DEFAULT_NUMBER_OF_POTIONS;
        enemiesKilled = 0;
        sword = new Sword("balloon");
        armour = new Armour("clothes");
        pouch = new Pouch();
    }

    // Returns the name of this player.
    public String getName()
    {
        return name;
    }

    // Returns the health of this player.
    public int health()
    {
        return health;
    }

    // Returns the enemies killed of this player.
    public int enemiesKilled()
    {
        return enemiesKilled;
    }

    // Returns the number of potions this player has.
    public int getPotions()
    {
        return potionsRemaining;
    }

    // Returns the sword of this player.
    public Sword getSword()
    {
        return sword;
    }

    // Returns the armour of this player.
    public Armour getArmour()
    {
        return armour;
    }

    // Returns the pouch of this player.
    public Pouch getPouch()
    {
        return pouch;
    }

    // Returns whether this player has a sword.
    public boolean hasSword()
    {
        return hasSword;
    }

    // Returns whether this player has armour.
    public boolean hasArmour()
    {
        return hasArmour;
    }

    // Sets the number of enemies killed by this player. Used for importing saved data.
    public void setEnemiesKilled(int enemiesKilled)
    {
        this.enemiesKilled = enemiesKilled;
    }

    // Sets the number of health points of this player.
    public void setHealth(int healthPoints)
    {
        if (healthPoints > 0 && healthPoints <= FULL_HEALTH)
        {
            health = healthPoints;
        }
    }

    // Sets the name of this player.
    public void setName(String name)
    {
        this.name = name;
    }

    // Sets the number of potions of this player.
    public void setNumberOfPotions(int potions)
    {
        if (potions >= 0)
        {
            potionsRemaining = potions;
        }
    }

    // Returns damage dealt by this player, accounting for their sword.
    public int attack()
    {
        if (hasSword)
        {
            // Player has a sword, use it to deal more damage.
            sword.useSword();

            // Check the hitpoint status of the sword.
            if (sword.hitpoints() <= 0)
            {
                // Warn the user their sword has broken.
                System.out.println("\nYour " + sword.name() + " broke.");

                try
                {
                    Thread.sleep(DELAY);
                }
                catch (InterruptedException exception)
                {
                    System.out.println("The game experienced an interrupted exception.");
                    System.out.println("The game data could not be saved.");
                    System.out.println("Please restart the game.");
                    System.exit(0);
                }
                // The sword is broken, the player no longer has a sword.
                hasSword = false;
            }

            // Increase the base attack damage by the sword's additional damage.
            return RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE) + sword.damageIncrease();
        }

        // Player does not have a sword, return the base attack damage.
        return RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE);
    }

    // Reduce the HP of this player by a specified amount.
    public void takeDamage(int damage)
    {
        if (hasArmour)
        {
            // Player has armour, use it to decrease the damage taken.
            armour.useArmour();

            // Protect against taking negative damage.
            health = health - Math.max(damage - armour.damageBlocked(), 0);

            // Check the hitpoint status of this armour.
            if (armour.hitpoints() <= 0)
            {
                // Warn the player that their armour has broken.
                System.out.println("\nYour " + armour.name() + " broke.");

                try
                {
                    Thread.sleep(DELAY);
                }
                catch (InterruptedException exception)
                {
                    System.out.println("The game experienced an interrupted exception.");
                    System.out.println("The game data could not be saved.");
                    System.out.println("Please restart the game.");
                    System.exit(0);
                }
                // The armour is broken, the player no longer has armour.
                hasArmour = false;
            }
        }
        else
        {
            // Take regular damage if the player does not have armour.
            health = health - damage;
        }
    }

    // Uses a potion on this player.
    public void usePotion()
    {
        // Exit the function if there are no potions.
        if (potionsRemaining <= 0) return;

        // Use the potion to increase the player's health.
        health = health + POTION_HEALING;

        // Decrement the potions since one was just used.
        potionsRemaining--;
    }

    // Increases the amount of potions this player has by a specified amount.
    public void addPotions(int potions)
    {
        potionsRemaining = potionsRemaining + potions;
    }

    // Increases the kill count of this player.
    public void increaseEnemiesKilled()
    {
        enemiesKilled++;
    }

    // Gives a sword to this player.
    public void addSword(String type)
    {
        if (type == null) return;

        sword = new Sword(type);

        hasSword = true;
    }

    // Gives armour to this player.
    public void addArmour(String type)
    {
        if (type == null) return;

        armour = new Armour(type);

        hasArmour = true;
    }

    // Returns this player's data to be saved in text format.
    public String getData()
    {
        return
                name + " "
                        + hasSword + " "
                        + hasArmour + " "
                        + enemiesKilled + " "
                        + health + " "
                        + potionsRemaining + " "
                        + pouch.getCoins();
    }

    // Resets the state of this player.
    public void reset()
    {
        health = FULL_HEALTH;
        potionsRemaining = DEFAULT_NUMBER_OF_POTIONS;
        enemiesKilled = 0;
        hasSword = false;
        hasArmour = false;
    }
}