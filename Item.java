
public class Item {

    private String name;
    private int attackMod;
    private int armorClassMod;
    private double speedMod;

    Item (String n, int atkMod, int acMod, double sMod) {
        name = n;
        attackMod = atkMod;
        armorClassMod = acMod;
        speedMod = sMod;
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
