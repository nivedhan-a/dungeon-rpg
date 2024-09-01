public class Sword
{
    /** The hitpoints of a wood sword. */
    public static final int WOOD_HITPOINTS = 10;

    /** The damage increase of a wood sword. */
    public static final int WOOD_DAMAGE_INCREASE = 5;

    /** The hitpoints of a metal sword. */
    public static final int METAL_HITPOINTS = 15;

    /** The damage increase of a metal sword. */
    public static final int METAL_DAMAGE_INCREASE = 10;

    /** The hitpoints of a gold sword. */
    public static final int GOLD_HITPOINTS = 20;

    /** The damage increase of a gold sword. */
    public static final int GOLD_DAMAGE_INCREASE = 15;

    // instance fields
    private int damageIncrease;
    private int hitpoints;
    private String name;

    // Constructs a sword with the specified characteristics.

    public Sword(String type)
    {
        if (type == null) return;

        name = type + " sword";

        switch (type)
        {
            case "wood":
                hitpoints = WOOD_HITPOINTS;
                damageIncrease = WOOD_DAMAGE_INCREASE;
                break;

            case "metal":
                hitpoints = METAL_HITPOINTS;
                damageIncrease = METAL_DAMAGE_INCREASE;
                break;

            case "gold":
                hitpoints = GOLD_HITPOINTS;
                damageIncrease = GOLD_DAMAGE_INCREASE;
                break;

            default:
                name = "wood sword";
                hitpoints = WOOD_HITPOINTS;
                damageIncrease = WOOD_DAMAGE_INCREASE;
                break;
        }
    }

    // Returns the name of this sword.

    public String name()
    {
        return name;
    }

    // Returns the hitpoints of this sword.

    public int hitpoints()
    {
        return hitpoints;
    }

    // Returns the damageIncrease of this sword.

    public int damageIncrease()
    {
        return damageIncrease;
    }

    // Changes the name of this sword to the specified name.

    public void setName(String name)
    {
        this.name = name;
    }

    // Uses this sword when attacking an enemy, reducing hitpoints.
    public void useSword()
    {
        hitpoints--;
    }

    // Adds hitpoints to sword, repairing the sword.
    public void repairSword(int hitpointsToRepair)
    {
        hitpoints = hitpoints + hitpointsToRepair;
    }
}
