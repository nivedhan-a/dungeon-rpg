import java.util.Random;

public class Enemy {
    // An array of possible enemy types.
    public static final String[] ENEMY_NAMES = { "Zombie", "Skeleton", "Warrior", "Goblin", "Werewolf", "Vampire" };

    // The maximum attack damage of this enemy.
    public static final int MAXIMUM_ATTACK_DAMAGE = 20;

    // The maximum health of this enemy.
    public static final int MAXIMUM_HEALTH = 75;

    // The minimum health of this enemy.
    public static final int MINIMUM_HEALTH = 1;

    // The random number generator of this enemy.
    public static final Random RANDOM = new Random();

    // instance fields
    private int health;
    private String name;

    // Constructs a new enemy.
    public Enemy()
    {
        // Fetch a random name from the list of enemies.
        name = ENEMY_NAMES[RANDOM.nextInt(ENEMY_NAMES.length)];

        // Give the enemy a random health
        health = RANDOM.nextInt(MAXIMUM_HEALTH);
    }


    public int attack() {
        return RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE);
    }

    public void takeDamage(int damage) {
        health = health - damage;
    }

    public String name() {
        return name;
    }

    public int health() {
        return health;
    }
}
