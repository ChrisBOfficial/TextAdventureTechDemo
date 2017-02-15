import java.io.IOException;
import java.lang.*;
import java.util.InputMismatchException;
import java.util.Scanner;
/*4595556e1cde9eac9e1cc98843a6ef94227b0e6c29e8f042be61600765e59ed*/

public class TextAdventure {
    private static int directionFlag;
    private static Location Church = new Location("Church", "Welcome to the Church!");
    private static Room Church_1 = new Room("Northwest", 1);
    private static Room Church_2 = new Room("Northeast", 2);
    private static Room Church_3 = new Room("Southwest", 3);
    private static Room Church_4 = new Room("Southeast", 4);
    private static Scanner scan = new Scanner(System.in);
    private static int runWorked = 0;
    private static String yesOrNo = "|1. YES | 2. NO |";
    private static String worldOptions = "|1. EXPLORE | 2. VIEW INVENTORY | 3. LORE |";

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
        //Uses selected item and modifies monster's stats accordingly
        if(itemChoice - 1 < Inventory.playerItems.size()) {
            if(!Inventory.playerItems.get(itemChoice - 1).selfUse) {
                Inventory.playerItems.get(itemChoice - 1).useItem(Monster);
            } else
                Inventory.playerItems.get(itemChoice - 1).useItem();
            if(Inventory.playerItems.get(itemChoice - 1).getCombatUse()) {
                System.out.println("You use " + Inventory.playerItems.get(itemChoice - 1));
                Inventory.playerItems.remove(itemChoice - 1);
            } else
                System.out.println("You can't use that item in combat.");
            timerDelay(500);
        } else
            System.out.println("There is no item there.");

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
        System.out.println("\n");
    }

    private static void combatOptions(String monsterName) {
        System.out.println(Artwork.getArtwork(monsterName));
        timerDelay(250);
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
            Player.setLevel(exp);
            System.out.println("\nYou win! Current level - " + (int)Player.levelRaw);
            //Player.HP = Player.maxHP;
        } else if (Monster.HP > 0 && Player.HP <= 0) {
            Player.setLevel(-exp);
            System.out.println("\nYou lost... Current level - " + (int)Player.levelRaw);
            Player.humanity--;
            System.out.println("\nYou have lost 1 Humanity");
            if(Player.humanity <= 0) {
                System.exit(0);
            }
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
            Player.humanity++;
            timerDelay(500);
            System.out.println("New level - " + (int)Player.levelRaw);
        }
    }

    private static void worldChoice(int x, Room r) {
        switch (x) {
            case 1:
                System.out.println("What direction?");
                System.out.println("NORTH EAST SOUTH WEST");

                String exploreChoice = scan.nextLine().toLowerCase();
                explore(exploreChoice, r);

                break;
            case 2:
                System.out.println("Your items: ");
                Inventory.printItems();

                break;
            case 3:
                System.out.println("What category?");

                optionsBorder(Lore.loreCategories);
                int loreChoice = 0;
                loreChoice = nextInt(loreChoice);

                Lore.choice(loreChoice);

                break;
        }
    }

    private static void explore(String exploreChoice, Room r) {
        if (r.roomLoc == 1) {
            directionFlag = 0;
            while (directionFlag == 0) {
                if ("north".equals(exploreChoice) || "west".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    directionFlag = 1;
                    worldChoice(1, r);
                } else if ("east".equals(exploreChoice)) {
                    TextAdventure.directionFlag = 1;
                    Player.currentRoom = 2;
                } else if ("south".equals(exploreChoice)) {
                    TextAdventure.directionFlag = 1;
                    Player.currentRoom = 3;
                }
            }
        } else if (r.roomLoc == 2) {
            directionFlag = 0;
            while (directionFlag == 0) {
                if ("north".equals(exploreChoice) || "east".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    directionFlag = 1;
                    worldChoice(1, r);
                } else if ("west".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 1;
                } else if ("south".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 4;
                }
            }
        } else if(r.roomLoc == 3) {
            directionFlag = 0;
            while (TextAdventure.directionFlag == 0) {
                if ("south".equals(exploreChoice) || "west".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    directionFlag = 1;
                    worldChoice(1, r);
                } else if ("north".equals(exploreChoice)) {
                    Player.currentRoom = 1;
                } else if ("east".equals(exploreChoice)) {
                    Player.currentRoom = 4;
                }
            }
        } else if(r.roomLoc == 4) {
            directionFlag = 0;
            while (directionFlag == 0) {
                if ("south".equals(exploreChoice) || "east".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    directionFlag = 1;
                    worldChoice(1, r);
                } else if ("north".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 2;
                } else if ("west".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 3;
                }
            }
        }
    }

    static int affixMake() {
        return (int)(Math.random() * 100);
    }

    public static void main (String[] str) throws IOException {
        Player.currentRoom = 1;
        Item churchKey = new Item ("Church Key", "79ed689e6714525a401b0acf19d2ac5");
        Inventory.addItems(churchKey);
        HealthPotion minorHealth = new HealthPotion("Minor Health Potion", 5);
        Door churchEntrance = new Door("79ed689e6714525a401b0acf19d2ac5");

        System.out.println("`\"-._                    \n" +
                "                      `. \"-._                \n" +
                "                        T.   \"-.             \n" +
                "                         $$p.   \"-.          \n" +
                "                         $$$$b.    `,        \n" +
                "                      .g$$$$$$$b    ;        \n" +
                "                    .d$$$$$$$$$$;   ;        \n" +
                "                 __d$$$$$$P\"\"^T$$   :        \n" +
                "               .d$$$$P^^\"\"___       :        \n" +
                "              d$P'__..gg$$$$$$$$$$; ;        \n" +
                "             d$$ :$$$$$$$$$$$$$$$$;  ;       \n" +
                "            :$$; $$$$$$$$$$$$$$$$P  :$       \n" +
                "            $$$  $$$$$$$$$$$$$$$$b  $$       \n" +
                "           :$$$ :$$$$$$$$$$$$$$$$$; $$;      \n" +
                "           $$$; $$$$$$$$$$$$$$$$$$; $$;      \n" +
                "          :$$$  $$$$$$$$$^$$$$$$$$$ :$$      \n" +
                "          $$$; :$$$p__gP' `Tp__g$$$ :$$      \n" +
                "         :$$$  $$P`T$P' .$. `T$P'T$; $$;     \n" +
                "         $$$; :$$;     :P^T;     :$; $$;     \n" +
                "        :$$$  $$$$-.           .-$$$ :$$     \n" +
                "        $$$$ :$$$$; \\   T$P   / :$$$  $$     \n" +
                "       :$$$; $$$$$$  ; b:$;d :  $$$$; $$;    \n" +
                "       $$$$; $$$$$$; : T T T ; :$$$$$ :$$    \n" +
                "    .g$$$$$  :$$$$$$  ;' | ':  $$$$$$  T$b   \n" +
                " .g$$$$$$$$   $$$$$$b :     ; d$$$$$;   `Tb  \n" +
                ":$$$$$$$$$;   :$$$$$$$;     :$$$$$$P       \\ \n" +
                ":$$$$$$$$$;    T$$$$$$$p._.g$$$$$$P         ;\n" +
                "$$$P^^T$$$$p.   `T$$$$$$$$$$$$$$P'     _/`. :\n" +
                "       `T$$$$$b.  `T$$$$$$$$$$P'    .g$P   \\;\n" +
                "         `T$$$$$b.  \"^T$$$$P^\"     d$P'      \n" +
                "           `T$$$$$b.             .dP'        \n" +
                "             \"^T$$$$b.        .g$P'          \n" +
                "                \"^T$$$b    .g$P^\"            \n" +
                "                   \"^T$b.g$P^\"               \n" +
                "                      \"^$^\" ");

        System.out.println("Welcome to the TextAdventure TechDemo. What is your name?");
        String playerName = scan.nextLine();

        System.out.println("Welcome " + playerName + ", which archetype would you like?\n");
        int cont = 0;
        do {
            try {
                System.out.println("| 1. WARRIOR | 2. THIEF | 3. KNIGHT |\n");
                int chosenClass = scan.nextInt();
                cont = chosenClass;
                Player.setArch(chosenClass);
                Player.classChoice(chosenClass);
            } catch (InputMismatchException e) {
                System.out.println("Enter a number please.\n");
                timerDelay(750);
            }
            scan.nextLine();
        } while (cont == 0);

        Inventory.addItems(minorHealth);

        System.out.println("Welcome " + Player.classType + ". Now we need to acquaint you with combat.\n\n" +
                           "| 1. Uh oh. | 2. I'm ready! | 3. No thank you... |\n");
        Monster tutorialCreature = new Monster("Skeleton",1,1,1.1, 10);
        cont = 0;
        int c = 0;

        do {
            try {
                cont = scan.nextInt();
            } catch (InputMismatchException e) {
                if(c % 3 == 0 && c != 0)
                    System.out.println("Do you want to play or not?!");
                System.out.println("Enter a number please.");
                c++;
            }
            scan.nextLine();
        } while (cont == 0);
        System.out.println("Great! You see a level " + tutorialCreature.level + " " + tutorialCreature.affixName +
                           tutorialCreature.getName() + "! What do you wish to do?\n");

        combatStart(tutorialCreature);

        timerDelay(1000);

        Monster imp = new Monster("Imp", 1, 1,0.8, 15);

        System.out.println("You are ambushed by a level " + imp.level + " " + imp.affixName + imp.getName() +
                           "! What do you wish to do?\n");

        combatStart(imp);

        timerDelay(750);
        System.out.println("\nYou move past the imp and find a door in front of you. Attempt to open...");
        timerDelay(750);
        System.out.println("\nEnd of tutorial!");

        for(Item x: Inventory.playerItems) {
            if(x.getCode().equals(churchEntrance.getLock())) {
                Inventory.playerItems.remove(x);
                Player.currentRoom = 1;
                Church(Player.currentRoom);
                break;
            }
            else
                System.out.println("Door is locked.");
        }

    }

    private static void endChurch(Room Room) {
        System.out.println("\nWhat do you wish to do?\n");
        Room.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        worldChoice(choice, Room);

        Church(Player.currentRoom);
    }

    private static void Church(int x) {
        switch (x) {
            case 1:
                Church_1();
                break;
            case 2:
                Church_2();
                break;
            case 3:
                Church_3();
                break;
            case 4:
                Church_4();
                break;
        }
    }

    private static void Church_1() {
        System.out.println("A large hallway extends before you. Stone pillars line the walls, a staircase to the east " +
                           "and a chest to the south.");

        if(Church_1.visits == 0) {
            System.out.println(Church.getIntroduction());
            Item net = new Item("Net", 3, 0, 0,false);
            System.out.println("\nYou find a net from the corpse of the imp. Net added to your inventory.");
            Inventory.addItems(net);
        }

        endChurch(Church_1);
    }

    private static void Church_2() {
        System.out.println("You see a door in front of you, and the rest of the Church around you.");

        endChurch(Church_2);
    }
    private static void Church_3() {
        System.out.println();

        endChurch(Church_3);
    }
    private static void Church_4() {

    }
}
