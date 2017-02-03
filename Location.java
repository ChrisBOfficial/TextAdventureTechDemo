class Location {
    String name;
    private String introduction;

    public String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    Location(String n, String i) {
        n = name;
        i = introduction;
    }

    Location() {
        this("Void", "An empty void. How'd you get here?");
    }

}

class Room extends Location {
    String roomDescription;
    private int roomLoc;

    public void chooseDirection(String exploreChoice) {
        if (roomLoc == 1) {
            while (TextAdventure.directionFlag == 0) {
                if ("north".equals(exploreChoice) || "west".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    TextAdventure.worldChoice(1);
                } else if ("east".equals(exploreChoice)) {
                    Player.currentRoom = 2;
                } else if ("south".equals(exploreChoice)) {
                    Player.currentRoom = 3;
                }
            }
        } else if (roomLoc == 2) {
            while (TextAdventure.directionFlag == 0) {
                if ("north".equals(exploreChoice) || "east".equals(exploreChoice)) {
                    System.out.println("Can't go there.");
                    TextAdventure.worldChoice(1);
                } else if ("west".equals(exploreChoice)) {
                    Player.currentRoom = 1;
                } else if ("south".equals(exploreChoice)) {
                    Player.currentRoom = 4;
                }
            }
        }
    }

    Room(String n, int r) {
        name = n + " " + name;
        roomLoc = r;
    }

}
