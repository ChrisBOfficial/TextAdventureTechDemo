import java.io.*;
import java.lang.*;
import java.util.InputMismatchException;
import java.util.Scanner;
/*4595556e1cde9eac9e1cc98843a6ef94227b0e6c29e8f042be61600765e59ed*/

public class TextAdventure implements Serializable {

    private static int directionFlag;

    private static Location Church = new Location("Church", "Welcome to the Church!");
    private static Room Church_1 = new Room("Northwest", 1, "Church");
    private static Room Church_2 = new Room("Northeast", 2, "Church");
    private static Room Church_3 = new Room("Southwest", 3, "Church");
    private static Room Church_4 = new Room("Southeast", 4, "Church");

    private static Location Garden = new Location("Garden", "Welcome to the Garden!");
    private static Room Garden_1 = new Room("Northwest", 1, "Garden");
    private static Room Garden_2 = new Room("Northeast", 2, "Garden");
    private static Room Garden_3 = new Room("Southwest", 3, "Garden");
    private static Room Garden_4 = new Room("Southeast", 4, "Garden");

    private static Location Forest = new Location("Forest", "Welcome to the Forest!");
    private static Room Forest_1 = new Room("Northwest", 1, "Forest");
    private static Room Forest_2 = new Room("Northeast", 2, "Forest");
    private static Room Forest_3 = new Room("Southwest", 3, "Forest");
    private static Room Forest_4 = new Room("Southeast", 4, "Forest");

    private static Location castleG = new Location("Castle Grounds", "Welcome to the castle grounds!");
    private static Room castleG_1 = new Room("Northwest", 1, "Castle Grounds");
    private static Room castleG_2 = new Room("Northeast", 2, "Castle Grounds");
    private static Room castleG_3 = new Room("Southwest", 3, "Castle Grounds");
    private static Room castleG_4 = new Room("Southeast", 4, "Castle Grounds");

    private static Location throneRoom = new Location("Throne Room", "Welcome to the throne room!");

    private static Scanner scan = new Scanner(System.in);

    private static int runWorked = 0;

    private static String worldOptions = "| 1. EXPLORE | 2. VIEW INVENTORY | 3. LORE |";

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

