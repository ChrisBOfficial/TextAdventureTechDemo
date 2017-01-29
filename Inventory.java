import java.util.ArrayList;

class Inventory {

    static ArrayList<Item> items = new ArrayList<>();

    static void addItems(Item x) {
        items.add(x);
    }

    static void printItems() {
        int c = 0;
        for(Item x : items) {
            c++;
            System.out.print(c + ": " + x + " ");
        }
        System.out.println("");
    }

}
