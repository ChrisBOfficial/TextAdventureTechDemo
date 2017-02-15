import java.util.ArrayList;

class Inventory {

    static ArrayList<Item> playerItems = new ArrayList<>();

    static void addItems(Item x) {
        playerItems.add(x);
    }

    static void printItems() {
        int c = 0;
        for(Item x : playerItems) {
            c++;
            System.out.print(c + ": " + x.name + " ");
        }
        System.out.println("");
        TextAdventure.timerDelay(750);
    }

}
