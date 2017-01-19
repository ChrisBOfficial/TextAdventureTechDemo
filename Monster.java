import java.io.IOException;
import java.lang.*;
import java.util.Scanner;

public class Monster {

    private String name;
    int level;
    private String affixName;
    double HP;
    double damage;
    int attack;
    int armorClass;
    int makeAttack;
    double speed;
    double xpVal;
    /*
    0-80 Normal
    81-90 Bolstered, more HP
    91-95 Mighty, more HP and damage
    96-100 Tyrannical, much more HP and damage
     */

    String getName() {
        return name;
    }

    void setMakeAttack () {
        makeAttack = (int)(Math.random() * 20 + 1) + attack;
    }

    void setHealth(double x) {
        HP -= x;
    }

    Monster (String n, int l, int d, double s, int AC) {
        name = n;
        level = l;
        HP = (level * 7);
        damage = d;
        speed = level * s;
        attack = level;
        armorClass = AC;
        xpVal = (HP / 100) * 2;
        int affix = TextAdventure.affixMake();

        if(affix > 80 && affix < 91) {
            affixName = "Bolstered ";
            name = affixName + n;
            HP *= 1.4;
            armorClass += 3;
            xpVal *= 1.5;
        } else if(affix > 90 && affix < 96) {
            affixName = "Mighty ";
            name = affixName + n;
            HP *= 1.5;
            damage *= 1.2;
            armorClass += 5;
            attack += 2;
            xpVal *= 1.8;
        } else if(affix > 95 && affix < 101) {
            affixName = "Tyrannical ";
            name = affixName + n;
            HP *= 1.5;
            damage *= 1.4;
            attack += 2;
            xpVal *= 2.5;
        }
    }

    public static void main (String str[]) {
    }
}
