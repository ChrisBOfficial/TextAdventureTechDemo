class Boss {

    private String name;
    double HP;
    double damage;
    int armorClass;
    double xpVal;

    String getName() {
        return name;
    }

    void setHealth(double x) {
        HP -= x;
    }

    Boss (String n, int l, int d, int AC) {
        name = n;
        HP = l * 10;
        damage = d;
        armorClass = AC;
        xpVal = HP / 5;
    }

}
