import java.io.IOException;
import java.lang.*;
import java.util.Scanner;

public class Player {
    static int archetype;
    static double levelRaw = 1.0;
    static String classType;
    static int oldLevel;

    static double HP = 10;
    static double maxHP = 10;
    static double damage = 1;
    static double speed = 1;
    private static int attack = 0;
    static int armorClass = 10;
    static int makeAttack;

    static void setLevel(double x) {
        levelRaw += x;
    }

    static void setMaxHP(double x) {
        maxHP += x;
    }

    static void setHealth(double x) {
        HP += x;
    }

    static void setDamage(double x) {
        damage *= x;
    }

    static void setSpeed(double x) {
        speed *= x;
    }

    static void setAttack(int x) {
        attack += x;
    }

    static void setArmorClass(int x) {
        armorClass += x;
    }

    static void setMakeAttack () {
        makeAttack = (int)(Math.random() * 20 + 1) + attack;
    }

    static void warriorMod () {
        setHealth(2.4);
        setMaxHP(2.4);
        setDamage(2.7);
        setSpeed(1.2);
        setAttack(3);
        classType = "Warrior";
        setArmorClass(1);
    }
    static void thiefMod () {
        setHealth(2.0);
        setMaxHP(2.0);
        setDamage(2.4);
        setSpeed(1.4);
        setAttack(4);
        classType = "Thief";
    }
    static void knightMod () {
        setHealth(2.6);
        setMaxHP(2.6);
        setDamage(2.2);
        setSpeed(1.0);
        setAttack(4);
        classType = "Knight";
        setArmorClass(3);
    }

    static void classChoice (int x) {
        if (x == 1)
            warriorMod();
        if (x == 2)
            thiefMod();
        if (x == 3)
            knightMod();
    }

    static void setArch (int x) {
        archetype = x;
    }

    public static void main (String str[]) {
    }
}
