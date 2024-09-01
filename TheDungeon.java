import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TheDungeon
{
    /** The menu option for attacking. */
    public static final int ATTACK = 1;

    /** The string which contains all acceptable affirmative answers to yes or no questions. Not case-sensitive. */
    public static final String CONFIRMATION = "yesyyupoksureof course";

    /** The delay used for display messages. */
    public static final long DELAY = 2000;

    /** The menu option for exiting the game. */
    public static final int EXIT = 5;

    /** The maximum amount of gold that can be dropped. */
    public static final int MAXIMUM_GOLD_DROP = 30;

    /** The coin/health penalty for running away. */
    public static final int PENALTY_FOR_RUNNING = 5;

    /** The random number generator of this game. */
    public static final Random RANDOM = new Random();

    /** The menu option for running away. */
    public static final int RUN = 3;

    /** The menu option for visiting the store. */
    public static final int VISIT_STORE = 4;

    /** The way of gathering input of this game. */
    public static final Scanner SCANNER = new Scanner(System.in);

    /** The menu option for using a potion. */
    public static final int USE_POTION = 2;

    /**
     * The dungeon game.
     *
     * @param argument not used
     */
    public static void main(String[] argument)
    {
        // Main character
        Player player = new Player();
        Pouch pouch = player.getPouch();

        // Game variables
        /* The following three chance variables are percentages */
        int armourDropChance = 10;
        int healthPotionDropChance = 50;
        int swordDropChance = 10;
        boolean running = true;
        boolean ranAway = false;

        // Game introduction
        System.out.println("\fWelcome to the dungeon.");

        System.out.print("Would you like to load your previous game? ");
        String loadGameState = SCANNER.nextLine();

        if (CONFIRMATION.contains(loadGameState))
        {
            System.out.print("\nWhat is your name? ");
            String name = SCANNER.nextLine();

            // Trim whitespace of the name.
            name = name.replaceAll("\\s+","");
            player.setName(name);

            // Search for the user's name in the database
            try
            {
                State.loadState(player);
            }
            catch (FileNotFoundException exception)
            {
                System.out.println("\nYour saved game was not found. Starting a new unsaved game.");
                delay();
            }
            catch (IOException exception)
            {
                System.out.println("Input from the keyboard could not be read. Please restart the game.");
            }
        }

        // Game loop
        while (running)
        {
            // Main enemy
            Enemy villain = new Enemy();

            while (villain.health() > 0)
            {
                printStatistics(player, villain);

                startBattle();

                int choice;

                try
                {
                    choice = Integer.parseInt(SCANNER.nextLine());
                }
                catch (NumberFormatException exception)
                {
                    choice = RUN;
                }

                switch (choice)
                {
                    case ATTACK:
                        ranAway = false;
                        int playerAttack = player.attack();
                        int enemyAttack = villain.attack();

                        System.out.println("\nYou dealt " + playerAttack + " damage.");
                        System.out.println("You took " + enemyAttack + " damage.");

                        villain.takeDamage(playerAttack);
                        player.takeDamage(enemyAttack);

                        delay();
                        break;

                    case USE_POTION:
                        if (player.health() > player.FULL_HEALTH - player.POTION_HEALING)
                        {
                            System.out.println("\nYou are healthy, and do not need a potion.");
                            TheDungeon.delay();
                            break;
                        }

                        player.usePotion();

                        System.out.println("\nYou drank the potion. Health restored by: " + Player.POTION_HEALING + " HP");
                        System.out.println("Current HP: " + player.health());

                        delay();
                        break;

                    case RUN:
                        // Penalize the player by removing their coins or health
                        if (player.getPouch().getCoins() > PENALTY_FOR_RUNNING)
                        {
                            System.out.println("\n" + PENALTY_FOR_RUNNING + " coins were stolen by the " + villain.name());
                            pouch.removeCoins(PENALTY_FOR_RUNNING);
                        }
                        // Player does not have enough coins. Take away health instead of coins.
                        else
                        {
                            System.out.println("\nThe enemy did " + PENALTY_FOR_RUNNING + " damage before you managed to escape");
                            player.takeDamage(PENALTY_FOR_RUNNING);
                        }

                        System.out.println("\nYou successfully ran away!");
                        delay();

                        // Kill the enemy by dealing damage equivalent to its health.
                        villain.takeDamage(villain.health());

                        ranAway = true;
                        break;

                    case VISIT_STORE:
                        // Print the store options.
                        Store.printStore(player);
                        break;

                    case EXIT:
                        System.out.println("\fExiting game...");
                        System.out.print("Would you like to save your progress? ");

                        if (CONFIRMATION.contains(SCANNER.nextLine()))
                        {
                            State.saveState(player);
                        }

                        running = false;
                        return;
                }

                if (player.health() <= 0)
                {
                    System.out.println("\nUh oh! You have died, game over.");

                    System.out.print("Would you like to respawn? ");
                    String continueGame = SCANNER.nextLine();

                    if (CONFIRMATION.contains(continueGame))
                    {
                        running = true;
                        player.reset();
                    }
                    else
                    {
                        System.out.println("\nProgram terminated.");

                        // Kill the enemy by dealing damage equivalent to its health.
                        villain.takeDamage(villain.health());
                        running = false;
                        return;
                    }
                }
            }

            if (!ranAway)
            {
                // The enemy has died and the player did not run away. This means the player killed the enemy. Reward the player.
                player.increaseEnemiesKilled();

                // Give the player some gold for killing the enemy.
                pouch.addCoins(RANDOM.nextInt(MAXIMUM_GOLD_DROP));

                if (RANDOM.nextInt(100) < swordDropChance)
                {
                    if (player.hasSword())
                    {
                        System.out.println("\nThe " + villain.name() + " dropped a sword, but you already have one.");
                    }
                    else
                    {
                        player.addSword("");
                        System.out.println("\nThe " + villain.name() + " dropped a " + player.getSword().name() + ".\nYour attack damage has now increased by " + player.getSword().damageIncrease() + ".");
                    }
                    delay();
                }

                else if (RANDOM.nextInt(100) < armourDropChance)
                {
                    if (player.hasArmour())
                    {
                        System.out.println("\nThe " + villain.name() + " dropped some armour, but you already have some.");
                    }
                    else
                    {
                        player.addArmour("leather");
                        System.out.println("\nThe " + villain.name() + " dropped " + player.getArmour().name() + ".\nYour damage taken has now decreased by " + player.getArmour().damageBlocked() + ".");
                    }
                    delay();
                }

                else if (RANDOM.nextInt(100) < healthPotionDropChance)
                {
                    player.addPotions(1);
                    System.out.println("\nThe " + villain.name() + " dropped a health potion.");
                    delay();
                }
            }
        }
    }

    // The battle prompt menu of this program.

    public static void startBattle()
    {
        System.out.println("\n1. Attack.");
        System.out.println("2. Use potion.");
        System.out.println("3. Run!");
        System.out.println("4. Visit Store.");
        System.out.println("5. Exit Game.");

        System.out.print("\nChoice? ");
    }

    // Prints the statistics of this game, includes the player's and enemy's state.

    public static void printStatistics(Player player, Enemy villain )
    {
        // Statistics
        System.out.println("\f# A " + villain.name() + " appeared #");

        System.out.println("\n# You have " + player.health() + " HP #");
        System.out.println("# Enemy has " + villain.health() + " HP #");
        System.out.println("# Potions left: " + player.getPotions() + " #");
        System.out.println("# Pouch has " + player.getPouch().getCoins() + " coins #");
        System.out.println("# Enemies killed: " + player.enemiesKilled() + " #");

        // Sword
        if (player.hasSword())
        {
            System.out.println("\n# Sword type: " + player.getSword().name() + " | hitpoints: " + player.getSword().hitpoints() + "  #");
        }

        // Armour
        if (player.hasArmour())
        {
            System.out.println("\n# Armour type: " + player.getArmour().name() + " | Armour hitpoints: " + player.getArmour().hitpoints() + "  #");
        }
    }


 public static void delay()
    {
        try
        {
            Thread.sleep(DELAY);
        }
        catch (InterruptedException exception)
        {
            System.out.println("\fThe game experienced an interrupted exception.");
            System.out.println("The game state was not saved.");
            System.out.println("Please restart the game.");

            System.exit(0);
        }
    }
}