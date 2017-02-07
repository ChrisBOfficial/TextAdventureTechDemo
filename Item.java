
public class Item {

    String name;
    private int attackMod;
    private int armorClassMod;
    private double speedMod;
    private String code = "";
    private boolean combatUse;

    Item (String n, int atkMod, int acMod, double sMod) {
        name = n;
        attackMod = atkMod;
        armorClassMod = acMod;
        speedMod = sMod;
        combatUse = true;
    }

    Item (String n, String c) {
        name = n;
        code = c;
        combatUse = false;
    }

    boolean getCombatUse() {
        return combatUse;
    }

    String getCode() {
        return code;
    }

    void useItem(Monster Monster) {
        Monster.attack -= attackMod;
        Monster.armorClass -= armorClassMod;
        Monster.speed *= speedMod;
    }

    public String toString() {
        return name;
    }

}
