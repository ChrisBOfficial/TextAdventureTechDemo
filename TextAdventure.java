//TODO: Items, Inventory management OOC, Add story
import java.io.IOException;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
/*4595556e1cde9eac9e1cc98843a6ef94227b0e6c29e8f042be61600765e59ed*/

public class TextAdventure {
    private static Scanner scan = new Scanner(System.in);
    private static int runWorked = 0;

    private static int nextInt(int x) {
        do {
            try {
                x = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a number please.");
            }
        } while (x == 0);
        return x;
    }

    static void timerDelay(int x) {
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

    private static void item(Monster Monster) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Which item would you like to use?");
        Inventory.printItems();
        int itemChoice = 0;
        do {
            try {
                itemChoice = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter a number please.\n");
                timerDelay(500);
            }
            scan.nextLine();
        } while (itemChoice == 0);
        Inventory.items.get(itemChoice - 1).useItem(Monster); //Uses selected item and modifies monster's stats accordingly
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

    private static void optionsBorder(String x) {
        for(int i = 0; i < x.length(); i++)
            System.out.print("-");
        System.out.println("\n" + x);
        for(int i = 0; i < x.length(); i++)
            System.out.print("-");
    }

    private static void combatOptions(String monsterName) {
        System.out.println(Artwork.getArtwork(monsterName));
        System.out.println("\n---------------------------------" +
                "\n| 1. ATTACK | 2. ITEM | 3. RUN | " +
                "\n---------------------------------\n");
    }

    private static void combatStart(Monster Monster) {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        int c = 0;

        while (Player.HP > 0) {
            timerDelay(250);
            if(Monster.HP <= 0 || runWorked == 1) {
                runWorked = 0;
                break;
            }
            do {
                try {
                    combatOptions(Monster.getName());
                    choice = scan.nextInt();
                    combatChoice(choice, Monster.speed, Monster);
                } catch (InputMismatchException ex) {
                    if(c % 3 == 0 && c != 0) {
                        System.out.println("No action is not an option.");
                        timerDelay(750);
                    }
                    System.out.println("Enter a number please.\n");
                    c++;
                    timerDelay(750);
                }
                scan.nextLine();
            } while (choice == 0);
        }
        if(runWorked == 0) {
            timerDelay(500);
            combatResult(Monster, Monster.xpVal);
        }
    }

    private static void combatChoice(int x, double y, Monster Monster) {
        switch (x) {
            case 1:
                attack(Monster);
                break;
            case 2:
                item(Monster);
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
            System.out.print("");
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

    public static void main (String[] str) throws IOException {
        Item churchKey = new Item ("Church Key", "79ed689e6714525a401b0acf19d2ac5");
        Inventory.addItems(churchKey);
        Door churchEntrance = new Door("79ed689e6714525a401b0acf19d2ac5");

        System.out.println("                                      /|\n" +
                "                                     |\\|\n" +
                "                                     |||\n" +
                "                                     |||\n" +
                "                                     |||\n" +
                "                                     |||\n" +
                "                                     |||\n" +
                "                                     |||\n" +
                "                                  ~-[{o}]-~\n" +
                "                                     |/|\n" +
                "              ___                    |/|\n" +
                "             ///~`     |\\\\_          `0'         =\\\\\\\\         . .\n" +
                "            ,  |='  ,))\\_| ~-_                    _)  \\      _/_/|\n" +
                "           / ,' ,;((((((    ~ \\                  `~~~\\-~-_ /~ (_/\\\n" +
                "         /' -~/~)))))))'\\_   _/'                      \\_  /'  D   |\n" +
                "        (       (((((( ~-/ ~-/                          ~-;  /    \\--_\n" +
                "         ~~--|   ))''    ')  `                            `~~\\_    \\   )\n" +
                "             :        (_  ~\\           ,                    /~~-     ./\n" +
                "              \\        \\_   )--__  /(_/)                   |    )    )|\n" +
                "    ___       |_     \\__/~-__    ~~   ,'      /,_;,   __--(   _/      |\n" +
                "  //~~\\`\\    /' ~~~----|     ~~~~~~~~'        \\-  ((~~    __-~        |\n" +
                "((()   `\\`\\_(_     _-~~-\\                      ``~~ ~~~~~~   \\_      /\n" +
                " )))     ~----'   /      \\                                   )       )\n" +
                "  (         ;`~--'        :                                _-    ,;;(\n" +
                "            |    `\\       |                             _-~    ,;;;;)\n" +
                "            |    /'`\\     ;                          _-~          _/\n" +
                "           /~   /    |    )                         /;;;''  ,;;:-~\n" +
                "          |    /     / | /                         |;;'   ,''\n" +
                "          /   /     |  \\\\|                         |   ,;(\n" +
                "        _/  /'       \\  \\_)                   .---__\\_    \\,--._______\n" +
                "       ( )|'         (~-_|                   (;;'  ;;;~~~/' `;;|  `;;;\\\n" +
                "        ) `\\_         |-_;;--__               ~~~----__/'    /'_______/\n" +
                "        `----'       (   `~--_ ~~~;;------------~~~~~ ;;;'_/'\n" +
                "                     `~~~~~~~~'~~~-----....___;;;____---~~\n");

        System.out.println("Welcome to the TextAdventure TechDemo. What is your name?");
        String playerName = scan.nextLine();

        System.out.println("Welcome " + playerName + ", which archetype would you like?\n");
        int uh = 0;
        do {
            try {
                System.out.println("| 1. WARRIOR | 2. THIEF | 3. KNIGHT |\n");
                int chosenClass = scan.nextInt();
                uh = chosenClass;
                Player.setArch(chosenClass);
                Player.classChoice(chosenClass);
            } catch (InputMismatchException e) {
                System.out.println("Enter a number please.\n");
                timerDelay(750);
            }
            scan.nextLine();
        } while (uh == 0);

        System.out.println("Welcome " + Player.classType + ". Now we need to acquaint you with combat.\n\n" +
                           "| 1. Uh oh. | 2. I'm ready! | 3. No thank you... |\n");
        Monster tutorialCreature = new Monster("Skeleton",1,1,1.1, 10);
        int meh = 0;
        int c = 0;

        do {
            try {
                meh = scan.nextInt();
            } catch (InputMismatchException e) {
                if(c % 3 == 0 && c != 0)
                    System.out.println("Do you want to play or not?!");
                System.out.println("Enter a number please.");
                c++;
            }
            scan.nextLine();
        } while (meh == 0);
        System.out.println("Great! You see a level " + tutorialCreature.level + " " + tutorialCreature.affixName +
                           tutorialCreature.getName() + "! What do you wish to do?\n");

        combatStart(tutorialCreature);

        timerDelay(1000);

        Monster imp = new Monster("Imp", 1, 1,0.8, 15);

        System.out.println("You are ambushed by a level " + imp.level + " " + imp.affixName + imp.getName() +
                           "! What do you wish to do?\n");

        combatStart(imp);
        timerDelay(1000);
        System.out.println("You find a net from the corpse of the imp. Net added to your inventory.");

        System.out.println("You move past the imp and find a door in front of you. Use your key?");

        String yesOrNo = "| 1. YES | 2. NO |";
        optionsBorder(yesOrNo);

        int worldChoice = 0;
        worldChoice = nextInt(worldChoice);

        if(worldChoice == 1)
            Church();
        else
            System.out.println("Game over.");

    }

    private static void Church() {
        System.out.println("Opening...");
        timerDelay(1500);
        System.out.println("Welcome to the church!");
    }

}
