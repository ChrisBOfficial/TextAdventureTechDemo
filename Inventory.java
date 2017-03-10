import java.util.ArrayList;

class Inventory {

    static ArrayList<Item> containedItems = new ArrayList<>();

    static void addItems(Item x) {
        containedItems.add(x);
    }

    static void printItems() {
        int c = 0;
        for(Item x : containedItems) {
            c++;
            System.out.print(c + ": " + x.name + " ");
        }
        System.out.println("");
        TextAdventure.timerDelay(750);
    }

}
