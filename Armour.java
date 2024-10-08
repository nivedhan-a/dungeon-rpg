
public class Armour
{
    // Leather Armour
    public static final int LEATHER_HITPOINTS = 5;
    public static final int LEATHER_DAMGAGE_BLOCKED = 2;

    // Iron armour
    public static final int IRON_HITPOINTS = 10;
    public static final int IRON_DAMAGE_BLOCKED = 5;

    // Gold armour
    public static final int GOLD_HITPOINTS = 20;
    public static final int GOLD_DAMAGE_BLOCKED = 10;

    /* instance fields */
    private String name;
    private int hitpoints;
    private int damageBlocked;

    public Armour(String type) {
        if (type == null) return;

        name = type + " armour";

        switch (type) {
            case "leather":
                hitpoints = LEATHER_HITPOINTS;
                damageBlocked = LEATHER_DAMGAGE_BLOCKED;
                break;

            case "iron":
                hitpoints = IRON_HITPOINTS;
                damageBlocked = IRON_DAMAGE_BLOCKED;
                break;

            case "gold":
                hitpoints = GOLD_HITPOINTS;
                damageBlocked = GOLD_DAMAGE_BLOCKED;
                break;
            
            default:
                name = "clothes";
                hitpoints = 0;
                damageBlocked = 0;
                break;
        }
    }


    public int damageBlocked()
    {
        return damageBlocked;
    } // end of method damageBlocked()

    public int hitpoints()
    {
        return hitpoints;
    }

    public String name()
    {
        return name;
    } // end of method name()

    public void setName(String name)
    {
        this.name = name;
    } // end of method setName(String name)

    public void useArmour()
    {
        hitpoints--;
    } // end of method useArmour()

    public void repairArmour(int hitpointsToRepair) {
        hitpoints = hitpoints + hitpointsToRepair;
    }
}