    private static void healthStats(Boss Boss) {
        System.out.println("Enemy Health: " + Boss.HP +
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
        if(itemChoice - 1 < Inventory.containedItems.size()) {
            if(!Inventory.containedItems.get(itemChoice - 1).selfUse) {
                Inventory.containedItems.get(itemChoice - 1).useItem(Monster);
            } else
                Inventory.containedItems.get(itemChoice - 1).useItem();
            if(Inventory.containedItems.get(itemChoice - 1).getCombatUse()) {
                System.out.println("You use " + Inventory.containedItems.get(itemChoice - 1));
                Inventory.containedItems.remove(itemChoice - 1);
            } else
                System.out.println("You can't use that item in combat.");
            timerDelay(500);
        } else
            System.out.println("There is no item there.");

    }

    private static void item(Boss Boss) {
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
        if(itemChoice - 1 < Inventory.containedItems.size()) {
            if(!Inventory.containedItems.get(itemChoice - 1).selfUse) {
                Inventory.containedItems.get(itemChoice - 1).useItem(Boss);
            } else
                Inventory.containedItems.get(itemChoice - 1).useItem();
            if(Inventory.containedItems.get(itemChoice - 1).getCombatUse()) {
                System.out.println("You use " + Inventory.containedItems.get(itemChoice - 1));
                Inventory.containedItems.remove(itemChoice - 1);
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
                "\n| 1. ATTACK | 2. ITEM | 3. RUN |" +
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
            System.out.println("\nYou lost... Current level - " + (int)Player.levelRaw);
            Player.humanity--;
            System.out.println("\nYou have lost 1 Humanity");
            Player.setHealth(Player.maxHP);
            if(Player.humanity <= 0) {
                System.exit(0);
            }
        }
        levelUpCheck(Monster.xpVal);
    }

    private static void combatResult(Boss Boss, double exp) {
        if(Player.HP > 0 && Boss.HP <= 0) {
            Player.setLevel(exp);
            System.out.println("\nYou win! Current level - " + (int)Player.levelRaw);
            //Player.HP = Player.maxHP;
        } else if (Boss.HP > 0 && Player.HP <= 0) {
            System.out.println("\nYou lost... Current level - " + (int)Player.levelRaw);
            Player.humanity -= 5;
            System.out.println("\nYou have lost 5 Humanity");
            Player.setHealth(Player.maxHP);
            if(Player.humanity <= 0) {
                System.exit(0);
            }
        }
        levelUpCheck(Boss.xpVal);
    }

    private static void levelUpCheck(double exp) {
        Player.oldLevel = (int)Player.levelRaw;
        Player.setLevel(exp);
        if((int)Player.levelRaw - Player.oldLevel >= 5) {
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
                optionsBorder("| NORTH | EAST | SOUTH | WEST |");

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
                    directionFlag = 1;
                    Player.currentRoom = 2;
                } else if ("south".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 3;
                } else
                    directionFlag = 1;
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
                } else
                    directionFlag = 1;
            }
        } else if(r.roomLoc == 3) {
            directionFlag = 0;
            while (directionFlag == 0) {
                if ("south".equals(exploreChoice) || "west".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    directionFlag = 1;
                    worldChoice(1, r);
                } else if ("north".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 1;
                } else if ("east".equals(exploreChoice)) {
                    directionFlag = 1;
                    Player.currentRoom = 4;
                } else
                    directionFlag = 1;
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
                } else
                    directionFlag = 1;
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

        System.out.println("Current Hash: " + MD5.getMD5());
        System.out.println("Welcome to the TextAdventure TechDemo. What is your name?");
        String playerName = scan.nextLine();

        System.out.println("Welcome " + playerName + ", which archetype would you like?\n");
        int cont = 0;
        do {
            try {
                optionsBorder("| 1. WARRIOR | 2. THIEF | 3. KNIGHT |");
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

        for(Item x: Inventory.containedItems) {
            if(x.getCode().equals(churchEntrance.getLock())) {
                Inventory.containedItems.remove(x);
                Player.currentRoom = 1;
                Church(Player.currentRoom);
                break;
            }
            else
                System.out.println("Door is locked.");
        }

    }


    private static void endRoom(Room Room) {
        System.out.println("\nWhat do you wish to do?\n");
        Room.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        worldChoice(choice, Room);
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
        optionsBorder(Church_1.getIntroduction());
        timerDelay(1000);

        if(Church_1.visits == 0) {
            System.out.println(Church.getIntroduction());
            Item net = new Item("Net", 3, 0, 0,false);
            System.out.println("\nYou find a net from the corpse of the imp. Net added to your inventory.");
            Inventory.addItems(net);
        }
        System.out.println("A large hallway extends before you. Stone pillars line the walls, a staircase to the east " +
                "and a chest to the south.");

        endRoom(Church_1);
        Church(Player.currentRoom);
    }

    private static void Church_2() {
        optionsBorder(Church_2.getIntroduction());
        timerDelay(1000);

        System.out.println("You see a staircase to the east, and a statue to the south.");
        System.out.println("\nWhat do you wish to do?\n");
        Church_2.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        switch (choice) {
            case 1:
                System.out.println("What direction?");
                optionsBorder("| NORTH | EAST | SOUTH | WEST |");

                String exploreChoice = scan.nextLine().toLowerCase();
                directionFlag = 0;
                while (directionFlag == 0) {
                    if ("north".equals(exploreChoice)) {
                        System.out.println("Can't go there.");
                        directionFlag = 1;
                    } else if ("east".equals(exploreChoice)) {
                        directionFlag = 1;
                        Garden_1();
                    } else if ("south".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 4;
                    } else if ("west".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 1;
                    } else
                        directionFlag = 1;
                }

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

        Church(Player.currentRoom);
    }

    private static void Church_3() {
        optionsBorder(Church_3.getIntroduction());
        timerDelay(1000);

        Monster churchSkel = new Monster("Skeleton", 1, 1, 500, 10);
        Monster churchSkel_2 = new Monster("Skeleton", 1, 1, 1.1, 10);
        if(Church_3.visits == 0) {
            Item crucifix = new Item("Crucifix", 0, 3, 0, false);
            Inventory.containedItems.add(crucifix);

            System.out.println("You see a chest and open it. Inside there was...");
            timerDelay(750);
            System.out.println("A Crucifix!");
            timerDelay(500);

            System.out.println("You're ambushed by two skeletons!");
            combatStart(churchSkel);
            System.out.println("The second skeleton attacks!");
            timerDelay(500);
            combatStart(churchSkel_2);
        }

        endRoom(Church_3);
        Church(Player.currentRoom);
    }

    private static void Church_4() {
        optionsBorder(Church_4.getIntroduction());
        timerDelay(1000);

        Monster churchStone = new Monster("Stone Guardian", 1, 2, 1.3, 12);
        if(Church_4.visits == 0) {
            timerDelay(700);
            System.out.println("A Stone Guardian breaks out of the wall and attacks you.!");

            combatStart(churchStone);
        }
        if(Church_3.visits == 0)
            System.out.println("You see a chest to the west, and the rest of the church around you.");
        else
            System.out.println("You see some walls around you, and a staircase to the north.");

        endRoom(Church_4);
        Church(Player.currentRoom);
    }


    private static void Garden(int x) {
        switch (x) {
            case 1:
                Garden_1();
                break;
            case 2:
                Garden_2();
                break;
            case 3:
                Garden_3();
                break;
            case 4:
                Garden_4();
                break;
        }
    }

    private static void Garden_1() {
        optionsBorder(Garden_1.getIntroduction());
        timerDelay(1000);

        if(Garden_1.visits == 0) {
            System.out.println(Garden.getIntroduction());
        }
        System.out.println("You see a walkway that extends east and south.");

        endRoom(Garden_1);
        Garden(Player.currentRoom);
    }

    private static void Garden_2() {
        optionsBorder(Garden_2.getIntroduction());
        timerDelay(1000);

        System.out.println("You see a staircase going down to the west, and a path going south.");
        Monster gardenImp = new Monster("Imp", 2, 2, 1.1, 13);
        if(Garden_2.visits == 0) {
            timerDelay(500);

            System.out.println("An imp flies down and attacks!");
            combatStart(gardenImp);
        }

        endRoom(Garden_2);
        Garden(Player.currentRoom);
    }

    private static void Garden_3() {
        optionsBorder(Garden_3.getIntroduction());
        timerDelay(1000);

        System.out.println("A walkway leads to the north, and to the east.");
        Monster gardenSkel = new Monster("Skeleton", 2, 2, 1.2, 17);
        if(Garden_3.visits == 0) {
            System.out.println("A skeleton rises from the ground and shuffles towards you.");
            timerDelay(500);

            combatStart(gardenSkel);
        }

        endRoom(Garden_3);
        Garden(Player.currentRoom);
    }

    private static void Garden_4() {
        optionsBorder(Garden_4.getIntroduction());
        timerDelay(1000);

        System.out.println("You see a stone path to a forest in the south, and paths to the north and west.");
        System.out.println("\nWhat do you wish to do?\n");
        Garden_4.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        switch (choice) {
            case 1:
                System.out.println("What direction?");
                optionsBorder("| NORTH | EAST | SOUTH | WEST |");

                String exploreChoice = scan.nextLine().toLowerCase();
                directionFlag = 0;
                while (directionFlag == 0) {
                    if ("east".equals(exploreChoice)) {
                        System.out.println("Can't go there.");
                        directionFlag = 1;
                    } else if ("north".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 2;
                    } else if ("south".equals(exploreChoice)) {
                        directionFlag = 1;
                        Forest_2();
                    } else if ("west".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 3;
                    } else
                        directionFlag = 1;
                }

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

        Garden(Player.currentRoom);
    }


    private static void Forest(int x) {
        switch (x) {
            case 1:
                Forest_1();
                break;
            case 2:
                Forest_2();
                break;
            case 3:
                Forest_3();
                break;
            case 4:
                Forest_4();
                break;
        }
    }

    private static void Forest_1() {
        optionsBorder(Forest_1.getIntroduction());
        timerDelay(1000);

        if(Forest_1.visits == 0) {
            Item forestNet = new Item("Net", 0, 0, .3, false);
            System.out.println("You see a net on the ground.");
            timerDelay(750);
            Inventory.containedItems.add(forestNet);
            System.out.println("Picked up a Net.");
        }


        endRoom(Forest_1);
        Forest(Player.currentRoom);
    }

    private static void Forest_2() {
        optionsBorder(Forest_2.getIntroduction());
        timerDelay(1000);

        Monster forestBat = new Monster("Bat", 2, 2, 1.3, 14);
        if(Forest_2.visits == 0) {
            System.out.println(Forest.getIntroduction());
            System.out.println("You're attacked by some bats, which fly down from the tree.");
            combatStart(forestBat);
        }
        if(Forest_1.visits == 0 && Forest_4.visits == 0)
            System.out.println("You see a garden to the north, a chest to the west and to the south.");
        else if(Forest_1.visits == 0 && Forest_4.visits > 0)
            System.out.println("You see a garden to the north, and a chest to the west.");
        else if(Forest_1.visits > 0 && Forest_4.visits == 0)
            System.out.println("You see a garden to the north, and a chest to the south.");
        else
            System.out.println("You see a garden to the north, and a castle further south.");

        endRoom(Forest_2);
        Forest(Player.currentRoom);
    }

    private static void Forest_3() {
        optionsBorder(Forest_3.getIntroduction());
        timerDelay(1000);
        Door castleG_Entrance = new Door("79ed689e6714525a401b0acf19d2ac5");

        if(Forest_1.visits == 0 && Forest_4.visits == 0)
            System.out.println("You see an object on the ground to the north, a chest to the east, and a gate to the south.");
        else if(Forest_1.visits == 0 && Forest_4.visits > 0)
            System.out.println("You see an object on the ground to the north, and a gate to the south.");
        else if(Forest_1.visits > 0 && Forest_4.visits == 0)
            System.out.println("You see a chest to the east, and a gate to the south.");
        else
            System.out.println("You see an empty chest to the east, and a gate to the south.");

        Monster forestLich = new Monster("Lich", 3, 1, 1.4, 15);
        Monster cannonFodder = new Monster("Skeleton", 1, 1, 1.1, 13);

        if(Forest_3.visits == 0) {
            System.out.println("A skeleton rises from the ground!");
            combatStart(cannonFodder);

            System.out.println("The source of the skeleton materializes before you - a Lich!");
            combatStart(forestLich);
        }

        System.out.println("\nWhat do you wish to do?\n");
        Forest_3.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        switch (choice) {
            case 1:
                System.out.println("What direction?");
                optionsBorder("| NORTH | EAST | SOUTH | WEST |");

                String exploreChoice = scan.nextLine().toLowerCase();
                directionFlag = 0;
                while (directionFlag == 0) {
                    if ("west".equals(exploreChoice)) {
                        System.out.println("Can't go there.");
                        directionFlag = 1;
                    } else if ("north".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 1;
                    } else if ("south".equals(exploreChoice)) {
                        directionFlag = 3;
                        for(Item x: Inventory.containedItems) {
                            if(x.getCode().equals(castleG_Entrance.getLock())) {
                                Inventory.containedItems.remove(x);
                                Player.currentRoom = 1;
                                castleG(Player.currentRoom);
                                break;
                            }
                            else {
                                System.out.println(x.name + " didn't work.");
                                timerDelay(500);
                            }
                        }
                    } else if ("east".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 4;
                    } else
                        directionFlag = 1;
                }

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

        Forest(Player.currentRoom);
    }

    private static void Forest_4() {
        optionsBorder(Forest_4.getIntroduction());
        timerDelay(1000);
        Item castleG_Key = new Item("Castle Key", "79ed689e6714525a401b0acf19d2ac5");

        if(Forest_4.visits == 0) {
            System.out.println("You see a chest.");
            timerDelay(750);
            System.out.println("Inside there was a small key!");
            Inventory.addItems(castleG_Key);
        }
        System.out.println("You see a path to your north and west.");

        endRoom(Forest_4);
        Forest(Player.currentRoom);
    }


    private static void castleG(int x) {
        switch (x) {
            case 1:
                castleG_1();
                break;
            case 2:
                castleG_2();
                break;
            case 3:
                castleG_3();
                break;
            case 4:
                castleG_4();
                break;
        }
    }

    private static void castleG_1() {
        optionsBorder(castleG_1.getIntroduction());
        timerDelay(1000);
        System.out.println("You see a gate to the north, a gate to the west, and the interior castle grounds around you.");

        if(castleG_1.visits == 0) {
            System.out.println(castleG.getIntroduction());
        }

        System.out.println("\nWhat do you wish to do?\n");
        castleG_1.visits++;
        optionsBorder(worldOptions);

        int choice = 0;
        choice = nextInt(choice);
        switch (choice) {
            case 1:
                System.out.println("What direction?");
                optionsBorder("| NORTH | EAST | SOUTH | WEST |");

                String exploreChoice = scan.nextLine().toLowerCase();
                directionFlag = 0;
                while (directionFlag == 0) {
                    if ("west".equals(exploreChoice)) {
                        directionFlag = 1;
                        throneRoom();
                    } else if ("north".equals(exploreChoice)) {
                        directionFlag = 1;
                        Forest_3();
                    } else if ("south".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 3;
                    } else if ("east".equals(exploreChoice)) {
                        directionFlag = 1;
                        Player.currentRoom = 2;
                    } else
                        directionFlag = 1;
                }

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

        castleG(Player.currentRoom);
    }

    private static void castleG_2() {
        optionsBorder(castleG_2.getIntroduction());
        timerDelay(1000);
        Monster eastGuardian = new Monster("Stone Guardian", 2, 2, 1.3, 16);

        if(castleG_2.visits == 0) {
            System.out.println("You see a gate to the west, and castle walls around you, with a statue behind you.");
            timerDelay(500);
            System.out.println("The statue comes to life and attacks!");
            combatStart(eastGuardian);
        } else
            System.out.println("You see a gate to the west, and castle walls around you, a crushed statue behind you.");

        endRoom(castleG_2);
        castleG(Player.currentRoom);
    }

    private static void castleG_3() {
        optionsBorder(castleG_3.getIntroduction());
        timerDelay(1000);

        if(castleG_2.visits == 0) {
            System.out.println("You see a gate to the northwest, and a statue to the northeast.");
        } else
            System.out.println("You see a gate to the northwest, and a crushed statue to the northeast.");

        if(castleG_3.visits == 0){
            HealthPotion majorHealth = new HealthPotion("Major Health Potion", 10);
            Inventory.addItems(majorHealth);
            System.out.println("You find a major health potion!");
            timerDelay(500);
            System.out.println("Added item to inventory.");
        }

        endRoom(castleG_3);
        castleG(Player.currentRoom);
    }

    private static void castleG_4() {
        optionsBorder(castleG_4.getIntroduction());
        timerDelay(1000);

        if(castleG_2.visits == 0 && castleG_3.visits == 0)
            System.out.println("You see an item to the west, and a statue to the north.");
        else if(castleG_2.visits == 0 && castleG_3.visits > 0)
            System.out.println("You see a statue to the north.");
        else if(castleG_2.visits > 0 && castleG_3.visits == 0)
            System.out.println("You see a crushed statue to the north and an item to the west.");
        else
            System.out.println("You see a crushed statue to the north.");

        if(castleG_4.visits == 0) {
            HealthPotion uberHealth = new HealthPotion("Uber Health Potion", 20);
            Inventory.addItems(uberHealth);
            System.out.println("You find an Uber health potion!");
            timerDelay(500);
            System.out.println("Added item to inventory.");
        }
        endRoom(castleG_4);
        castleG(Player.currentRoom);
    }



    private static void throneRoom() {
        Boss stoneGiant = new Boss("Stone Colossus", 10, 2, 12);
        optionsBorder(throneRoom.getIntroduction());

        System.out.println("A stone colossus is perched on a throne in front of you.");
        timerDelay(1500);

        while (Player.HP > 0) {
            timerDelay(250);
            if(stoneGiant.HP <= 0) {
                combatResult(stoneGiant, stoneGiant.xpVal);
                break;
            }

            System.out.println(Artwork.getArtwork(stoneGiant.getName()));
            timerDelay(250);

            optionsBorder("| 1. ATTACK | 2. ITEM |");
            int choice = 0;
            choice = nextInt(choice);

            switch (choice) {
                case 1:
                    stoneGiant(stoneGiant);
                    break;
                case 2:
                    item(stoneGiant);
                    break;
            }
        }
    }

    private static void stoneGiant(Boss stoneGiant) {
        String target = "";

        int bossAttack = (int)((Math.random() * 4) + 1);
        switch (bossAttack) {
            case 1:
                System.out.println("Stone Colossus swings his right arm down towards you.");
                target = "left";
                break;
            case 2:
                System.out.println("Stone Colossus swings his left arm down towards you.");
                target = "right";
                break;
            case 3:
                System.out.println("Stone Colossus winds up an overhead strike.");
                target = "high";
                break;
            case 4:
                System.out.println("Stone Colossus uppercuts.");
                target = "low";
                break;
        }

        timerDelay(500);
        System.out.println("Where do you wish to attack?");

        optionsBorder("| 1. LEFT | 2. RIGHT | 3. HIGH | 4. LOW");
        int choice = 0;
        choice = nextInt(choice);

        switch (choice) {
            case 1:
                attackLeft(target, stoneGiant);
                break;
            case 2:
                attackRight(target, stoneGiant);
                break;
            case 3:
                attackHigh(target, stoneGiant);
                break;
            case 4:
                attackLow(target, stoneGiant);
                break;
        }
    }


    private static void attackLeft(String t, Boss Boss) {
        if("left".equals(t)) {
            System.out.println("You dodge the attack!");
            healthStats(Boss);

            System.out.println("You attack...\n");
            Player.setMakeAttack();

            if(Boss.HP <= 0) {
                return;
            }
            if(Player.HP <= 0) {
                return;
            }
            timerDelay(500);
            if(Player.makeAttack >= Boss.armorClass) {
                Boss.setHealth(Player.damage);
                System.out.println("Your attack hits! " + Player.damage + " Damage.");
                timerDelay(500);
                healthStats(Boss);
                timerDelay(500);
            } else {
                System.out.println("Your attack is blocked.");
            }
        } else {
            timerDelay(750);
            System.out.println("You're hit! " + Boss.damage + " Damage.");
            Player.setHealth(-Boss.damage);
            timerDelay(500);
            healthStats(Boss);
            timerDelay(500);
        }
    }

    private static void attackRight(String t, Boss Boss) {
        if("right".equals(t)) {
            System.out.println("You dodge the attack!");
            healthStats(Boss);

            System.out.println("You attack...\n");
            Player.setMakeAttack();

            if(Boss.HP <= 0) {
                return;
            }
            if(Player.HP <= 0) {
                return;
            }
            timerDelay(500);
            if(Player.makeAttack >= Boss.armorClass) {
                Boss.setHealth(Player.damage);
                System.out.println("Your attack hits! " + Player.damage + " Damage.");
                timerDelay(500);
                healthStats(Boss);
                timerDelay(500);
            } else {
                System.out.println("Your attack is blocked.");
            }
        } else {
            timerDelay(750);
            System.out.println("You're hit! " + Boss.damage + " Damage.");
            Player.setHealth(-Boss.damage);
            timerDelay(500);
            healthStats(Boss);
            timerDelay(500);
        }
    }

    private static void attackHigh(String t, Boss Boss) {
        if("high".equals(t)) {
            System.out.println("You dodge the attack!");
            healthStats(Boss);

            System.out.println("You attack...\n");
            Player.setMakeAttack();

            if(Boss.HP <= 0) {
                return;
            }
            if(Player.HP <= 0) {
                return;
            }
            timerDelay(500);
            if(Player.makeAttack >= Boss.armorClass) {
                Boss.setHealth(Player.damage);
                System.out.println("Your attack hits! " + Player.damage + " Damage.");
                timerDelay(500);
                healthStats(Boss);
                timerDelay(500);
            } else {
                System.out.println("Your attack is blocked.");
            }
        } else {
            timerDelay(750);
            System.out.println("You're hit! " + Boss.damage + " Damage.");
            Player.setHealth(-Boss.damage);
            timerDelay(500);
            healthStats(Boss);
            timerDelay(500);
        }
    }

    private static void attackLow(String t, Boss Boss) {
        if("low".equals(t)) {
            System.out.println("You dodge the attack!");
            healthStats(Boss);

            System.out.println("You attack...\n");
            Player.setMakeAttack();

            if(Boss.HP <= 0) {
                return;
            }
            if(Player.HP <= 0) {
                return;
            }
            timerDelay(500);
            if(Player.makeAttack >= Boss.armorClass) {
                Boss.setHealth(Player.damage);
                System.out.println("Your attack hits! " + Player.damage + " Damage.");
                timerDelay(500);
                healthStats(Boss);
                timerDelay(500);
            } else {
                System.out.println("Your attack is blocked.");
            }
        } else {
            timerDelay(750);
            System.out.println("You're hit! " + Boss.damage + " Damage.");
            Player.setHealth(-Boss.damage);
            timerDelay(500);
            healthStats(Boss);
            timerDelay(500);
        }
    }

}
