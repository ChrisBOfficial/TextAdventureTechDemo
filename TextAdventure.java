import java.io.IOException;
import java.lang.*;
import java.util.Scanner;
import java.util.ArrayList;

public class TextAdventure {
    private static int runWorked = 0;

    private static void timerDelay(int x) {
        try {
            Thread.sleep(x);
        } catch (InterruptedException ex) {
            System.out.print("Interrupted");
        }
    }

    private static void attack(Monster Monster) {
        System.out.println("You attack...\n");
        Player.setMakeAttack();
        Monster.setMakeAttack();
        if(Monster.HP <= 0) {
            return;
        }
        if(Player.HP <= 0) {
            return;
        }
        timerDelay(500);
        if(Player.makeAttack >= Monster.armorClass) {
            Monster.setHealth(Player.damage);
            System.out.println("Your attack hits! " + Player.damage + " Damage.");
            healthStats(Monster);
            if(Monster.HP <= 0)
                return;
        } else {
            System.out.println("Your attack is blocked.");
        }
        timerDelay(750);
        System.out.println("\n" + Monster.getName() + " attacks...\n");
        timerDelay(500);
        if(Monster.makeAttack > Player.armorClass) {
            System.out.println("You're hit! " + Monster.damage + " Damage.");
            Player.setHealth(-Monster.damage);
            healthStats(Monster);
        } else {
            System.out.println("You block the " + Monster.getName() + "'s attack.");
        }
    }

    private static void healthStats(Monster Monster) {
        System.out.println("Enemy Health: " + Monster.HP +
                "\nYour Health: " + Player.HP);
    }

    private static void item() {
    }

    private static void run(double s, double monS) {
        runWorked = 0;
        System.out.println("You attempt to run...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.print("Interrupted");
        }
        if(s > monS) {
            System.out.println("\nYou escaped!");
            runWorked = 1;
        }
        else
            System.out.println("\nYou fail to escape.\n");
    }

    private static void combatOptions() {
        System.out.println("---------------------------------" +
                "\n| 1. ATTACK | 2. ITEM(fix me) | 3. RUN | " +
                "\n---------------------------------\n");
    }

    private static void combatStart(Monster Monster) {
        Scanner scan = new Scanner(System.in);

        while (Player.HP > 0) {
            timerDelay(250);
            if(Monster.HP <= 0 || runWorked == 1)
                break;
            combatOptions();
            int choice = scan.nextInt();
            combatChoice(choice, Monster.speed, Monster);
        }
        if(runWorked == 0) {
            timerDelay(1000);
            combatResult(Monster, Monster.xpVal);
        }
    }

    private static void combatChoice(int x, double y, Monster Monster) {
        switch (x) {
            case 1:
                attack(Monster);
                break;
            case 2:
                item();
                break;
            case 3:
                run(Player.speed, y);
                break;
        }
    }

    private static void combatResult(Monster Monster, double exp) {
        if(Player.HP > 0 && Monster.HP <= 0) {
            System.out.println("\nYou win! Current level - " + (int)Player.levelRaw);
            Player.HP = Player.maxHP;
        } else if (Monster.HP > 0 && Player.HP <= 0) {
            Player.setLevel(-exp);
            System.out.println("\nYou lost... Current level - " + (int)Player.levelRaw);
        } else
            System.out.println("Error.");
        levelUpCheck(Monster.xpVal);
    }

    private static void levelUpCheck(double exp) {
        Player.oldLevel = (int)Player.levelRaw;
        Player.setLevel(exp);
        if((int)Player.levelRaw - Player.oldLevel >= 3) {
            Player.oldLevel = (int)Player.levelRaw;
            System.out.println("Level up! Your stats have increased.");
            Player.classChoice(Player.archetype);
            Player.HP = Player.maxHP;
            timerDelay(500);
            System.out.println("New level - " + (int)Player.levelRaw);
        }
    }

    static int affixMake() {
        return (int)(Math.random() * 100);
    }

    public static void main (String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the TextAdventure TechDemo. What is your name?");
        String playerName = scan.nextLine();

        System.out.println("Welcome " + playerName + ", which archetype would you like?\n");
        System.out.println("| 1. WARRIOR | 2. THIEF | 3. KNIGHT |\n");
        int chosenClass = scan.nextInt();
        Player.setArch(chosenClass);
        Player.classChoice(chosenClass);

        System.out.println("Welcome " + Player.classType + ". Now we need to acquaint you with combat.\n\n" +
                           "| 1. Uh oh. | 2. I'm ready! | 3. No thank you... |\n");
        Monster tutorialCreature = new Monster("Skeleton",1,1,1.1, 10);

        scan.nextInt();
        System.out.println("Great! You see a level " + tutorialCreature.level + " " +
                           tutorialCreature.getName() + "! What do you wish to do?\n");

        combatStart(tutorialCreature);

        timerDelay(1000);

        Monster zombie = new Monster("Zombie", 1, 1,0.8, 15);

        System.out.println("You are ambushed by a level " + zombie.level + " " + zombie.getName() +
                           "! What do you wish to do?\n");

        combatStart(zombie);
        timerDelay(1000);

        System.out.println("Thanks for trying the tech, I will be adding more to the game over time.");
    }
}
//Testing GitKraken push from IntelliJ