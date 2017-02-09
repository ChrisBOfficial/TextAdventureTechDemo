class Item {

    String name;
    private int attackMod;
    private int armorClassMod;
    private double speedMod;
    private String code = "";
    boolean combatUse;
    boolean selfUse;

    Item (String n, int atkMod, int acMod, double sMod, boolean sf) {
        name = n;
        attackMod = atkMod;
        armorClassMod = acMod;
        speedMod = sMod;
        combatUse = true;
        selfUse = sf;
    }

    Item (String n, String c) {
        name = n;
        code = c;
        combatUse = false;
        selfUse = false;
    }

    Item() {
        this("Void", "8675309");
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

    void useItem() {
        Player.attack += attackMod;
        Player.armorClass += armorClassMod;
        Player.speed *= speedMod;
    }

    public String toString() {
        return name;
    }

}

class HealthPotion extends Item {
    private int restore;

    HealthPotion(String n, int hp) {
        restore = hp;
        name = n;
        combatUse = true;
        selfUse = true;
    }

    @Override
    void useItem() {
        Player.HP += restore;
        if(Player.HP > Player.maxHP)
            Player.HP = Player.maxHP;
    }
}
